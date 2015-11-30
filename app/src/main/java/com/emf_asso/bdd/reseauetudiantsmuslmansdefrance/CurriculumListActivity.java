package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        */
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

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((CurriculumListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.curriculum_list))
                    .setActivateOnItemClick(true);
        }

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            if (bundle.getSerializable("AppSessionContext") != null) {
                AppSessionContext = (SessionWsService) bundle.getSerializable("AppSessionContext");
                //ListViewInit.loadListStaticCursus_View(AppSessionContext);
                //loadCursus();
            } else
                AppSessionContext = new SessionWsService();
        }
    }

    public void ActionOpenNewCursus() {
        Curriculum newCursus = new Curriculum("Nouveau Cursus", new Date(), new Date(),
                new Discipline("", "", 0)
                , "", "",
                new DegreeStudy("", 0, 0));
        newCursus.setId(DummyContent.getMaxID() + 1);
        DummyContent.addItem(newCursus);
        onItemSelected((Integer.toString(DummyContent.getMaxID())));
    }

    public void loadCursus() {
        DummyContent.setNewCursusList(AppSessionContext.getServiceProcessInscription().getInscription().getUser().getCurriculum());
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
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
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
            detailIntent.putExtra("AppSessionContext", AppSessionContext);
            startActivity(detailIntent);
        }
    }
}
