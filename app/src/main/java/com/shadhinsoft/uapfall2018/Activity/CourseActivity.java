package com.shadhinsoft.uapfall2018.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shadhinsoft.uapfall2018.R;

public class CourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        TextView notice = (TextView) findViewById(R.id.notice_board_course);
        notice.setSelected(true);

    }

    public void onVirtualCourse(View view) {
        switch (view.getId()){
            case R.id.firstfirst:
                Intent intent = new Intent(CourseActivity.this, CourseDetails.class);
                intent.putExtra("semester", "11");
                startActivity(intent);
                break;
            case R.id.firstsecond:
                Intent i = new Intent(CourseActivity.this, CourseDetails.class);
                i.putExtra("semester", "12");
                startActivity(i);
                break;
            case R.id.secondfirst:
                Intent i1 = new Intent(CourseActivity.this, CourseDetails.class);
                i1.putExtra("semester", "21");
                startActivity(i1);
                break;
            case R.id.secondsecond:
                Intent i2 = new Intent(CourseActivity.this, CourseDetails.class);
                i2.putExtra("semester", "22");
                startActivity(i2);
                break;
            case R.id.thirdfirst:
                Intent i3 = new Intent(CourseActivity.this, CourseDetails.class);
                i3.putExtra("semester", "31");
                startActivity(i3);
                break;
            case R.id.thirdsecond:
                Intent i4 = new Intent(CourseActivity.this, CourseDetails.class);
                i4.putExtra("semester", "32");
                startActivity(i4);
                break;
            case R.id.fourthfirst:
                Intent i5 = new Intent(CourseActivity.this, CourseDetails.class);
                i5.putExtra("semester", "32");
                startActivity(i5);
                break;
            case R.id.fourthsecond:
                Intent i6 = new Intent(CourseActivity.this, CourseDetails.class);
                i6.putExtra("semester", "32");
                startActivity(i6);
                break;




        }
    }
}
