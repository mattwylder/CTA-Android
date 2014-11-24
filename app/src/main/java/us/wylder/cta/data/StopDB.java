package us.wylder.cta.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.util.Log;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Created by mattwylder on 11/15/14.
 */
public class StopDB extends SQLiteOpenHelper{

    private Context context;

    public static final String[] lineStrs = {"Red Line", "Blue Line", "Brown Line",
            "Green Line", "Pink Line", "Purple Line", "Orange Line", "Yellow Line" };

    private static final String SQL_CREATE_STATIONS = "CREATE TABLE stations(_id INTEGER PRIMARY KEY," +
            "name TEXT, " +
            "lat FLOAT, " +
            "lng FLOAT, " +
            "red INTEGER," +
            "blue INTEGER," +
            "brown INTEGER," +
            "green INTEGER," +
            "pink INTEGER," +
            "purple INTEGER," +
            "orange INTEGER," +
            "yellow INTEGER)";
    private static final String SQL_DROP_STATIONS = "DROP TABLE stations";
    private static final String SQL_CREATE_LINES = "CREATE TABLE lines(_id INTEGER PRIMARY KEY, name TEXT)";
    private static final String SQL_DROP_LINES = "DROP TABLE lines";
    private static final String SQL_CREATE_FAVORITES = "CREATE TABLE favorites(_id INTEGER PRIMARY KEY)";
    private static final String SQL_DROP_FAVORITES = "DROP TABLE favorites";

    public static final int DATABASE_VERSION = 9;
    public static final String DATABASE_NAME = "Stops.db";

    private static final String DB_STATIONS_TABLE_NAME = "stations";

    private static final String[] DB_STATIONS_COLUMN_NAMES = {"_id", "name", "lat", "lng", "red", "blue",
            "orange", "yellow", "purple", "brown", "pink", "green"};

    private static final String DB_LINES_TABLE_NAME = "lines";
    private static final String[] DB_LINES_COLUMN_NAMES = { "_id", "name"};

    private static final String DB_FAVORITES_TABLE_NAME = "favorites";
    private static final String[] DB_FAVORITES_COLUMN_NAMES = {"_id"};
    private static final String DB_FAVORITES_JOIN_STATION = DB_STATIONS_TABLE_NAME + " NATURAL JOIN " +
            DB_FAVORITES_TABLE_NAME;


    private static final String TAG = "Stop Database";

    private static StopDB singleton = null;

