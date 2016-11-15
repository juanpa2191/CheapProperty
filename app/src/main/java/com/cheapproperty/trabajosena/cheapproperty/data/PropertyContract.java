package com.cheapproperty.trabajosena.cheapproperty.data;

import android.provider.BaseColumns;

/**
 * Esquema de la base de datos para abogados
 */
public class PropertyContract {

    public static abstract class PropertyEntry implements BaseColumns{
        public static final String TABLE_NAME ="property";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String SPECIALTY = "specialty";
        public static final String PHONE_NUMBER = "phoneNumber";
        public static final String AVATAR_URI = "avatarUri";
        public static final String BIO = "bio";
    }
}
