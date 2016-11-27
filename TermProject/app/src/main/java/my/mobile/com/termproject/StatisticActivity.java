package my.mobile.com.termproject;

import android.app.Activity;
import android.os.Bundle;

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

    MyDB mydb1 = new MyDB(this);
    MyDB mydb2 = new MyDB(this);
    ArrayList<MyDataBaseIntent> mbi = new ArrayList<>();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        BarChart barChart1 = (BarChart)findViewById(R.id.bar_chart_1);
        BarChart barChart2 = (BarChart)findViewById(R.id.bar_chart_2);
        ArrayList<BarEntry> entries1 = new ArrayList<>();
        ArrayList<BarEntry> entries2 = new ArrayList<>();
        ArrayList<String> labels1 = new ArrayList<>();
        ArrayList<String> labels2 = new ArrayList<>();

        mydb1.showMyMap(mbi);
        for (int i = 0; i < mbi.size(); i++) {
            int temp_num = mbi.get(i).category;
            switch (temp_num) {
                case 0: num_study++; break;
                case 1: num_meeting++; break;
                case 2: num_health++; break;
                case 3: num_eating++; break;
                case 4: num_etc++; break;
            }
        }
        mydb2.showMyMap(mbi);
        for (int i = 0; i < mbi.size(); i++) {
            int temp_num = mbi.get(i).category;
            switch (temp_num) {
                case 5: num_show++; break;
                case 6: num_demo++; break;
                case 7: num_adv++; break;
                case 8: num_contest++; break;
                case 9: num_event_etc++; break;
            }
        }

        entries1.add(new BarEntry(num_study, 0));
        entries1.add(new BarEntry(num_meeting, 1));
        entries1.add(new BarEntry(num_health, 2));
        entries1.add(new BarEntry(num_eating, 3));
        entries1.add(new BarEntry(num_etc, 4));

        labels1.add("공부");
        labels1.add("회의");
        labels1.add("운동");
        labels1.add("식사");
        labels1.add("etc");

        BarDataSet barDataSet1 = new BarDataSet(entries1, "한 일 카테고리");
        BarData barData1 = new BarData(labels1, barDataSet1);
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart1.setData(barData1);
        barChart1.animateY(3000);

        entries2.add(new BarEntry(num_show, 0));
        entries2.add(new BarEntry(num_demo, 1));
        entries2.add(new BarEntry(num_adv, 2));
        entries2.add(new BarEntry(num_contest, 3));
        entries2.add(new BarEntry(num_event_etc, 4));

        labels2.add("공연");
        labels2.add("시위");
        labels2.add("광고");
        labels2.add("대회");
        labels2.add("etc");

        BarDataSet barDataSet2 = new BarDataSet(entries2, "이벤트 카테고리");
        BarData barData2 = new BarData(labels2, barDataSet2);
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart2.setData(barData2);
        barChart2.animateY(3000);

    }
}

