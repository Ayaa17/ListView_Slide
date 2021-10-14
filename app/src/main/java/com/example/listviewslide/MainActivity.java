package com.example.listviewslide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ListViewCompat;

import android.app.Activity;
import android.os.Bundle;
import android.util.AndroidException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.listviewslide.SlideView.OnSlideListener.SLIDE_STATUS_ON;

public class MainActivity extends Activity {


    private ZQListView lv_message_center;
    private List<String> list = new ArrayList<>();
    private MessageListAdapter messageListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        TextView tv_titile
        TextView tv_message_hide_null = findViewById(R.id.tv_message_hide_null);
        lv_message_center = findViewById(R.id.lv_message_center);
//        tv_tiite_order

        initView();

    }
    private void initView(){
        for (int i = 0;i<20;i++){
            list.add("123");
        }

        messageListAdapter = new MessageListAdapter(MainActivity.this);
        messageListAdapter.setData(list);
        lv_message_center.setAdapter(messageListAdapter);
        lv_message_center.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "第n個: "+position, Toast.LENGTH_SHORT);
            }
        });
    }

}