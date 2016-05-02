package io.moxd.moco2016.storingdata.controller;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import io.moxd.moco2016.storingdata.R;
import io.moxd.moco2016.storingdata.db.MyDBContract.ListItemTable;
import io.moxd.moco2016.storingdata.db.ShoppingListDBHelper;

public class ShoppingListActivity extends ListActivity {

    private SQLiteDatabase db;


    /**
     * can be used to easily start this activity
     *
     * @param parent
     */
    public static void openActivity(Activity parent) {
        parent.startActivity(new Intent(parent, ShoppingListActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppinglist);

        String[] projection = {ListItemTable.COLUMN_NAME_QUANTITY, ListItemTable.COLUMN_NAME_BOUGHT, ListItemTable.COLUMN_NAME_ITEMNAME, ListItemTable._ID};

        String[] fromColumns = {ListItemTable.COLUMN_NAME_QUANTITY, ListItemTable.COLUMN_NAME_BOUGHT, ListItemTable.COLUMN_NAME_ITEMNAME};
        int[] toViews = {R.id.item_shoppinglist_quantity, R.id.item_shoppinglist_bought, R.id.item_shoppinglist_itemname};


        ShoppingListDBHelper dbHelper = new ShoppingListDBHelper(this);
        db = dbHelper.getWritableDatabase();

        Cursor cursor = db.query(ListItemTable.TABLE_NAME, projection, null, null, null, null, null);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.item_shoppinglist, cursor, fromColumns, toViews, 0);

        ListView listView = getListView();
        listView.setAdapter(adapter);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(ShoppingListActivity.this, "clicked pos " + position + " w/ id #" + id, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onStop() {
        super.onStop();
        db.close();
    }
}
