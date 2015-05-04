package br.com.herediadesign.pecs.db.repo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.herediadesign.pecs.db.contract.PecContract;
import br.com.herediadesign.pecs.db.helper.PecsDbHelper;
import br.com.herediadesign.pecs.model.Pec;

/**
 * Created by Avell B155 MAX on 22/04/2015.
 */
public class PecRepo {
    private PecsDbHelper dbHelper;

    public PecRepo(Context context){
        dbHelper = new PecsDbHelper(context);
    }

    public int insert(Pec pec) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PecContract.Pec.PATH, pec.getPath());
        values.put(PecContract.Pec.LABEL, pec.getLabel());
        values.put(PecContract.Pec.CATEGORY_ID, pec.getCategoryId());

        long pecId = db.insert(PecContract.Pec.TABLE_NAME, null, values);
        db.close();
        return (int) pecId;
    }

    public void delete(int pecId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(PecContract.Pec.TABLE_NAME, PecContract.Pec._ID + "= ?", new String[] {String.valueOf(pecId)});
        db.close();
    }

    public void update(Pec pec) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PecContract.Pec.PATH, pec.getPath());
        values.put(PecContract.Pec.LABEL,pec.getLabel());
        values.put(PecContract.Pec.CATEGORY_ID, pec.getCategoryId());

        db.update(PecContract.Pec.TABLE_NAME, values, PecContract.Pec._ID + "= ?", new String[] { String.valueOf(pec.getId()) });
        db.close();
    }

    public ArrayList<Pec> getPecList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery =  "SELECT  " +
                PecContract.Pec._ID + "," +
                PecContract.Pec.PATH + "," +
                PecContract.Pec.LABEL + "," +
                PecContract.Pec.CATEGORY_ID +
                " FROM " + PecContract.Pec.TABLE_NAME;

        ArrayList<Pec> pecList = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Pec pec = new Pec(
                        cursor.getInt(cursor.getColumnIndex(PecContract.Pec._ID)),
                        cursor.getString(cursor.getColumnIndex(PecContract.Pec.PATH)),
                        cursor.getString(cursor.getColumnIndex(PecContract.Pec.LABEL)),
                        cursor.getInt(cursor.getColumnIndex(PecContract.Pec.CATEGORY_ID))
                );
                pecList.add(pec);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return pecList;
    }

    public Pec getPecById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery =  "SELECT  " +
                PecContract.Pec._ID + "," +
                PecContract.Pec.PATH + "," +
                PecContract.Pec.LABEL + "," +
                PecContract.Pec.CATEGORY_ID +
                " FROM " + PecContract.Pec.TABLE_NAME
                + " WHERE " +
                PecContract.Pec._ID + "=?";

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(id) } );

        Pec pec = new Pec();

        if (cursor.moveToFirst()) {
            do {
                pec.setId(cursor.getInt(cursor.getColumnIndex(PecContract.Pec._ID)));
                pec.setPath(cursor.getString(cursor.getColumnIndex(PecContract.Pec.PATH)));
                pec.setLabel(cursor.getString(cursor.getColumnIndex(PecContract.Pec.LABEL)));
                pec.setCategoryId(cursor.getInt(cursor.getColumnIndex(PecContract.Pec.CATEGORY_ID)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return pec;
    }

    public ArrayList<Pec> getPecListByCategory(int cat) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery =  "SELECT  " +
                PecContract.Pec._ID + "," +
                PecContract.Pec.PATH + "," +
                PecContract.Pec.LABEL + "," +
                PecContract.Pec.CATEGORY_ID +
                " FROM " + PecContract.Pec.TABLE_NAME
                + " WHERE " +
                PecContract.Pec.CATEGORY_ID + "=?";

        ArrayList<Pec> pecList = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(cat) });

        if (cursor.moveToFirst()) {
            do {
                Pec pec = new Pec(
                        cursor.getInt(cursor.getColumnIndex(PecContract.Pec._ID)),
                        cursor.getString(cursor.getColumnIndex(PecContract.Pec.PATH)),
                        cursor.getString(cursor.getColumnIndex(PecContract.Pec.LABEL)),
                        cursor.getInt(cursor.getColumnIndex(PecContract.Pec.CATEGORY_ID))
                );
                pecList.add(pec);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return pecList;
    }
}
