package com.example.fragmentmaker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MyDBhelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydb";
    private static final int VERSION_NO = 1;
    private static final String TABLE_NAME = "students";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_ROLL_NO = "rollNo";

    public MyDBhelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION_NO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_NAME + "("+
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                KEY_NAME + " TEXT ,"+
                KEY_ROLL_NO + " TEXT"+
                ")");
//        SQLiteDatabase database = this.getReadableDatabase();
//        database.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP  TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

//    It insert the data into the database
    public void insert_db(String name , String roll_no){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME , name);
        values.put(KEY_ROLL_NO , roll_no);
        database.insert(TABLE_NAME , null , values);
    }

//    It fetch the data from the database
    public ArrayList<StudentModel> fetch_data(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME , null);
        ArrayList<StudentModel> arrStudent = new ArrayList<>();
        while (cursor.moveToNext()){
            StudentModel model = new StudentModel();
            model.id = cursor.getInt(0);
            model.name = cursor.getString(1);
            model.roll_no = cursor.getString(2);
            arrStudent.add(model);
        }
        return  arrStudent;
    }

//    It update the data from the database
    public void update_data(StudentModel studentModel){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME , studentModel.name);
        values.put(KEY_ROLL_NO , studentModel.roll_no);
        db.update(TABLE_NAME ,values , KEY_ID + "="+studentModel.id , null);

    }

//    It delete the data from the database
    public void delete_data(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME , KEY_ID +"= ?" , new String[]{String.valueOf(id)});

    }
}
