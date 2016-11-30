package my.mobile.com.termproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import java.util.Calendar;


/**
 * Created by chaminjun on 2016. 11. 22..
 */

public class ShowActivity extends Activity {

    CalendarView calView;
    TextView caltext;
    String strampm;
    Button query_btn_1, query_btn_2, query_btn_3, query_btn_4;
    Intent gomapquery1, gomapquery2;
    Intent golistquery1, golistquery2;

    static int querycountmap = 0;
    static int querycountlist = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

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

        int intampm = Calendar.AM_PM;

        if(intampm == 0) {
            strampm = "오전";
        }else{
            strampm = "오후";
        }
        String today = Calendar.getInstance().get(Calendar.YEAR) +
                "년 " + (Calendar.getInstance().get(Calendar.MONTH) + 1) +
                "월 " + Calendar.getInstance().get(Calendar.DAY_OF_MONTH) +
                "일 \n";
        String nowtime = strampm + " " + Calendar.getInstance().get(Calendar.HOUR) +
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
                startActivity(golistquery1);
                break;
            case R.id.query_btn_4:
                querycountlist = 2;
                startActivity(golistquery2);
                break;
        }
    }



}

