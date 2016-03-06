package com.scaperow.metaler.dummy;


import java.util.Date;

/**
 * Created by Ben on 2016/3/1.
 */
public class TaskContent {
    public static class TaskItem {

        public int id;
        public String title;
        public String order_number;
        public String releaser;
        public String reciver;
        public String describe;
        public float price;
        public Date release_time;
        public Date close_time;
        public int state;
        public AreaContent.AreaItem area_id;
        public Date create_time;

    }
}
