package com.rumato.lab4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class Lab4AddEditActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "com.rumato.lab4.extra_position";
    public static final String EXTRA_SURNAME = "com.rumato.lab4.extra_surname";
    public static final String EXTRA_NAME = "com.rumato.lab4.extra_name";
    public static final String EXTRA_PATRONYMIC = "com.rumato.lab4.extra_patronymic";
    public static final String EXTRA_PHOTO_PATH = "com.rumato.lab4.extra_photo_path";
    public static final String EXTRA_SMALL_PHOTO_PATH = "com.rumato.lab4.extra_small_photo_path";

    public static final int CAMERA_REQUEST_PERMISSION_CODE = 1996;
    public static final int CAMERA_REQUEST_CODE = 1997;

    private EditText etSurname;
    private EditText etName;
    private EditText etPatronymic;

    private ImageView ivPhoto;

    private TempStudentPref studentPref;
    private BitmapProcessor bitmapProcessor;
    private String largePhotoPath;
    private String smallPhotoPath;

    private boolean skipSaveToPrefs = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student_lab4);

        bitmapProcessor = new BitmapProcessor(this);
        studentPref = new TempStudentPref(this);

        etSurname = findViewById(R.id.et_surname);
        etName = findViewById(R.id.et_name);
        etPatronymic = findViewById(R.id.et_patronymic);
        ivPhoto = findViewById(R.id.iv_photo_preview);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        largePhotoPath = studentPref.getLargePhotoPath();
        smallPhotoPath = studentPref.getSmallPhotoPath();

        if (largePhotoPath != null) {
            ivPhoto.setImageBitmap(BitmapFactory.decodeFile(largePhotoPath));
        }

        etSurname.setText(studentPref.getSurname());
        etName.setText(studentPref.getName());
        etPatronymic.setText(studentPref.getPatronymic());

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_POSITION)) {
            setTitle(getString(R.string.editstudent));
            etSurname.setText(intent.getStringExtra(EXTRA_SURNAME));
            etName.setText(intent.getStringExtra(EXTRA_NAME));
            etPatronymic.setText(intent.getStringExtra(EXTRA_PATRONYMIC));

            largePhotoPath = intent.getStringExtra(EXTRA_PHOTO_PATH);
            smallPhotoPath = intent.getStringExtra(EXTRA_SMALL_PHOTO_PATH);
            if (largePhotoPath != null) {
                ivPhoto.setImageBitmap(BitmapFactory.decodeFile(largePhotoPath));
            }
        } else {
            setTitle(getString(R.string.addstudent));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.lab4_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        if (item.getItemId() == R.id.b_menu_save_student) {
            saveStudent();
        }

        if (item.getItemId() == R.id.b_menu_take_photo) {
            requestPhotoFromCamera();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (getTitle() == getString(R.string.editstudent)) {
            skipSaveToPrefs = true;
            studentPref.clear();
        }

        if (!skipSaveToPrefs) {
            String surname = etSurname.getText().toString().trim();
            String name = etName.getText().toString().trim();
            String patronymic = etPatronymic.getText().toString().trim();

            if (!surname.isEmpty()) {
                studentPref.setPrefSurname(surname);
            }

            if (!name.isEmpty()) {
                studentPref.setPrefName(name);
            }

            if (!patronymic.isEmpty()) {
                studentPref.setPrefPatronymic(patronymic);
            }

            if (largePhotoPath != null) {
                studentPref.setPrefLargePhotoPath(largePhotoPath);
            }

            if (smallPhotoPath != null) {
                studentPref.setPrefSmallPhotoPath(smallPhotoPath);
            }
        }
    }

    private void saveStudent() {
        String surname = etSurname.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String patronymic = etPatronymic.getText().toString().trim();
        String fullImgPath = largePhotoPath;
        String reducedImgPath = smallPhotoPath;

        if (surname.isEmpty() || name.isEmpty() || patronymic.isEmpty()) {
            Toast.makeText(this, "Please fill all empty fields!", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_SURNAME, surname);
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_PATRONYMIC, patronymic);
        data.putExtra(EXTRA_PHOTO_PATH, largePhotoPath);
        data.putExtra(EXTRA_SMALL_PHOTO_PATH, smallPhotoPath);

        int position = getIntent().getIntExtra(EXTRA_POSITION, -1);
        if (position != -1) {
            data.putExtra(EXTRA_POSITION, position);
        }

        skipSaveToPrefs = true;
        studentPref.clear();

        setResult(RESULT_OK, data);
        finish();
    }

    private void requestPhotoFromCamera() {
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            invokeCamera();
        } else {
            String[] permissionRequest = {Manifest.permission.CAMERA};
            requestPermissions(permissionRequest, CAMERA_REQUEST_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                invokeCamera();
            } else {
                Toast.makeText(this, R.string.unableinvokecamera, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void invokeCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = bitmapProcessor.createTempFile();
        if (photo != null) {
            largePhotoPath = photo.getAbsolutePath();
            Uri photoUri = FileProvider.getUriForFile(this,
                    getApplicationContext().getPackageName() + ".provider", photo);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            cameraIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Toast.makeText(this, getString(R.string.pricturesaved), Toast.LENGTH_SHORT)
                    .show();

            int ivPhotoWidth = ivPhoto.getLayoutParams().width;
            int ivPhotoHeight = ivPhoto.getLayoutParams().height;

            int ivSmallPhotoWidth = getResources().getDimensionPixelSize(R.dimen.smallimagewidth);
            int ivSmallPhotoHeight = getResources().getDimensionPixelSize(R.dimen.smallimageheight);

            Bitmap photo = bitmapProcessor.getScaledBitmap
                    (largePhotoPath, ivPhotoWidth, ivPhotoHeight);
            Bitmap smallPhoto = bitmapProcessor.getScaledBitmap(
                    largePhotoPath, ivSmallPhotoWidth, ivSmallPhotoHeight);

            File smallPhotoFile = bitmapProcessor.createTempFile();

            try {
                bitmapProcessor.saveBitmapToFile(photo, largePhotoPath);
                ivPhoto.setImageBitmap(photo);

                if (smallPhotoFile != null) {
                    smallPhotoPath = smallPhotoFile.getAbsolutePath();
                    bitmapProcessor.saveBitmapToFile(smallPhoto, smallPhotoPath);
                }

            } catch (IOException e) {
                e.printStackTrace();
                largePhotoPath = null;
            }
        }
    }
}
