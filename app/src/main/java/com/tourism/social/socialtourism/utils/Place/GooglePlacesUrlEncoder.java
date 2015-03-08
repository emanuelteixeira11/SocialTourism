package com.tourism.social.socialtourism.utils.Place;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by emanuelteixeira on 02/03/15.
 */
public class GooglePlacesUrlEncoder {

    protected static String GOOGLE_API_URL = new String("https://maps.googleapis.com/maps/api/place/search/json?");
    protected static String GOOGLE_DETAILS_API_URL = new String("https://maps.googleapis.com/maps/api/place/details/json?");
    protected static String GOOGLE_API_PLACES_KEY = "AIzaSyAxITjnkzax53HyuV2Adp_0aYSr0hNZa4k";
    protected static String GOOGLE_API_TEXT_SEARCH = "https://maps.googleapis.com/maps/api/place/textsearch/json?";
    protected static String GOOGLE_PHOTO_SEARCH = "https://maps.googleapis.com/maps/api/place/photo?";
    /*
    *
    * */
    public static String getUrlEncoded(HashMap<String, String> params, GoogleTypes[] types)
    {
        StringBuilder urlBase = new StringBuilder(GOOGLE_API_URL.toString());
        urlBase.append("location=");
        urlBase.append(params.get("lat"));
        urlBase.append(",");
        urlBase.append(params.get("lng"));
        urlBase.append("&radius=");
        int radius = (params.containsKey("radius")) ? Integer.parseInt(params.get("radius")) : 5000;
        urlBase.append(radius);
        if (types != null)
        {
            urlBase.append("&types=");
            urlBase.append(getTypesEncoded(types));
        }
        urlBase.append("&sensor=");
        urlBase.append(true);
        urlBase.append("&key=");
        urlBase.append(GOOGLE_API_PLACES_KEY);
        /*
        * Optional Params
        * */
        if (params.containsKey("name")){
            urlBase.append("&name=");
            urlBase.append(params.get("name"));
        }
        if (params.containsKey("keyword")){
            urlBase.append("&keyword=");
            urlBase.append(params.get("keyword"));
        }
        return urlBase.toString();
    }

    public static String getDetailsUrlEncoded(String reference)
    {
        StringBuilder urlBase = new StringBuilder(GOOGLE_DETAILS_API_URL.toString());
        urlBase.append("reference=");
        urlBase.append(reference);
        urlBase.append("&sensor=");
        urlBase.append("true");
        urlBase.append("&key=");
        urlBase.append(GOOGLE_API_PLACES_KEY);
        return urlBase.toString();
    }

    public static String getPageTockenUrlEncoded(String token)
    {
        StringBuilder urlBase = new StringBuilder(GOOGLE_API_URL.toString());
        urlBase.append("pagetoken=");
        urlBase.append(token);
        urlBase.append("&key=");
        urlBase.append(GOOGLE_API_PLACES_KEY);
        return urlBase.toString();
    }

    public static String getTextSearchUrlEncoded(String query, GoogleTypes[] typeses)
    {
        StringBuilder urlBase = new StringBuilder(GOOGLE_API_TEXT_SEARCH.toString());
        urlBase.append("query=");
        urlBase.append(query);
        urlBase.append("&sensor=true");
        if (typeses != null){
            urlBase.append("&types=");
            urlBase.append(getTypesEncoded(typeses));
        }
        urlBase.append("&key=");
        urlBase.append(GOOGLE_API_PLACES_KEY);
        return urlBase.toString();
    }

    public static String getPhotoEncoded(String photoReference, int maxWidth)
    {
        StringBuilder urlBase = new StringBuilder(GOOGLE_PHOTO_SEARCH.toString());
        urlBase.append("photoreference=");
        urlBase.append(photoReference);
        urlBase.append("&maxwidth=");
        urlBase.append(maxWidth);
        urlBase.append("&key=");
        urlBase.append(GOOGLE_API_PLACES_KEY);
        return urlBase.toString();
    }

    private static String getTypesEncoded(GoogleTypes... types){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < types.length; i++) {
            stringBuilder.append(types[i].getGoogleType());
            if (i < types.length - 1)
                stringBuilder.append("|");
        }
        try {
            return URLEncoder.encode(stringBuilder.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
