package com.tourism.social.socialtourism.utils.Place;

/**
 * Created by emanuelteixeira on 02/03/15.
 */
public enum GoogleTypes {
    RESTAURANT("restaurant", 0), ART_GALLERY("art_gallery", 1),
    BAR("bar", 2), CAFE("cafe", 3), ESTABLISHMENT("establishment", 4),
    FOOD("food", 5), AIRPORT("airport", 6), MUSEUM("museum", 7), NIGHT_CLUB("night_club", 8), LODGING("lodging", 9);

    private String type;
    private int code;
    GoogleTypes(String type, int name)
    {
        this.type = type;
        this.code = name;
    }

    public int getCode() {
        return code;
    }

    public String getGoogleType()
    {
        return this.type;
    }
}
