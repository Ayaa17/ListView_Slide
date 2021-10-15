package com.example.listviewslide;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.List;

public class MessageListAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    private LayoutInflater inflater;

    public MessageListAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<String> lists) {
        list = lists;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder =null;
        ZQview slideView = (ZQview)convertView;
        if(slideView == null){
            View itemView = inflater.inflate(R.layout.message_list_item, null);
            slideView = new ZQview(context);
            slideView.setContentView(itemView);
            viewHolder = new ViewHolder(slideView);
            slideView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)slideView.getTag();
        }
        slideView.shrink();
        viewHolder.tv_message_item_content.setText((list.get(position)));
        viewHolder.deleteHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });
        return slideView;
    }

    static class ViewHolder{
        public TextView tv_message_item_content ;
        public TextView tv_message_item_time ;
        public ViewGroup deleteHolder;
        ViewHolder(View view){
            tv_message_item_content = (TextView)view.findViewById(R.id.tv_message_item_content);
            tv_message_item_time =(TextView)view.findViewById(R.id.tv_message_item_time);

            deleteHolder = (ViewGroup)view.findViewById(R.id.holder);
        }
    }
}
