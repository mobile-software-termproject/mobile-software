package com.example.dietmannagentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AteAnalysis extends AppCompatActivity {

    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ate_analysis);

        barChart = initBarChart();
        barChart.invalidate();
    }

    private BarChart initBarChart() {
        ArrayList<BarEntry> chartEntry = new ArrayList<>();
        ArrayList<Integer> barColorList = new ArrayList<>();
        barChart = (BarChart) findViewById(R.id.cost_chart);

        // 조식 중식 석식 음료순 초기화
        for (int i=0; i<4; i++) {
            chartEntry.add(new BarEntry(i, 0)); // 조식
        }

        BarDataSet dataSet = new BarDataSet(chartEntry, "식사 및 음료");

        barColorList.add(Color.RED);
        barColorList.add(Color.GREEN);
        barColorList.add(Color.BLUE);
        barColorList.add(Color.YELLOW);
        dataSet.setColors(barColorList);

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        String[] labels = {"조식", "중식", "석식", "음료"};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        YAxis leftAxis = barChart.getAxisLeft();
        YAxis rightAxis = barChart.getAxisRight();
        leftAxis.setAxisMinimum(0f);
        rightAxis.setAxisMinimum(0f);

        return barChart;
    }
}