package com.example.rest_thread;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    int value = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        new TaskThreadDoUI().execute("");
    }

    private class TaskThreadDoUI extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            for (int i = 0; i <= 10; i++) {
                int value = i;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                publishProgress();
            }
            return "Изпълнена";
        }

        @Override
        protected void onPreExecute() {
            TextView tex;
            ProgressBar bar;

            bar = findViewById(R.id.progressBar1);
            tex = findViewById(R.id.SecondsTEXT);

            String temp = String.valueOf(0);
            tex.setText(temp);

            bar.setProgress(0);

            Log.d("PreExecute", "Before executing doInBackground...");
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            TextView tex;
            ProgressBar bar;

            bar = findViewById(R.id.progressBar1);
            tex = findViewById(R.id.SecondsTEXT);

            String temp = String.valueOf(value);
            tex.setText(temp);

            bar.setProgress(value);

            Log.d("onProgressUpdate", "\n" + "Updating after calling publishProgress()...");
        }

        @Override
        protected void onPostExecute(String result) {
            TextView tex;
            ProgressBar bar;

            bar = findViewById(R.id.progressBar1);
            tex = findViewById(R.id.SecondsTEXT);

            String temp = String.valueOf(value);
            tex.setText(temp);

            bar.setProgress(value);

            Log.d("onPostExecute", "Did it complete? " + result);
        }
    }

}