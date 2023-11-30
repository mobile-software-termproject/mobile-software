package com.example.dietmannagentapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.io.ByteArrayInputStream;

public class AteShowing extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ate_showing);


        // "DIET LIST" 텍스트뷰
        TextView titleTextView = findViewById(R.id.titleTextView);

        // 리스트뷰
        ListView listView = findViewById(R.id.listView);

        // 식사 데이터를 가져오는 메서드 호출
        Cursor cursor = getDataFromDatabase();

        // 어댑터 설정
        String[] fromColumns = {
                MyContentProvider.RESTAURANT_NAME,
                MyContentProvider.DIET_NAME,
                MyContentProvider.DATE,
                MyContentProvider.TIME,
                MyContentProvider._ID
        };
        int[] toViews = {
                R.id.restaurantTextView,
                R.id.dietNameTextView,
                R.id.dateTextView,
                R.id.timeTextView,
                R.id.DietListTextView
        };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this, R.layout.list_item, cursor, fromColumns, toViews, 0
        );
        listView.setAdapter(adapter);

        // 리스트뷰에 아이템 클릭 이벤트 추가
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 클릭한 아이템의 ID를 가져와 DietInformation 액티비티로 전달
                Intent intent = new Intent(AteShowing.this, DietInformation.class);
                intent.putExtra("dietId", id);
                startActivity(intent);
            }
        });
    }

    private Cursor getDataFromDatabase() {
        // Content Provider를 통해 데이터베이스에서 식사 데이터를 가져옴
        String[] projection = {
                MyContentProvider._ID,
                MyContentProvider.RESTAURANT_NAME,
                MyContentProvider.DIET_NAME,
                MyContentProvider.DATE,
                MyContentProvider.TIME
        };
        return getContentResolver().query(MyContentProvider.CONTENT_URI, projection, null, null, null);
    }
    //식사 분석하기 버튼
    public void analysisEnter(View view)
    {
        Intent intent1=new Intent(this, AteAnalysis.class);
        startActivity(intent1);
    }
    //달력으로 보기 버튼
    public void goToAteCalendar(View view)
    {
        Intent intent2=new Intent(this, AteCalendarForShowing.class);
        startActivity(intent2);
    }

}
