package com.example.esercitazionebonus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.net.ContentHandler;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class MyDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "myexams";
    private static final int DATABASE_VERSION = 6;
    private static final String TABLE_NAME_1 = "registered_users_2";
    private static final String TABLE_NAME_2 = "exams_2";
    private static final String KEY_MATRICOLA = "matricola";
    private static final String KEY_NAME = "name";
    private static final String KEY_SURNAME = "surname";
    private static final String KEY_BIRTHDATE = "birthdate";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EXAM_NAME = "key_exam_name";
    private static final String KEY_EXAM_MARK = "key_exam_mark";
    private static final String KEY_EXAM_CFU = "key_exam_cfu";

    public MyDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME_1 + "(" + KEY_MATRICOLA + " TEXT PRIMARY KEY, "
        + KEY_NAME + " TEXT, " + KEY_SURNAME + " TEXT, " + KEY_BIRTHDATE + " TEXT, " + KEY_PASSWORD + " TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_NAME_2 + "(" + KEY_EXAM_NAME + " TEXT PRIMARY KEY, "
        + KEY_EXAM_MARK + " INT, " + KEY_EXAM_CFU + " INT, " + KEY_MATRICOLA + " TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
        onCreate(db);
    }

    public void addUser(User user){
        Log.d("DEBUG", "ADD USER");
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MATRICOLA, user.getMatricola());
        values.put(KEY_NAME, user.getName());
        values.put(KEY_SURNAME, user.getSurname());
        values.put(KEY_BIRTHDATE, user.getBirthdateLiteral());
        values.put(KEY_PASSWORD, user.getPassword());

        db.insert(TABLE_NAME_1, null, values);
        //db.close();
    }

    public User loadUser(String matricola, String password){
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                KEY_NAME,
                KEY_SURNAME,
                KEY_PASSWORD,
                KEY_MATRICOLA,
                KEY_BIRTHDATE
        };

        String selection = KEY_MATRICOLA + " = ? AND " + KEY_PASSWORD + " = ?";
        String[] selectionArgs = {matricola, password};

        Cursor cursor = db.query(
                TABLE_NAME_1,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        User newUser = null;

        if (cursor.moveToNext()) {
            String newMatricola = cursor.getString(cursor.getColumnIndexOrThrow(KEY_MATRICOLA));
            String newName = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME));
            String newSurname = cursor.getString(cursor.getColumnIndexOrThrow(KEY_SURNAME));
            String newPassword = cursor.getString(cursor.getColumnIndexOrThrow(KEY_PASSWORD));
            String newBirthdate = cursor.getString(cursor.getColumnIndexOrThrow(KEY_BIRTHDATE));

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.sss", Locale.ITALY);


            try {
                Date birthdayDate = format.parse(newBirthdate);

                Calendar calendar = GregorianCalendar.getInstance(Locale.ITALY);
                assert birthdayDate != null;
                calendar.setTime(birthdayDate);

                newUser = new User(newName, newSurname, newMatricola, newPassword, calendar);
            } catch (Exception ignored) {
            }

        }

        cursor.close();
        //db.close();

        return newUser;
    }

    public void addExam(Exam exam){
        Log.d("DEBUG", "ADD EXAM");
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EXAM_NAME, exam.getName());
        values.put(KEY_EXAM_CFU, exam.getCFU());
        values.put(KEY_EXAM_MARK, exam.getMark());
        values.put(KEY_MATRICOLA, exam.getMatricola());

        db.insert(TABLE_NAME_2, null, values);
        //db.close();
    }

    public List<Exam> loadExams(String matricola){
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                KEY_EXAM_NAME,
                KEY_EXAM_MARK,
                KEY_EXAM_CFU,
                KEY_MATRICOLA
        };

        String selection = KEY_MATRICOLA + " = ?";
        String[] selectionArgs = {matricola};

        Cursor cursor = db.query(
                TABLE_NAME_2,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        List<Exam> exams = new ArrayList<>();

        while (cursor.moveToNext()) {
            String newName = cursor.getString(cursor.getColumnIndexOrThrow(KEY_EXAM_NAME));
            int newMark = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_EXAM_MARK));
            int newCFU = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_EXAM_CFU));
            String newMatricola = cursor.getString(cursor.getColumnIndexOrThrow(KEY_MATRICOLA));

            try {
                exams.add(new Exam(newName, newCFU, newMark, newMatricola));
            } catch (Exception ignored) {
            }

        }

        cursor.close();
        // db.close();

        return exams;
    }

    public void deleteExam(Exam exam){
        SQLiteDatabase db = getWritableDatabase();

        String selection = KEY_EXAM_NAME + " = ? ";
        String[] selectionArgs = {exam.getName()};

        db.delete(TABLE_NAME_2, selection, selectionArgs);
    }

}