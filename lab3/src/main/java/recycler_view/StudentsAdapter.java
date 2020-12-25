package recycler_view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rumato.lab3.R;

import java.util.ArrayList;
import java.util.List;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.StudentHolder> {
    private List<Student> alStudents = new ArrayList<>();
    private OnLongClickListener listener;

    @NonNull
    @Override
    public StudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lab3_student_item, parent, false);
        return new StudentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentHolder holder, int position) {
        Student currentStudent = alStudents.get(position);
        String surname = currentStudent.getSurname();
        String name = currentStudent.getName();
        String patronymic = currentStudent.getPatronymic();

        String studentInfo = position + ") " + surname + " " + name + " " + patronymic;
        holder.tvStudentInfo.setText(studentInfo);
    }

    @Override
    public int getItemCount() {
        return alStudents.size();
    }

    public void setStudents(List<Student> alStudents) {
        this.alStudents = alStudents;
        notifyDataSetChanged();
    }

    class StudentHolder extends RecyclerView.ViewHolder {
        private TextView tvStudentInfo;

        public StudentHolder(@NonNull final View itemView) {
            super(itemView);
            tvStudentInfo = itemView.findViewById(R.id.tv_student);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onLongClick(alStudents.get(position), position);
                    }
                    return true;
                }
            });
        }
    }

    public interface OnLongClickListener {
        void onLongClick(Student student, int position);
    }

    public void setOnLongClickListener(OnLongClickListener listener) {
        this.listener = listener;
    }
}
