package br.com.herediadesign.pecs.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import br.com.herediadesign.pecs.R;
import br.com.herediadesign.pecs.db.repo.CategoryRepo;
import br.com.herediadesign.pecs.db.repo.PecRepo;
import br.com.herediadesign.pecs.model.Pec;
import br.com.herediadesign.pecs.util.ImageUtils;

/**
 * Created by aheredia on 4/22/2015.
 */
public class BasicImageAdapter extends BaseAdapter {
    private Context mContext;

    private int cat;

    public BasicImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return this.getPecs(this.cat).size();
    }

    public Object getItem(int position) {
        return this.getPecs(this.cat).get(position);
    }

    public long getItemId(int position) {
        return this.getPecs(this.cat).get(position).getId();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            //imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        //imageView.setImageResource(mThumbIds[position]);
        //imageView.setImageResource(Integer.parseInt(this.getPecs(this.cat).get(position).getPath()));
        try{
            Pec pec = this.getPecs(this.cat).get(position);

            Bitmap pecBase = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.pec_base);

            File f = new File(mContext.getFilesDir(), "pec_"+pec.getLabel().toLowerCase()+".png");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

            imageView.setImageBitmap(ImageUtils.overlay(pecBase, b, pec.getLabel()));
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        CategoryRepo catRepo = new CategoryRepo(mContext);
        imageView.setBackgroundColor(Color.parseColor(catRepo.getCategoryById(this.cat).getColor()));
        return imageView;
    }

    public void setCategory(int cat){
        this.cat = cat;
    }

    private List<Pec> getPecs(int cat){
        PecRepo pecRepo = new PecRepo(mContext);
        List<Pec> pecs = pecRepo.getPecListByCategory(cat);

        return pecs;
    }


}