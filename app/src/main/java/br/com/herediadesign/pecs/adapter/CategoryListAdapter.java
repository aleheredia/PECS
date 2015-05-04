package br.com.herediadesign.pecs.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.herediadesign.pecs.R;
import br.com.herediadesign.pecs.activities.category.CategoryFormActivity;
import br.com.herediadesign.pecs.activities.category.CategoryListActivity;
import br.com.herediadesign.pecs.model.Category;

/**
 * Created by aheredia on 4/27/2015.
 */
public class CategoryListAdapter extends ArrayAdapter<Object> {

    Context context;
    int resource;
    Object categories[] = null;


    public CategoryListAdapter(Context context, int resource, Object[] categories) {
        super(context, resource, categories);

        this.resource = resource;
        this.context = context;
        this.categories = categories;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            // inflate the layout
            LayoutInflater inflater = ((CategoryListActivity) context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
        }

        Category cat = (Category) categories[position];

        TextView categoryItem = (TextView) convertView.findViewById(R.id.categoria);
        categoryItem.setText(cat.getLabel());
        categoryItem.setTag(cat.getId());

        categoryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCat = new Intent(CategoryListAdapter.this.context, CategoryFormActivity.class);
                Bundle b = new Bundle();
                b.putInt("catId", (int) v.getTag());
                addCat.putExtras(b);
                CategoryListAdapter.this.context.startActivity(addCat);
            }
        });

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        imageView.setBackgroundColor(Color.parseColor(cat.getColor()));

        return convertView;
    }
}