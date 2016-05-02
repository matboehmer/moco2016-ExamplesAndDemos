package io.moxd.moco2016.storingdata.controller;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import io.moxd.moco2016.storingdata.R;
import io.moxd.moco2016.storingdata.db.MyDBContract.ListItemTable;
import io.moxd.moco2016.storingdata.db.ShoppingListDBHelper;

public class MainActivity extends AppCompatActivity {

    private TextView textViewSettingsCheckbox;

    private TextView textViewSettingsText;
    private EditText editTextListEntryAmount;
    private EditText editTextListEntryItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewSettingsCheckbox = (TextView) findViewById(R.id.textViewSettingsCheckbox);
        textViewSettingsText = (TextView) findViewById(R.id.textViewSettingsText);
        editTextListEntryAmount = (EditText) findViewById(R.id.editTextListEntryAmount);
        editTextListEntryItem = (EditText) findViewById(R.id.editTextListEntryItem);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        boolean check = settings.getBoolean("pref_checkbox_demo", false);
        String text = settings.getString("pref_edittext_demo", "");

        textViewSettingsCheckbox.setText(check ? "is checked" : "is not checked");
        textViewSettingsText.setText(text);
    }


    /**
     * button click handler
     *
     * @param view
     */
    public void buttonOpenSettings(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    /**
     * button click handler
     *
     * @param view
     */
    public void buttonOpenShoppingList(View view) {
        ShoppingListActivity.openActivity(this);
    }

    /**
     * button click handler
     *
     * @param view
     */
    public void buttonAddRandomEntry(View view) {

        String amountString = editTextListEntryAmount.getText().toString();
        int amount = Integer.parseInt(amountString);

        ContentValues cv = new ContentValues();
        cv.put(ListItemTable.COLUMN_NAME_QUANTITY, amount);
        cv.put(ListItemTable.COLUMN_NAME_ITEMNAME, editTextListEntryItem.getText().toString());

        ShoppingListDBHelper dbh = new ShoppingListDBHelper(this);
        SQLiteDatabase db = dbh.getWritableDatabase();

        db.insert(ListItemTable.TABLE_NAME, null, cv);
        db.close();
    }

    /**
     * button click handler
     *
     * @param view
     */
    public void buttonDeleteAllEntries(View view) {

        ShoppingListDBHelper dbh = new ShoppingListDBHelper(this);
        SQLiteDatabase db = dbh.getWritableDatabase();

        db.delete(ListItemTable.TABLE_NAME, null, null);

        db.close();
    }


}
