package com.example.wenqixian.myfirstapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by tom on 2/19/16.
 */
public class RecentItemAdapter extends BaseAdapter{
    List<RecentItemDetails> dataList;
    Context context;

    private static LayoutInflater inflater=null;

    public RecentItemAdapter(Activity mainActivity, List<RecentItemDetails> dataList) {
        // TODO Auto-generated constructor stub
        this.dataList =dataList;
        context= mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return dataList.get(position).id;
    }

    public class Holder
    {
        TextView title;
        TextView rating;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.d("hey", Integer.toString(dataList.size()));
        // TODO Auto-generated method stub
        Holder ridView=new Holder();
        View rowView;
//        rowView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
        rowView = inflater.inflate(R.layout.recent_item_option_layout, parent, false);
        ridView.title=(TextView) rowView.findViewById(R.id.recent_item_title);
        ridView.rating=(TextView) rowView.findViewById(R.id.recent_item_rating);
        ridView.title.setText(dataList.get(position).title);
        ridView.rating.setText(Integer.toString(dataList.get(position).critics_score));

        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p = new Intent(v.getContext(), MainActivity.class);
                Log.d("Jumping", v.getContext().toString());
                v.getContext().startActivity(p);
            }
        });
        Log.d("hey", dataList.get(position).title);
        return rowView;
    }

    public void dataComing(List<RecentItemDetails> inList) {
        dataList = inList;
        this.notifyDataSetChanged();
    }

}