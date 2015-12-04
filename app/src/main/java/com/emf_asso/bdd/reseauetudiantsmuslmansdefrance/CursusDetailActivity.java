package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.adaptater.CursusContent;

/**
 * An activity representing a single Cursus detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link CursusListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link CursusDetailFragment}.
 */
public class CursusDetailActivity extends AppCompatActivity {

    private String current_ARG_ITEM_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursus_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //

        if (savedInstanceState == null) {
            current_ARG_ITEM_ID = getIntent().getStringExtra(CursusDetailFragment.ARG_ITEM_ID);
            //getIntent().getStringExtra(CursusDetailFragment.ARG_ITEM_ID)
            Bundle arguments = new Bundle();
            arguments.putString(CursusDetailFragment.ARG_ITEM_ID, current_ARG_ITEM_ID);
            CursusDetailFragment fragment = new CursusDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.cursus_detail_container, fragment)
                    .commit();
        }

        Button deleteBtn = (Button) findViewById(R.id.btn_delete);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CursusContent.removeItem(current_ARG_ITEM_ID);
                backToListCursus();
                //recreate();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, CursusListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void backToListCursus() {
        NavUtils.navigateUpTo(this, new Intent(this, CursusListActivity.class));
    }
}
