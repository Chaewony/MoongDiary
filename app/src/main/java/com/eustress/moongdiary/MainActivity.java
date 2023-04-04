package com.eustress.moongdiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private CollapsibleCalendar collapsibleCalendar;
    private Button plusCloudBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        collapsibleCalendar = findViewById(R.id.calendarView);
        plusCloudBtn = findViewById(R.id.cloudBtn);

        collapsibleCalendar.setCalendarListener(new CollapsibleCalendar.CalendarListener() {
            @Override
            public void onDaySelect() {
                Day day = collapsibleCalendar.getSelectedDay();
                Log.i(getClass().getName(), "Selected Day: "
                        + day.getYear() + "/" + (day.getMonth() + 1) + "/" + day.getDay());

                Toast.makeText(MainActivity.this, "Selected Day: "
                        + day.getYear() + "/" + (day.getMonth() + 1) + "/" + day.getDay(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemClick(View view) {

            }

            @Override
            public void onDataUpdate() {

            }

            @Override
            public void onMonthChange() {

            }

            @Override
            public void onWeekChange(int i) {

            }
        });

        //구름모양 일기 추가하기 버튼
        plusCloudBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //버튼이 눌렸을 때
                //감정 구름 씬으로 넘어가기
                Intent intent = new Intent(getApplication(), EmotionCloud.class);
                startActivity(intent);
            }
        });
    }
}