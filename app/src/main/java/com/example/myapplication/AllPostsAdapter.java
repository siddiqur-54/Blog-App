package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AllPostsAdapter extends ArrayAdapter<Post> {

    private Activity context;
    private List<Post> postList;

    public AllPostsAdapter(Activity context,List<Post> postList) {
        super(context,R.layout.all_posts_layout,postList);
        this.context = context;
        this.postList = postList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater=context.getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.all_posts_layout,null,true);

        Post allPosts=postList.get(position);

        TextView nameTextView=view.findViewById(R.id.nameTextViewId);
        TextView insTextView=view.findViewById(R.id.insTextViewId);
        TextView dateTextView=view.findViewById(R.id.dateTextViewId);
        TextView postTextView=view.findViewById(R.id.postTextViewId);

        nameTextView.setText(allPosts.getName());
        insTextView.setText(allPosts.getIns());
        dateTextView.setText(allPosts.getDate());
        postTextView.setText(allPosts.getPost());

        return view;
    }
}
