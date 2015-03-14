package com.tourism.social.socialtourism;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appyvet.rangebar.RangeBar;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.tourism.social.socialtourism.resultsUI.ResultsActivity;
import com.tourism.social.socialtourism.utils.AsynkTasks.GooglePaclesApiRequest;
import com.tourism.social.socialtourism.utils.GPS.GPSManager;
import com.tourism.social.socialtourism.utils.Place.GooglePlacesUrlEncoder;
import com.tourism.social.socialtourism.utils.Place.GoogleTypes;
import com.tourism.social.socialtourism.utils.Place.ListPlaces;
import com.tourism.social.socialtourism.utils.UI.WaitAlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import info.hoang8f.widget.FButton;

/**
 * Created by emanuelteixeira on 04/03/15.
 */
public class PlaceholderFragment extends Fragment {
    private GPSManager gpsManager;
    private boolean getCoordinatesIsPressed;
    private HashMap<CheckBox, GoogleTypes> checkBoxGoogleTypesHashMap;
    private MaterialEditText latitudeTextView;
    private MaterialEditText longitudeTextView;
    private MaterialEditText text_input;
    private FButton searchPlace;
    private FButton getCoordinates;
    private RangeBar radiusRangeBar;

    public PlaceholderFragment() {
        getCoordinatesIsPressed = false;
        checkBoxGoogleTypesHashMap = new HashMap<CheckBox, GoogleTypes>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.restaurant_finder_fragment, container, false);
        this.latitudeTextView = (MaterialEditText) rootView.findViewById(R.id.latitude_input);
        this.longitudeTextView = (MaterialEditText) rootView.findViewById(R.id.longitude_input);
        this.text_input = (MaterialEditText) rootView.findViewById(R.id.address_text_input);
        this.searchPlace = (FButton) rootView.findViewById(R.id.search_place);
        this.getCoordinates = (FButton) rootView.findViewById(R.id.get_coordinates_button);
        this.radiusRangeBar = (RangeBar) rootView.findViewById(R.id.rangebar);

