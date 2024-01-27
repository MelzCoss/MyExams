package com.example.esercitazionebonus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;

public class HomeActivity extends AppCompatActivity {

    CardView add_exam, my_exams, my_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        add_exam = findViewById(R.id.add_exam_cardview);
        my_exams = findViewById(R.id.my_exams_cardview);
        my_profile = findViewById(R.id.my_profile_cardview);

        add_exam.setOnClickListener(v -> {
            Intent addExam = new Intent(HomeActivity.this, AddExamActivity.class);
            startActivity(addExam);
        });

        my_exams.setOnClickListener(v -> {
            Intent myExams = new Intent(HomeActivity.this, MyExamsActivity.class);
            startActivity(myExams);
        });

        my_profile.setOnClickListener(v -> {
            Intent myProfile = new Intent(HomeActivity.this, MyProfileActivity.class);
            startActivity(myProfile);
        });
    }
}