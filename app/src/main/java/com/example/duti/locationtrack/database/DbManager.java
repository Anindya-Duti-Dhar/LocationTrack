package com.example.duti.locationtrack.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duti.locationtrack.models.AllLocation;

import java.util.ArrayList;
import java.util.List;

import static com.example.duti.locationtrack.database.FieldConstants.ADDRESS;
import static com.example.duti.locationtrack.database.FieldConstants.LATITUDE;
import static com.example.duti.locationtrack.database.FieldConstants.LONGITUDE;
import static com.example.duti.locationtrack.database.FieldConstants.TABLE_ALL_LOCATION;

public class DbManager {
    private static String TAG = DbManager.class.getSimpleName();

    private static DbManager instance;
    private Context mContext;

    private DbManager(Context context) {
        this.mContext = context;
    }

    public static DbManager getInstance(Context context) {
        if (instance == null) {
            instance = new DbManager(context);
        }

        return instance;
    }

    /* Insert into All Location table*/
    public void insertIntoAllLocationDB(String latitude, String longitude, String address){
        Log.d("local_location","All Location table before insert");

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();

        // 1. get reference to writable DB
        DbHelper dbHelper = new DbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        values.put(LATITUDE, latitude);
        values.put(LONGITUDE, longitude);
        values.put(ADDRESS, address);

        // 3. insert
        db.insert(TABLE_ALL_LOCATION, null, values);
        // 4. close
        dbHelper.close();
        Log.i("local_geo", "All Geo table After insert");
    }

    /* get  data from Location table */
    public List<AllLocation> getAllLocationDataFromDB() {
        List<AllLocation> modelList = new ArrayList<AllLocation>();
        DbHelper dbHelper = new DbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "select * from " + TABLE_ALL_LOCATION;
        Cursor cursor = db.rawQuery(query, null);
        Log.d("local_Location", "total number of Location: "+cursor.getCount());

        if (cursor.moveToFirst()) {
            do {
                AllLocation model = new AllLocation();
                model.setLatitude(cursor.getString(0));
                model.setLongitude(cursor.getString(1));
                model.setAddress(cursor.getString(2));
                modelList.add(model);
            } while (cursor.moveToNext());
        }
        cursor.close();
        dbHelper.close();
        return modelList;
    }

    public void clearDB(String tableName) {
        DbHelper dbHelper = new DbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(tableName, null, null);
        dbHelper.close();
    }

}

