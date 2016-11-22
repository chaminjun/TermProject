package my.mobile.com.termproject;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    Intent go_goal, go_record, go_show, go_statistic;
    ImageButton goal_set_btn, record_btn, show_btn, statistic_btn, init_btn;
    MyDB mydb = new MyDB(this);
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        go_goal = new Intent(getApplicationContext(), GoalActivity.class);
        go_record = new Intent(getApplicationContext(), RecordActivity.class);
        go_show = new Intent(getApplicationContext(), ShowActivity.class);
        go_statistic = new Intent(getApplicationContext(), StatisticActivity.class);

        goal_set_btn = (ImageButton) findViewById(R.id.goal_set_btn);
        record_btn = (ImageButton) findViewById(R.id.record_btn);
        show_btn = (ImageButton) findViewById(R.id.show_btn);
        statistic_btn = (ImageButton) findViewById(R.id.statistic_btn);
        init_btn = (ImageButton) findViewById(R.id.init_btn);


        //목표 설정 버튼
        goal_set_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(go_goal);
            }
        });
        //기록하기 버튼
        record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(go_record);
            }
        });
        //조회하기 버튼
        show_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(go_show);
            }
        });
        //통계치 조회하기 버튼
        statistic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(go_statistic);
            }
        });
        //초기화 버튼
        init_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = mydb.getWritableDatabase();
                mydb.onUpgrade(db, 1, 2);
                db.close();
            }
        });
    }
}
