package com.tourism.social.socialtourism.utils.Contacts;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by emanuelteixeira on 03/03/15.
 */
public class Contact {
    private final String name;
    private final ArrayList<String> phoneNumbers;
    private final ArrayList<String> emails;
    private final Bitmap imageBitmap;
    private final String id;

    public Contact(String id, String name, ArrayList<String> phoneNumbers, ArrayList<String> emails, Bitmap imageBitmap) {
        this.id = id;
        this.name = name;
        this.phoneNumbers = phoneNumbers;
        this.emails = emails;
        this.imageBitmap = imageBitmap;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public ArrayList<String> getEmails() {
        return emails;
    }
}
