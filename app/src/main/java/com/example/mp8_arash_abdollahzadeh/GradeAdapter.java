package com.example.mp8_arash_abdollahzadeh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.GradeViewHolder> {

    private Context mCtx;
    private List<Grade> gradeList;

    public GradeAdapter(Context mCtx, List<Grade> gradeList) {
        this.mCtx = mCtx;
        this.gradeList = gradeList;
    }

    @Override
    public GradeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_layout, null);
        GradeViewHolder holder = new GradeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(GradeViewHolder holder, int position) {
        Grade grade = gradeList.get(position);

        holder.name.setText(String.valueOf(grade.getStudent_name()));
        holder.course.setText(grade.getcourse_name());
        holder.grade.setText(grade.getgrade());
    }

    @Override
    public int getItemCount() {
        return gradeList.size();
    }

    class GradeViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView course;
        TextView grade;

        public GradeViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_list);
            course = itemView.findViewById(R.id.course_list);
            grade = itemView.findViewById(R.id.course_grade_list);
        }
    }
}
