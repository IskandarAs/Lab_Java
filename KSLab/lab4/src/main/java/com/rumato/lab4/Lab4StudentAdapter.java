package com.rumato.lab4;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rumato.lab4.DB.Student;

import java.util.ArrayList;
import java.util.List;

public class Lab4StudentAdapter extends RecyclerView.Adapter<Lab4StudentAdapter.StudentViewHolder> {

    private List<Student> listStudents = new ArrayList<>();
    private OnLingClickListener listener;

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lab4_student_item, parent, false);
        return new StudentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = listStudents.get(position);
        String info = student.getSurname() + " " + student.getName() + " " + student.getPatronymic();
        holder.tvPosition.setText(String.format("%d) ", position));
        holder.tvPersonInfo.setText(info);

        if (student.getReducedPhotoPath() != null) {
            holder.ivPhoto.setImageBitmap(BitmapFactory.decodeFile(student.getReducedPhotoPath()));
        } else {
            holder.ivPhoto.setImageResource(R.drawable.ic_person);
        }
    }

    @Override
    public int getItemCount() {
        return listStudents.size();
    }

    public void setStudents(List<Student> listStudents) {
        this.listStudents = listStudents;
        notifyDataSetChanged();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPosition;
        private TextView tvPersonInfo;
        private ImageView ivPhoto;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPosition = itemView.findViewById(R.id.tv_position);
            tvPersonInfo = itemView.findViewById(R.id.tv_person_info);
            ivPhoto = itemView.findViewById(R.id.iv_image);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onLongClick(listStudents.get(position), position);
                    }
                    return false;
                }
            });
        }
    }

    public interface OnLingClickListener {
        void onLongClick(Student student, int position);
    }

    public void setOnLongClickListener(OnLingClickListener listener) {
        this.listener = listener;
    }
}
