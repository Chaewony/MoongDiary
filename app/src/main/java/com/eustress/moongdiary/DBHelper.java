package com.eustress.moongdiary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "MoongDiary.db";

    // DBHelper 생성자
    public DBHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    // Diary Table 생성
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Diary(date DATE, text TEXT, cloudColor INT, level INT)");
    }

    // Diary Table Upgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Diary");
        onCreate(db);
    }

    // Diary Table 데이터 입력
    public void insert(String text, int cloudColor, int level) { //같은 날짜에 쓰인 여러 메모를 구별할 수 있도록 id를 부여해야함, 아직 구현 안됨
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("INSERT INTO Diary VALUES(DATE('NOW', 'LOCALTIME'),'" + text + "', " + cloudColor + ", " + level + ")");
        db.close();
    }

    // 해당 날짜의 Diary Table 조회
    public String getDiary(int year, int month, int day) {
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
        if(month/10==0)
            date += '0';
        date += Integer.toString(day);

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT text FROM Diary WHERE date = '"+date+"';", null);
        while (cursor.moveToNext()) {
            result += cursor.getString(0);
        }

        return result;
    }
}
