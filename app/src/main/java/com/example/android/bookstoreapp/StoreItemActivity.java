package com.example.android.bookstoreapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.bookstoreapp.data.StoreContract;
import com.example.android.bookstoreapp.data.StoreDbHelper;

public class StoreItemActivity extends AppCompatActivity {

    private StoreDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_item);
        
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StoreItemActivity.this, AddItemActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new StoreDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                StoreContract.ItemEntry._ID,
                StoreContract.ItemEntry.COLUMN_PRODUCT_NAME,
                StoreContract.ItemEntry.COLUMN_PRICE,
                StoreContract.ItemEntry.COLUMN_QUANTITY,
                StoreContract.ItemEntry.COLUMN_PRODUCT_IN_STOCK,
                StoreContract.ItemEntry.COLUMN_SUPPLIER_NAME,
                StoreContract.ItemEntry.COLUMN_SUPPLIER_PHONE_NUMBER };


        // Perform a query on the bookstore table
        Cursor cursor = db.query(
                StoreContract.ItemEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        TextView displayView = (TextView) findViewById(R.id.text_view_item);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The pets table contains <number of rows in Cursor> pets.
            // _id - product name - price - quantity - product in stock - supplier name - phone number
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The bookstore table contains " + cursor.getCount() + " items.\n\n");
            displayView.append(StoreContract.ItemEntry._ID + " - " +
                    StoreContract.ItemEntry.COLUMN_PRODUCT_NAME + " - " +
                    StoreContract.ItemEntry.COLUMN_PRICE + " - " +
                    StoreContract.ItemEntry.COLUMN_QUANTITY + " - " +
                    StoreContract.ItemEntry.COLUMN_PRODUCT_IN_STOCK + " - " +
                    StoreContract.ItemEntry.COLUMN_SUPPLIER_NAME + " - " +
                    StoreContract.ItemEntry.COLUMN_SUPPLIER_PHONE_NUMBER + "\n");


            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(StoreContract.ItemEntry._ID);
            int productColumnIndex = cursor.getColumnIndex(StoreContract.ItemEntry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(StoreContract.ItemEntry.COLUMN_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(StoreContract.ItemEntry.COLUMN_QUANTITY);
            int inStockColumnIndex = cursor.getColumnIndex(StoreContract.ItemEntry.COLUMN_PRODUCT_IN_STOCK);
            int supplierColumnIndex = cursor.getColumnIndex(StoreContract.ItemEntry.COLUMN_SUPPLIER_NAME);
            int contactColumnIndex = cursor.getColumnIndex(StoreContract.ItemEntry.COLUMN_SUPPLIER_PHONE_NUMBER);


            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(productColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String currentStock = cursor.getString(inStockColumnIndex);
                String currentSupplier = cursor.getString(supplierColumnIndex);
                int currentContact = cursor.getInt(contactColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentPrice + " - " +
                        currentQuantity + " - " +
                        currentStock + " - " +
                        currentSupplier + " - " +
                        currentContact));
            }
        } finally {

            cursor.close();
        }
    }


    private void insertItem() {
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(StoreContract.ItemEntry.COLUMN_PRODUCT_NAME, "Kindle Fire");
        values.put(StoreContract.ItemEntry.COLUMN_PRICE, "59.99");
        values.put(StoreContract.ItemEntry.COLUMN_QUANTITY, 12);
        values.put(StoreContract.ItemEntry.COLUMN_PRODUCT_IN_STOCK, StoreContract.ItemEntry.PRODUCT_IN_STOCK);
        values.put(StoreContract.ItemEntry.COLUMN_SUPPLIER_NAME, "READERZ, INC");
        values.put(StoreContract.ItemEntry.COLUMN_SUPPLIER_PHONE_NUMBER, 213-456-5000);

        long newRowId = db.insert(StoreContract.ItemEntry.TABLE_NAME, null, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_store_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertItem();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
