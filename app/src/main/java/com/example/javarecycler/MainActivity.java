package com.example.javarecycler;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    // RecyclerView components
    private RecyclerView recyclerView;
    private final ArrayList<Student> studentList = new ArrayList<>();
    private ItemAdapter adapter;

    // Form components
    private EditText fullnameEditText;
    private EditText idNoEditText;
    private Spinner courseSpinner;
    private Spinner yearLevelSpinner;
    private ImageView profileImageView;
    private Uri selectedImageUri;

    // Navigation components
    private ViewSwitcher viewSwitcher;
    private Button btnAdd;
    private Button saveButton;
    private Button cancelButton;

    // Image picker launcher
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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        initViews();
        setupRecyclerView();
        setupSpinners();
        setupClickListeners();
        setupTouchHelper();
        setupWindowInsets();
    }

    private void initViews() {
        // ViewSwitcher
        viewSwitcher = findViewById(R.id.viewSwitcher);

        // RecyclerView components
        recyclerView = findViewById(R.id.recyclerView);
        btnAdd = findViewById(R.id.button);

        // Form components
        fullnameEditText = findViewById(R.id.fullnameEditText);
        idNoEditText = findViewById(R.id.idNoEditText);
        courseSpinner = findViewById(R.id.courseSpinner);
        yearLevelSpinner = findViewById(R.id.yearLevelSpinner);
        profileImageView = findViewById(R.id.profileImageView);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);
    }

    private void setupRecyclerView() {
        adapter = new ItemAdapter(studentList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void setupSpinners() {
        // Course spinner
        String[] courses = {
                "Select Course",
                "BSCS - Bachelor of Science in Computer Science",
                "BSIT - Bachelor of Science in Information Technology",
                "BSIS - Bachelor of Science in Information Systems",
                "BSCpE - Bachelor of Science in Computer Engineering"
        };
        ArrayAdapter<String> courseAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, courses);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(courseAdapter);

        // Year level spinner
        String[] yearLevels = {
                "Select Year Level", "1st Year", "2nd Year", "3rd Year", "4th Year"
        };
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, yearLevels);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearLevelSpinner.setAdapter(yearAdapter);
    }

    private void setupClickListeners() {
        // ADD button - switch to form view
        btnAdd.setOnClickListener(v -> {
            clearForm();
            viewSwitcher.showNext(); // Switch to form view
        });

        // Image picker
        profileImageView.setOnClickListener(v -> imagePickerLauncher.launch("image/*"));

        // SAVE button - save data and return to list view
        saveButton.setOnClickListener(v -> saveData());

        // CANCEL button - return to list view without saving
        cancelButton.setOnClickListener(v -> {
            clearForm();
            viewSwitcher.showPrevious(); // Switch back to list view
        });
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

        // Create new student and add to list
        String imageUriString = selectedImageUri != null ? selectedImageUri.toString() : null;
        Student newStudent = new Student(fullname, idNo, course, yearLevel, imageUriString);

        studentList.add(newStudent);
        adapter.notifyItemInserted(studentList.size() - 1);
        recyclerView.scrollToPosition(studentList.size() - 1);

        // Clear form and switch back to list view
        clearForm();
        viewSwitcher.showPrevious();

        Toast.makeText(this, "Student added successfully!", Toast.LENGTH_SHORT).show();
    }

    private void clearForm() {
        fullnameEditText.setText("");
        idNoEditText.setText("");
        courseSpinner.setSelection(0);
        yearLevelSpinner.setSelection(0);
        profileImageView.setImageResource(R.drawable.outline_account_circle_24);
        selectedImageUri = null;
    }

    private void setupTouchHelper() {
        ItemTouchHelper.Callback callback = new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                Collections.swap(studentList, fromPosition, toPosition);
                adapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                studentList.remove(position);
                adapter.notifyItemRemoved(position);
            }
        };

        new ItemTouchHelper(callback).attachToRecyclerView(recyclerView);
    }

    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.viewSwitcher), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
