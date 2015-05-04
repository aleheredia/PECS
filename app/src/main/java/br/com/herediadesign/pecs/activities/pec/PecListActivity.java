package br.com.herediadesign.pecs.activities.pec;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.herediadesign.pecs.R;
import br.com.herediadesign.pecs.adapter.PecListAdapter;
import br.com.herediadesign.pecs.db.repo.CategoryRepo;
import br.com.herediadesign.pecs.db.repo.PecRepo;
import br.com.herediadesign.pecs.model.Category;
import br.com.herediadesign.pecs.model.Pec;

public class PecListActivity extends Activity {

    private Spinner catSpinner;

    private CategoryRepo cr = new CategoryRepo(this);

    private ArrayList<Category> categoryList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pec_list);

        ArrayList<String> catsLabels = new ArrayList<String>();

        categoryList = cr.getCategoryList();

        for(Category cat : categoryList){
            catsLabels.add(cat.getLabel());
        }

        catSpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> cats_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, catsLabels);
        cats_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catSpinner.setAdapter(cats_adapter);
        catSpinner.setSelection(0);

        catSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category cat = categoryList.get(position);

                PecRepo pr = new PecRepo(PecListActivity.this);

                List<Pec> pecs = pr.getPecListByCategory(cat.getId());

                PecListAdapter pecListAdapter = new PecListAdapter(PecListActivity.this, R.layout.pec_list_item, pecs.toArray());

                ListView pecList = (ListView)PecListActivity.this.findViewById(R.id.pec_lista);

                pecList.setAdapter(pecListAdapter);

                Button btnAddPec = (Button)findViewById(R.id.btnAddPec);
                btnAddPec.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent pecForm = new Intent(PecListActivity.this, PecFormActivity.class);
                        PecListActivity.this.startActivity(pecForm);
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
