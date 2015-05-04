package br.com.herediadesign.pecs.activities.category;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.larswerkman.holocolorpicker.ColorPicker;

import br.com.herediadesign.pecs.R;
import br.com.herediadesign.pecs.db.repo.CategoryRepo;
import br.com.herediadesign.pecs.model.Category;

/**
 * Created by aheredia on 4/27/2015.
 */
public class CategoryFormActivity extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_form);

        final CategoryRepo cr = new CategoryRepo(this);

        Button btnSave = (Button)findViewById(R.id.btn_save);

        if(this.getIntent().getExtras()!=null){
            final int catId = this.getIntent().getExtras().getInt("catId");
            final Category cat = cr.getCategoryById(catId);
            ((EditText)findViewById(R.id.category_name)).setText(cat.getLabel());
            ((ColorPicker)findViewById(R.id.color_picker)).setColor(Color.parseColor(cat.getColor()));
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String catLabel = ((EditText)CategoryFormActivity.this.findViewById(R.id.category_name)).getText().toString().trim();
                    int catColorInt = ((ColorPicker) CategoryFormActivity.this.findViewById(R.id.color_picker)).getColor();

                    String hexColor = String.format("#%06X", (0xFFFFFF & catColorInt));

                    cat.setLabel(catLabel);
                    cat.setColor(hexColor);

                    cr.update(cat);

                    Intent catList = new Intent(CategoryFormActivity.this, CategoryListActivity.class);
                    catList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    CategoryFormActivity.this.startActivity(catList);
                    CategoryFormActivity.this.finish();

                }
            });

            RelativeLayout layout = (RelativeLayout)findViewById(R.id.viewgroup);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, btnSave.getId());

            Button btnDelete = new Button(this);

            btnDelete.setText("Apagar");

            btnDelete.setLayoutParams(layoutParams);
            layout.addView(btnDelete);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog ad = new AlertDialog.Builder(CategoryFormActivity.this)
                            .setTitle("Apagar Categoria")
                            .setMessage("Tem certeza que deseja apagar essa categoria e todas as PECs relacionadas?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    cr.delete(catId);
                                    Intent catList = new Intent(CategoryFormActivity.this, CategoryListActivity.class);
                                    catList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    CategoryFormActivity.this.startActivity(catList);
                                    CategoryFormActivity.this.finish();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });
        }else{
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Category cat = new Category();

                    String catLabel = ((EditText)CategoryFormActivity.this.findViewById(R.id.category_name)).getText().toString().trim();
                    int catColorInt = ((ColorPicker) CategoryFormActivity.this.findViewById(R.id.color_picker)).getColor();

                    String hexColor = String.format("#%06X", (0xFFFFFF & catColorInt));

                    cat.setLabel(catLabel);
                    cat.setColor(hexColor);

                    cr.insert(cat);

                    Intent catList = new Intent(CategoryFormActivity.this, CategoryListActivity.class);
                    catList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    CategoryFormActivity.this.startActivity(catList);
                    CategoryFormActivity.this.finish();

                }
            });
        }

    }
}
