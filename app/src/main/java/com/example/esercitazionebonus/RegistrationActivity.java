package com.example.esercitazionebonus;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Locale;

public class RegistrationActivity extends AppCompatActivity {
    final Calendar myCalendar= Calendar.getInstance();

    EditText name, surname, matricola, birthdate, password, confirmpassword;

    TextView name_error, surname_error, matricola_error, date_error, password_error, confirm_password_error;

    Button registration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        MyDBHelper dbHelper = new MyDBHelper(this);

        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };

        name = findViewById(R.id.name_field);
        surname = findViewById(R.id.surname_field);
        matricola = findViewById(R.id.matricola_field);
        birthdate = findViewById(R.id.birthdate_field);
        password = findViewById(R.id.password_field);
        confirmpassword = findViewById(R.id.confirm_password_field);
        registration = findViewById(R.id.registration_button);
        name_error = findViewById(R.id.name_error);
        surname_error = findViewById(R.id.surname_error);
        matricola_error = findViewById(R.id.matricola_error);
        date_error = findViewById(R.id.date_error);
        password_error = findViewById(R.id.password_error);
        confirm_password_error = findViewById(R.id.confirm_password_error);

        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(RegistrationActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        registration.setOnClickListener(v -> {
            String name_data = name.getText().toString();
            String surname_data = surname.getText().toString();
            String matricola_data = matricola.getText().toString();
            String password_data = password.getText().toString();
            String confirm_password_data = confirmpassword.getText().toString();

            boolean flag = false;

            if (name_data.equals("")){
                name_error.setText("Campo Obbligatorio");
                flag = true;
            } else {
                name_error.setText("");
            }

            if (surname_data.equals("")){
                surname_error.setText("Campo Obbligatorio!");
                flag = true;
            } else {
                surname_error.setText("");
            }

            if (matricola_data.equals("")){
                matricola_error.setText("Campo Obbligatorio!");
                flag = true;
            } else {
                matricola_error.setText("");
            }

            if (password_data.equals("")){
                password_error.setText("Campo Obbligatorio!");
                flag = true;
            } else {
                password_error.setText("");
            }

            if (confirm_password_data.equals("")){
                confirm_password_error.setText("Campo Obbligatorio!");
                flag = true;
            } else {
                confirm_password_error.setText("");
            }

            if (!password_data.equals(confirm_password_data)){
                password_error.setText("Le password non corrispondono!");
                confirm_password_error.setText("Le password non corrispondono!");
                flag = true;
            } else if (flag != true){
                password_error.setText("");
                confirm_password_error.setText("");
            }

            Calendar today_date = Calendar.getInstance();

            long days = Math.round((float) (today_date.getTimeInMillis() - myCalendar.getTimeInMillis()) / (24 * 60 * 60 * 1000));
            if (days < 365 * 18) {
                date_error.setText("Devi essere maggiorenne!");
                flag = true;
            } else {
                date_error.setText("");
            }

            if (flag){
                return;
            }

            User user = new User(name_data, surname_data, matricola_data, password_data, myCalendar);

            dbHelper.addUser(user);

            SharedPreferences preferences = getSharedPreferences("login_data", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("matricola", user.getMatricola());
            editor.putString("password", user.getPassword());
            editor.apply();

            Intent homeActivity = new Intent(RegistrationActivity.this, HomeActivity.class);
            startActivity(homeActivity);
        });
    }

    private void updateLabel() {
        String myFormat="dd/MM/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.ITALIAN);
        birthdate.setText(dateFormat.format(myCalendar.getTime()));
    }

}