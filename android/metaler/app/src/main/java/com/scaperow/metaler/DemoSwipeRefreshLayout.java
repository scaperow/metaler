package com.scaperow.metaler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DemoSwipeRefreshLayout extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    private List<String> datas = new ArrayList<String>();//lis的数据

    private ListView listView = null;

    private SwipeRefreshLayout refresh_layout = null;//刷新控件

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_demo_swipe_refresh_layout);
        listView  = (ListView) this.findViewById(R.id.listview);
        refresh_layout = (SwipeRefreshLayout) this.findViewById(R.id.refresh_layout);
//        refresh_layout.setColorScheme(R.color.green, R.color.gray, R.color.blue_50, R.color.light_white);//设置跑动的颜色值
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, datas);
        listView.setAdapter(adapter);
        for (int i = 0; i < 30; i++) {
            datas.add("item:"+i);
        }
        adapter.notifyDataSetChanged();
        refresh_layout.setOnRefreshListener(this);//设置下拉的监听
    }

    @Override
    public void onRefresh() {
        new Thread(new Runnable() {//下拉触发的函数，这里是谁1s然后加入一个数据，然后更新界面
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                datas.add(0,"item:refresh...");
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    private MyHandler handler = new MyHandler();

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    refresh_layout.setRefreshing(false);
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    }

}
