package my.mobile.com.termproject;

import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by chaminjun on 2016. 11. 30..
 */

public class ShowListAdapter extends CursorAdapter {

    String category1[] = {"공부", "회의", "운동", "식사", "etc"};
    String category2[] = {"공연", "시위", "광고", "대회", "etc"};

    public ShowListAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        //레이아웃 안에 있는 View들을 뿌려준다.
        LayoutInflater inflater = LayoutInflater.from(context);
        //뷰를 만드는데 row 레이아웃을 이용하여 부모에 뿌린다
        View v = inflater.inflate(R.layout.activity_query_list_row, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView query_list_diary = (TextView)view.findViewById(R.id.query_list_diary);
        ImageView query_list_photo = (ImageView)view.findViewById(R.id.query_list_photo);


        String order = cursor.getString(0);
        int hour = cursor.getInt(cursor.getColumnIndex("hour"));
        int minute = cursor.getInt(cursor.getColumnIndex("minute"));
        Double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
        Double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
        int category = cursor.getInt(cursor.getColumnIndex("category"));
        String whatido = cursor.getString(cursor.getColumnIndex("whatido"));
        float time = cursor.getInt(cursor.getColumnIndex("time"));
        String photo_location = cursor.getString(cursor.getColumnIndex("photo_location"));

        category %= 5;
        int order1 = Integer.parseInt(order);
        order1 -= 1;
        String list_diary = "";

        switch (ShowActivity.querycountlist) {
            case 1:
                list_diary = hour + "시 " + minute + "분 \n" + (int) time + "초 동안\n"
                        + category1[category] + "를 하였습니다.\n구체적으로는 " + whatido +"\n"
                        + ShowActivity.tagdistance.get(order1) + "m 이동 후 다음 일을 하였습니다.";
                break;
            case 2:
                list_diary = hour + "시 " + minute + "분 \n" + (int) time + "초 동안\n"
                        + category2[category] + "를 하였습니다.\n구체적으로는 " + whatido+"\n"
                        + ShowActivity.eventdistance.get(order1) + "m 이동 후 다음 일을 하였습니다.";
                break;
        }
        query_list_diary.setText(list_diary);
        query_list_photo.setImageURI(Uri.parse(photo_location));
    }
}
