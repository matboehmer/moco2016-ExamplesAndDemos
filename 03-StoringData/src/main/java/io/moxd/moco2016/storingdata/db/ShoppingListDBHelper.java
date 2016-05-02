package io.moxd.moco2016.storingdata.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import io.moxd.moco2016.storingdata.db.MyDBContract.ListItemTable;

/**
 * Helper class for database of this app.
 *
 * @author Matthias Boehmer, matthias.boehmer@th-koeln.de
 */
public class ShoppingListDBHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "shoppingListDatabase.db";

    public ShoppingListDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createSchema(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // ideally this should be handled by comparing old and new version code and doing schema migration,
        // for sake of simplicity we just delete old tables
        db.execSQL("DROP TABLE IF EXISTS " + ListItemTable.TABLE_NAME + "; ");

        createSchema(db);
    }

    /**
     * creates schema required for this simple application
     *
     * @param db
     */
    private void createSchema(SQLiteDatabase db) {

        // create the table we need
        db.execSQL(
                "CREATE TABLE " + ListItemTable.TABLE_NAME + " (" +
                        ListItemTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        ListItemTable.COLUMN_NAME_BOUGHT + " BOOLEAN , " +
                        ListItemTable.COLUMN_NAME_QUANTITY + " INTEGER , " +
                        ListItemTable.COLUMN_NAME_ITEMNAME + " TEXT " +
                        " );"
        );
    }
}
