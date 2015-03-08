package com.tourism.social.socialtourism.resultsUI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tourism.social.socialtourism.R;
import com.tourism.social.socialtourism.utils.AsynkTasks.GooglePaclesApiRequest;
import com.tourism.social.socialtourism.utils.Place.GooglePlacesUrlEncoder;
import com.tourism.social.socialtourism.utils.Place.ListPlaces;
import com.tourism.social.socialtourism.utils.Place.Place;
import com.tourism.social.socialtourism.utils.UI.WaitAlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import info.hoang8f.widget.FButton;

/**
 * Created by emanuelteixeira on 04/03/15.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{

    private ListPlaces places;
    private View itemView;

    public ItemAdapter(ListPlaces places) {
        this.places = places;
    }

    public void addAll(ListPlaces places){
        places.getPlaces().addAll(places.getPlaces());
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        this.itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
        return new ItemViewHolder(itemView);
    }

    public void changeItem(int index, Place place){
        this.places.getPlaces().remove(index);
        this.places.getPlaces().add(index, place);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int i) {
        Place place = this.places.getPlaces().get(i);
        itemViewHolder.place = place;
        itemViewHolder.reference = place.getReference();
        itemViewHolder.title.setText(place.getName());
        itemViewHolder.address.setText(place.getAdress());
        double distance = place.getDistance();
        if (distance >= 1000)
        {
            itemViewHolder.distance.setText(new DecimalFormat("0.00").format(distance/1000)+ " km");
        }
        else {
            itemViewHolder.distance.setText(new DecimalFormat("0.00").format(distance)+ " m");
        }
        itemViewHolder.ratingView.setText(Double.parseDouble(place.getRating() + "") * 10 / 5 + "/10.0");
        if (place.isOpnenNow()){
            itemViewHolder.openNow.setText(itemViewHolder.openNowArray[0]);
            itemViewHolder.openNow.setTextColor(itemView.getResources().getColor(R.color.green));
            itemViewHolder.clock.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_clock_green));
        }
        else {
            itemViewHolder.openNow.setText(itemViewHolder.openNowArray[1]);
            itemViewHolder.openNow.setTextColor(itemView.getResources().getColor(android.R.color.white));
            itemViewHolder.clock.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_clock));
        }
        if (place.getPriceLevel() == 1){
            itemViewHolder.price1.setImageResource(R.drawable.ic_dollar);
        }
        if (place.getPriceLevel() == 1){
            itemViewHolder.price2.setImageResource(R.drawable.ic_dollar);
        }
        if (place.getPriceLevel() == 1){
            itemViewHolder.price3.setImageResource(R.drawable.ic_dollar);
        }
    }

    @Override
    public int getItemCount() {
        return places.getPlaces().size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        protected final String[] openNowArray;
        protected TextView title;
        protected TextView address;
        protected String reference;
        protected TextView distance;
        protected TextView ratingView;
        protected TextView openNow;
        protected ImageView price1;
        protected ImageView price2;
        protected ImageView price3;
        protected ImageView clock;
        protected Place place;
        protected View view;

        public ItemViewHolder(View v) {
            super(v);
            view = v;
            openNowArray = v.getContext().getResources().getStringArray(R.array.open_now);
            title = (TextView) v.findViewById(R.id.title);
            address = (TextView) v.findViewById(R.id.adress);
            distance = (TextView) v.findViewById(R.id.distance);
            ratingView = (TextView) v.findViewById(R.id.rating);
            openNow = (TextView) v.findViewById(R.id.open_now);
            clock = (ImageView) v.findViewById(R.id.clock_view);

            price1 = (ImageView) v.findViewById(R.id.priceIcon1);
            price2 = (ImageView) v.findViewById(R.id.priceIcon2);
            price3 = (ImageView) v.findViewById(R.id.priceIcon3);
            FButton button = (FButton) v.findViewById(R.id.primary_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    if (!place.hasDetails())
                    {
                        WaitAlertDialog waitAlertDialog = new WaitAlertDialog(view.getContext());
                        final AlertDialog alertDialog = waitAlertDialog.getAlertDialog();
                        alertDialog.show();
                        String url = GooglePlacesUrlEncoder.getDetailsUrlEncoded(place.getReference());
                        new GooglePaclesApiRequest(){
                            @Override
                            protected void onPostExecute(JSONObject jsonObject) {
                                super.onPostExecute(jsonObject);
                                try {
                                    if (jsonObject.getString("status").equals("OK"))
                                    {
                                        place.addDetails(jsonObject);
                                        Intent i = new Intent(view.getContext(), DetailsActivity.class);
                                        i.putExtra("place", place);
                                        ((Activity)view.getContext()).startActivityForResult(i, 101);
                                        alertDialog.dismiss();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.execute(url);
                    }
                    else
                    {
                        Intent i = new Intent(view.getContext(), DetailsActivity.class);
                        i.putExtra("place", place);
                        ((Activity)view.getContext()).startActivityForResult(i, 101);
                    }
                }
            });
        }
    }
}
