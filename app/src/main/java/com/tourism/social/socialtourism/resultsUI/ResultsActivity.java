package com.tourism.social.socialtourism.resultsUI;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.melnykov.fab.FloatingActionButton;
import com.tourism.social.socialtourism.R;
import com.tourism.social.socialtourism.utils.AsynkTasks.GooglePaclesApiRequest;
import com.tourism.social.socialtourism.utils.Place.GooglePlacesUrlEncoder;
import com.tourism.social.socialtourism.utils.Place.ListPlaces;
import com.tourism.social.socialtourism.utils.Place.Place;
import com.tourism.social.socialtourism.utils.UI.WaitAlertDialog;

import org.json.JSONException;
import org.json.JSONObject;


public class ResultsActivity extends ActionBarActivity {

    private FloatingActionButton fab;
    private RecyclerView recList;
    private ListPlaces places;
    private ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        this.places = (ListPlaces) getIntent().getExtras().get("places");
        this.recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        final LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        this.recList.setLayoutManager(llm);
        this.itemAdapter = new ItemAdapter(places);
        recList.setAdapter(itemAdapter);

        this.fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.attachToRecyclerView(recList);

        this.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WaitAlertDialog waitAlertDialog = new WaitAlertDialog(ResultsActivity.this);
                final AlertDialog alertDialog = waitAlertDialog.getAlertDialog();
                alertDialog.show();
                ResultsActivity.this.recList.scrollToPosition(places.getPlaces().size() - 1);
                String url = GooglePlacesUrlEncoder.getPageTockenUrlEncoded(places.getNextPageToken());
                new GooglePaclesApiRequest(){
                    @Override
                    protected void onPostExecute(JSONObject jsonObject) {
                        super.onPostExecute(jsonObject);
                        try {
                            if (jsonObject.getString("status").equals("OK"))
                            {
                                ListPlaces newListPlace = new ListPlaces();
                                newListPlace.setNewPlacesList(jsonObject, ResultsActivity.this.places.getMyLat(),
                                        ResultsActivity.this.places.getMyLat());
                                ResultsActivity.this.places.getPlaces().addAll(newListPlace.getPlaces());
                                ResultsActivity.this.places.setNextPageToken(newListPlace.getNextPageToken());
                                ResultsActivity.this.itemAdapter.addAll(newListPlace);
                                ResultsActivity.this.itemAdapter.notifyDataSetChanged();
                                alertDialog.dismiss();
                                if (newListPlace.getNextPageToken().equals(""))
                                {
                                    ResultsActivity.this.recList.setOnScrollListener(new RecyclerView.OnScrollListener() {
                                        @Override
                                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                            fab.hide();
                                        }
                                    });
                                    fab.hide(false);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }.execute(url);
            }
        });
        if (places.getNextPageToken().equals(""))
        {
            fab.hide();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101) {
            if(resultCode == RESULT_OK){
                Place place = (Place) data.getExtras().get("placeResult");
                int index = this.places.getPlaces().indexOf(place);
                this.itemAdapter.changeItem(index, place);
                this.itemAdapter.notifyDataSetChanged();
            }
            if (resultCode == RESULT_CANCELED) {

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.results, menu);
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
}
