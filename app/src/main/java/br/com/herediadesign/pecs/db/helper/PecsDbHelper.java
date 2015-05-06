package br.com.herediadesign.pecs.db.helper;

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
import br.com.herediadesign.pecs.model.Category;
import br.com.herediadesign.pecs.model.Pec;
import br.com.herediadesign.pecs.db.contract.PecContract;

public class PecsDbHelper extends SQLiteOpenHelper {
    public static final Object[] dbLock = new Object[0];

    public static final int DATABASE_VERSION = 23;
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
            db.execSQL("INSERT INTO " + PecContract.Pec.TABLE_NAME + " (" +
                    PecContract.Pec.PATH + "," +
                    PecContract.Pec.LABEL + "," +
                    PecContract.Pec.CATEGORY_ID + ") VALUES ('" +
                    pec.getPath() + "','" + pec.getLabel() + "'," + pec.getCategoryId() + ")");
        }
    }

    private List<Category> getCats() {
        List<Category> cats = new ArrayList<>();

        cats.add(new Category(1, "Letras", "#72AACA"));
        cats.add(new Category(2, "Cores", "#D6D8DD"));
        cats.add(new Category(3, "Formas", "#F6F080"));
        cats.add(new Category(4, "Atividades", "#B8D977"));
        cats.add(new Category(5, "Comida", "#EE6D6D"));
        cats.add(new Category(6, "Pessoas", "#DB7093"));
        cats.add(new Category(7, "Comunicação", "#FFAA00"));

        return cats;
    }

    private List<Pec> getPecs(){
        List<Pec> pecs = new ArrayList<>();

        // LETRAS
        pecs.add(new Pec(this.storeImage(R.drawable.pec_a, "pec_a.png"), "A", 1));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_b, "pec_b.png"), "B", 1));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_c, "pec_c.png"), "C", 1));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_d, "pec_d.png"), "D", 1));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_e, "pec_e.png"), "E", 1));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_f, "pec_f.png"), "F", 1));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_g, "pec_g.png"), "G", 1));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_h, "pec_h.png"), "H", 1));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_i, "pec_i.png"), "I", 1));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_j, "pec_j.png"), "J", 1));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_k, "pec_k.png"), "K", 1));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_l, "pec_l.png"), "L", 1));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_m, "pec_m.png"), "M", 1));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_n, "pec_n.png"), "N", 1));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_o, "pec_o.png"), "O", 1));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_p, "pec_p.png"), "P", 1));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_q, "pec_q.png"), "Q", 1));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_r, "pec_r.png"), "R", 1));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_s, "pec_s.png"), "S", 1));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_t, "pec_t.png"), "T", 1));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_u, "pec_u.png"), "U", 1));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_v, "pec_v.png"), "V", 1));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_w, "pec_w.png"), "W", 1));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_x, "pec_x.png"), "X", 1));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_y, "pec_y.png"), "Y", 1));
        pecs.add(new Pec(this.storeImage(R.drawable.pec_z, "pec_z.png"), "Z", 1));

        // FORMAS
        pecs.add(new Pec(this.storeImage(R.drawable.pec_bola, "pec_bola.png"), "BOLA", 3));

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
