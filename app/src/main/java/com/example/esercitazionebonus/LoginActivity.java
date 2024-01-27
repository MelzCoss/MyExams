package com.example.esercitazionebonus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button login_button;
    TextView not_registered;

    EditText matricola_field, password_field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MyDBHelper myDBHelper = new MyDBHelper(this);

        login_button = findViewById(R.id.login_button);
        not_registered = findViewById(R.id.not_account_register);
        matricola_field = findViewById(R.id.matricola_field);
        password_field = findViewById(R.id.password_field);

        String matricola_2 = getSharedPreferences("login_data", MODE_PRIVATE).getString("matricola", null);

        if (matricola_2 != null){
            Intent homeActivity = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(homeActivity);
            return;
        }

        login_button.setOnClickListener(v -> {
            String matricola = matricola_field.getText().toString();
            String password = password_field.getText().toString();

            if (matricola.equals("")){
                matricola_field.setError("Campo Obbligatorio!");
            }

            if (password_field.equals("")){
                password_field.setError("Campo Obbligatorio!");
            }

            User user = myDBHelper.loadUser(matricola, password);


            if (user == null) {
                Toast.makeText(getApplicationContext(), "Utente non trovato! Modifica le tue credenziali e riprova.", Toast.LENGTH_SHORT).show();
            } else {

                SharedPreferences preferences = getSharedPreferences("login_data", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("matricola", user.getMatricola());
                editor.putString("password", user.getPassword());
                editor.apply();

                Intent homeActivity = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(homeActivity);
            }
        });

        not_registered.setOnClickListener(v -> {
            Intent notRegistered = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(notRegistered);
        });

        SpannableStringBuilder builder = new SpannableStringBuilder();
        SpannableString string_1= new SpannableString("Non sei registrato? ");
        builder.append(string_1);
        SpannableString string_2= new SpannableString("Registrati");
        string_2.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.dark_blue)), 0, string_2.length(), 0);
        builder.append(string_2);
        not_registered.setText(builder, TextView.BufferType.SPANNABLE);
    }
}