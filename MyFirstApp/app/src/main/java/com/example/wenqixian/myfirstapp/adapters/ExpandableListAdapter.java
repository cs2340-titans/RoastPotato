package com.example.wenqixian.myfirstapp.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.wenqixian.myfirstapp.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by He on 3/12/2016.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context ctx;
    private HashMap<String, List<String>> Movies_Category;
    private List<String> Movies_List;

    public ExpandableListAdapter(Context ctx, HashMap<String, List<String>> Movies_Category, List<String> Movies_List) {
        this.ctx = ctx;
        this.Movies_Category = Movies_Category;
        this.Movies_List = Movies_List;
    }

    @Override
    public int getGroupCount() {
        return Movies_List.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return Movies_Category.get(Movies_List.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return Movies_List.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return Movies_Category.get(Movies_List.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parentview) {
        String group_title = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.parent_layout, parentview, false);
        }
        TextView parent_textview = (TextView)convertView.findViewById(R.id.parent_text);
        parent_textview.setTypeface(null, Typeface.BOLD);
        parent_textview.setText(group_title);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parentview) {
        String child_title = (String)getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_layout, parentview, false);
        }
        TextView child_textview = (TextView) convertView.findViewById(R.id.child_txt);
        child_textview.setText(child_title);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
