package com.rumato.lab4.DB;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "students_table")
public class Student {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String surname;
    private String name;
    private String patronymic;

    private String fullPhotoPath;
    private String reducedPhotoPath;

    public Student(String surname, String name, String patronymic, String fullPhotoPath, String reducedPhotoPath) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.fullPhotoPath = fullPhotoPath;
        this.reducedPhotoPath = reducedPhotoPath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getFullPhotoPath() {
        return fullPhotoPath;
    }

    public String getReducedPhotoPath() {
        return reducedPhotoPath;
    }
}
