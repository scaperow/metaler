package com.scaperow.metaler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scaperow.metaler.TaskFragment.OnTaskFragmentInteractionListener;
import com.scaperow.metaler.dummy.TaskContent;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link TaskFragment.OnTaskFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder> {

    private final List<TaskContent.TaskItem> mValues;
    private final OnTaskFragmentInteractionListener mListener;

    public TaskRecyclerViewAdapter(List<TaskContent.TaskItem> items, OnTaskFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_task, parent, false);
        return new ViewHolder(view);
    }

    SimpleDateFormat formatter =
            new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).title);
        holder.mCreateTimeView.setText(formatter.format((mValues.get(position).create_time)));
        holder.mPriceTimeView.setText(mValues.get(position).price+"元");

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public final TextView mCreateTimeView;
        public final TextView mPriceTimeView;
        public TaskContent.TaskItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.task_title);
            mCreateTimeView = (TextView) view.findViewById(R.id.task_create_time);
            mPriceTimeView = (TextView) view.findViewById(R.id.task_price);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
