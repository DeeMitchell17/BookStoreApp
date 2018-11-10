package com.example.android.bookstoreapp.data;

import android.provider.BaseColumns;

/**
 * API Contract for the BookStore app.
 */
public final class StoreContract {

   
    private StoreContract() {}

    /**
     * Inner class that defines constant values for the bookstore database table.
     * Each entry in the table represents a single item.
     */
    public static final class ItemEntry implements BaseColumns {

        /** Name of database table for items */
        public final static String TABLE_NAME = "bookstore";

        /**
         * Unique ID number for the pet (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the item.
         *
         * Type: TEXT
         */
        public final static String COLUMN_PRODUCT_NAME ="item";

        /**
         * price of the item.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_PRICE = "price";

        /**
         * Number of item available
         *
         * Type: INTEGER
         */
        public final static String COLUMN_QUANTITY = "quantity";

        /**
         * Supplier name.
         *
         * Type: STRING
         */

        public final static String COLUMN_SUPPLIER_NAME = "supplier";

        /**
         * Supplier phone number.
         *
         * Type: INTEGER
         */

        public final static String COLUMN_SUPPLIER_PHONE_NUMBER = "contact";

    }

}

