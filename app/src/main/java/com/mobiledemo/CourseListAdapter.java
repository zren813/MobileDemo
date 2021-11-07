package com.mobiledemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.MyViewHolder>{
    List<String> courseNo;
    List<String> courseName;
    Context context;
    public CourseListAdapter(Context ct, List<String> courseNo, List<String> courseName) {
        context = ct;
        this.courseNo = courseNo;
        this.courseName = courseName;
    }
    @NonNull
    @Override
    public CourseListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.course_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseListAdapter.MyViewHolder holder, int position) {
        holder.courseNoView.setText(courseNo.get(position));
        holder.courseNameView.setText(courseName.get(position));
    }

    @Override
    public int getItemCount() {
        return courseNo.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView courseNoView;
        TextView courseNameView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            courseNoView = itemView.findViewById(R.id.courseNoView);
            courseNameView = itemView.findViewById(R.id.courseNameTextView);
        }
    }
}
