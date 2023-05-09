package com.eustress.moongdiary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "MoongDiary.db";

    // DBHelper 생성자
    public DBHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    // Diary Table 생성
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Diary(date DATE, time TIME, text TEXT, cloudColor INT, level INT)");
    }

    // Diary Table Upgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Diary");
        onCreate(db);
    }

    // Diary Table 데이터 입력
    public void insert(String date, String time, String text, int cloudColor, int level) { //같은 날짜에 쓰인 여러 메모를 구별할 수 있도록 id를 부여해야함, 아직 구현 안됨
        SQLiteDatabase db = getWritableDatabase();

        //아래 코드는 insert 가 호출 됐을때의 시간을 기준으로 함
        //db.execSQL("INSERT INTO Diary VALUES(DATE('NOW', 'LOCALTIME'),'" + text + "', " + cloudColor + ", " + level + ")");
        
        //아래 코드는 사용자가 구름 만들기 버튼을 눌렀을 때를 기준으로 함
        db.execSQL("INSERT INTO Diary VALUES('" + date + "','" + time + "','" + text + "', " + cloudColor + ", " + level + ")");
        db.close();
    }

    // Diary Table 데이터 수정
    public void Update(String text, String date, String time) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Diary SET text = '" + text + "' WHERE date = '" + date + "' AND time = '" + time + "' ;");
        db.close();
    }

    //미완성 코드
    public void Delete(String date, String time) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Diary WHERE date = '" + date + "' AND time = '" + time + "' ;");
        db.close();
    }

    // 해당 날짜의 Diary Table 조회
    /*public String getDiary(int year, int month, int day) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // 정수형으로 들어온 데이터를
        // 쿼리문에서 사용할 용도로
        // DATE형 문자열로 바꾸는 과정
        String date = "";
        date += Integer.toString(year) + "-";
        if(month/10==0)
            date += "0";
        date += Integer.toString(month) + "-";
        if(day/10==0)
            date += '0';
        date += Integer.toString(day);

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT text FROM Diary WHERE date = '"+date+"';", null);
        while (cursor.moveToNext()) {
            result += cursor.getString(0);
        }

        return result;
    }*/

    // 해당 날짜의 Diary Table 조회
    public ArrayList<HashMap<String, String>> getDiary(String date) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> texts = new ArrayList<String>();
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();

        // DB에 있는 데이터 중 date영역이 전달받은 date에 해당하는 text만 저장함
        // textCursor 사용하여 임시로 texts 리스트에 저장
        Cursor textCursor = db.rawQuery("SELECT text FROM Diary WHERE date = '"+date+"';", null);
        while (textCursor.moveToNext()) {
            texts.add(textCursor.getString(0));
        }

        // DB에 있는 데이터 중 date영역이 전달받은 date에 해당하는 time만 저장함
        Cursor timeCursor = db.rawQuery("SELECT time FROM Diary WHERE date = '"+date+"';", null);
        int index=0;
        while (timeCursor.moveToNext()) {
            //time과 그에 맞는 text가 저장된 HashMap 쌍을 list에 저장
            //index 변수를 사용해 순서대로 list애 저장함
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(timeCursor.getString(0), texts.get(index));
            list.add(map);
            index++;
        }
        return list;
    }
}
