package io.moxd.moco2016.storingdata.db;

import android.provider.BaseColumns;

/**
 * Contract of our database providing access to all table and attribute names.
 *
 * @author Matthias Boehmer, matthias.boehmer@th-koeln.de
 */
public class MyDBContract {

    public static abstract class ListItemTable implements BaseColumns {
        public static final String TABLE_NAME = "shoppingListEntry";
        public static final String COLUMN_NAME_ITEMNAME = "item";
        public static final String COLUMN_NAME_QUANTITY = "quantity";
        public static final String COLUMN_NAME_BOUGHT = "bought";
    }
}
