package my.mobile.com.termproject;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

/**
 * Created by chaminjun on 2016. 11. 30..
 */

public class ShowListActivity2 extends Activity {

    Cursor cursor;
    SQLiteDatabase sqlite;
    ShowListAdapter queryAdapter;
    ListView query_list2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_list2);

        query_list2 = (ListView)findViewById(R.id.query_List2);

        getqueryForCursorAdapter();
    }

    public void getqueryForCursorAdapter(){
        sqlite = MainActivity.mydb2.getReadableDatabase();
        adaptQuery(query_list2, sqlite);
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
