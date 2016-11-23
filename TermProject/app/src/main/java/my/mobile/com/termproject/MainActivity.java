package my.mobile.com.termproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Intent go_goal, go_record, go_show, go_statistic;
    ImageView goal_set_view, record_view, show_view, statistic_view, init_view;
    MyDB mydb = new MyDB(this);
    SQLiteDatabase db;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        go_goal = new Intent(getApplicationContext(), GoalActivity.class);
        go_record = new Intent(getApplicationContext(), RecordActivity.class);
        go_show = new Intent(getApplicationContext(), ShowActivity.class);
        go_statistic = new Intent(getApplicationContext(), StatisticActivity.class);

        //이미지 버튼을 이미지 뷰로 바꿈(11.23)
        goal_set_view = (ImageView)findViewById(R.id.goal_set_view);
        record_view = (ImageView)findViewById(R.id.record_view);
        show_view = (ImageView)findViewById(R.id.show_view);
        statistic_view = (ImageView)findViewById(R.id.statistic_view);
        init_view = (ImageView)findViewById(R.id.init_view);

        //목표 설정 버튼
        goal_set_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(go_goal);
            }
        });
        //기록하기 버튼
        record_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(go_record);
            }
        });
        //조회하기 버튼
        show_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(go_show);
            }
        });
        //통계치 조회하기 버튼
        statistic_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(go_statistic);
            }
        });
        //초기화 버튼
        init_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);
                // 제목셋팅
                alertDialogBuilder.setTitle("행적 지우기");

                // AlertDialog 셋팅
                alertDialogBuilder.setMessage("지금까지의 기록을 지우시겠습니까?").setCancelable(false)
                        .setPositiveButton("취소",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {

                                        dialog.cancel();
                                    }
                                })
                        .setNegativeButton("지우기",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        Toast.makeText(getApplicationContext(), "성공적으로 초기화 되었습니다.", Toast.LENGTH_SHORT).show();
                                        db = mydb.getWritableDatabase();
                                        mydb.onUpgrade(db, 1, 2);
                                        db.close();
                                    }
                                });
                // 다이얼로그 생성
                AlertDialog alertDialog = alertDialogBuilder.create();
                // 다이얼로그 보여주기
                alertDialog.show();
            }
        });
    }
}
