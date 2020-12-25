package com.rumato.lab4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rumato.lab4.DB.Student;
import com.rumato.lab4.DB.StudentDatabase;

import java.util.ArrayList;
import java.util.List;

public class Lab4Activity extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD_STUDENT = 2025;
    public static final int REQUEST_EDIT_STUDENT = 2035;

    private RecyclerView rvStudents;
    private FloatingActionButton fabAddStudent;

    private StudentDatabase dbStudents;
    private Lab4StudentAdapter adapter;
    private List<Student> lstStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab4);

        setTitle(getString(R.string.welcome_lab4, getClass().getSimpleName()));

        rvStudents = findViewById(R.id.rv_students);
        fabAddStudent = findViewById(R.id.fab_add_student);

        dbStudents = StudentDatabase.getInstance(this);

        lstStudents = dbStudents.studentDao().getAllStudents();
//        lstStudents.add(new Student("McAdams", "Rachel", "Anne",
//                null, null));
//        lstStudents.add(new Student("Stone", "Emily", "Jean",
//                null, null));
//        lstStudents.add(new Student("Lawrence", "Jennifer", "Shrader",
//                null, null));
        adapter = new Lab4StudentAdapter();

        rvStudents.setLayoutManager(new LinearLayoutManager(this));
        rvStudents.setHasFixedSize(true);
        rvStudents.setAdapter(adapter);

        adapter.setStudents(lstStudents);

        adapter.setOnLongClickListener(new Lab4StudentAdapter.OnLingClickListener() {
            @Override
            public void onLongClick(Student student, int position) {
                Intent intent = new Intent(
                        Lab4Activity.this, Lab4AddEditActivity.class);
                intent.putExtra(Lab4AddEditActivity.EXTRA_POSITION, position);
                intent.putExtra(Lab4AddEditActivity.EXTRA_SURNAME, student.getSurname());
                intent.putExtra(Lab4AddEditActivity.EXTRA_NAME, student.getName());
                intent.putExtra(Lab4AddEditActivity.EXTRA_PATRONYMIC, student.getPatronymic());
                intent.putExtra(
                        Lab4AddEditActivity.EXTRA_PHOTO_PATH,
                        student.getFullPhotoPath());
                intent.putExtra(
                        Lab4AddEditActivity.EXTRA_SMALL_PHOTO_PATH,
                        student.getReducedPhotoPath());
                startActivityForResult(intent, REQUEST_EDIT_STUDENT);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(
                    @NonNull RecyclerView recyclerView,
                    @NonNull RecyclerView.ViewHolder viewHolder,
                    @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                dbStudents.studentDao().deleteStudent(lstStudents.get(position));
                lstStudents.clear();
                lstStudents = dbStudents.studentDao().getAllStudents();
                adapter.setStudents(lstStudents);
            }
        }).attachToRecyclerView(rvStudents);

        fabAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent
                        (Lab4Activity.this, Lab4AddEditActivity.class);

                startActivityForResult(intent, REQUEST_CODE_ADD_STUDENT );
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_ADD_STUDENT && resultCode == RESULT_OK) {
            if (data != null) {
                addStudent(data);
            }
        } else if (requestCode == REQUEST_EDIT_STUDENT && resultCode == RESULT_OK) {
            if (data != null) {
                updateStudent(data);
            }
        }
        else {
            Toast.makeText(
                    this, "Student wasn't added/updated.", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void addStudent(Intent data) {
        String surname = data.getStringExtra(Lab4AddEditActivity.EXTRA_SURNAME);
        String name = data.getStringExtra(Lab4AddEditActivity.EXTRA_NAME);
        String patronymic = data.getStringExtra(Lab4AddEditActivity.EXTRA_PATRONYMIC);
        String fullPhotoPath = data.getStringExtra(Lab4AddEditActivity.EXTRA_PHOTO_PATH);
        String reducedPhotoPath = data.getStringExtra(
                Lab4AddEditActivity.EXTRA_SMALL_PHOTO_PATH);
        Student student = new Student(
                surname, name, patronymic, fullPhotoPath, reducedPhotoPath);
        dbStudents.studentDao().insertStudent(student);
        lstStudents.clear();
        lstStudents = dbStudents.studentDao().getAllStudents();
        adapter.setStudents(lstStudents);
        Toast.makeText(this, "Student was added.", Toast.LENGTH_SHORT).show();
    }

    private void updateStudent(Intent data) {
        String surname = data.getStringExtra(Lab4AddEditActivity.EXTRA_SURNAME);
        String name = data.getStringExtra(Lab4AddEditActivity.EXTRA_NAME);
        String patronymic = data.getStringExtra(Lab4AddEditActivity.EXTRA_PATRONYMIC);
        String fullPhotoPath = data.getStringExtra(
                Lab4AddEditActivity.EXTRA_PHOTO_PATH);
        String reducedPhotoPath = data.getStringExtra(
                Lab4AddEditActivity.EXTRA_SMALL_PHOTO_PATH);
        Student student = new Student(
                surname, name, patronymic, fullPhotoPath, reducedPhotoPath);
        int position = data.getIntExtra(Lab4AddEditActivity.EXTRA_POSITION, -1);
        dbStudents.studentDao().deleteStudent(lstStudents.get(position));
        dbStudents.studentDao().insertStudent(student);
        lstStudents.clear();
        lstStudents = dbStudents.studentDao().getAllStudents();
        adapter.setStudents(lstStudents);
        Toast.makeText(this, "Student was updated.", Toast.LENGTH_SHORT).show();
    }
}
