package com.example.boiteoutils;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Appareil photo
        Button photo = findViewById(R.id.photo);
        photo.setOnClickListener((v) -> {
            //Intent avec activité actuelle et activité de destination
            Intent ic = new Intent(MainActivity.this, PhotoActivity.class);
            // Puis on lance l'activité
            startActivity(ic);
        });

        //Heure actuelle
        Button heure = findViewById(R.id.heure_modif);
        heure.setOnClickListener((v -> {
            Intent ic = new Intent(MainActivity.this, HeureActivity.class);
            startActivity(ic);
        }));

        Button chrono = findViewById(R.id.chronometre);
        chrono.setOnClickListener((v -> {
            Intent ic = new Intent(MainActivity.this, ChronometreActivity.class);
            startActivity(ic);
        }));

        //Minuteur
        Button minuteur = findViewById(R.id.minuteur);
        minuteur.setOnClickListener((v -> {
            Intent ic = new Intent(MainActivity.this, MinuteurActivity.class);
            startActivity(ic);
        }));

        //Meteo
        Button meteo = findViewById(R.id.Meteo_button);
        meteo.setOnClickListener((v) -> {
            //Intent avec activité actuelle et activité de destination
            Intent ic = new Intent(MainActivity.this, MeteoActivity.class);
            // Puis on lance l'activité
            startActivity(ic);
        });

        //Contact
        Button contacts = findViewById(R.id.contact);
        contacts.setOnClickListener((v) -> {
            Intent ic = new Intent(MainActivity.this, ContactsActivity.class);
            startActivity(ic);
        });
    }

}