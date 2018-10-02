package com.shadhinsoft.uapfall2018.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shadhinsoft.uapfall2018.R;


public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.notice_board);
        textView.setSelected(true);



    }

    public void onVirtualClick(View view) {
        switch (view.getId()){
            case R.id.student_information:
                startActivity(new Intent(MainActivity.this, StudentInformation.class));
                break;
            case R.id.course:
                startActivity(new Intent(MainActivity.this, CourseActivity.class));
                break;
            case R.id.aboutme:
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                break;
            case R.id.notification:
                startActivity(new Intent(MainActivity.this, NotificationActivity.class));
                break;

        }

    }
}
