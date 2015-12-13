package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.UserMember;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.MenuDrawer;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.Messages;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.SessionWsService;

/**
 * Created by taha on 24/11/2015.
 */

public class UpdateCurusuListActivity extends AppCompatActivity {

    public int Current_Position = 5;
    public SessionWsService AppCtx;
    public MenuDrawer menu;
    private UserMember usermember;
    private Context context = this;
    private Activity activity = this;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    private ListView maListViewPerso;

    public UserMember getUsermember() {
        return usermember;
    }

    public void setUsermember(UserMember usermember) {
        this.usermember = usermember;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_listcursus);
        menu = new MenuDrawer(this, Current_Position);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        if (intent.getSerializableExtra("AppSessionContext") != null)
            AppCtx = (SessionWsService) intent.getSerializableExtra("AppSessionContext");
        if (bundle != null)
            if (bundle.getSerializable("AppSessionContext") != null)
                AppCtx = (SessionWsService) bundle.getSerializable("AppSessionContext");
        if (AppCtx == null) {
            DisplayToast(Messages.error_load_profil, 3000);
            SystemClock.sleep(3000);
            gotoMainActivity();
        }
        //ListViewInit.loadListStaticPI_View(this, AppCtx);
        setUsermember(AppCtx.getUserMember());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        menu.setMaListViewPerso(maListViewPerso);
        menu.setmDrawerLayout(mDrawerLayout);
        menu.setAppCtx(AppCtx);
        menu.setContext(context);
        menu.addDrawerItems();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mActivityTitle = getTitle().toString();
        setupDrawer();
        ImageListener();
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    private void setupDrawer() {

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {
            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about_emf) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void ImageListener() {
        ImageView home_icon;
        home_icon = (ImageView) this.findViewById(R.id.icon_home);
        // set a onclick listener for when the button gets clicked
        home_icon.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                Intent mainIntent = new Intent(context, UpdateCurusuListActivity.class);
                startActivity(mainIntent);
            }
        });

    }


    public void DisplayToast(String text, int time) {
        if (time > 0)
            time = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, time);
        toast.show();
    }

    public void DisplayToast(String text) {
        DisplayToast(text, 0);
    }

    public void gotoMainActivity() {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
