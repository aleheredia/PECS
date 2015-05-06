package br.com.herediadesign.pecs.db.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import br.com.herediadesign.pecs.R;
import br.com.herediadesign.pecs.db.contract.CategoryContract;
import br.com.herediadesign.pecs.db.repo.PecRepo;
import br.com.herediadesign.pecs.model.Category;
import br.com.herediadesign.pecs.model.Pec;
import br.com.herediadesign.pecs.db.contract.PecContract;

public class PecsDbHelper extends SQLiteOpenHelper {
    public static final Object[] dbLock = new Object[0];

    public static final int DATABASE_VERSION = 30;
    public static final String DATABASE_NAME = "Pecs.db";
    private Context context;

    public PecsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CategoryContract.Category.SQL_CREATE_TABLE);
        db.execSQL(PecContract.Pec.SQL_CREATE_TABLE);
        this.preloadCategories(this.getCats(), db);
        this.preloadPecs(this.getPecs(), db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(PecContract.Pec.SQL_DELETE_TABLE);
        db.execSQL(CategoryContract.Category.SQL_DELETE_TABLE);
        onCreate(db);
    }

    private void preloadCategories(List<Category> cats, SQLiteDatabase db){
        for(Category cat : cats) {
            db.execSQL("INSERT INTO " + CategoryContract.Category.TABLE_NAME + " (" +
                    CategoryContract.Category.LABEL + "," +
                    CategoryContract.Category.COLOR + ") VALUES ('" +
                    cat.getLabel() + "', '" + cat.getColor() +"')");
        }
    }

    private void preloadPecs(List<Pec> pecs, SQLiteDatabase db){
        for(Pec pec : pecs) {
            ContentValues values = new ContentValues();
            values.put(PecContract.Pec.PATH, pec.getPath());
            values.put(PecContract.Pec.LABEL, pec.getLabel());
            values.put(PecContract.Pec.CATEGORY_ID, pec.getCategoryId());

            db.insert(PecContract.Pec.TABLE_NAME, null, values);
        }
    }

    private List<Category> getCats() {
        List<Category> cats = new ArrayList<>();

        cats.add(new Category(1, "AVD", "#D6D8DD"));
        cats.add(new Category(2, "Comida", "#DB7093"));
        cats.add(new Category(3, "Pessoas", "#72AACA"));

        return cats;
    }

    private List<Pec> getPecs(){
        List<Pec> pecs = new ArrayList<>();

        // AVD
        pecs.add(new Pec(this.storeImage(R.drawable.pec_almoco, "pec_almoço.png"), "Almoço", 1));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_brincar, "pec_brincar.png"), "Brincar", 1));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_coco, "pec_cocô.png"), "Cocô", 1));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_dentes, "pec_dentes.png"), "Dentes", 1));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_dormir, "pec_dormir.png"), "Dormir", 1));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_janta, "pec_janta.png"), "Janta", 1));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_lavar_mao, "pec_lavar mão.png"), "Lavar Mão", 1));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_tomar_banho, "pec_banho.png"), "Banho", 1));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_xixi, "pec_xixi.png"), "Xixi", 1));

        // Comida
        pecs.add(new Pec(this.storeImage(R.drawable.pec_agua, "pec_água.png"), "Água", 2));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_banana, "pec_banana.png"), "Banana", 2));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_danete, "pec_danete.png"), "Danete", 2));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_gelatina, "pec_gelatina.png"), "Gelatina", 2));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_iogurte, "pec_iogurte.png"), "Iogurte", 2));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_laranja, "pec_laranja.png"), "Laranja", 2));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_macarrao, "pec_macarrão.png"), "Macarrão", 2));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_manga, "pec_manga.png"), "Manga", 2));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_pipoca, "pec_pipoca.png"), "Pipoca", 2));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_suco, "pec_suco.png"), "Suco", 2));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_tete, "pec_tetê.png"), "Tetê", 2));

        return pecs;
    }

    private String storeImage(int resourceId, String filename){
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), resourceId);

        FileOutputStream outputStream;

        try {
            synchronized (dbLock) {
                outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return context.getFilesDir().toString();
    }

}
