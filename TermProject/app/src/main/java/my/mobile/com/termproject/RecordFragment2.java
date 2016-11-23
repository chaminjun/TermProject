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

import static my.mobile.com.termproject.RecordFragment1.flag1;

/**
 * Created by chaminjun on 2016. 11. 23..
 */

public class RecordFragment2 extends Fragment{

    static int spinnernum2 = 0;
    static EditText edit02;
    static int flag2 = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_record_2, null);
        edit02 = (EditText)view.findViewById(R.id.edit01);
        Spinner s2 = (Spinner)view.findViewById(R.id.spinner2);
        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.RED);
                spinnernum2 = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        flag1 = 0;
        flag2 = 1;
        return view;


    }
}
