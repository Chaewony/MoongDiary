package com.eustress.moongdiary;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

public class CloudTodayActivity extends AppCompatActivity {
    TabLayout tabs;

    EmotionCloudFragment fragment1;
    CloudDiaryFragment fragment2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloudtody);

        //intent에 원하는 정보가 없을경우에 대한 null체크를 해줘야 하는데
        //make cloud가 아닌, 달력을 통해 cloud today에 들어간다고 해도
        //date는 넘겨줘야되니까 null이 아니려나...
        //여기 저기서 date라는 같은 이름으로 넘겨받아도 되는건가...
        Intent intent = getIntent();
        String date = intent.getStringExtra("date");

        fragment1 = new EmotionCloudFragment();
        fragment2 = new CloudDiaryFragment();

        Bundle bundle = new Bundle(1);; // 파라미터의 숫자는 전달하려는 값의 갯수
        bundle.putString("date", date);
        fragment1.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment1).commit();

        tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("감정 구름"));
        tabs.addTab(tabs.newTab().setText("구름 일기장"));

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;
                if(position == 0)
                    selected = fragment1;
                else if(position == 1)
                    selected = fragment2;
                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
