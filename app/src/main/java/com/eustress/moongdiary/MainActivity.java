package com.eustress.moongdiary;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;

public class MainActivity extends AppCompatActivity {

    private CollapsibleCalendar collapsibleCalendar;
    private Button plusCloudBtn;
    private ImageView moongGif;

    private Toolbar toolbar;
    private ActionBar actionBar;
    private TextView toobarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        collapsibleCalendar = findViewById(R.id.calendarView);
        plusCloudBtn = findViewById(R.id.cloudBtn);
        moongGif = findViewById(R.id.moongGif);
        toolbar = findViewById(R.id.toolbar);
        toobarText = findViewById(R.id.toolbarText);

        //움직이는 뭉티, gif 재생
        Glide.with(this).load(R.drawable.gif_moong).into(moongGif);
        //달력 위 이벤트 표시 예제 코드
        collapsibleCalendar.addEventTag(2023, 3, 7, Color.RED);
        //툴바 관련 세팅
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionBar.setDisplayHomeAsUpEnabled(true);
        UpdateToolbar();

        collapsibleCalendar.setCalendarListener(new CollapsibleCalendar.CalendarListener() {
            @Override
            public void onDayChanged() {

            }

            @Override
            public void onClickListener() {

            }

            @Override
            public void onDaySelect() {
                Day day = collapsibleCalendar.getSelectedDay();
                Log.i(getClass().getName(), "Selected Day: "
                        + day.getYear() + "/" + (day.getMonth() + 1) + "/" + day.getDay());

                Toast.makeText(MainActivity.this, "Selected Day: "
                        + day.getYear() + "/" + (day.getMonth() + 1) + "/" + day.getDay(), Toast.LENGTH_SHORT).show();

                // 정수형으로 들어온 데이터를
                // 쿼리문에서 사용할 용도로
                // DATE형 문자열로 바꾸는 과정
                String date = "";
                date += Integer.toString(day.getYear()) + "-";
                if((day.getMonth() + 1)/10==0)
                    date += "0";
                date += Integer.toString((day.getMonth() + 1)) + "-";
                if(day.getDay()/10==0)
                    date += '0';
                date += Integer.toString(day.getDay());

                //오늘의 구름 씬으로 넘어가기
                Intent intent = new Intent(getApplication(), CloudTodayActivity.class);
                intent.putExtra("date", date);
                startActivity(intent);
            }

            @Override
            public void onItemClick(View view) {

            }

            @Override
            public void onDataUpdate() {

            }

            @Override
            public void onMonthChange() {
                UpdateToolbar();
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
                Intent intent = new Intent(getApplication(), MakeCloudActivity.class);
                startActivity(intent);
            }
        });
    }

    void UpdateToolbar()
    {
        toobarText.setText(collapsibleCalendar.getYear() + "년 " + (collapsibleCalendar.getMonth()+1) +"월");
    }
}