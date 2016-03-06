package com.scaperow.metaler;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.scaperow.metaler.dummy.TaskContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnTaskFragmentInteractionListener}
 * interface.
 */
public class TaskFragment extends Fragment {

    SwipeRefreshLayout taskSwiper;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnTaskFragmentInteractionListener mListener;
    private SwipeRefreshLayout taskRefreshLayout;
    View view;
    CoordinatorLayout coordinator;
    List<TaskContent.TaskItem> tasks = new ArrayList<TaskContent.TaskItem>();
    TaskRecyclerViewAdapter adapter = null;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TaskFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TaskFragment newInstance(int columnCount) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    private RefreshTaskHandler handler = new RefreshTaskHandler();

    class RefreshTaskHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            taskSwiper.setRefreshing(false);
            switch (msg.what) {
                case 0:
                    Snackbar.make(view, "加载完成", Snackbar.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                    break;

                default:
                    Bundle bundle = msg.getData();
                    String message = bundle.getString("message");
                    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private void fetch() {

        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String URL = Config.domain + "/order";
                // pass second argument as "null" for GET requests
                JsonObjectRequest req = new JsonObjectRequest(URL, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.getInt("errno") == 0) {
                                        JSONArray array = response.getJSONArray("data");
//                                               tasks.addAll(JSON.parseArray(response.getJSONArray("data"),TaskContent.TaskItem.class));
                                        for (int i = 0; i < array.length(); i++) {

                                            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                                            TaskContent.TaskItem item = JSON.parseObject(array.get(i).toString(), TaskContent.TaskItem.class);
                                            tasks.add(0, item);
                                        }

                                        handler.sendEmptyMessage(0);
                                    } else {
                                        Message message = handler.obtainMessage(-1);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("message", response.getString("errmsg"));
                                        message.setData(bundle);
                                        handler.sendMessage(message);
                                    }

                                    VolleyLog.v("Response:%n %s", response.toString(4));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Message message = handler.obtainMessage(-1);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("message", "发生错误");
                                    message.setData(bundle);
                                    handler.sendMessage(message);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        handler.sendEmptyMessage(-1);
                        taskSwiper.setRefreshing(false);
                    }
                });
                requestQueue.add(req);
            }
        }).start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_task_list, container, false);
        taskSwiper = (SwipeRefreshLayout) view.findViewById(R.id.taskRefreshLayout);
        taskSwiper.setProgressBackgroundColorSchemeResource(android.R.color.white);
        taskSwiper.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        taskSwiper.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.taskList);
        taskSwiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               fetch();
            }
        });

        Context context = view.getContext();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

//            recyclerView.addItemDecoration(new DividerItemDecoration(
//                getActivity(), DividerItemDecoration.HORIZONTAL_LIST));
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        adapter = new
                TaskRecyclerViewAdapter(tasks, mListener);
//        recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        taskSwiper.setRefreshing(true);
        fetch();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTaskFragmentInteractionListener) {
            mListener = (OnTaskFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTaskFragmentInteractionListener");
        }


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnTaskFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(TaskContent.TaskItem item);
    }
}
