package com.cheapproperty.trabajosena.cheapproperty.data;

import android.content.ContentValues;
import android.database.Cursor;

import com.cheapproperty.trabajosena.cheapproperty.data.PropertyContract.PropertyEntry;

import java.util.UUID;

/**
 * Entidad "abogado"
 */
public class Property {
    private String id;
    private String name;
    private String specialty;
    private String phoneNumber;
    private String bio;
    private String avatarUri;

    public Property(String name,
                    String specialty, String phoneNumber,
                    String bio, String avatarUri) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.specialty = specialty;
        this.phoneNumber = phoneNumber;
        this.bio = bio;
        this.avatarUri = avatarUri;
    }

    public Property(Cursor cursor) {
        id = cursor.getString(cursor.getColumnIndex(PropertyEntry.ID));
        name = cursor.getString(cursor.getColumnIndex(PropertyEntry.NAME));
        specialty = cursor.getString(cursor.getColumnIndex(PropertyEntry.SPECIALTY));
        phoneNumber = cursor.getString(cursor.getColumnIndex(PropertyEntry.PHONE_NUMBER));
        bio = cursor.getString(cursor.getColumnIndex(PropertyEntry.BIO));
        avatarUri = cursor.getString(cursor.getColumnIndex(PropertyEntry.AVATAR_URI));
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(PropertyEntry.ID, id);
        values.put(PropertyEntry.NAME, name);
        values.put(PropertyEntry.SPECIALTY, specialty);
        values.put(PropertyEntry.PHONE_NUMBER, phoneNumber);
        values.put(PropertyEntry.BIO, bio);
        values.put(PropertyEntry.AVATAR_URI, avatarUri);
        return values;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBio() {
        return bio;
    }

    public String getAvatarUri() {
        return avatarUri;
    }
}
