package com.example.android.bookstoreapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
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

public class AddItemActivity extends AppCompatActivity {

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
    private Spinner mStockSpinner;

    private String mInStock = StoreContract.ItemEntry.AVAILABILITY_UNKNOWN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        mProductNameEditText = (EditText) findViewById(R.id.edit_product_name);
        mPriceEditText = (EditText) findViewById(R.id.edit_price);
        mQuantityEditText = (EditText) findViewById(R.id.edit_quantity);
        mStockSpinner = (Spinner) findViewById(R.id.spinner_stock);
        mSupplierNameEditText = (EditText) findViewById(R.id.edit_supplier_name);
        mSupplierPhoneNumberEditText = (EditText) findViewById(R.id.edit_phone_number);

        setupSpinner();
    }

    private void setupSpinner() {

        ArrayAdapter stockSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_in_stock_options, android.R.layout.simple_spinner_item);

        stockSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mStockSpinner.setAdapter(stockSpinnerAdapter);

        mStockSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        String priceString = mPriceEditText.getText().toString().trim();
        double price = Double.parseDouble(priceString);

        String quantityString = mQuantityEditText.getText().toString().trim();
        int quantity = Integer.parseInt(quantityString);

        String supplierNameString = mSupplierNameEditText.getText().toString().trim();

        String contactString = mSupplierPhoneNumberEditText.getText().toString().trim();
        int contact = Integer.parseInt(contactString);

        StoreDbHelper mDbHelper = new StoreDbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(StoreContract.ItemEntry.COLUMN_PRODUCT_NAME, nameString);
        values.put(StoreContract.ItemEntry.COLUMN_PRICE, price);
        values.put(StoreContract.ItemEntry.COLUMN_QUANTITY, quantity);
        values.put(StoreContract.ItemEntry.COLUMN_IN_STOCK, mInStock);
        values.put(StoreContract.ItemEntry.COLUMN_SUPPLIER_NAME, supplierNameString);
        values.put(StoreContract.ItemEntry.COLUMN_SUPPLIER_PHONE_NUMBER, contact);

        long newRowId = db.insert(StoreContract.ItemEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {

            Toast.makeText(this, "Error saving new product info", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Product saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                insertItem();
                finish();
                return true;
            case R.id.action_delete:
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