        radiusRangeBar.setEnabled(true);
        radiusRangeBar.setSeekPinByIndex(2);
        final TextView radiusLabel = (TextView) rootView.findViewById(R.id.radius_label);
        radiusLabel.setText(getActivity().getResources().getString(R.string.radius_label) + " " +
                radiusRangeBar.getRightIndex() * 5 + "km");
        final CheckBox rangeBarEnable = (CheckBox) rootView.findViewById(R.id.rangeBar_enable);
        rangeBarEnable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                radiusRangeBar.setEnabled(b);
            }
        });

        GoogleTypes[] googleTypes = GoogleTypes.values();
        for (int i = 0; i < googleTypes.length; i+=2) {
            LinearLayout collumn1 = (LinearLayout)rootView.findViewById(R.id.collumn1);
            View child1 = getActivity().getLayoutInflater().inflate(R.layout.checkbox_layout, null);
            CheckBox checkBox1 = (CheckBox) child1.findViewById(R.id.checkbox);
            checkBox1.setText(getActivity().getResources().getStringArray(
                    R.array.google_types)[googleTypes[i].getCode()]);
            collumn1.addView(child1);
            this.checkBoxGoogleTypesHashMap.put(checkBox1, googleTypes[i]);
            if (i + 1 < googleTypes.length){
                LinearLayout collumn2 = (LinearLayout)rootView.findViewById(R.id.collumn2);
                View child2 = getActivity().getLayoutInflater().inflate(R.layout.checkbox_layout, null);
                CheckBox checkBox2 = (CheckBox) child2.findViewById(R.id.checkbox);
                checkBox2.setText(getActivity().getResources().getStringArray(
                        R.array.google_types)[googleTypes[i + 1].getCode()]);
                collumn2.addView(child2);
                this.checkBoxGoogleTypesHashMap.put(checkBox2, googleTypes[i + 1]);
            }
        }

        radiusRangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int i, int i2, String s, String s2) {
                radiusLabel.setText(getActivity().getResources().getString(R.string.radius_label) + " " +
                        s2 + "km");
            }
        });
        this.setButtonEnable(false, searchPlace);

        latitudeTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.length() != 0 && longitudeTextView.getText().length() != 0)
                    PlaceholderFragment.this.setButtonEnable(true, searchPlace);
                else
                    PlaceholderFragment.this.setButtonEnable(false, searchPlace);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        longitudeTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.length() != 0 && latitudeTextView.getText().length() != 0)
                    PlaceholderFragment.this.setButtonEnable(true, searchPlace);
                else
                    PlaceholderFragment.this.setButtonEnable(false, searchPlace);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        text_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.length() != 0)
                    PlaceholderFragment.this.setButtonEnable(true, searchPlace);
                else if (charSequence.length() == 0 && (latitudeTextView.getText().length() == 0
                && longitudeTextView.getText().length() == 0))
                    PlaceholderFragment.this.setButtonEnable(false, searchPlace);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        this.gpsManager = new GPSManager((LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE))
        {
            @Override
            public void onLocationChanged(Location location) {
                if (getCoordinatesIsPressed)
                {
                    gpsManager.setMY_LAT(location.getLatitude());
                    gpsManager.setMY_LNG(location.getLongitude());
                    latitudeTextView.setText(location.getLatitude() + "");
                    longitudeTextView.setText(location.getLongitude() + "");
                }
            }
        };
        this.gpsManager.run();

        searchPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!rangeBarEnable.isChecked()){
                    int i = 0;
                    for (CheckBox checkBox : checkBoxGoogleTypesHashMap.keySet())
                    {
                        if (checkBox.isChecked())
                            i++;
                    }
                    if (i == 0)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage(getActivity().getResources().getString(R.string.least_type));
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.create();
                        builder.show();
                        return;
                    }
                }

                MainActivity mainActivity = (MainActivity) getActivity();
                if (!mainActivity.isOnline()){
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_SOUND_SETTINGS), 1023);
                }
                else {
                    final HashMap<String, String> params = new HashMap<String, String>();
                    String url;
                    ArrayList<GoogleTypes> selectedGoogleTypes = new ArrayList<GoogleTypes>();
                    for(CheckBox checkBox : checkBoxGoogleTypesHashMap.keySet()){
                        if (checkBox.isChecked())
                            selectedGoogleTypes.add(checkBoxGoogleTypesHashMap.get(checkBox));
                    }
                    GoogleTypes [] googleTypes = new GoogleTypes[selectedGoogleTypes.size()];
                    for (int i = 0; i < googleTypes.length; i++) {
                        googleTypes[i]  = selectedGoogleTypes.get(i);
                    }
                    if (text_input.getText().length() > 0)
                    {
                        url = GooglePlacesUrlEncoder.getTextSearchUrlEncoded(text_input.getText().toString(), googleTypes);
                    }
                    else {
                        if (rangeBarEnable.isChecked())
                        {
                            Integer radius = Integer.parseInt(radiusLabel.getText().toString().split(": ")[1].split("km")[0]);
                            params.put("radius", (radius * 1000) + "");
                        }
                        params.put("lat", gpsManager.getMY_LAT() + "");
                        params.put("lng", gpsManager.getMY_LNG() + "");
                        url = GooglePlacesUrlEncoder.getUrlEncoded(params, googleTypes);
                    }
                    PlaceholderFragment.this.executeTask(url);
                }
            }
        });
        /*
        searchByText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = GooglePlacesUrlEncoder.getTextSearchUrlEncoded(text_input.getText().toString(), null);
                PlaceholderFragment.this.executeTask(url);
            }
        });*/

        getCoordinates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCoordinatesIsPressed = !getCoordinatesIsPressed;
                if (getCoordinatesIsPressed) {
                    if (!gpsManager.isGpsEnabled())
                    {
                        AlertDialog.Builder enableGpsDialog = new AlertDialog.Builder(getActivity());
                        enableGpsDialog.setMessage("Para melhorar a experiência ligue o GPS");
                        enableGpsDialog.setPositiveButton("Ligar GPS", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            }
                        });
                        enableGpsDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        enableGpsDialog.create();
                        enableGpsDialog.show();
                    }

                    latitudeTextView.setText(gpsManager.getMY_LAT() + "");
                    longitudeTextView.setText(gpsManager.getMY_LNG() + "");
                    gpsManager.updates();
                    getCoordinates.setButtonColor(getActivity().getResources().getColor(R.color.greenDark));
                    latitudeTextView.setEnabled(false);
                    longitudeTextView.setEnabled(false);
                }
                else {
                    gpsManager.getLocationManager().removeUpdates(gpsManager);
                    getCoordinates.setButtonColor(getActivity().getResources().getColor(R.color.Primary));
                    latitudeTextView.setEnabled(true);
                    longitudeTextView.setEnabled(true);
                }
            }
        });
        return rootView;
    }

    private void executeTask(String url) {
        final AlertDialog waitAlertDialog = new WaitAlertDialog(getActivity()).getAlertDialog();
        waitAlertDialog.show();
        final ListPlaces[] listPlaces = new ListPlaces[1];
        new GooglePaclesApiRequest(){
            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                super.onPostExecute(jsonObject);
                try {
                    if (jsonObject.getString("status").equals("OK"))
                    {
                        listPlaces[0] = new ListPlaces();
                        listPlaces[0].setNewPlacesList(jsonObject, gpsManager.getMY_LAT(), gpsManager.getMY_LNG());
                        Intent i = new Intent(getActivity().getApplicationContext(), ResultsActivity.class);
                        i.putExtra("places", listPlaces[0]);
                        waitAlertDialog.dismiss();
                        startActivityForResult(i, 102);
                        getActivity().overridePendingTransition(R.animator.slide_in_left, R.animator.slide_ou_left);
                    }
                    else {
                        Toast.makeText(getActivity().getBaseContext(), "NAO", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute(url);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1023)
        {
            if (((MainActivity)getActivity()).isOnline())
                searchPlace.callOnClick();
        }
    }

    private void setButtonEnable(boolean enabled, FButton fButton) {
        if (enabled){
            fButton.setEnabled(enabled);
            fButton.setTextColor(getActivity().getResources().getColor(R.color.white));
            fButton.setButtonColor(getActivity().getResources().getColor(R.color.Primary));
        }
        else {
            fButton.setEnabled(enabled);
            fButton.setTextColor(getActivity().getResources().getColor(android.R.color.darker_gray));
            fButton.setButtonColor(getActivity().getResources().getColor(R.color.grey));
        }
    }
}
