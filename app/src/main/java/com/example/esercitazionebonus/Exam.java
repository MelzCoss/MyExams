package com.example.esercitazionebonus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Exam {
    String name, matricola;
    int cfu, mark;

    public Exam (String name, int cfu, int mark, String matricola){
        this.name = name;
        this.cfu = cfu;
        this.mark = mark;
        this.matricola = matricola;
    }

    public String getName() {
        return name;
    }

    public String getMatricola() {
        return matricola;
    }

    public int getCFU() {
        return cfu;
    }

    public int getMark() {
        return mark;
    }

}
