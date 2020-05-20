package com.example.smartwatch.Model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.smartwatch.R;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter {

    Activity activity;
    List<User> users;
    LayoutInflater inflater;

    //short to create constructer using command+n for mac & Alt+Insert for window


    public CustomAdapter(Activity activity) {
        this.activity = activity;
    }

    public CustomAdapter(Activity activity, List<User> users) {
        this.activity   = activity;
        this.users      = users;

        inflater        = activity.getLayoutInflater();
    }


    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;

        if (view == null){

            view = inflater.inflate(R.layout.user_list_row, viewGroup, false);

            holder = new ViewHolder();

            holder.tvUserName = (TextView)view.findViewById(R.id.user_name);
            holder.checkBox = (CheckBox) view.findViewById(R.id.checkBox);

            view.setTag(holder);
        }else
            holder = (ViewHolder)view.getTag();

        User user = users.get(i);

        holder.tvUserName.setText(user.getName());

        if (user.isSelected())
            holder.checkBox.setChecked(true);

        else
            holder.checkBox.setChecked(false);

        return view;

    }

    public void updateRecords(List<User> users){
        this.users = users;

        notifyDataSetChanged();
    }

    class ViewHolder{

        TextView tvUserName;
        CheckBox checkBox;

    }
}
