package com.example.smartwatch.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.smartwatch.R;

import java.util.ArrayList;

public class TraineeListAdapter extends ArrayAdapter<Trainee> {

    private ArrayList<Trainee> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;

    }

    public TraineeListAdapter(ArrayList<Trainee> data, Context context) {
        super(context, R.layout.trainee_list_row, data);
        this.dataSet = data;
        this.mContext=context;

    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.user_list_row, parent, false);
            viewHolder.txtName = convertView.findViewById(R.id.user_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        lastPosition = position;

        Trainee trainee = dataSet.get(position);
        viewHolder.txtName.setText(trainee.getName());


        // Return the completed view to render on screen
        return convertView;
    }

    public ArrayList<Trainee> getDataSet() {
        return dataSet;
    }

    public void setDataSet(ArrayList<Trainee> dataSet) {
        this.dataSet = dataSet;
    }
}
