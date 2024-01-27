package com.example.esercitazionebonus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class MyExamsActivity extends AppCompatActivity implements ClickListener, DeleteExamDialogListener {

    ImageButton back_icon;

    TextView media_aritmetica, media_ponderata, voto_laurea;

    RecyclerView exams_list;

    MyDBHelper dbHelper;
    List<Exam> exams;

    ExamAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_exams);

        String matricola = getSharedPreferences("login_data", MODE_PRIVATE).getString("matricola", "");

        dbHelper = new MyDBHelper(this);
        exams = dbHelper.loadExams(matricola);
        Log.d("LENGHT", String.valueOf(exams.size()));
        back_icon = findViewById(R.id.arrow_back);
        media_aritmetica = findViewById(R.id.average_number);
        media_ponderata = findViewById(R.id.average_2_number);
        voto_laurea = findViewById(R.id.graduation_mark_number);
        exams_list = findViewById(R.id.exams_list);

        adapter = new ExamAdapter(exams, this);
        exams_list.setAdapter(adapter);
        exams_list.setLayoutManager(new LinearLayoutManager(this));


        back_icon.setOnClickListener(v -> {
            Intent backIcon = new Intent(MyExamsActivity.this, HomeActivity.class);
            startActivity(backIcon);
        });

        calculate_values();
    }

    @Override
    public void deleteExam(int position) {
        openDialog(exams.get(position));
    }

    public void openDialog(Exam exam) {
        DeleteExamDialog delete_exam_dialog = new DeleteExamDialog(exam, this);
        delete_exam_dialog.show(getSupportFragmentManager(), "Delete Exam Dialog");
    }

    @Override
    public void cancelExam(Exam exam) {
        dbHelper.deleteExam(exam);
        exams.remove(exam);
        adapter.notifyDataSetChanged();
        calculate_values();
    }

    public void calculate_values(){
        if (exams.size() > 0) {
            int i = 0;
            float somma = 0;
            while (i < exams.size()) {
                somma += exams.get(i).getMark();
                i += 1;
            }
            float media_aritmetica_val = somma / (float) exams.size();
            media_aritmetica.setText(String.format(Locale.ITALIAN, "%.2f", media_aritmetica_val));

            i = 0;
            float somma_voti = 0;
            float somma_cfu = 0;
            while (i < exams.size()) {
                somma_voti += exams.get(i).getMark() * exams.get(i).getCFU();
                somma_cfu += exams.get(i).getCFU();
                i += 1;
            }
            float media_ponderata_val = somma_voti/somma_cfu;
            media_ponderata.setText(String.format(Locale.ITALIAN, "%.2f", media_ponderata_val));

            float voto_di_laurea_val = (media_aritmetica_val * 110)/30;
            voto_laurea.setText(String.format(Locale.ITALIAN, "%.2f", voto_di_laurea_val));
        } else{
            media_aritmetica.setText("0,00");
            media_ponderata.setText("0,00");
            voto_laurea.setText("0,00");
        }
    }
}