package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.CreateDate;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.CustomDatePickerDialog;

/**
 * An activity representing a single Curriculum detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link CurriculumListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link CurriculumDetailFragment}.
 */
public class CurriculumDetailActivity extends AppCompatActivity {
    public CreateDate start_curriculum_date;
    public CreateDate end_curriculum_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculum_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
       /* fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
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
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(CurriculumDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(CurriculumDetailFragment.ARG_ITEM_ID));
            CurriculumDetailFragment fragment = new CurriculumDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.curriculum_detail_container, fragment)
                    .commit();
        }
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
            NavUtils.navigateUpTo(this, new Intent(this, CurriculumListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onChooseCurriculumDateBegin(View view) {
        showDate(start_curriculum_date, R.id.icon_choose_curriculumDateBegin, R.id.editxt_date_begin);

    }

    public void onChooseCurriculumDateEnd(View view) {
        showDate(end_curriculum_date, R.id.icon_choose_birthday, R.id.editxt_date_end);

    }

    public void showDate(CreateDate cr, int img, int txt) {
        cr = new CreateDate();
        cr.initDate();
        cr.setIb((ImageButton) findViewById(img));
        cr.setEt((EditText) findViewById(txt));
        CustomDatePickerDialog dp = new CustomDatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog,
                cr.getDatePickerListener(),
                cr.getYear(),
                cr.getMonth(),
                cr.getDay());

        DatePickerDialog obj = dp.getPicker();

        obj.show();

    }
}
