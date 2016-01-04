package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.UserMember;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.MenuDrawer;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.SessionWsService;

/**
 * Created by taha on 24/11/2015.
 */

public class AboutEmfActivity extends AppCompatActivity {

    public int Current_Position;
    public SessionWsService AppCtx;
    public MenuDrawer menu;
    private UserMember usermember;
    private Context context = this;
    private Activity activity = this;
    private ListView mDrawerList;
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
        setContentView(R.layout.activity_about_emf);
        initText();
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        int a = bundle.getInt("p");
        Current_Position = a;

        Boolean intentExtrat = false;

        if (intent.getSerializableExtra("AppSessionContext") != null) {
            AppCtx = (SessionWsService) intent.getSerializableExtra("AppSessionContext");
            intentExtrat = true;
        }
        if (bundle != null) {
            if (bundle.getSerializable("AppSessionContext") != null) {
                AppCtx = (SessionWsService) bundle.getSerializable("AppSessionContext");
                intentExtrat = true;
            }
        }

        menu = new MenuDrawer(this, Current_Position);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        menu.setMaListViewPerso(maListViewPerso);
        menu.setmDrawerLayout(mDrawerLayout);
        menu.setAppCtx(AppCtx);
        menu.setContext(context);
        menu.addDrawerItems();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);




        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        setupDrawer();


        ImageListener();


        if (Current_Position == -1)
            mDrawerLayout.openDrawer(GravityCompat.START);

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
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

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
                gotousermemberprofile();
            }
        });

    }

    public void gotousermemberprofile() {
        Intent intent;
        Bundle b;
        b = new Bundle();
        b.putInt("p", 0);
        b.putSerializable("AppSessionContext", AppCtx);

        intent = new Intent(this.context, UserMemberProfilActivity.class);
        intent.putExtras(b);
        this.context.startActivity(intent);
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

    public void initText() {
        TextView textview = (TextView) findViewById(R.id.txtview_emf_fonction);
        String emffonction = "Dans un monde où l'emploi est de plus en plus difficile d'accès, " +
                "où les formations se démultiplient et que le conseil utile se perd dans le flot continu d'information, " +
                "EMF - BDD permet de profiler les étudiants en fonction de leur cursus, formation personnel, " +
                "localisation, niveau et domaine d'étude. Ces profils permettent ainsi la mise en relations d’une part les besoin " +
                "réel de l'étudiant avec d'autre part les solutions concrètes que ce réseau peu apporté." + "\n\n" +
                "Ainsi, avec plus de 25 ans d'existence, EMF compte aujourd'hui une communauté d'ancien membre aujourd'hui " +
                "dans la vie active, de jeunes diplômés avec des cursus singulier et des étudiants de toute la France. " +
                "Rien de mieux pour recevoir les offres de stage, recherche un parrain ou simplement un conseil. " +
                "Cette communauté grandissante vous attend !";
        textview.setText(emffonction);

        textview = (TextView) findViewById(R.id.txtview_emf_network);
        String emfnetwork = "Musulmans de France, est une association loi 1901, créée par des étudiants pour des étudiants. " +
                "Elle s'inspire de l'éthique et des valeurs musulmanes. EMF vise à accompagner l'étudiant lors de son " +
                "cursus dans l’enseignement supérieur, à améliorer sa vie, et à l’aider à s'intégrer au campus.\n\n" +
                "Pour cela, elle organise différents types d'activités : syndicales et sociales, solidaires et humanitaires, " +
                "culturelles et sportives. \n\n" +
                "Elle facilite l’insertion des étudiants dans les campus, notamment celle des étudiants étrangers, " +
                "tente de combattre l'isolement qui touche parfois l'étudiant, et participe à son bien-être en " +
                "l’accompagnant dans ses démarches. \n\n" +
                "Ses membres souhaitent y normaliser et démystifier la présence musulmane en France, en travaillant sur" +
                " des sujets de réflexion, d’actualité, intellectuels, et de société.";
        textview.setText(emfnetwork);

    }

}
