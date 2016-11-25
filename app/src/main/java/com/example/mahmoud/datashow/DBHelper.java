package com.example.mahmoud.datashow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mahmoud on 11/22/2016.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 10;
    private static final String DATABASE_NAME = "vacation";
    private static final String TABLE_NAME = "Users";
    private static final String KEY_ID = "id";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE= "phone";
    private static final String KEY_JOB_TITLE = "job_title";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + KEY_ID + " TEXT PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT," + KEY_PHONE + " TEXT,"
                + KEY_JOB_TITLE  + " TEXT" + ");";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void addUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_ID, user.getId());
        values.put(KEY_NAME, user.getName());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_PHONE, user.getPhone());
        values.put(KEY_JOB_TITLE, user.getJobTitle());


        db.insert(TABLE_NAME,null,values);
        db.close();
    }


    public User searchForUserByEmail(String email){
        SQLiteDatabase db =getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_NAME +" WHERE "+KEY_EMAIL +" = ? " , new String[]{email});

       if(cursor.moveToFirst()){
           User user = new User();

           user.setId(cursor.getString(0));
           user.setName(cursor.getString(1));
           user.setEmail(cursor.getString(2));
           user.setPhone(cursor.getString(3));
           user.setJobTitle(cursor.getString(4));


           return user;
       }
        cursor.close();

        return null;

    }



    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_ID, user.getId());
        values.put(KEY_NAME, user.getName());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_PHONE, user.getPhone());
        values.put(KEY_JOB_TITLE, user.getJobTitle());


        return db.update(TABLE_NAME, values, KEY_ID + " = ?",
                new String[]{user.getId()});
    }


}
