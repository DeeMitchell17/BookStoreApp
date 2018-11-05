package com.example.android.bookstoreapp;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.bookstoreapp.data.StoreContract;
import com.example.android.bookstoreapp.data.StoreDbHelper;

public class AddItemActivity extends Activity {

    /** EditText field to enter the product name */
    private EditText mProductNameEditText;

    /** EditText field to enter the product price */
    private EditText mPriceEditText;

    /** EditText field to enter the product quantity */
    private EditText mQuantityEditText;

    /** EditText field to enter the product supplier name */
    private EditText mSupplierNameEditText;

    /** EditText field to enter the product supplier phone number */
    private EditText mSupplierPhoneNumberEditText;

    /** EditText field to enter if product is in stock */
    private Spinner mInStockSpinner;

    private String mInStock = StoreContract.ItemEntry.AVAILABILITY_UNKNOWN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        mProductNameEditText = (EditText) mProductNameEditText.findViewById(R.id.edit_product_name);
        mPriceEditText = (EditText) mPriceEditText.findViewById(R.id.edit_price);
        mQuantityEditText = (EditText) mQuantityEditText.findViewById(R.id.edit_quantity);
        mInStockSpinner = (Spinner) mInStockSpinner.findViewById(R.id.spinner_in_stock);
        mSupplierNameEditText = (EditText) mSupplierNameEditText.findViewById(R.id.edit_supplier_name);
        mSupplierPhoneNumberEditText = (EditText) mSupplierPhoneNumberEditText.findViewById(R.id.edit_phone_number);

        setupSpinner();
    }

    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter inStockSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_in_stock_options, android.R.layout.simple_spinner_item);

        inStockSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mInStockSpinner.setAdapter(inStockSpinnerAdapter);

        mInStockSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        if (selection.equals(getString(R.string.product_in_stock))) {
                            mInStock = StoreContract.ItemEntry.PRODUCT_IN_STOCK;
                        } else if(selection.equals(getString(R.string.product_not_in_stock))) {
                            mInStock = StoreContract.ItemEntry.PRODUCT_NOT_IN_STOCK;
                        } else {
                            mInStock = StoreContract.ItemEntry.AVAILABILITY_UNKNOWN;
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mInStock = StoreContract.ItemEntry.AVAILABILITY_UNKNOWN;
            }
        });
    }

    private void insertItem() {

        String nameString = mProductNameEditText.getText().toString().trim();

        String supplierNameString = mSupplierNameEditText.getText().toString().trim();

        String priceString = mPriceEditText.getText().toString().trim();
        int price = Integer.parseInt(priceString);

        String quantityString = mQuantityEditText.getText().toString().trim();
        int quantity = Integer.parseInt(quantityString);

        String contactString = mSupplierPhoneNumberEditText.getText().toString().trim();
        int contact = Integer.parseInt(contactString);

        StoreDbHelper mDbHelper = new StoreDbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(StoreContract.ItemEntry.COLUMN_PRODUCT_NAME, nameString);
        values.put(StoreContract.ItemEntry.COLUMN_PRICE, price);
        values.put(StoreContract.ItemEntry.COLUMN_QUANTITY, quantity);
        values.put(StoreContract.ItemEntry.COLUMN_SUPPLIER_NAME, supplierNameString);
        values.put(StoreContract.ItemEntry.COLUMN_SUPPLIER_PHONE_NUMBER, contact);

        long newRowId = db.insert(StoreContract.ItemEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error saving new product info", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Product saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_add_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save item to database
                insertItem();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (StoreItemActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
