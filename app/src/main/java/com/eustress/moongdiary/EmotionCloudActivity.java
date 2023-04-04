package com.eustress.moongdiary;

import static android.view.View.GONE;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.Slider;

public class EmotionCloudActivity extends AppCompatActivity {
    private Slider discreteSlider;
    private ImageView yellowCloud;
    private ImageView orangeCloud;
    private ImageView pinkCloud;
    private Button nextBtn;
    private TextView textView;
    private Button doneBtn;
    private com.google.android.material.textfield.TextInputEditText emotionEdt;

    //선택된 cloud를 구분하기 위한 배열, 0은 선택x 1이 선택o
    int[] clouds = { 0, 1, 0 }; //0번: yellow, 1번: orange, 2번: pink

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotioncloud);

        discreteSlider = findViewById(R.id.discreteSlider);
        yellowCloud = findViewById(R.id.yellowCloud);
        orangeCloud = findViewById(R.id.orangeCloud);
        pinkCloud = findViewById(R.id.pinkCloud);
        nextBtn = findViewById(R.id.nextBtn);
        textView = findViewById(R.id.textView);
        doneBtn = findViewById(R.id.doneBtn);
        emotionEdt = findViewById(R.id.emotionEdt);

        //색상 구름 클릭하면 슬라이더 색 바꿔주는거
        yellowCloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discreteSlider.setTickActiveTintList(getResources().getColorStateList(R.color.yellow_cloud));
                discreteSlider.setTrackActiveTintList(getResources().getColorStateList(R.color.yellow_cloud));
                
                //선택된 구름을 구분하기 위한 업데이트
                clouds[0] = 1;
                clouds[1] = 0;
                clouds[2] = 0;
            }

        });

        //색상 구름 클릭하면 슬라이더 색 바꿔주는거
        orangeCloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discreteSlider.setTickActiveTintList(getResources().getColorStateList(R.color.orange_cloud));
                discreteSlider.setTrackActiveTintList(getResources().getColorStateList(R.color.orange_cloud));

                //선택된 구름을 구분하기 위한 업데이트
                clouds[0] = 0;
                clouds[1] = 1;
                clouds[2] = 0;
            }

        });

        //색상 구름 클릭하면 슬라이더 색 바꿔주는거
        pinkCloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discreteSlider.setTickActiveTintList(getResources().getColorStateList(R.color.pink_cloud));
                discreteSlider.setTrackActiveTintList(getResources().getColorStateList(R.color.pink_cloud));

                //선택된 구름을 구분하기 위한 업데이트
                clouds[0] = 0;
                clouds[1] = 0;
                clouds[2] = 1;
            }

        });
        
        //나중에 구름들 다 묶어서 배열이나 다른 자료구조로 관리하고 싶다
        //겹치는거 반복문으로 구현하는걸로 바꾸고 싶다
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //다음 버튼 누르면 일어날 일 처리
                //선택된 구름은 선택 불가능하게 하고
                //선택된 구름 제외하고는 없애고
                if(clouds[0]==1){
                    yellowCloud.setClickable(false);
                    orangeCloud.setVisibility(GONE);
                    pinkCloud.setVisibility(GONE);
                }
                else if(clouds[1]==1){
                    orangeCloud.setClickable(false);
                    yellowCloud.setVisibility(GONE);
                    pinkCloud.setVisibility(GONE);
                }
                else{
                    pinkCloud.setClickable(false);
                    yellowCloud.setVisibility(GONE);
                    orangeCloud.setVisibility(GONE);
                }

                //슬라이드 비활성화 하고
                discreteSlider.setEnabled(false);
                discreteSlider.setVisibility(GONE);

                //다음 버튼 비활성화 하고
                nextBtn.setVisibility(GONE);
                
                //텍스트 박스 내용 바꾸고
                textView.setText("감정에 이름을 붙여보자");
                
                //감정구름 설명 edt box 활성화 하고
                emotionEdt.setEnabled(true);
                emotionEdt.setVisibility(View.VISIBLE);
                
                //완료 버튼 활성화
                doneBtn.setEnabled(true);
            }
        });

        //완료 버튼 클릭 시 실행될 코드
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EmotionCloudActivity.this, emotionEdt.getText().toString().trim(), Toast.LENGTH_SHORT).show();
            }

        });
    }
}
