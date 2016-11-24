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

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    Intent go_goal, go_record, go_show, go_statistic;
    ImageView goal_set_view, record_view, show_view, statistic_view, init_view;
    TextView goal_text;

    Queue<String> goal_queue;
    final Context context = this;
    private String goal_queue_size[] = new String[5];
    private int front = -1, rear = -1;

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

        //이미지 버튼을 이미지 뷰로 바꿈(11.23)
        goal_set_view = (ImageView)findViewById(R.id.goal_set_view);
        record_view = (ImageView)findViewById(R.id.record_view);
        show_view = (ImageView)findViewById(R.id.show_view);
        statistic_view = (ImageView)findViewById(R.id.statistic_view);
        init_view = (ImageView)findViewById(R.id.init_view);

        goal_text = (TextView)findViewById(R.id.goal_text);

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

        goal_queue = new Queue<String>() {
            @Override
            public boolean add(String s) {
                goal_queue_size[++rear] = s;
                return false;
            }

            @Override
            public boolean offer(String s) {
                return false;
            }

            @Override
            public String remove() {
                return goal_queue_size[++front];
            }

            @Override
            public String poll() {
                return null;
            }

            @Override
            public String element() {
                return null;
            }

            @Override
            public String peek() {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<String> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends String> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }
        };

        Intent intent = getIntent();
        try{
            goal_queue.add(intent.getExtras().getString("STUDY"));
            goal_queue.add(intent.getExtras().getString("HEALTH"));
            goal_queue.add(intent.getExtras().getString("CIGABEER"));
            goal_queue.add(intent.getExtras().getString("SLEEP"));
            goal_text.setText("공부는  "+goal_queue.remove()+"\n운동은  "+goal_queue.remove()
                    +"\n담배와 술은  "+goal_queue.remove()+"\n잠은  "+goal_queue.remove());
        }catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
