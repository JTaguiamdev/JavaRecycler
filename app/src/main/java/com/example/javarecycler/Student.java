package com.example.javarecycler;

import android.net.Uri;

public class Student {
    private String fullName;
    private String idNo;
    private String course;
    private String yearLevel;
    private String imageUri;

    public Student(String fullName, String idNo, String course, String yearLevel, String imageUri) {
        this.fullName = fullName;
        this.idNo = idNo;
        this.course = course;
        this.yearLevel = yearLevel;
        this.imageUri = imageUri;
    }

    // Getters
    public String getFullName() { return fullName; }
    public String getIdNo() { return idNo; }
    public String getCourse() { return course; }
    public String getYearLevel() { return yearLevel; }
    public String getImageUri() { return imageUri; }

    // Method for display in RecyclerView
    public String getDisplayText() {
        return fullName + " - " + idNo + " - " + course + " " + yearLevel;
    }
}
