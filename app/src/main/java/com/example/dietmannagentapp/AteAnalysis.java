package com.example.dietmannagentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AteAnalysis extends AppCompatActivity {

    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ate_analysis);

        barChart = (BarChart) findViewById(R.id.cost_chart);
        Cursor resultSet = getAnalyzeCursor();

        updateTotalCalories(resultSet);
        updateBarChart(resultSet,barChart);
    }

    private Cursor getAnalyzeCursor() {
        //현재 날짜에서 30일 이전 날짜 계산
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
        String startDate = dateFormat.format(calendar.getTime());
        String currentDate = dateFormat.format(new Date());

        String[] projection = {"date", "time", "cost","checkbox1"};
        String selection = "date BETWEEN ? AND ?";
        String [] selectionArgs = {startDate,currentDate};

        return getContentResolver().query(
                MyContentProvider.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null
        );
    }

    private TextView updateTotalCalories(Cursor cursor) {
        int totalCalories = 0;
        TextView calorieText = findViewById(R.id.textView16);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int cost = (int) cursor.getFloat(cursor.getColumnIndexOrThrow("cost"));
                    totalCalories += cost;
                } while (cursor.moveToNext());
            }
        }
        
        calorieText.setText(String.valueOf(totalCalories));

        return calorieText;
    }

    private void updateBarChart(Cursor cursor,BarChart barChart) {
        // 0 조식 1 중식 2 석식 3 음료
        int[] mealTypeCost = {0,0,0,0};
        ArrayList<BarEntry> chartEntry = new ArrayList<>();
        Log.i("조회 수","조회 수 : "+cursor.getCount());

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
                    int cost = (int) cursor.getFloat(cursor.getColumnIndexOrThrow("cost"));
                    int isBeverage = cursor.getInt(cursor.getColumnIndexOrThrow("checkbox1"));
                    setMealTypeCost(mealTypeCost, time, isBeverage, cost);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        // Y 축 레이블 값 설정
        for (int i = 0; i < mealTypeCost.length; i++) {
            chartEntry.add(new BarEntry(i, mealTypeCost[i]));
        }

        // Description label 제거
        Description description = new Description();
        description.setText("");
        barChart.setDescription(description);
        
        BarDataSet dataSet = new BarDataSet(chartEntry,"조식, 중식, 석식, 음료 순");
        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        barChart.invalidate();
    }
    
    // return 0 : 조식 1 : 중식 2: 석식 3: 음료
    private void setMealTypeCost(int[] mealTypeCost,String time,int isBeverage,int cost) {
        if (isBeverage == 1) {
            mealTypeCost[3] += cost;
            return;
        }

        int hours = Integer.parseInt(time.split("시")[0]);

        if (hours >= 6 && hours <=10) {
            mealTypeCost[0] += cost;
        } else if (hours >= 11 && hours <=15) {
            mealTypeCost[1] += cost;
        } else if (hours >= 16 && hours <= 20) {
            mealTypeCost[2] += cost;
        }
    }
}