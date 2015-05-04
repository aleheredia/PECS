package br.com.herediadesign.pecs.db.contract;

import android.provider.BaseColumns;

/**
 * Created by aheredia on 4/22/2015.
 */
public final class CategoryContract {

    public CategoryContract() {}

    public static abstract class Category implements BaseColumns {
        public static final String TABLE_NAME = "category";
        public static final String LABEL = "label";
        public static final String COLOR = "color";

        private static final String TEXT_TYPE = " TEXT";
        private static final String COMMA_SEP = ",";
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        LABEL + TEXT_TYPE + COMMA_SEP +
                        COLOR + TEXT_TYPE +
                " )";

        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + Category.TABLE_NAME;

    }
}
