package com.example.boiteoutils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class MeteoActivity extends AppCompatActivity {

    private Location l;
    private double latitude = 0;
    private double longitude = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteo);

        //Vérifier qu'on a la permission de géolocalisation
        //Sinon, on la demande a l'utilisateur
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},48);
        }

        //Service en charge de la géolocalisation
        final LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

    }

    public void startAsyncTask(View v){
        MeteoActivity.TacheLongue task = new MeteoActivity.TacheLongue(this);
        final LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        //l = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //latitude = l.getLatitude();
        //longitude = l.getLongitude();
        //Toast.makeText(MeteoActivity.this, l + " / " +" Latitude : " + latitude + ", Longitude : " + longitude , Toast.LENGTH_SHORT).show();

        task.execute();
    }


    @SuppressLint("StaticFieldLeak")
    private class TacheLongue extends AsyncTask<Integer, Integer, String> {
        private WeakReference<MeteoActivity> activityWeakReference;

        public TacheLongue(MeteoActivity activity){
            activityWeakReference = new WeakReference<MeteoActivity>(activity);
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            MeteoActivity activity = activityWeakReference.get();
            if(activity == null || activity.isFinishing()){
                return;
            }
            //activity.progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(Integer... p){
            /*for(int i = 0; i < p[0]; i++){
                publishProgress((i*100) / p[0]);
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }*/

            return "Terminer";
        }


        @Override
        protected void onProgressUpdate(Integer... p){
            super.onProgressUpdate(p);

            MeteoActivity activity = activityWeakReference.get();
            if(activity == null || activity.isFinishing()){
                return;
            }
            //activity.progressBar.setProgress(p[0]);
        }

        @Override
        protected void onPostExecute(String r){
            super.onPostExecute(r);

            MeteoActivity activity = activityWeakReference.get() ;
            if(activity == null || activity.isFinishing()){
                return;
            }


            final LocationManager locationManager = (LocationManager) activity.getSystemService(LOCATION_SERVICE);

            Location l = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            String adresse = "Aucune adresse trouvée";

            if(l != null){
                activity.latitude = l.getLatitude();
                activity.longitude = l.getLongitude();
                Toast.makeText(activity, "Latitude : " + activity.latitude + ", Longitude : " + activity.longitude , Toast.LENGTH_SHORT).show();

                //Affichage de l'adresse
                Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(latitude,longitude,1);
                    StringBuilder sb = new StringBuilder();
                    if(addresses.size() > 0){
                        Address address = addresses.get(0);
                        sb.append(address.getLocality());
                    }
                    adresse = sb.toString();
                }catch (IOException e){}
                //MeteoActivity.TacheMeteo task = new MeteoActivity.TacheMeteo(activity);
            }
            else{
                Toast.makeText(activity, "Aucune position trouvé" , Toast.LENGTH_SHORT).show();

            }

            TextView textView = (TextView) findViewById(R.id.textView2);
            textView.setText(adresse);

            TextView textView1 = (TextView) findViewById(R.id.textView3);
            textView1.setText("10 °C");
            //task.execute();

        }

    }
    private class TacheMeteo extends AsyncTask <String, Void, JSONObject> {

        private WeakReference<MeteoActivity> activityWeakReference;
        public TacheMeteo(MeteoActivity activity){
            activityWeakReference = new WeakReference<MeteoActivity>(activity);
        }

        @Override
        protected JSONObject doInBackground(String... urls) {
            MeteoActivity activity = activityWeakReference.get();
            URL url = null;
            HttpURLConnection urlConnection = null;
            JSONObject result = null;
            try{
                url = new URL("http://api.openweathermap.org/data/2.5/weather?lat={"+activity.latitude+"}&lon={"+activity.longitude+"}appid={d03aa73a6b4a7d733722bba868c4f7ec}");
                //url = new URL(urls[0]);
                Log.d("my weather received",url.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                result = readStream(in);
            } catch (IOException | JSONException e){
                e.printStackTrace();
            } finally {
                if(urlConnection != null)
                    urlConnection.disconnect();
            }
            return result;
        }

        private JSONObject readStream(InputStream in) throws IOException, JSONException{
            StringBuilder sb = new StringBuilder();
            BufferedReader r = new BufferedReader(new InputStreamReader(in), 1000);
            for(String line = r.readLine(); line != null; line = r.readLine()) {
                sb.append(line);
            }
            in.close();
            return new JSONObject(sb.toString());
        }


        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            MeteoActivity activity = activityWeakReference.get();
            if(activity == null || activity.isFinishing()){
                return;
            }
            //activity.progressBar.setVisibility(View.VISIBLE);

        }


        @Override
        protected void onProgressUpdate(Void... p){
            super.onProgressUpdate(p);

            MeteoActivity activity = activityWeakReference.get();
            if(activity == null || activity.isFinishing()){
                return;
            }
            //activity.progressBar.setProgress(p[0]);
        }

        @Override
        protected void onPostExecute(JSONObject r){
            MeteoActivity activity = activityWeakReference.get();
            try{
                double temp = r.getJSONObject("main").getDouble("temp")-273.15;
                JSONObject desc = (JSONObject) r.getJSONArray("weather").get(0);

                TextView textView = (TextView) findViewById(R.id.editTextNumber2);
                textView.setText(r.toString());
                Toast.makeText(activity, "test" , Toast.LENGTH_SHORT).show();


            } catch (JSONException e){
                e.printStackTrace();
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