package com.tourism.social.socialtourism.utils.Place;

/**
 * Created by emanuelteixeira on 02/03/15.
 */
public enum GoogleTypes {
    RESTAURANT("restaurant"), ART_GALLERY("art_gallery"),
    BAR("bar"), CAFE("cafe"), ESTABLISHMENT("establishment"),
    FOOD("food"), AIRPORT("airport"), MUSEUM("museum"), NIGHT_CLUB("night_club");

    private String type;
    GoogleTypes(String type)
    {
        this.type = type;
    }

    public String getGoogleType()
    {
        return this.type;
    }
}
