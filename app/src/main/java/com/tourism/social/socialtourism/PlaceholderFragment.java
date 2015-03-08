package com.tourism.social.socialtourism;

import android.app.Fragment;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tourism.social.socialtourism.utils.GPS.GPSManager;

import info.hoang8f.widget.FButton;

/**
 * Created by emanuelteixeira on 04/03/15.
 */
public class PlaceholderFragment extends Fragment {
    private GPSManager gpsManager;

    public PlaceholderFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.restaurant_finder_fragment, container, false);

        FButton getCoordinates = (FButton) rootView.findViewById(R.id.get_coordinates_button);
        getCoordinates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getBaseContext(), "TOS", Toast.LENGTH_LONG).show();
            }
        });
        this.gpsManager = new GPSManager((LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE)){
            @Override
            public void onLocationChanged(Location location) {

            }
        };
        this.gpsManager.run();
        return rootView;
    }
}
