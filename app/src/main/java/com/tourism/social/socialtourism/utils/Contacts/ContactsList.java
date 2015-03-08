package com.tourism.social.socialtourism.utils.Contacts;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by emanuelteixeira on 03/03/15.
 */
public class ContactsList {
    private final ContentResolver contentResolver;
    private ArrayList<Contact> contactArrayList;
    public ContactsList(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
        this.contactArrayList = new ArrayList<Contact>();
    }

    public ArrayList<Contact> getContactList() {
        return contactArrayList;
    }

    public void readContacts(){
        Cursor cursor = this.contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if(cursor.getCount() > 0){
            while (cursor.moveToNext())
            {
                String imageContact = null;
                String name = null;
                ArrayList<String> phoneNumbers = new ArrayList<String>();
                ArrayList<String> emails = new ArrayList<String>();
                Bitmap imageBitmap = null;
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                imageContact = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0){
                    Cursor phoneCursor = this.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    while (phoneCursor.moveToNext()){
                        phoneNumbers.add(phoneCursor.getString(phoneCursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    }
                    phoneCursor.close();
                    name += "";
                    Cursor emailCursor = this.contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
                    while (emailCursor.moveToNext()){
                        emails.add(emailCursor.getString(emailCursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Email.DATA)));
                    }
                }
                if (imageContact != null){
                    try {
                        imageBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, Uri.parse(imageContact));
                        name += "";
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Contact contact = new Contact(id, name, phoneNumbers, emails, imageBitmap);
                this.contactArrayList.add(contact);
            }
        }
    }
}
