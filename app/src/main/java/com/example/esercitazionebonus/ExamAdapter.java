package com.example.esercitazionebonus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ViewHolder> {
    private List<Exam> exams;
    private ClickListener delete;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View serviceCell = inflater.inflate(R.layout.exam_cell, parent, false);

        return new ViewHolder(serviceCell);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exam exam = exams.get(position);
        holder.name.setText(exam.getName());
        holder.mark.setText(String.valueOf(exam.getMark()));
        holder.cfu.setText(String.valueOf(exam.getCFU()));

        holder.delete.setOnClickListener(listener -> {
            delete.deleteExam(position);
        });
    }

    @Override
    public int getItemCount() {
        return exams.size();
    }

    public ExamAdapter(List<Exam> exams, ClickListener delete) {
        this.exams = exams;
        this.delete = delete;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mark, name, cfu;
        public ImageView delete;

        public ViewHolder(View itemView) {
            super(itemView);

            mark = itemView.findViewById(R.id.textView6);
            name = itemView.findViewById(R.id.textView7);
            delete = itemView.findViewById(R.id.imageView3);
            cfu = itemView.findViewById(R.id.textView10);

        }

    }
}