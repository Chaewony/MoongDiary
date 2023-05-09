package com.eustress.moongdiary;

import static android.view.View.GONE;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;

public class EmotionCloudFragment extends Fragment {
    private TextView diaryText;
    DBHelper dbHelper;

    private LinearLayout listView;

    private int index=0;
    private String date="";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_emotioncloud, container, false);
        diaryText = rootView.findViewById(R.id.textView3);
        listView = rootView.findViewById(R.id.listView);

        Bundle extra = this.getArguments();
        dbHelper = new DBHelper(getContext(), 1);
        if(extra != null) {
            extra = getArguments();
            //diaryText.setText(extra.getString("date"));

            date = extra.getString("date"); // 날짜 읽기

            //다이어리 가져오기
            ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
            list = dbHelper.getDiary(extra.getString("date"));

            //diaryText.setText(dbHelper.getDiary(extra.getString("date")));
            for (HashMap<String, String> texts : list) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    texts.forEach((k,v) -> createTextView(k,v));
                    index++;
                }
            }
        }
        return rootView;
    }

    private void createTextView(String time, String text)
    {
        //리니어 레이아웃을 동적 생성
        LinearLayout newListView= new LinearLayout(getContext());
        newListView.setOrientation(LinearLayout.HORIZONTAL);
        newListView.setId(index);
        //공통 사항들
        // 아래 위젯에서 변경하고 싶으면 이 코드를 추가
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin=30;
        //공통 사항들
        
        newListView.setLayoutParams(params);
        listView.addView(newListView);

        //타임 정보가 들어갈 텍스트 뷰를 동적 생성 후
        //위에서 만든 리니어 레이아웃에 넣음
        TextView textViewTime = new TextView(getContext());
        textViewTime.setText(time+"에 작성됨");
        textViewTime.setTextSize(15);
        textViewTime.setTypeface(Typeface.DEFAULT);

        textViewTime.setLayoutParams(params);
        newListView.addView(textViewTime);

        //텍스트가 들어갈 텍스트 뷰를 동적 생성 후
        //위에서 만든 리니어 레이아웃에 넣음
        TextView textViewText = new TextView(getContext());
        textViewText.setText(text);
        textViewText.setTextSize(30);
        textViewText.setTypeface(Typeface.DEFAULT);

        textViewText.setLayoutParams(params);
        newListView.addView(textViewText);

        //버튼을 오른쪽 정렬하기 위한 뷰 생성
        View view = new View(getContext());
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(0, 0,1);
        view.setLayoutParams(param);
        newListView.addView(view);
        
        //삭제 버튼 기능을 할 버튼위젯을 만든 다음
        //위 위에서 만든 리니어 레이아웃에 넣음
        Button deleteBtn = new Button(getContext());
        deleteBtn.setText("삭제하기");
        deleteBtn.setId(index);
        deleteBtn.setLayoutParams(params);
        newListView.addView(deleteBtn);
        //deleteBtn.setOnClickListener(v -> Toast.makeText(getActivity(), time, Toast.LENGTH_SHORT).show());
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //다음 버튼 누르면 일어날 일 처리

                dbHelper.Delete(date, time);

                LinearLayout layout =listView.findViewById(deleteBtn.getId());
                listView.removeView(layout);
            }
        });

    }
}
