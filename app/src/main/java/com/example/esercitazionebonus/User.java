package com.example.esercitazionebonus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class User {
    String name, surname, matricola, password;
    Calendar birthdate;

    public User (String name, String surname, String matricola, String password, Calendar birthdate){
        this.name = name;
        this.surname = surname;
        this.matricola = matricola;
        this.password = password;
        this.birthdate = birthdate;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getMatricola() {
        return matricola;
    }

    public String getPassword() {
        return password;
    }

    public Calendar getBirthdate() {
        return birthdate;
    }

    public String getBirthdateLiteral() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.sss", Locale.ITALY);
        return format.format(birthdate.getTime());

    }
}
