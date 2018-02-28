package com.example.duti.locationtrack.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.duti.locationtrack.database.FieldConstants.DATABASE_VERSION;
import static com.example.duti.locationtrack.database.FieldConstants.DB_NAME;
import static com.example.duti.locationtrack.database.FieldConstants.TABLE_ALL_LOCATION;
import static com.example.duti.locationtrack.database.FieldConstants.TABLE_STATEMENT_ALL_LOCATION;

public class DbHelper extends SQLiteOpenHelper {

    private String TAG = DbHelper.class.getSimpleName();

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "--- onCreate database ---");
        // statement for create table
        db.execSQL(TABLE_STATEMENT_ALL_LOCATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "--- onUpgrade database ---");
        //Drop all table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALL_LOCATION);
        // Create tables again
        onCreate(db);
    }

}
