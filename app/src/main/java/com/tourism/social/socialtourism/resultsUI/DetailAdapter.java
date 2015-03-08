package com.tourism.social.socialtourism.resultsUI;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.tourism.social.socialtourism.R;
import com.tourism.social.socialtourism.utils.Place.Review;
import com.tourism.social.socialtourism.utils.UI.RandomColor;

import java.util.ArrayList;

/**
 * Created by emanuelteixeira on 06/03/15.
 */
public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder> {

    private ArrayList<Review> reviews;
    private RandomColor randomColor;

    public DetailAdapter(ArrayList<Review> reviews) {
        this.reviews = reviews;
        this.randomColor = new RandomColor();
    }

    @Override
    public DetailViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View detailView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.review_item, viewGroup, false);
        return new DetailViewHolder(detailView);
    }

    @Override
    public void onBindViewHolder(DetailViewHolder detailViewHolder, int i) {
        Review review = this.reviews.get(i);
        TextDrawable drawable = TextDrawable.builder().buildRoundRect(
                review.getAuthorName().toUpperCase().toCharArray()[0] + "", randomColor.getRandomColor(), 60);
        detailViewHolder.nameView.setImageDrawable(drawable);
        detailViewHolder.authorNameView.setText(review.getAuthorName());
        detailViewHolder.ratingTextview.setText(review.getRating()*2 + "/10.0");
        detailViewHolder.googleMoreLink.setText(review.getAuthorUrl());
        detailViewHolder.textReview.setText(review.getText());
        detailViewHolder.date.setText(review.getDate());
    }

    @Override
    public int getItemCount() {
        return this.reviews.size();
    }

    public static class DetailViewHolder extends RecyclerView.ViewHolder {

        protected TextView date;
        protected TextView googleMoreLink;
        protected TextView ratingTextview;
        protected TextView authorNameView;
        protected ImageView nameView;
        protected TextView textReview;

        public DetailViewHolder(View itemView) {
            super(itemView);
            nameView = (ImageView) itemView.findViewById(R.id.letter_name);
            authorNameView = (TextView) itemView.findViewById(R.id.author_name_review);
            ratingTextview = (TextView) itemView.findViewById(R.id.rating_view);
            googleMoreLink = (TextView) itemView.findViewById(R.id.google_more_text);
            textReview = (TextView) itemView.findViewById(R.id.text_review);
            date = (TextView) itemView.findViewById(R.id.date_text_view);
        }
    }
}
