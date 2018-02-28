package com.example.duti.locationtrack.database;


public class FieldConstants {

    public static final String DB_NAME = "location_track";
    public static final int DATABASE_VERSION = 1;

    // table field Information
    public static final String TABLE_ALL_LOCATION = "all_location";

    public static final String LATITUDE = "latitude" ;
    public static final String LONGITUDE = "longitude";
    public static final String ADDRESS = "address";

    public static final String TABLE_STATEMENT_ALL_LOCATION = "CREATE TABLE IF NOT EXISTS " + TABLE_ALL_LOCATION + "("
            + LATITUDE + " VARCHAR," +
            LONGITUDE + " VARCHAR," +
            ADDRESS + " VARCHAR" +
            ")";
}
