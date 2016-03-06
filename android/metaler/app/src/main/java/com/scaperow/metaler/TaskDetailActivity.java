package com.scaperow.metaler;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TaskDetailActivity extends AppCompatActivity {
    Button button_orders = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = getApplicationContext();
        setContentView(R.layout.activity_task_detail);
        button_orders = (Button) findViewById(R.id.button_orders);
        button_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_orders.setVisibility(View.INVISIBLE);

                Snackbar.make(getWindow().getDecorView(), "接单成功", Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
