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
    List<String> professorName;
    List<String> regNo;
    Context context;
    private ItemClickListener listener;
    public CourseListAdapter(Context ct, List<String> courseNo, List<String> courseName, List<String> professorName, List<String> regNo) {
        context = ct;
        this.courseNo = courseNo;
        this.courseName = courseName;
        this.professorName = professorName;
        this.regNo = regNo;
    }
    @NonNull
    @Override
    public CourseListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.course_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.courseNoView.setText(courseNo.get(position));
        holder.courseNameView.setText(courseName.get(position));
        holder.professorNameView.setText(professorName.get(position));
    }

    @Override
    public int getItemCount() {
        return courseNo.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView courseNoView;
        TextView courseNameView;
        TextView professorNameView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            courseNoView = itemView.findViewById(R.id.courseNoView);
            courseNameView = itemView.findViewById(R.id.courseNameTextView);
            professorNameView = itemView.findViewById(R.id.professorView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }
    }
    void setClickListener(ItemClickListener listener) {
        this.listener = listener;
    }
}
