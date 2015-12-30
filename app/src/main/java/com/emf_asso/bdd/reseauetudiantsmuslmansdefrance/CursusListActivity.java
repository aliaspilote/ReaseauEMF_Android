package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.adaptater.CursusContent;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Curriculum;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.MenuDrawer;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.SessionWsService;

/**
 * An activity representing a list of LesCursus. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link CursusDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link CursusListFragment} and the item details
 * (if present) is a {@link CursusDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link CursusListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class CursusListActivity extends AppCompatActivity
        implements CursusListFragment.Callbacks {

    MenuDrawer menu;
    int Current_Position;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private Context context;
    private SessionWsService AppSessionContext;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ListView mnavList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        Intent intent = this.getIntent();
        if (intent.getSerializableExtra("AppSessionContext") != null) {
            AppSessionContext = (SessionWsService) intent.getSerializableExtra("AppSessionContext");
            if (AppSessionContext.inProfileView) {
                if (AppSessionContext.getUserMember() != null)
                    if (AppSessionContext.getUserMember().getCurriculum() != null)
                        CursusContent.pushCursusList(AppSessionContext.getUserMember().getCurriculum());
                Bundle bundle = intent.getExtras();
                Current_Position = bundle.getInt("p");
            } else if (AppSessionContext.inProssInscrView) {
                if (AppSessionContext.getServiceProcessInscription().getInscription().getUser() != null)
                    if (AppSessionContext.getServiceProcessInscription().getInscription().getUser().getCurriculum() != null)
                        CursusContent.pushCursusList(AppSessionContext.getServiceProcessInscription().getInscription().getUser().getCurriculum());
            }
        }
        setContentView(R.layout.activity_cursus_app_bar);
        if (AppSessionContext != null) {
            if (AppSessionContext.inProfileView) {
                mnavList = (ListView) findViewById(R.id.navList);
                findViewById(R.id.btn_PI_next).setVisibility(View.GONE);
                mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                mDrawerLayout.setVisibility(View.VISIBLE);
                menu = new MenuDrawer(this, context, AppSessionContext, mDrawerLayout, mnavList, Current_Position);
                menu.addDrawerItems();
                setupDrawer();
            }
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btn_add_cursus_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Curriculum newCursus = new Curriculum(getResources().getString(R.string.label_new_cursus));
                //CursusContent.pushCursusList(newCursus);
                CursusContent.addItem(newCursus);
                if (AppSessionContext.inProfileView)
                    AppSessionContext.getUserMember().setCurriculum(CursusContent.pullCursusList());
                else
                    AppSessionContext.getServiceProcessInscription().getInscription().getUser().setCurriculum(CursusContent.pullCursusList());
                onItemSelected(newCursus.id);
            }
        });

        if (findViewById(R.id.cursus_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((CursusListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.cursus_list))
                    .setActivateOnItemClick(true);
        }

    }

    /**
     * Callback method from {@link CursusListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(CursusDetailFragment.ARG_ITEM_ID, id);
            CursusDetailFragment fragment = new CursusDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.cursus_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, CursusDetailActivity.class);
            detailIntent.putExtra(CursusDetailFragment.ARG_ITEM_ID, id);
            detailIntent.putExtra("AppSessionContext", AppSessionContext);
            startActivityForResult(detailIntent, 1);
        }
    }

    public void OnNext(View view) {
        goBackPreviousActivity(1);
    }

    public void OnPrevious(View view) {
        goBackPreviousActivity(2);
    }

    public void goBackPreviousActivity(int resutlInt) {

        Intent intent;
        if (AppSessionContext.inProssInscrView) {
            AppSessionContext.getServiceProcessInscription().getInscription().getUser().setCursuses(CursusContent.ITEMS);
            intent = new Intent();
            intent.putExtra("Result", resutlInt);
            intent.putExtra("AppSessionContext", AppSessionContext);
            setResult(resutlInt, intent);
            finish();
        } else if (AppSessionContext.inProfileView) {
            Bundle bundle = new Bundle();
            AppSessionContext.getUserMember().setCursuses(CursusContent.ITEMS);
            if (AppSessionContext.getUserMember().isAdmin())
                intent = new Intent(context, AdminActivity.class);
            else
                intent = new Intent(context, UserMemberProfilActivity.class);
            bundle.putSerializable("AppSessionContext", AppSessionContext);
            bundle.putInt("p", -1);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    private void setupDrawer() {

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {
            /**
             * Called when a drawer has settled in a completely open state.
             */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /**
             * Called when a drawer has settled in a completely closed state.
             */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(getTitle().toString());
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }
}
