package com.rumato.lab4.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StudentDao {
    @Query("SELECT * FROM students_table ORDER BY surname ASC")
    List<Student> getAllStudents();

    @Insert
    void insertStudent(Student student);

    @Delete
    void deleteStudent(Student student);

    @Query("DELETE FROM students_table")
    void deleteAllStudents();
}
