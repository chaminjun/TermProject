package my.mobile.com.termproject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by chaminjun on 2016. 11. 23..
 */

public class StatisticActivity extends Activity{


    ArrayList<MyDataBaseIntent> mbi1 = new ArrayList<>();
    ArrayList<MyDataBaseIntent> mbi2 = new ArrayList<>();
    float num_study = 0f;
    float num_meeting = 0f;
    float num_health = 0f;
    float num_eating = 0f;
    float num_etc = 0f;
    float num_show = 0f;
    float num_demo = 0f;
    float num_adv = 0f;
    float num_contest = 0f;
    float num_event_etc = 0f;

    float num_study_time = 0;
    float num_meeting_time = 0;
    float num_health_time = 0;
    float num_eating_time = 0;
    float num_etc_time = 0;
    float num_show_time = 0;
    float num_demo_time = 0;
    float num_adv_time = 0;
    float num_contest_time = 0;
    float num_event_etc_time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        BarChart barChart1 = (BarChart)findViewById(R.id.bar_chart_1);
        BarChart barChart2 = (BarChart)findViewById(R.id.bar_chart_2);
        ArrayList<BarEntry> do_entries1 = new ArrayList<>();
        ArrayList<BarEntry> do_entries2 = new ArrayList<>();
        ArrayList<BarEntry> event_entries1 = new ArrayList<>();
        ArrayList<BarEntry> event_entries2 = new ArrayList<>();
        ArrayList<String> do_labels = new ArrayList<>();
        ArrayList<String> event_labels = new ArrayList<>();

        MainActivity.mydb1.showMyMap(mbi1);
        for (int i = 0; i < mbi1.size(); i++) {
            int temp_num = mbi1.get(i).category;
            switch (temp_num) {
                case 0: num_study++;
                        num_study_time += mbi1.get(i).time;
                        break;
                case 1: num_meeting++;
                        num_meeting_time += mbi1.get(i).time;
                        break;
                case 2: num_health++;
                        num_health_time += mbi1.get(i).time;
                        break;
                case 3: num_eating++;
                        num_eating_time += mbi1.get(i).time;
                        break;
                case 4: num_etc++;
                        num_etc_time += mbi1.get(i).time;
                        break;
            }
        }
        MainActivity.mydb2.showMyMap(mbi2);
        for (int i = 0; i < mbi2.size(); i++) {
            int temp_num = mbi2.get(i).category;
            switch (temp_num) {
                case 5: num_show++;
                        num_show_time += mbi2.get(i).time;
                        break;
                case 6: num_demo++;
                        num_demo_time += mbi2.get(i).time;
                        break;
                case 7: num_adv++;
                        num_adv_time += mbi2.get(i).time;
                        break;
                case 8: num_contest++;
                        num_contest_time += mbi2.get(i).time;
                        break;
                case 9: num_event_etc++;
                        num_event_etc_time += mbi2.get(i).time;
                        break;
            }
        }

        do_labels.add("공부");
        do_labels.add("회의");
        do_labels.add("운동");
        do_labels.add("식사");
        do_labels.add("etc");

        do_entries1.add(new BarEntry(num_study, 0));
        do_entries1.add(new BarEntry(num_meeting, 1));
        do_entries1.add(new BarEntry(num_health, 2));
        do_entries1.add(new BarEntry(num_eating, 3));
        do_entries1.add(new BarEntry(num_etc, 4));

        do_entries2.add(new BarEntry(num_study_time, 0));
        do_entries2.add(new BarEntry(num_meeting_time, 1));
        do_entries2.add(new BarEntry(num_health_time, 2));
        do_entries2.add(new BarEntry(num_eating_time, 3));
        do_entries2.add(new BarEntry(num_etc_time, 4));

        BarDataSet do_barDataSet1 = new BarDataSet(do_entries1, "한 일 횟수");
        do_barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

        BarDataSet do_barDataSet2 = new BarDataSet(do_entries2, "한 일 시간");
        do_barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        ArrayList<BarDataSet> do_dataset = new ArrayList<>();
        do_dataset.add(do_barDataSet1);
        do_dataset.add(do_barDataSet2);

        BarData do_barData = new BarData(do_labels, do_dataset);
        barChart1.setData(do_barData);
        barChart1.animateY(3000);

        event_labels.add("공연");
        event_labels.add("시위");
        event_labels.add("광고");
        event_labels.add("대회");
        event_labels.add("etc");

        event_entries1.add(new BarEntry(num_show, 0));
        event_entries1.add(new BarEntry(num_demo, 1));
        event_entries1.add(new BarEntry(num_adv, 2));
        event_entries1.add(new BarEntry(num_contest, 3));
        event_entries1.add(new BarEntry(num_event_etc, 4));

        event_entries2.add(new BarEntry(num_show_time, 0));
        event_entries2.add(new BarEntry(num_demo_time, 1));
        event_entries2.add(new BarEntry(num_adv_time, 2));
        event_entries2.add(new BarEntry(num_contest_time, 3));
        event_entries2.add(new BarEntry(num_event_etc_time, 4));

        BarDataSet event_barDataSet1 = new BarDataSet(event_entries1, "이벤트 횟수");
        event_barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

        BarDataSet event_barDataSet2 = new BarDataSet(event_entries2, "이벤트 시간");
        event_barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        ArrayList<BarDataSet> event_dataset = new ArrayList<>();
        event_dataset.add(event_barDataSet1);
        event_dataset.add(event_barDataSet2);

        BarData event_barData = new BarData(event_labels, event_dataset);
        barChart2.setData(event_barData);
        barChart2.animateY(3000);
    }
}

