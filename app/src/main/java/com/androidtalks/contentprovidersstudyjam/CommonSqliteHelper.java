package com.androidtalks.contentprovidersstudyjam;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by leo on 3/12/15.
 */
public class CommonSqliteHelper extends SQLiteOpenHelper {

    // Sentence to create a table into database with different items

    private String sqliteCreateSentence =
            "CREATE TABLE Courses " +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " name TEXT, " +
            " desc TEXT, " +
            " date TEXT )";



    public CommonSqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(sqliteCreateSentence);
        insertDefaultValues(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS Courses");
        db.execSQL(sqliteCreateSentence);
    }


    private void insertDefaultValues(SQLiteDatabase db){

        for (int i = 0; i <20 ; i++) {

            String name = "Course " + i;
            String desc = "Desc " + i;
            String date = 1 + "/03/2015";

            db.execSQL("INSERT INTO Courses (name, desc, date)" + "VALUES ( '" + name + "','"+desc+"','"+date+"'");
        }
    }
}
