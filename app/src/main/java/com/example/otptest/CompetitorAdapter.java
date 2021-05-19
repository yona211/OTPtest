package com.example.otptest;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class CompetitorAdapter extends ArrayAdapter<Competitor> {
    Context context;
    List<Competitor> objects;
    public CompetitorAdapter(Context context, int resource, int textViewResourceId, List<Competitor> objects) {
        super(context, resource, textViewResourceId, objects);

        this.context = context;
        this.objects = objects;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_competitor_second,parent,false);
        TextView tvFirstName = (TextView)view.findViewById(R.id.tvFirstName);
        TextView tvLastName = (TextView)view.findViewById(R.id.tvLastName);
        ImageView ivCompetitorSelfie = (ImageView)view.findViewById(R.id.ivCompetitorSelfie);
        Competitor temp = objects.get(position);
        ivCompetitorSelfie.setImageBitmap(temp.getBitmap());
        tvFirstName.setText(String.valueOf(temp.getFirstName()));
        tvLastName.setText(temp.getLastName());
        return view;
    }
}
