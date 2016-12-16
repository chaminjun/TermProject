package my.mobile.com.termproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    Intent go_goal, go_record, go_show, go_statistic;
    ImageView goal_set_view, record_view, show_view, statistic_view, init_view;
    TextView goal_text;

    ArrayList<MyGoalDataBaseIntent> myGoalDataBaseIntents = new ArrayList<>();

    Queue<String> goal_queue;
    final Context context = this;
    private String goal_queue_size[] = new String[5];
    private int front = -1, rear = -1;

    static MyDB mydb1;
    static MyDB mydb2;
    static MyDB mydb3;
    SQLiteDatabase db1;
    SQLiteDatabase db2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //각 액티비티로 intent를 이용하여 넘겨줌
        go_goal = new Intent(getApplicationContext(), GoalActivity.class);
        go_record = new Intent(getApplicationContext(), RecordActivity.class);
        go_show = new Intent(getApplicationContext(), ShowActivity.class);
        go_statistic = new Intent(getApplicationContext(), StatisticActivity.class);

        //이미지 버튼을 이미지 뷰로 바꿈
        goal_set_view = (ImageView)findViewById(R.id.goal_set_view);
        record_view = (ImageView)findViewById(R.id.record_view);
        show_view = (ImageView)findViewById(R.id.show_view);
        statistic_view = (ImageView)findViewById(R.id.statistic_view);
        init_view = (ImageView)findViewById(R.id.init_view);

        //목표량을 보여주기 위한 TextView
        goal_text = (TextView)findViewById(R.id.goal_text);

        //한 일과 이벤트에 따른 db 생성 및 이름 저장
        mydb1 = new MyDB(getApplicationContext(), "task");
        mydb2 = new MyDB(getApplicationContext(), "event");
        mydb3 = new MyDB(getApplicationContext(), "goal");

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
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
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
                                        db1 = mydb1.getWritableDatabase();
                                        mydb1.onUpgrade(db1, 1, 2);
                                        db1.close();
                                        db2 = mydb2.getWritableDatabase();
                                        mydb2.onUpgrade(db2, 1, 2);
                                        db2.close();
                                    }
                                });
                // 다이얼로그 생성
                AlertDialog alertDialog = alertDialogBuilder.create();
                // 다이얼로그 보여주기
                alertDialog.show();
            }
        });

        try {
            mydb3.showMyGoal(myGoalDataBaseIntents);
            int order = myGoalDataBaseIntents.size()-1;
            goal_text.setText("공부는  " + myGoalDataBaseIntents.get(order).study + "\n운동은  " + myGoalDataBaseIntents.get(order).health
                    + "\n담배와 술은  " + myGoalDataBaseIntents.get(order).cb + "\n잠은  " + myGoalDataBaseIntents.get(order).sleep);
        }catch(Exception e){
            goal_text.setText("오늘의 목표를 설정해 주세요");
        }
    }
}
