package com.example.android.bookstoreapp.data;

import android.provider.BaseColumns;

/**
 * API Contract for the BookStore app.
 */
public final class StoreContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
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
         * Product in stock?
         *
         * Type: STRING
         */
        public final static String COLUMN_IN_STOCK = "in_stock";

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

        /**
         * Possible values if product is in stock or not.
         */
        public static final String PRODUCT_IN_STOCK ="yes";
        public static final String PRODUCT_NOT_IN_STOCK = "no";
        public static final String AVAILABILITY_UNKNOWN = "";
    }

}

