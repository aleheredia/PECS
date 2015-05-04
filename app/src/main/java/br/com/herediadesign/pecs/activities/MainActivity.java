package br.com.herediadesign.pecs.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import br.com.herediadesign.pecs.R;
import br.com.herediadesign.pecs.activities.category.CategoryListActivity;
import br.com.herediadesign.pecs.activities.pec.PecListActivity;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button basicButton = (Button) findViewById(R.id.btn_basico);
        basicButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent basic = new Intent(MainActivity.this, BasicActivity.class);
                startActivity(basic);
            }
        });

        Button intermediateButton = (Button) findViewById(R.id.btn_intermediario);
        intermediateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Intent intermediate = new Intent(MainActivity.this, TabActivity.class);
                //startActivity(intermediate);
            }
        });

        Button btnQuit = (Button)findViewById(R.id.btn_quit);
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_categories) {
            Intent categories = new Intent(MainActivity.this, CategoryListActivity.class);
            startActivity(categories);
        }
        if (id == R.id.action_pecs) {
            Intent pecs = new Intent(MainActivity.this, PecListActivity.class);
            startActivity(pecs);
        }

        return super.onOptionsItemSelected(item);
    }
}
