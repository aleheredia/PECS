package br.com.herediadesign.pecs.db.contract;

import android.provider.BaseColumns;

/**
 * Created by aheredia on 4/22/2015.
 */
public final class PecContract {

    public PecContract() {}

    public static abstract class Pec implements BaseColumns {
        public static final String TABLE_NAME = "pec";
        public static final String PATH = "path";
        public static final String LABEL = "label";
        public static final String CATEGORY_ID = "category_id";

        private static final String TEXT_TYPE = " TEXT";
        private static final String INT_TYPE = " INTEGER";
        private static final String COMMA_SEP = ",";
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        PATH + TEXT_TYPE + COMMA_SEP +
                        LABEL + TEXT_TYPE + COMMA_SEP +
                        CATEGORY_ID + INT_TYPE+
                " )";

        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + Pec.TABLE_NAME;

    }
}