    protected StopDB(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public static StopDB getInstance(Context context)
    {
        if(singleton == null)
        {
            singleton = new StopDB(context);
            Log.d(TAG, "Created singleton");
        }

        return singleton;
    }


    public void onCreate(SQLiteDatabase db)
    {
        Log.d(TAG, "onCreating");

        db.execSQL(SQL_CREATE_STATIONS);
        db.execSQL(SQL_CREATE_LINES);
        db.execSQL(SQL_CREATE_FAVORITES);

        initLines(db);
    }

    public void onUpgrade(SQLiteDatabase db, int i, int i2)
    {
        db.execSQL(SQL_DROP_STATIONS);
        db.execSQL(SQL_CREATE_STATIONS);
        db.execSQL(SQL_DROP_FAVORITES);
        db.execSQL(SQL_CREATE_FAVORITES);

        parseFile(db);
        initLines(db);
    }

    public long addStation(SQLiteDatabase db, int staId, String name, float lat, float lng,
                        int red, int blue, int brown,int green,
                        int pink, int purple, int orange, int yellow)
    {
        ContentValues values = new ContentValues();
        values.put("_id", staId);
        values.put("name", name);
        values.put("lat", lat);
        values.put("lng", lng);
        values.put("red", red);
        values.put("blue", blue);
        values.put("orange", orange);
        values.put("yellow", yellow);
        values.put("purple", purple);
        values.put("brown", brown);
        values.put("pink", pink);
        values.put("green", green);

        Log.d(TAG, name + " added to database");

        return db.insert(DB_STATIONS_TABLE_NAME, null, values);

    }
    public void parseFile(SQLiteDatabase db)
    {
        final int NDX_STA_ID    =  0;
        final int NDX_NAME      =  2;
        final int NDX_LATITUDE  =  4;
        final int NDX_LONGITUDE =  5;
        final int NDX_RED       =  9;
        final int NDX_BLUE      = 10;
        final int NDX_BROWN     = 11;
        final int NDX_GREEN     = 12;
        final int NDX_PINK      = 13;
        final int NDX_PURPLE    = 14;
        final int NDX_ORANGE    = 15;
        final int NDX_YELLOW    = 16;
        try{
            InputStream is = context.getAssets().open("stops.txt");
            Scanner in = new Scanner(is);
            String str;
            String[] data;


            while(in.hasNext())
            {
                str = in.nextLine();
                data = str.split(",");
                addStation(

                        db,
                        Integer.parseInt(data[NDX_STA_ID]),
                        data[NDX_NAME].replace("\"", ""),
                        Float.parseFloat(data[NDX_LATITUDE]),
                        Float.parseFloat(data[NDX_LONGITUDE]),
                        parseIntSafe(data[NDX_RED], 0),
                        parseIntSafe(data[NDX_BLUE], 0),
                        parseIntSafe(data[NDX_BROWN], 0),
                        parseIntSafe(data[NDX_GREEN], 0),
                        parseIntSafe(data[NDX_PINK], 0),
                        parseIntSafe(data[NDX_PURPLE], 0),
                        parseIntSafe(data[NDX_ORANGE], 0),
                        parseIntSafe(data[NDX_YELLOW], 0));

                Log.d(TAG, data[NDX_NAME] + " successfully parsed from file");

                Log.d(TAG, data[2] + " successfully parsed from file");
            }

        }
        catch(IOException e)
        {
            Log.e(TAG, "Stop.txt not found: " + e.getMessage());
        }
    }

    private int parseIntSafe(String str, int default_val)
    {
        if ((str == null) || str.length() == 0) {
            return default_val;
        }
        return Integer.parseInt(str);
    }

    public void initLines(SQLiteDatabase db)
    {
        Log.d(TAG, "initLines");
        long rv = 0;
        ContentValues values = new ContentValues();

        values.put("name", "Red Line");
        db.insert(DB_LINES_TABLE_NAME, null, values);
        values.clear();

        values.put("name", "Blue Line");
        db.insert(DB_LINES_TABLE_NAME, null, values);
        values.clear();

        values.put("name", "Yellow Line");
        db.insert(DB_LINES_TABLE_NAME, null, values);
        values.clear();

        values.put("name", "Green Line");
        db.insert(DB_LINES_TABLE_NAME, null, values);
        values.clear();

        values.put("name", "Brown Line");
        db.insert(DB_LINES_TABLE_NAME, null, values);
        values.clear();

        values.put("name", "Orange Line");
        db.insert(DB_LINES_TABLE_NAME, null, values);
        values.clear();

        values.put("name", "Pink Line");
        db.insert(DB_LINES_TABLE_NAME, null, values);
        values.clear();

        values.put("name", "Purple Line");
        db.insert(DB_LINES_TABLE_NAME, null, values);
        values.clear();

    }

    public Cursor getCursor(String line)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        if (line.equals("Red Line"))
        {Log.d(TAG,"Red Line");
            c = db.query(DB_STATIONS_TABLE_NAME, DB_STATIONS_COLUMN_NAMES, "red > 0", null, null, null, "red", null);}
        else if(line.equals("Blue Line"))
            c = db.query(DB_STATIONS_TABLE_NAME, DB_STATIONS_COLUMN_NAMES, "blue > 0", null, null, null, "blue", null);
        else if(line.equals("Green Line"))
            c = db.query(DB_STATIONS_TABLE_NAME, DB_STATIONS_COLUMN_NAMES, "green > 0", null, null, null, "green", null);
        else if(line.equals("Brown Line"))
            c = db.query(DB_STATIONS_TABLE_NAME, DB_STATIONS_COLUMN_NAMES, "brown > 0", null, null, null, "brown", null);
        else if(line.equals("Orange Line"))
            c = db.query(DB_STATIONS_TABLE_NAME, DB_STATIONS_COLUMN_NAMES, "orange > 0", null, null, null, "orange", null);
        else if(line.equals("Yellow Line"))
            c = db.query(DB_STATIONS_TABLE_NAME, DB_STATIONS_COLUMN_NAMES, "yellow > 0", null, null, null, "yellow", null);
        else if(line.equals("Pink Line"))
            c = db.query(DB_STATIONS_TABLE_NAME, DB_STATIONS_COLUMN_NAMES, "pink > 0", null, null, null, "pink", null);
        else if(line.equals("Purple Line"))
            c = db.query(DB_STATIONS_TABLE_NAME, DB_STATIONS_COLUMN_NAMES, "purple > 0", null, null, null, "purple", null);

        return c;
    }

    public Cursor getLineCursor(){
        Log.d(TAG, "getLineCursor");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(DB_LINES_TABLE_NAME,DB_STATIONS_COLUMN_NAMES, null, null, null, null, null, null);
        return c;
    }

    public Cursor getFavoriteCursor(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(DB_FAVORITES_JOIN_STATION, DB_STATIONS_COLUMN_NAMES, null, null, null, null, null, null);
        return c;
    }

    public void addFavorite(String staId)
    {
        Log.d(TAG, "About to Add Favorite");
        ContentValues values = new ContentValues();

        SQLiteDatabase db = this.getWritableDatabase();

        values.put("_id", staId);
        db.insert(DB_FAVORITES_TABLE_NAME, null, values);
        values.clear();
    }

    public void clearFavorites()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + DB_FAVORITES_TABLE_NAME);
        Log.d(TAG, "Cleared favorites");
    }


    public ArrayList<TrainStation> findNearby(Location loc, float meterlimit){
        SQLiteDatabase db = this.getReadableDatabase();
        float lat;
        float lng;
        Location stLoc = new Location(loc);
        ArrayList<TrainStation> results = new ArrayList<TrainStation>();
        String name;
        int id;
        Cursor c = db.query(DB_STATIONS_TABLE_NAME, DB_STATIONS_COLUMN_NAMES,null, null, null, null, null, null);
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            lat = c.getFloat(c.getColumnIndex("lat"));
            lng = c.getFloat(c.getColumnIndex("lng"));
            name = c.getString(c.getColumnIndex("name"));
            id = c.getInt(c.getColumnIndex("_id"));
            stLoc.setLatitude(lat);
            stLoc.setLongitude(lng);
            if(loc.distanceTo(stLoc) <= meterlimit){
                results.add(new TrainStation(id, name, lat, lng));
                Log.d(TAG, ""+ name);
            }
        }

        return results;
    }

}
