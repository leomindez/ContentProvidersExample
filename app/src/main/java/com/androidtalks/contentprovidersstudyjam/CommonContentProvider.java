package com.androidtalks.contentprovidersstudyjam;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by leo on 3/13/15.
 */
public class CommonContentProvider extends ContentProvider{

    public static final Uri CONTENT_URI = Uri.parse("content://com.androidtalks.contentprovidersstudyjam/courses");
    private CommonSqliteHelper commonSqliteHelper;
    private static final String TABLE_NAME = "Courses";

    private static final int COURSES = 1;
    private static final int COURSES_ID = 2;
    private static UriMatcher uriMatcher = null;


    @Override
    public boolean onCreate() {

        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.androidtalks.contentprovidersstudyjam","courses",COURSES);
        uriMatcher.addURI("com.androidtalks.contentprovidersstudyjam","courses/#",COURSES_ID);
        commonSqliteHelper = new CommonSqliteHelper(getContext(),"DBCourses",null,1);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        String where = selection;
        if(uriMatcher.match(uri) == COURSES_ID){
            where = "_id=" + uri.getLastPathSegment();
        }

        SQLiteDatabase db = commonSqliteHelper.getWritableDatabase();

        Cursor c = db.query(TABLE_NAME, projection, where,
                selectionArgs, null, null, sortOrder);

        return c;
    }

    @Override
    public String getType(Uri uri) {

        int match = uriMatcher.match(uri);

        switch (match)
        {
            case COURSES:
                return "vnd.android.cursor.dir/vnd.contentprovidersstudyjam.course";
            case COURSES_ID:
                return "vnd.android.cursor.item/vnd.contentprovidersstudyjam.course";
            default:
                return "";
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {


        long regId = 1;

        SQLiteDatabase db = commonSqliteHelper.getWritableDatabase();

        regId = db.insert(TABLE_NAME, null, values);

        Uri newUri = ContentUris.withAppendedId(CONTENT_URI, regId);

        return newUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int cont;

        //Si es una consulta a un ID concreto construimos el WHERE
        String where = selection;
        if(uriMatcher.match(uri) == COURSES_ID){
            where = "_id=" + uri.getLastPathSegment();
        }

        SQLiteDatabase db = commonSqliteHelper.getWritableDatabase();

        cont = db.delete(TABLE_NAME, where, selectionArgs);

        return cont;


    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int cont;
        String where = selection;
        if(uriMatcher.match(uri) == COURSES_ID){
            where = "_id=" + uri.getLastPathSegment();
        }

        SQLiteDatabase db = commonSqliteHelper.getWritableDatabase();

        cont = db.update(TABLE_NAME, values, where, selectionArgs);

        return cont;
    }


    public static class Courses implements BaseColumns{

        public static final String COL_NAME = "name";
        public static final String COL_DESC = "desc";
        public static final String COL_DATE = "date";
    }



}
