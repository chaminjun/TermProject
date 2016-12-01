package my.mobile.com.termproject;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by chaminjun on 2016. 11. 30..
 */

public class ShowListActivity1 extends Activity {

    SQLiteDatabase sqlite;
    ShowListAdapter queryAdapter;
    ListView query_list1;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_list1);

        query_list1 = (ListView)findViewById(R.id.query_List1);

        getquery();
    }
    public void getquery(){
        sqlite = MainActivity.mydb1.getReadableDatabase();
        adaptQuery(query_list1, sqlite);
    }
    public void adaptQuery(ListView query_list, SQLiteDatabase sqlite) {
        String sql = "SELECT * FROM database";

        cursor = sqlite.rawQuery(sql, null);
        if (cursor.getCount() > 0) {          //데이터 있는지 없는지만 확인하고
            startManagingCursor(cursor);    //cursor에 대한 managing 권한을 넘겨줌
            queryAdapter = new ShowListAdapter(this, cursor);
            query_list.setAdapter(queryAdapter);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        sqlite.close();
    }
}
