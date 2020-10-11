package com.example.boiteoutils;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        requestContactPermission();


    }

    private void recupContact() {
        ContentResolver contentResolver = this.getContentResolver();

        //Reuperer les contacts dans un curseur
        Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_ALTERNATIVE,
                        ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
        if (cursor == null){
            Log.d("recup", "********** erreur curseur");
        }else {


            List<Annuaire> listContact = new ArrayList<>();

            //parcours des contacts
            while(cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_ALTERNATIVE));
                String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                //affichage de la récupération

                listContact.add(new Annuaire(name,phone));

            }
            Toast.makeText(this,"Terminer",Toast.LENGTH_SHORT).show();
            //fermer le curseur
            cursor.close();

            RecyclerView recyclerView = findViewById(R.id.recyclerView);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            AnnuaireAdapter annuaireAdapter = new AnnuaireAdapter(listContact);
            recyclerView.setAdapter(annuaireAdapter);

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(dividerItemDecoration);
        }
    }


    /*Permissions*/

    private void getContacts() {
        //TODO get contacts code here
        Toast.makeText(this, "Ajout des contact en cours ....", Toast.LENGTH_LONG).show();
    }

    public void requestContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        android.Manifest.permission.READ_CONTACTS)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Autorisation des contacts");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("S'il vous plaît acceptez que l'application accède à vos contacts.");
                    builder.setOnDismissListener(dialog -> requestPermissions(
                            new String[]
                                    {android.Manifest.permission.READ_CONTACTS}, 1));
                    builder.show();
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{android.Manifest.permission.READ_CONTACTS},1);

                }
            } else {
                getContacts();
                recupContact();
            }
        } else {
            getContacts();
            recupContact();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getContacts();
                recupContact();
            } else {
                Toast.makeText(this, "Vous avez refusé que l'application accède à vos contacts", Toast.LENGTH_LONG).show();
            }
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