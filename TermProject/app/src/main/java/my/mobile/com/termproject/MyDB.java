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
        String sql = "CREATE TABLE database (_id INTEGER PRIMARY KEY AUTOINCREMENT, latitude REAL , "
                + "longitude REAL , category INTEGER ,  whatido TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 만약 member라는 테이블이 존재한다면 날려버려라.
        String sql = "DROP TABLE IF EXISTS database";
        db.execSQL(sql);
        onCreate(db);
    }
    public void insert(Double latitude, Double longitude, int category, String whatido){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO database VALUES(NULL, " + latitude + ", " + longitude + ", "
                + category + ", '" + whatido + "');");
        Log.d ("SQL", "select : " + "(latitude:" + latitude + ")(longitude:" + longitude + ")(category:" + category + ")(whatido:" + whatido + ")");
        db.close();
    }
    public void showMyMap (ArrayList<MyDataBaseIntent> mylist) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM database", null);

        while (cursor.moveToNext()) {
            Double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
            Double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
            int category = cursor.getInt(cursor.getColumnIndex("category"));
            String whatido = cursor.getString(cursor.getColumnIndex("whatido"));

            MyDataBaseIntent mbi = new MyDataBaseIntent();

            mbi.latitude = latitude ;
            mbi.longitude = longitude ;
            mbi.category = category ;
            mbi.whatido = whatido ;

            mylist.add(mbi) ;
        }
        cursor.close();
        db.close();
    }
}
