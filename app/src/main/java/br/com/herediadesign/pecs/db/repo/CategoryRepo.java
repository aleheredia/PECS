package br.com.herediadesign.pecs.db.repo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.herediadesign.pecs.backup.PecsBackupAgent;
import br.com.herediadesign.pecs.db.contract.CategoryContract;
import br.com.herediadesign.pecs.db.contract.PecContract;
import br.com.herediadesign.pecs.db.helper.PecsDbHelper;
import br.com.herediadesign.pecs.model.Category;
import br.com.herediadesign.pecs.model.Pec;

/**
 * Created by Avell B155 MAX on 22/04/2015.
 */
public class CategoryRepo {
    private PecsDbHelper dbHelper;
    private PecsBackupAgent bkpAgent = new PecsBackupAgent();

    Context ctx;

    public CategoryRepo(Context context){
        this.ctx = context;
        dbHelper = new PecsDbHelper(context);
    }

    public int insert(Category category) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CategoryContract.Category.LABEL, category.getLabel());
        values.put(CategoryContract.Category.COLOR, category.getColor());

        long cat_id = db.insert(CategoryContract.Category.TABLE_NAME, null, values);
        db.close();

        bkpAgent.requestBackup(this.ctx);

        return (int) cat_id;
    }

    public void delete(int cat_id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(CategoryContract.Category.TABLE_NAME, CategoryContract.Category._ID + "= ?", new String[] {String.valueOf(cat_id)});
        db.close();

        bkpAgent.requestBackup(this.ctx);
    }

    public void update(Category cat) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CategoryContract.Category.LABEL, cat.getLabel());
        values.put(CategoryContract.Category.COLOR, cat.getColor());

        db.update(CategoryContract.Category.TABLE_NAME, values, CategoryContract.Category._ID + "= ?", new String[]{String.valueOf(cat.getId())});
        db.close();

        bkpAgent.requestBackup(this.ctx);
    }

    public ArrayList<Category> getCategoryList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery =  "SELECT  " +
                CategoryContract.Category._ID + "," +
                CategoryContract.Category.LABEL + "," +
                CategoryContract.Category.COLOR +
                " FROM " + CategoryContract.Category.TABLE_NAME;

        ArrayList<Category> catList = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Category cat = new Category(
                        cursor.getInt(cursor.getColumnIndex(CategoryContract.Category._ID)),
                        cursor.getString(cursor.getColumnIndex(CategoryContract.Category.LABEL)),
                        cursor.getString(cursor.getColumnIndex(CategoryContract.Category.COLOR))
                );
                catList.add(cat);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return catList;
    }

    public Category getCategoryById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery =  "SELECT  " +
                CategoryContract.Category._ID + "," +
                CategoryContract.Category.LABEL + "," +
                CategoryContract.Category.COLOR +
                " FROM " + CategoryContract.Category.TABLE_NAME
                + " WHERE " +
                CategoryContract.Category._ID + "=?";

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(id) } );

        Category cat = new Category();

        if (cursor.moveToFirst()) {
            do {
                cat.setId(cursor.getInt(cursor.getColumnIndex(CategoryContract.Category._ID)));
                cat.setLabel(cursor.getString(cursor.getColumnIndex(CategoryContract.Category.LABEL)));
                cat.setColor(cursor.getString(cursor.getColumnIndex(CategoryContract.Category.COLOR)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return cat;
    }
}
