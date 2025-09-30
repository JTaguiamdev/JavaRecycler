package com.example.javarecycler;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemHolder extends RecyclerView.ViewHolder {

    private ImageView profileImageView;
    private TextView fullnameTextView;
    private TextView idNoTextView;
    private TextView courseYearTextView;

    public ItemHolder(@NonNull View itemView) {
        super(itemView);

        profileImageView = itemView.findViewById(R.id.profileImageView);
        fullnameTextView = itemView.findViewById(R.id.fullnameTextView);
        idNoTextView = itemView.findViewById(R.id.idNoTextView);
        courseYearTextView = itemView.findViewById(R.id.courseYearTextView);
    }

    public void bind(Student student) {
        // Set text data
        fullnameTextView.setText(student.getFullName());
        idNoTextView.setText(student.getIdNo());
        courseYearTextView.setText(student.getCourse().substring(0, 4) + " " + student.getYearLevel());

        // Set profile image
        if (student.getImageUri() != null && !student.getImageUri().isEmpty()) {
            try {
                Uri imageUri = Uri.parse(student.getImageUri());
                profileImageView.setImageURI(imageUri);
                profileImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } catch (Exception e) {
                // Fallback to default image if URI is invalid
                profileImageView.setImageResource(R.drawable.outline_account_circle_24);
            }
        } else {
            // Set default profile image
            profileImageView.setImageResource(R.drawable.outline_account_circle_24);
        }
    }
}
