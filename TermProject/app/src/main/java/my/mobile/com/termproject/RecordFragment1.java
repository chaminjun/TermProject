package my.mobile.com.termproject;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import static my.mobile.com.termproject.RecordFragment2.flag2;

/**
 * Created by chaminjun on 2016. 11. 23..
 */

public class RecordFragment1 extends Fragment{
    static int spinnernum1 = 0;
    static EditText edit01;
    static int flag1 = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_record_1, null);

        edit01 = (EditText)view.findViewById(R.id.edit01);
        Spinner s1 = (Spinner)view.findViewById(R.id.spinner1);
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.RED);
                spinnernum1 = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        flag1 = 1;
        flag2 = 0;
        return view;
    }
}
