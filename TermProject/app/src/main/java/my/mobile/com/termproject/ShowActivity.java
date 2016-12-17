package my.mobile.com.termproject;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by chaminjun on 2016. 11. 22..
 */

public class ShowActivity extends Activity {

    CalendarView calView;
    TextView caltext;
    ImageView weather;
    Button query_btn_1, query_btn_2, query_btn_3, query_btn_4;
    Intent gomapquery1, gomapquery2;
    Intent golistquery1, golistquery2;

    static int querycountmap = 0;
    static int querycountlist = 0;
    static ArrayList<Integer> tagdistance = new ArrayList<>();
    static ArrayList<Integer> eventdistance = new ArrayList<>();

    ArrayList<MyDataBaseIntent> mbi = new ArrayList<>();
    ArrayList<MyDataBaseIntent> mbi1 = new ArrayList<>();
    ArrayList<MyDataBaseIntent> mbi2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        weather = (ImageView)findViewById(R.id.weather);

        calView = (CalendarView)findViewById(R.id.calView);
        caltext = (TextView)findViewById(R.id.caltext);
        query_btn_1 = (Button)findViewById(R.id.query_btn_1);
        query_btn_2 = (Button)findViewById(R.id.query_btn_2);
        query_btn_3 = (Button)findViewById(R.id.query_btn_3);
        query_btn_4 = (Button)findViewById(R.id.query_btn_4);

        gomapquery1 = new Intent(ShowActivity.this, MapActivity1.class);
        gomapquery2 = new Intent(ShowActivity.this, MapActivity2.class);

        golistquery1 = new Intent(ShowActivity.this, ShowListActivity1.class);
        golistquery2 = new Intent(ShowActivity.this, ShowListActivity2.class);

        calView.setEnabled(false);

        showToday();
    }

    public void weatherClick(View v){
        Intent intent = new Intent(getApplicationContext(), ShowWeatherActivity.class);
        startActivity(intent);
    }

    public void showToday(){
        String str_am_pm = "";
        int am_pm = Calendar.getInstance().get(Calendar.AM_PM);
        if(am_pm == 0){
            str_am_pm = "오전";
        }else{
            str_am_pm = "오후";
        }

        String today = Calendar.getInstance().get(Calendar.YEAR) +
                "년 " + (Calendar.getInstance().get(Calendar.MONTH) + 1) +
                "월 " + Calendar.getInstance().get(Calendar.DAY_OF_MONTH) +
                "일 \n";
        String nowtime = str_am_pm + " " +Calendar.getInstance().get(Calendar.HOUR_OF_DAY) +
                "시 " + Calendar.getInstance().get(Calendar.MINUTE) + "분 까지 남긴 행적 보기";
        caltext.setText(today + nowtime);
    }

    public void qOnclick(View v) {
        switch (v.getId()) {
            case R.id.query_btn_1:
                querycountmap = 1;
                startActivity(gomapquery1);
                break;
            case R.id.query_btn_2:
                querycountmap = 2;
                startActivity(gomapquery2);
                break;
            case R.id.query_btn_3:
                querycountlist = 1;
                walkdistance(MainActivity.mydb1, tagdistance);
                startActivity(golistquery1);
                break;
            case R.id.query_btn_4:
                querycountlist = 2;
                walkdistance(MainActivity.mydb2, eventdistance);
                startActivity(golistquery2);
                break;
        }
    }
    public void walkdistance(MyDB mydb, ArrayList<Integer> distanceList) {
        if(querycountlist == 1){
            mbi = mbi1;
        }else{
            mbi = mbi2;
        }
        mydb.showMyMap(mbi);
        for (int i = 0; i < mbi.size(); i++) {
            if (i == mbi.size() - 1) {
                distanceList.add(0);
            } else {
                Location start = new Location("start");
                start.setLatitude(mbi.get(i).latitude);
                start.setLongitude(mbi.get(i).longitude);

                Location end = new Location("end");
                end.setLatitude(mbi.get(i + 1).latitude);
                end.setLongitude(mbi.get(i + 1).longitude);

                int distance = (int) start.distanceTo(end);
                distanceList.add(distance);
            }
        }
    }
}

