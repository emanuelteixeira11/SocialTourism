package com.tourism.social.socialtourism.resultsUI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;
import com.tourism.social.socialtourism.R;
import com.tourism.social.socialtourism.utils.AsynkTasks.GooglePlacesPhotoRequest;
import com.tourism.social.socialtourism.utils.Place.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import info.hoang8f.widget.FButton;

public class DetailsActivity extends ActionBarActivity {

    private Place place;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        this.place = ((Place) getIntent().getExtras().get("place"));
        getSupportActionBar().setTitle(place.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView adressTextView = (TextView) findViewById(R.id.detail_adress);
        adressTextView.setText(place.getAdress());

        TextView isOpen = (TextView) findViewById(R.id.is_open);
        if (place.isOpnenNow()){
            isOpen.setText(getResources().getStringArray(R.array.open_now)[0]);
        }
        else {
            isOpen.setText(getResources().getStringArray(R.array.open_now)[1]);
        }

        TextView internationalPhone = (TextView) findViewById(R.id.phone_details);
        internationalPhone.setText(place.getPhoneNumber());

        TextView siteDetails = (TextView) findViewById(R.id.site_details);
        siteDetails.setText(place.getWebsite());

        TextView typeDetails = (TextView) findViewById(R.id.types_details);
        typeDetails.setText("");
        for (int i = 0; i < place.getTypes().size(); i++) {
            if (i + 1 < place.getTypes().size()){
                typeDetails.setText(typeDetails.getText() +
                        getResources().getStringArray(R.array.google_types)[place.getTypes().get(i).getCode()] + " | ");
            }
            else
                typeDetails.setText(typeDetails.getText() +
                        getResources().getStringArray(R.array.google_types)[place.getTypes().get(i).getCode()] + "");
        }


        FButton callButton = (FButton) findViewById(R.id.call_button);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + place.getPhoneNumber()));
                startActivity(callIntent);
            }
        });

        FButton directionsButton = (FButton) findViewById(R.id.directions_button);
        directionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=" + place.getMy_lat() + "," +
                                place.getMy_lng() + "&daddr=" + place.getLat() + "," + place.getLng()));
                startActivity(intent);
            }
        });

        if (place.getListReviews().size() > 0)
        {
            RecyclerView recList = (RecyclerView) findViewById(R.id.cardListReview);
            recList.setHasFixedSize(true);
            final LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recList.setLayoutManager(llm);
            DetailAdapter adapter = new DetailAdapter(place.getListReviews());
            recList.setAdapter(adapter);
        }
        else {
            LinearLayout layout = (LinearLayout) findViewById(R.id.recicler_view_reviews);
            ((ViewManager)layout.getParent()).removeView(layout);
        }
        try {
            if (this.place.hasPhotos()){
                final LinearLayout listView = (LinearLayout)findViewById(R.id.gallery_vertical_layout); //layout vertical
                JSONArray jsonArray = new JSONArray(this.place.getJsonPhotosArray());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = null;
                    try {
                        obj = jsonArray.getJSONObject(i);
                        final int WIDTH = 256;
                        String photoReference = obj.getString("photo_reference");
                        new GooglePlacesPhotoRequest(){
                            @Override
                            protected void onPostExecute(Bitmap bitmap) {
                                View child = getLayoutInflater().inflate(R.layout.gallery_view, null);
                                final CircularImageView imageView = (CircularImageView) child.findViewById(R.id.circular_photo);
                                imageView.setImageBitmap(bitmap);
                                listView.addView(child);
                            }
                        }.execute(photoReference, WIDTH + "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("placeResult", this.place);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details, menu);
        return true;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ( keyCode == KeyEvent.KEYCODE_MENU ) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("placeResult", this.place);
            setResult(RESULT_OK, returnIntent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("placeResult", this.place);
            setResult(RESULT_OK, returnIntent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
