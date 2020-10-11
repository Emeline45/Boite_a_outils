package com.example.boiteoutils;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class HeureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heure);

        GregorianCalendar heure = new GregorianCalendar();
        TextView text = findViewById(R.id.heureModif);
        text.setText(heure.get(Calendar.HOUR_OF_DAY) + ":" + heure.get(Calendar.MINUTE));
        Toast.makeText(HeureActivity.this, "l'heure : " + heure.get(Calendar.HOUR_OF_DAY) + ":" + heure.get(Calendar.MINUTE) + ":" + heure.get(Calendar.SECOND), Toast.LENGTH_SHORT).show();

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