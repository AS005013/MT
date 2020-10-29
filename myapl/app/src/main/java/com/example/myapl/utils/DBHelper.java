package com.example.myapl.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERISON = 1;
    public static final String DATABASE_NAME = "stars";
    public static final String TABLE_FAVOUTRITES = "users";

    public static final String KEY_ID = "id";
    public static final String KEY_USER_ID = "user_id";

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, @Nullable int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERISON);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_FAVOUTRITES + "(" + KEY_ID + " integer primary key, " + KEY_USER_ID + " integer" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_FAVOUTRITES);

        onCreate(db);
    }
}
