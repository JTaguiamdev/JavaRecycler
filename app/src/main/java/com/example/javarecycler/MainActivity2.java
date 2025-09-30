package com.example.javarecycler;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    private EditText fullnameEditText;
    private EditText idNoEditText;
    private Spinner courseSpinner;
    private Spinner yearLevelSpinner;
    private Button saveButton;
    private Button cancelButton;
    private ImageView profileImageView;
    private Uri selectedImageUri;

    // Image picker launcher using modern ActivityResultLauncher
    private final ActivityResultLauncher<String> imagePickerLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.GetContent(),
                    uri -> {
                        if (uri != null) {
                            selectedImageUri = uri;
                            profileImageView.setImageURI(uri);
                            profileImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Initialize views
        initializeViews();

        // Setup spinners
        setupCourseSpinner();
        setupYearLevelSpinner();

        // Setup button listeners
        saveButton.setOnClickListener(v -> saveData());
        cancelButton.setOnClickListener(v -> cancelData());

        // Setup image selection
        profileImageView.setOnClickListener(v -> openImagePicker());
    }

    private void initializeViews() {
        fullnameEditText = findViewById(R.id.fullnameEditText);
        idNoEditText = findViewById(R.id.idNoEditText);
        courseSpinner = findViewById(R.id.courseSpinner);
        yearLevelSpinner = findViewById(R.id.yearLevelSpinner);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);
        profileImageView = findViewById(R.id.profileImageView);
    }

    private void openImagePicker() {
        // Launch image picker for "image/*" MIME type
        imagePickerLauncher.launch("image/*");
    }

    private void setupCourseSpinner() {
        String[] courses = {
                "Select Course",
                "BSCS - Bachelor of Science in Computer Science",
                "BSIT - Bachelor of Science in Information Technology",
                "BSIS - Bachelor of Science in Information Systems",
                "BSCpE - Bachelor of Science in Computer Engineering"
        };

        ArrayAdapter<String> courseAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                courses
        );
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(courseAdapter);
    }

    private void setupYearLevelSpinner() {
        String[] yearLevels = {
                "Select Year Level",
                "1st Year",
                "2nd Year",
                "3rd Year",
                "4th Year"
        };

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                yearLevels
        );
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearLevelSpinner.setAdapter(yearAdapter);
    }

    private void saveData() {
        String fullname = fullnameEditText.getText().toString().trim();
        String idNo = idNoEditText.getText().toString().trim();
        String course = courseSpinner.getSelectedItem().toString();
        String yearLevel = yearLevelSpinner.getSelectedItem().toString();

        // Validate inputs
        if (fullname.isEmpty()) {
            fullnameEditText.setError("Full name is required");
            fullnameEditText.requestFocus();
            return;
        }

        if (idNo.isEmpty()) {
            idNoEditText.setError("ID number is required");
            idNoEditText.requestFocus();
            return;
        }

        if (course.equals("Select Course")) {
            Toast.makeText(this, "Please select a course", Toast.LENGTH_SHORT).show();
            return;
        }

        if (yearLevel.equals("Select Year Level")) {
            Toast.makeText(this, "Please select a year level", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create result intent and add data
        Intent resultIntent = new Intent();
        resultIntent.putExtra("fullname", fullname);
        resultIntent.putExtra("idNo", idNo);
        resultIntent.putExtra("course", course);
        resultIntent.putExtra("yearLevel", yearLevel);

        // Add image URI if selected
        if (selectedImageUri != null) {
            resultIntent.putExtra("imageUri", selectedImageUri.toString());
        }

        // Format combined string for display in main activity
        String combinedData = fullname + " - " + idNo + " - " +
                course.substring(0, course.indexOf(" -")) + " " + yearLevel;
        resultIntent.putExtra("combinedData", combinedData);

        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void cancelData() {
        setResult(RESULT_CANCELED);
        finish();
    }
}
