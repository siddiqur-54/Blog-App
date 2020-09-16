package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AllProfilesAdapter extends ArrayAdapter<Profile> {

    private Activity context;
    private List<Profile> profileList;

    public AllProfilesAdapter(Activity context,List<Profile> profileList) {
        super(context,R.layout.all_profiles_layout,profileList);
        this.context = context;
        this.profileList = profileList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater=context.getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.all_profiles_layout,null,true);

        Profile profile=profileList.get(position);

        TextView nameTextView=view.findViewById(R.id.nameTextViewId);
        TextView insTextView=view.findViewById(R.id.insTextViewId);

        nameTextView.setText(profile.getName());
        insTextView.setText(profile.getIns());

        return view;
    }
}
