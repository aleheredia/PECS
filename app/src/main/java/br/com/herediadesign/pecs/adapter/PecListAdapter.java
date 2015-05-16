package br.com.herediadesign.pecs.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import br.com.herediadesign.pecs.R;
import br.com.herediadesign.pecs.activities.pec.PecFormActivity;
import br.com.herediadesign.pecs.activities.pec.PecListActivity;
import br.com.herediadesign.pecs.model.Pec;
import br.com.herediadesign.pecs.util.ImageUtils;

/**
 * Created by aheredia on 4/27/2015.
 */
public class PecListAdapter extends ArrayAdapter<Object> {

    Context context;
    int resource;
    Object pecs[] = null;
    public static final Object sDataLock = new Object();

    public PecListAdapter(Context context, int resource, Object[] pecs) {
        super(context, resource, pecs);

        this.resource = resource;
        this.context = context;
        this.pecs = pecs;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            // inflate the layout
            LayoutInflater inflater = ((PecListActivity) context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
        }

        Pec pec = (Pec) pecs[position];

        TextView pecItem = (TextView) convertView.findViewById(R.id.pec);
        pecItem.setText(pec.getLabel());
        pecItem.setTag(pec.getId());

        ImageView imageView = (ImageView) convertView.findViewById(R.id.pec_image);

        try{
            Bitmap pecBase = BitmapFactory.decodeResource(context.getResources(), R.drawable.pec_base);
            synchronized (sDataLock) {
                File f = new File(context.getFilesDir(), "pec_" + pec.getLabel().toLowerCase() + ".png");
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

                imageView.setImageBitmap(ImageUtils.overlay(pecBase, b, pec.getLabel()));
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        return convertView;
    }
}