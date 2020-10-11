package com.example.boiteoutils;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;

public class MinuteurActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minuteur);

        progressBar = findViewById(R.id.progressBar);
    }

    public void startAsyncTask(View v){
        TacheLongue task = new TacheLongue(this);

        EditText editText = findViewById(R.id.editTextNumber2);
        String num = editText.getText().toString();
        int i;
        try{
            i = Integer.parseInt(num);
        } catch (Exception e){
            i = 10;
            Toast.makeText(MinuteurActivity.this, "Le temps est initialement à 10 secondes" , Toast.LENGTH_SHORT).show();
        }
        task.execute(i);
    }

    private static class TacheLongue extends AsyncTask<Integer, Integer, String>{
        private final WeakReference<MinuteurActivity> activityWeakReference;

        public TacheLongue(MinuteurActivity activity){
            activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            MinuteurActivity activity = activityWeakReference.get();
            if(activity == null || activity.isFinishing()){
                return;
            }
            activity.progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(Integer... p){
            for(int i = 0; i < p[0]; i++){
                publishProgress((i*100) / p[0]);
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

            return "Terminer";
        }


        @Override
        protected void onProgressUpdate(Integer... p){
            super.onProgressUpdate(p);

            MinuteurActivity activity = activityWeakReference.get();
            if(activity == null || activity.isFinishing()){
                return;
            }
            activity.progressBar.setProgress(p[0]);
        }

        @Override
        protected void onPostExecute(String r){
            super.onPostExecute(r);

            MinuteurActivity activity = activityWeakReference.get() ;
            if(activity == null || activity.isFinishing()){
                return;
            }

            Toast.makeText(activity,r,Toast.LENGTH_SHORT).show();
            activity.progressBar.setProgress(0);
            activity.progressBar.setVisibility(View.INVISIBLE);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_quitter){
            finish();
            return true;
        }
        return false;
    }

}