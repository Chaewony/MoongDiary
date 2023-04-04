package com.eustress.moongdiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class EmotionCloudFragment extends Fragment {
    private TextView diaryText;
    DBHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_emotioncloud, container, false);
        diaryText = rootView.findViewById(R.id.textView3);

        Bundle extra = this.getArguments();
        dbHelper = new DBHelper(getContext(), 1);
        if(extra != null) {
            extra = getArguments();
            //diaryText.setText(extra.getString("date"));

            diaryText.setText(dbHelper.getDiary(extra.getString("date")));
        }
        return rootView;
    }
}
