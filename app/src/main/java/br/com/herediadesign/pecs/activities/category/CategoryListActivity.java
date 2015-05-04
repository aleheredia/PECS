package br.com.herediadesign.pecs.activities.category;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.herediadesign.pecs.R;
import br.com.herediadesign.pecs.adapter.CategoryListAdapter;
import br.com.herediadesign.pecs.db.repo.CategoryRepo;
import br.com.herediadesign.pecs.model.Category;

public class CategoryListActivity extends Activity {

    ListView categoriesListView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_list);

        categoriesListView = (ListView) findViewById(R.id.category_list);

        CategoryRepo catRepo = new CategoryRepo(this);

        ArrayList<Category> cats = catRepo.getCategoryList();

        final CategoryListAdapter catAdapter = new CategoryListAdapter(this, R.layout.category_list_item, cats.toArray());

        categoriesListView.setAdapter(catAdapter);

        Button addCategory = (Button) findViewById(R.id.add_category);

        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addCat = new Intent(CategoryListActivity.this, CategoryFormActivity.class);
                startActivity(addCat);
            }
        });
    }

}
