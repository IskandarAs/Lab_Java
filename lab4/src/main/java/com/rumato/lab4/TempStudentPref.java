package com.rumato.lab4;

import android.content.Context;
import android.content.SharedPreferences;

public class TempStudentPref {
    private static final String PREF_SURNAME = "pref_surname";
    private static final String PREF_NAME = "pref_name";
    private static final String PREF_PATRONYMIC = "pref_patronymic";
    private static final String PREF_LARGE_PHOTO_PATH = "pref_large_path";
    private static final String PREF_SMALL_PHOTO_PATH = "pref_small_path";

    private final SharedPreferences prefs;

    public TempStudentPref(Context context) {
        prefs = context.getSharedPreferences("temp_student", Context.MODE_PRIVATE);
    }

    public String getSurname() {
        return prefs.getString(PREF_SURNAME, null);
    }

    public String getName() {
        return prefs.getString(PREF_NAME, null);
    }

    public String getPatronymic() {
        return prefs.getString(PREF_PATRONYMIC, null);
    }

    public String getLargePhotoPath() {
        return prefs.getString(PREF_LARGE_PHOTO_PATH, null);
    }

    public String getSmallPhotoPath() {
        return prefs.getString(PREF_SMALL_PHOTO_PATH, null);
    }

    public void setPrefSurname(String surname) {
        prefs.edit().putString(PREF_SURNAME, surname).apply();
    }

    public void setPrefName(String name) {
        prefs.edit().putString(PREF_NAME, name).apply();
    }

    public void setPrefPatronymic(String patronymic) {
        prefs.edit().putString(PREF_PATRONYMIC, patronymic).apply();
    }

    public void setPrefLargePhotoPath(String largePhotoPath) {
        prefs.edit().putString(PREF_LARGE_PHOTO_PATH, largePhotoPath).apply();
    }

    public void setPrefSmallPhotoPath(String smallPhotoPath) {
        prefs.edit().putString(PREF_SMALL_PHOTO_PATH, smallPhotoPath).apply();
    }

    public void clear() {
        prefs.edit().clear().apply();
    }
}
