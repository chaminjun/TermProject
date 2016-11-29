package my.mobile.com.termproject;

/**
 * Created by chaminjun on 2016. 11. 22..
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class MyDB extends SQLiteOpenHelper {

    public MyDB(Context context){
        super(context, "MyLocation", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE database (_id INTEGER PRIMARY KEY AUTOINCREMENT, hour INTEGER, minute INTEGER, "
                + "latitude REAL , longitude REAL , category INTEGER ,  whatido TEXT, time FLOAT, photo_location TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 만약 member라는 테이블이 존재한다면 날려버려라.
        String sql = "DROP TABLE IF EXISTS database";
        db.execSQL(sql);
        onCreate(db);
    }
    public void insert(int hour, int minute, Double latitude, Double longitude, int category, String whatido, float time, String photo_location){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO database VALUES(NULL, " + hour + ", " + minute + ", " + latitude + ", " + longitude + ", "
                + category + ", '" + whatido + "', " + time + ", '" + photo_location + "');");
        Log.d ("SQL", "select : " + "(hour:" + hour + ")(minute:" + minute + ")(latitude:" + latitude + ")(longitude:" + longitude
                + ")(category:" + category + ")(whatido:" + whatido + ")(time:" + time +")(photo_location: " + photo_location + ")");
        db.close();
    }
    public void showMyMap (ArrayList<MyDataBaseIntent> mylist) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM database", null);

        while (cursor.moveToNext()) {
            int hour = cursor.getInt(cursor.getColumnIndex("hour"));
            int minute = cursor.getInt(cursor.getColumnIndex("minute"));
            Double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
            Double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
            int category = cursor.getInt(cursor.getColumnIndex("category"));
            String whatido = cursor.getString(cursor.getColumnIndex("whatido"));
            float time = cursor.getInt(cursor.getColumnIndex("time"));
            String photo_location = cursor.getString(cursor.getColumnIndex("photo_location"));

            MyDataBaseIntent mbi = new MyDataBaseIntent();

            mbi.hour = hour;
            mbi.minute = minute;
            mbi.latitude = latitude ;
            mbi.longitude = longitude ;
            mbi.category = category ;
            mbi.whatido = whatido ;
            mbi.time = time;
            mbi.photo_location = photo_location;

            mylist.add(mbi) ;
        }
        cursor.close();
        db.close();
    }
}
