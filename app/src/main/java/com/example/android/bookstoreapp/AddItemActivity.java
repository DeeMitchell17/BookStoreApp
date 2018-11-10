package com.example.android.bookstoreapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        mProductNameEditText = (EditText) findViewById(R.id.edit_product_name);
        mPriceEditText = (EditText) findViewById(R.id.edit_price);
        mQuantityEditText = (EditText) findViewById(R.id.edit_quantity);
        mSupplierNameEditText = (EditText) findViewById(R.id.edit_supplier_name);
        mSupplierPhoneNumberEditText = (EditText) findViewById(R.id.edit_phone_number);

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
