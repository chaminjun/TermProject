package my.mobile.com.termproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by chaminjun on 2016. 11. 23..
 */

public class GoalActivity extends Activity{

    Button goal_set_btn;
    RadioGroup radio_study, radio_health, radio_cb, radio_sleep;
    String study_set = "";
    String health_set = "";
    String cb_set = "";
    String sleep_set = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        goal_set_btn = (Button)findViewById(R.id.goal_set_btn);

        radio_study = (RadioGroup)findViewById(R.id.radio_study);
        radio_health = (RadioGroup)findViewById(R.id.radio_health);
        radio_cb = (RadioGroup)findViewById(R.id.radio_cb);
        radio_sleep = (RadioGroup)findViewById(R.id.radio_sleep);

        radio_study.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selected_radio_study_btn = (RadioButton) findViewById(checkedId);
                study_set = selected_radio_study_btn.getText().toString();
            }
        });
        radio_health.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selected_radio_health_btn = (RadioButton) findViewById(checkedId);
                health_set = selected_radio_health_btn.getText().toString();
            }
        });
        radio_cb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selected_radio_cb_btn = (RadioButton) findViewById(checkedId);
                cb_set = selected_radio_cb_btn.getText().toString();
            }
        });
        radio_sleep.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selected_radio_sleep_btn = (RadioButton) findViewById(checkedId);
                sleep_set = selected_radio_sleep_btn.getText().toString();
            }
        });

        goal_set_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoalActivity.this, MainActivity.class);    // 보내는 클래스, 받는 클래스
//                intent.putExtra("STUDY", study_set);
//                intent.putExtra("HEALTH", health_set);
//                intent.putExtra("CIGABEER", cb_set);
//                intent.putExtra("SLEEP", sleep_set);
                MainActivity.mydb3.insertGoal(study_set, health_set, cb_set, sleep_set);
                startActivity(intent);
                finish();

            }
        });
    }

}
