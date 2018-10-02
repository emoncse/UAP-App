package com.shadhinsoft.uapfall2018.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shadhinsoft.uapfall2018.Model.CourseModel;
import com.shadhinsoft.uapfall2018.R;
import com.shadhinsoft.uapfall2018.Utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CourseDetails extends AppCompatActivity {

    private String semesterName ="";
    private TextView show_semester, codeCourse;



    private ListView listView;
    private ProgressDialog dialog;
    String chalaki = "http://shadhinsoft.com/UAP_CSE/course.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        try {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            requestWindowFeature(Window.FEATURE_NO_TITLE);

        } catch (Exception e) {

        }

        codeCourse = (TextView) findViewById(R.id.code_course);
        codeCourse.setSelected(true);

        try {

            dialog = new ProgressDialog(this);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.setMessage("Loading. Please wait..."); // showing a dialog for loading the data


            listView = (ListView) findViewById(R.id.listview);
            show_semester = (TextView) findViewById(R.id.semester_name_here);

            new JSONTask().execute(chalaki);

            semesterName = "";
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();

            if (bundle != null) {
                semesterName = bundle.getString("semester");
            }


            if (semesterName.equals("11")){
                show_semester.setText("1st Year 1st Semester");
            }
            else if (semesterName.equals("12")){
                show_semester.setText("1st Year 2nd Semester");
            }else if (semesterName.equals("21")){
                show_semester.setText("2nd Year 1st Semester");
            }else if (semesterName.equals("22")){
                show_semester.setText("2nd Year 2nd Semester");
            }else if (semesterName.equals("31")){
                show_semester.setText("3rd Year 1st Semester");
            }else if (semesterName.equals("32")){
                show_semester.setText("3rd Year 2nd Semester");
            }else if (semesterName.equals("41")){
                show_semester.setText("4th Year 1st Semester");
            }else if (semesterName.equals("42")){
                show_semester.setText("4th Year 2nd Semester");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressLint("StaticFieldLeak")
    public class JSONTask extends AsyncTask<String, String, List<CourseModel>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<CourseModel> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                System.out.println("Emon Json  ---->"+finalJson.toString());

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray(semesterName);

                List<CourseModel> courseModelList = new ArrayList<>();

                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    CourseModel courseModel = gson.fromJson(finalObject.toString(), CourseModel.class);

                    courseModel.setCouse_code(finalObject.getString("course_code"));
                    courseModel.setCourse_name(finalObject.getString("course_title"));
                    courseModel.setCourse_credit(finalObject.getString("credits"));


                    courseModelList.add(courseModel);

                }
                return courseModelList;

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(final List<CourseModel> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            if (result != null) {
                CourseAdapter adapter = new CourseAdapter(getApplicationContext(), R.layout.course_row, result);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {  // list item click opens a new detailed activity
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        CourseModel courseModel = result.get(position);
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Sorry,you have not any request!!! Or Check your internet connectivity.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class CourseAdapter extends ArrayAdapter {

        private List<CourseModel> courseModelList;
        private int resource;
        private LayoutInflater inflater;

        private CourseAdapter(Context context, int resource, List<CourseModel> objects) {
            super(context, resource, objects);
            courseModelList = objects;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @SuppressLint("SetTextI18n")
        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
          //  System.out.println("Emon    getView");

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(resource, null);

                holder.code = (TextView) convertView.findViewById(R.id.row_code_course);
                holder.name = (TextView) convertView.findViewById(R.id.row_name_course);
                holder.credit = (TextView) convertView.findViewById(R.id.row_credit_course);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final ViewHolder finalHolder = holder;


                holder.code.setText(courseModelList.get(position).getCouse_code());
                holder.name.setText(courseModelList.get(position).getCourse_name());
                holder.credit.setText(courseModelList.get(position).getCourse_credit());

                System.out.println("Emon Code   :" + courseModelList.get(position).getCouse_code());
                System.out.println("Emon Name   :" + courseModelList.get(position).getCourse_name());
                System.out.println("Emon Credit :" + courseModelList.get(position).getCourse_credit());

            return convertView;
        }

        class ViewHolder {
            TextView code, name, credit;
        }
    }
}
