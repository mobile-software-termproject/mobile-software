package com.example.dietmannagentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TimePicker;

public class Time extends AppCompatActivity {
    TimePicker t;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        t = findViewById(R.id.timePicker);
        b=findViewById(R.id.time_set_end);
        // "time_set_end" 버튼 클릭 시 액티비티 종료
        b.setOnClickListener(view -> {
            // 현재 선택된 시간을 가져옴
            int hour = t.getHour();
            int minute = t.getMinute();

            Intent resultIntent = new Intent();
            String selectedTime = hour + "시 " + minute + "분 ";
            resultIntent.putExtra("selectedTime", selectedTime);
            setResult(RESULT_OK, resultIntent);

            // 액티비티 종료
            finish();
        });

    }
}