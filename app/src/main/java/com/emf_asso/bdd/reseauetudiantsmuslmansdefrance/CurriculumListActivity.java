package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Curriculum;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DegreeStudy;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Discipline;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.SessionWsService;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.dummy.DummyContent;

import java.util.Date;

/**
 * An activity representing a list of Curriculums. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link CurriculumDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link CurriculumListFragment} and the item details
 * (if present) is a {@link CurriculumDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link CurriculumListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class CurriculumListActivity extends AppCompatActivity
        implements CurriculumListFragment.Callbacks {

    public SessionWsService AppSessionContext;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculum_app_bar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btn_add_cursus_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActionOpenNewCursus();
            }
        });
        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (findViewById(R.id.curriculum_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
            ((CurriculumListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.curriculum_list))
                    .setActivateOnItemClick(true);
        }

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            if (bundle.getSerializable("AppContextFromDetails") != null) {
                AppSessionContext = (SessionWsService) bundle.getSerializable("AppContextFromDetails");
                loadCursus();
            } else if (bundle.getSerializable("AppSessionContext") != null) {
                AppSessionContext = (SessionWsService) bundle.getSerializable("AppSessionContext");
                loadCursus();
            } else
                AppSessionContext = new SessionWsService();
            if (bundle.getString("test") != null)
                DisplayToast(bundle.getString("test") + "cool", 2000);
        }
    }

    public void DisplayToast(String text, int time) {
        if (time > 0)
            time = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, text, time);
        toast.show();
    }

    public void ActionOpenNewCursus() {
        Curriculum newCursus = new Curriculum("Nouveau Cursus", new Date(), new Date(),
                new Discipline("", "", 0)
                , "", "",
                new DegreeStudy("", 0, 0));/*
        newCursus.setId(DummyContent.getMaxID() + 1);
        DummyContent.addItem(newCursus);
        onItemSelected((Integer.toString(DummyContent.getMaxID())));*/
    }

    public void loadCursus() {
        // if (AppSessionContext.getServiceProcessInscription().getInscription().getUser().getCurriculum().size() > 0)
        // DummyContent.setNewCursusList(AppSessionContext.getServiceProcessInscription().getInscription().getUser().getCurriculum());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            super.getIntent().putExtra("AppSessionContext", AppSessionContext);
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        DummyContent.DummyItem selectedItem = DummyContent.ITEM_MAP.get(id);
        // super.getIntent().putExtra("selectedItem", selectedItem);
        super.getIntent().putExtra("AppSessionContext", AppSessionContext);
        return super.onOptionsItemSelected(item);
    }


    /**
     * Callback method from {@link CurriculumListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(CurriculumDetailFragment.ARG_ITEM_ID, id);
            arguments.putSerializable("AppSessionContext", AppSessionContext);

            CurriculumDetailFragment fragment = new CurriculumDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.curriculum_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, CurriculumDetailActivity.class);
            detailIntent.putExtra(CurriculumDetailFragment.ARG_ITEM_ID, id);

            DummyContent.DummyItem selectedItem = DummyContent.ITEM_MAP.get(id);
            //detailIntent.putExtra("selectedItem", selectedItem);
            detailIntent.putExtra("AppSessionContext", AppSessionContext);
            startActivity(detailIntent);
        }
    }
}
