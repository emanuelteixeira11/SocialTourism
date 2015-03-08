package com.tourism.social.socialtourism;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.tourism.social.socialtourism.resultsUI.ResultsActivity;
import com.tourism.social.socialtourism.utils.Place.ListPlaces;
import com.tourism.social.socialtourism.utils.UI.WaitAlertDialog;


public class MainActivity extends ActionBarActivity {
    private ListPlaces listPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //isOnline();
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        /*SharedPreferences sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        if (!sharedPreferences.contains("user"))
        {
            Intent i = new Intent(getBaseContext(), LoginActivity.class);
            startActivityForResult(i, 123);
        }*/

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburguer);
        WaitAlertDialog waitAlertDialog = new WaitAlertDialog(this);

        /*final HashMap<String, String> map = new HashMap<String, String>();
        map.put("radius", "500");

        this.gpsManager = new GPSManager((LocationManager) getSystemService(LOCATION_SERVICE)){
            @Override
            public void onLocationChanged(Location location) {
                map.put("lat", location.getLatitude() + "");
                map.put("lng", location.getLongitude() + "");
            }
        };
        this.gpsManager.run();

        map.put("lat", gpsManager.getMY_LAT() + "");
        map.put("lng", gpsManager.getMY_LNG() + "");

        final AlertDialog dialog = waitAlertDialog.getAlertDialog();
        dialog.show();
        String url = GooglePlacesUrlEncoder.getUrlEncoded(map, new GoogleTypes[]{GoogleTypes.RESTAURANT, GoogleTypes.ESTABLISHMENT});
        this.listPlaces = new ListPlaces();
        new GooglePaclesApiRequest(){
            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                super.onPostExecute(jsonObject);
                try {
                    if (jsonObject.getString("status").equals("OK"))
                    {
                        dialog.dismiss();
                        listPlaces = new ListPlaces();
                        listPlaces.setNewPlacesList(jsonObject, Double.parseDouble(map.get("lat")),
                                Double.parseDouble(map.get("lng")));
                    }
                    else {
                        Toast.makeText(getBaseContext(), "NAO", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute(url);*/

        //ContactsList list = new ContactsList(getContentResolver());
        //list.readContacts();
    }
    public void MyOnClickListener(View v) {
        switch (v.getId()) {
            case R.id.button:
                Intent i = new Intent(getApplicationContext(), ResultsActivity.class);
                i.putExtra("places", this.listPlaces);
                startActivity(i);
                overridePendingTransition(R.animator.slide_in_left, R.animator.slide_ou_left);
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        //this.gpsManager.updates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //this.gpsManager.getLocationManager().removeUpdates(this.gpsManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            startActivity(new Intent(Settings.ACTION_SETTINGS));
            return false;
        }
    }
}
