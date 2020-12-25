package com.rumato.lab3.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.rumato.lab3.R;

public class Lab3AddEditActivity extends AppCompatActivity {

    public final static String EXTRA_SURNAME = "com.rumato.lab3.Activities.extra_surname";
    public final static String EXTRA_NAME = "com.rumato.lab3.Activities.extra_name";
    public final static String EXTRA_PATRONYMIC = "com.rumato.lab3.Activities.extra_patronymic";
    public final static String EXTRA_POSITION = "com.rumato.lab3.Activities.extra_position";

    private EditText etSurname;
    private EditText etName;
    private EditText etPatronymic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab3_add_student);

        etSurname = findViewById(R.id.et_surname);
        etName = findViewById(R.id.et_name);
        etPatronymic = findViewById(R.id.et_patronymic);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_POSITION)) {
            setTitle(getString(R.string.edit_student));
            etSurname.setText(intent.getStringExtra(EXTRA_SURNAME));
            etName.setText(intent.getStringExtra(EXTRA_NAME));
            etPatronymic.setText(intent.getStringExtra(EXTRA_PATRONYMIC));
        } else {
            setTitle(getString(R.string.add_student));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lab3_save_student, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mb_save_student) {
            saveStudent();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveStudent(){
        String surname = etSurname.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String patronymic = etPatronymic.getText().toString().trim();

        if (surname.isEmpty() || name.isEmpty() || patronymic.isEmpty()) {
            Toast.makeText(this, "Please, fill all empty fields", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        Intent intent = new Intent();

        intent.putExtra(EXTRA_SURNAME, surname);
        intent.putExtra(EXTRA_NAME, name);
        intent.putExtra(EXTRA_PATRONYMIC, patronymic);

        int position = getIntent().getIntExtra(EXTRA_POSITION, -1);
        Log.i("MyResult", "pos 2: " + position);
        if (position != -1) {
            intent.putExtra(EXTRA_POSITION, position);
        }

        setResult(RESULT_OK, intent);
        finish();
    }
}
