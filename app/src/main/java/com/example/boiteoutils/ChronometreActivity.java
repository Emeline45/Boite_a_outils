package com.example.boiteoutils;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ChronometreActivity extends AppCompatActivity {

    private GregorianCalendar time;
    private GregorianCalendar time2;
    private int number = 0;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometre);

        Toast.makeText(ChronometreActivity.this, "Le chronometre", Toast.LENGTH_SHORT).show();

        time = new GregorianCalendar();

        ImageButton start = findViewById(R.id.playStop);
        start.setOnClickListener((v)-> {
            if(number == 0) {
                GregorianCalendar heure = new GregorianCalendar();
                Toast.makeText(ChronometreActivity.this, "l'heure : " + heure.get(Calendar.HOUR_OF_DAY) + ":" + heure.get(Calendar.MINUTE) + ":" + heure.get(Calendar.SECOND) + "." + heure.get(Calendar.MILLISECOND), Toast.LENGTH_SHORT).show();
                this.time = heure;
                number = 1;
                start.setBackgroundResource(R.drawable.stop);
            }
            else{
                GregorianCalendar heure2 = new GregorianCalendar();
                Toast.makeText(ChronometreActivity.this, "l'heure2 : " + heure2.get(Calendar.HOUR_OF_DAY) + ":" + heure2.get(Calendar.MINUTE) + ":" + heure2.get(Calendar.SECOND) + "." + heure2.get(Calendar.MILLISECOND), Toast.LENGTH_SHORT).show();
                this.time2 = heure2;
                number = 0;
                int h1 = time.get(Calendar.HOUR_OF_DAY);
                int h2 = time2.get(Calendar.HOUR_OF_DAY);
                int heure;
                if(h2 >= h1) {
                    heure = h2 - h1;
                }
                else{
                    heure = 24 - h1 + h2;
                }
                int min1 = time.get(Calendar.MINUTE);
                int min2 = time2.get(Calendar.MINUTE);
                int minute ;
                if(min2 >= min1){
                    minute = min2 - min1;
                }
                else{
                    minute = 60 - min1 + min2;
                }


                int sec1 = time.get(Calendar.SECOND);
                int sec2 = time2.get(Calendar.SECOND);
                int seconde;
                if(sec2 >= sec1) {
                    seconde = sec2 - sec1;
                }
                else{
                    seconde = 60 - sec1 + sec2;
                }
                int milli1 = time.get(Calendar.MILLISECOND);
                int milli2 = time2.get(Calendar.MILLISECOND);
                int milliSeconde;
                if(milli2 >= milli1){
                    milliSeconde = milli2 - milli1;
                }
                else{
                    milliSeconde = 1000 - milli1 + milli2;
                }


                text = (TextView) findViewById(R.id.chrono);
                text.setText(heure + ":" + minute + ":" + seconde + "." + milliSeconde);
                start.setBackgroundResource(R.drawable.play);
            }
        });

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