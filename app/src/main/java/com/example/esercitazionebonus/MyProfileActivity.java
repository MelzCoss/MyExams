package com.example.esercitazionebonus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

public class MyProfileActivity extends AppCompatActivity {

    ImageButton back_icon;
    TextView name, matricola, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        MyDBHelper myDBHelper = new MyDBHelper(this);

        back_icon = findViewById(R.id.arrow_back);
        name = findViewById(R.id.textView2);
        matricola = findViewById(R.id.textView4);
        logout = findViewById(R.id.textView5);

        User user = null;

        SharedPreferences preferences = getSharedPreferences("login_data", MODE_PRIVATE);
        if (preferences.contains("matricola") && preferences.contains("password")) {
            String matricola_2 = preferences.getString("matricola", "");
            String password_2 = preferences.getString("password", "");
            user = myDBHelper.loadUser(matricola_2, password_2);
        }

        name.setText(user.getName() + " " + user.getSurname());
        matricola.setText(user.getMatricola());

        back_icon.setOnClickListener(v -> {
            Intent backIcon = new Intent(MyProfileActivity.this, HomeActivity.class);
            startActivity(backIcon);
        });

        logout.setOnClickListener(v -> {
            openDialog();
        });
    }

    public void openDialog() {
        LogoutDialog logoutDialog = new LogoutDialog();
        logoutDialog.show(getSupportFragmentManager(), "Logout Dialog");
    }
}