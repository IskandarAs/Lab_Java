package com.rumato.lab3.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rumato.lab3.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import Cash.StudentsCash;
import recycler_view.Student;
import recycler_view.StudentsAdapter;

import static java.util.Collections.sort;

public class Lab3Activity extends AppCompatActivity {
    public static final int EDIT_NOTE_REQUEST = 1433;
    private static final int ADD_NOTE_REQUEST = 1432;

    public static final String PARCELABLE_LIST_KEY = "com.rumato.lab3.parcelable_list_key";

    private final StudentsCash cashStudents = StudentsCash.getInstance();

    private RecyclerView rvStudents;
    private FloatingActionButton fabAddStudent;
    private StudentsAdapter adapter;

//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putParcelableArrayList(PARCELABLE_LIST_KEY, alStudents);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        alStudents.clear();
//        alStudents = savedInstanceState.getParcelableArrayList(PARCELABLE_LIST_KEY);
//        adapter.setStudents(alStudents);
//    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab3);

        setTitle(getString(R.string.welcome_lab3, getClass().getSimpleName()));

        rvStudents = findViewById(R.id.rv_students);
        fabAddStudent = findViewById(R.id.fab_add_student);

        rvStudents.setLayoutManager(new LinearLayoutManager(this));
        rvStudents.setHasFixedSize(true);

        if (cashStudents.getStudents().size() == 0) {
            cashStudents.addStudent(new Student("Clooney", "George", "Timothy"));
            cashStudents.addStudent(new Student("Cooper", "Bradley", "Charles"));
            cashStudents.addStudent(new Student("Shaykhlislamova", "Irina", "Valeryevna"));
            cashStudents.addStudent(new Student("Lawrence", "Jennifer", "Shrader"));
            cashStudents.addStudent(new Student("Anderson", "Bob", "Hays"));
        }

        adapter = new StudentsAdapter();
        rvStudents.setAdapter(adapter);
        cashStudents.sortStudents();
        adapter.setStudents(cashStudents.getStudents());

        adapter.setOnLongClickListener(new StudentsAdapter.OnLongClickListener() {
            @Override
            public void onLongClick(Student student, int position) {
                Intent intent = new Intent
                        (Lab3Activity.this, Lab3AddEditActivity.class);
                intent.putExtra(Lab3AddEditActivity.EXTRA_SURNAME, student.getSurname());
                intent.putExtra(Lab3AddEditActivity.EXTRA_NAME, student.getName());
                intent.putExtra(Lab3AddEditActivity.EXTRA_PATRONYMIC, student.getPatronymic());
                intent.putExtra(Lab3AddEditActivity.EXTRA_POSITION, position);
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });

        fabAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        Lab3Activity.this, Lab3AddEditActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Student student = cashStudents.getStudents().get(position);
                cashStudents.removeStudent(student);
                adapter.setStudents(cashStudents.getStudents());
            }
        }).attachToRecyclerView(rvStudents);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            addStudent(data);
            cashStudents.sortStudents();
            adapter.setStudents(cashStudents.getStudents());
            Toast.makeText(this,
                    "Student've been successfully added.", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int position = data.getIntExtra(Lab3AddEditActivity.EXTRA_POSITION, -1);
            Log.i("MyResult", "pos 3: " + position);
            if (position == -1) {
                Toast.makeText(this,
                        "Student can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            updateStudent(data, position);
            cashStudents.sortStudents();
            adapter.setStudents(cashStudents.getStudents());

            Toast.makeText(this,
                    "Student've been successfully updated.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,
                    "Student haven't been added.", Toast.LENGTH_SHORT).show();
        }
    }

    private void addStudent(@Nullable Intent data) {
        String surname = data.getStringExtra(Lab3AddEditActivity.EXTRA_SURNAME);
        String name = data.getStringExtra(Lab3AddEditActivity.EXTRA_NAME);
        String patronymic = data.getStringExtra(Lab3AddEditActivity.EXTRA_PATRONYMIC);
        cashStudents.addStudent(new Student(surname, name, patronymic));
    }

    private void updateStudent(@Nullable Intent data, int position) {
        String surname = data.getStringExtra(Lab3AddEditActivity.EXTRA_SURNAME);
        String name = data.getStringExtra(Lab3AddEditActivity.EXTRA_NAME);
        String patronymic = data.getStringExtra(Lab3AddEditActivity.EXTRA_PATRONYMIC);
        Student student = new Student(surname, name, patronymic);
        cashStudents.removeStudent(cashStudents.getStudents().get(position));
        cashStudents.addStudent(student);
    }
}
