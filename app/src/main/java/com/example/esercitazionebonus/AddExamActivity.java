package com.example.esercitazionebonus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.nio.charset.StandardCharsets;

public class AddExamActivity extends AppCompatActivity {

    ImageButton back_icon;
    EditText name, CFU, mark;
    Button add_exam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exam);

        MyDBHelper dbHelper = new MyDBHelper(this);

        SharedPreferences preferences = getSharedPreferences("login_data", MODE_PRIVATE);
        String matricola = preferences.getString("matricola", "");

        back_icon = findViewById(R.id.arrow_back);
        name = findViewById(R.id.exam_name_field);
        CFU = findViewById(R.id.cfu_field);
        mark = findViewById(R.id.mark_field);
        add_exam = findViewById(R.id.add_button);

        back_icon.setOnClickListener(v -> {
            Intent backIcon = new Intent(AddExamActivity.this, HomeActivity.class);
            startActivity(backIcon);
        });

        add_exam.setOnClickListener(v -> {
            String name_data = name.getText().toString();
            String cfu_data = CFU.getText().toString();
            String mark_data = mark.getText().toString();

            boolean flag = false;

            if (name_data.equals("")){
                name.setError("Campo Obbligatorio!");
                flag = true;
            }
            if (cfu_data.equals("")){
                CFU.setError("Campo Obbligatorio!");
                flag = true;
            }
            if (mark_data.equals("")){
                mark.setError("Campo Obbligatorio!");
                flag = true;
            }

            if (!flag) {
                int cfu_num = Integer.parseInt(CFU.getText().toString());
                int mark_num = Integer.parseInt(mark.getText().toString());

                if (cfu_num < 1) {
                    CFU.setError("Inserisci un numero di CFU valido, maggiore di 0!");
                    flag = true;
                }

                if (mark_num < 18 || mark_num > 30) {
                    mark.setError("Inserisci un voto valido, tra 18 e 30!");
                    flag = true;
                }

                if (!flag) {

                    Exam exam = new Exam(name_data, cfu_num, mark_num, matricola);
                    Log.d("DEBUG", String.valueOf(exam.getMark()));

                    dbHelper.addExam(exam);
                    openDialog();
                }

            }
            if (flag){
                return;
            }
        });
    }

    public void openDialog() {
        ExamAddedDialog exam_added_dialog = new ExamAddedDialog();
        exam_added_dialog.show(getSupportFragmentManager(), "Exam Added Dialog");
    }
}