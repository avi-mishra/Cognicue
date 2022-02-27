package com.example.selfcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;

public class ReminderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        CardView card1 = findViewById(R.id.card1);
        CardView card2 = findViewById(R.id.card2);
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent insertCalendarIntent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI);
        startActivity(insertCalendarIntent);
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        long CALENDAR_EVENT_TIME = System.currentTimeMillis();
        ContentUris.appendId(CalendarContract.CONTENT_URI.buildUpon().appendPath("time"), CALENDAR_EVENT_TIME);
        Uri uri = CalendarContract.CONTENT_URI.buildUpon().appendPath("time").build();
                Intent intent = new Intent(Intent.ACTION_VIEW)
                .setData(uri);
        startActivity(intent);

            }
        });



//        Intent insertCalendarIntent = new Intent(Intent.ACTION_INSERT)
//                .setData(CalendarContract.Events.CONTENT_URI);
//        startActivity(insertCalendarIntent);


//        Long CALENDAR_EVENT_TIME = System.currentTimeMillis(); // 2019-01-01 00:00:00
//
//// Part 1: URI construction
//
//        ContentUris.appendId(CalendarContract.CONTENT_URI.buildUpon().appendPath("time"), CALENDAR_EVENT_TIME);
//
//
//        Uri uri = CalendarContract.CONTENT_URI.buildUpon().appendPath("time").build(); // Log: content://com.android.calendar/time/1546300800000
//
//// Part 2: Set Intent action to Intent.ACTION_VIEW
//        Intent intent = new Intent(Intent.ACTION_VIEW)
//                .setData(uri);
//        startActivity(intent);

    }
}