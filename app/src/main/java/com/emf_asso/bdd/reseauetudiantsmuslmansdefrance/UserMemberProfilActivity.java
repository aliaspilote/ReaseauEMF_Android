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
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Curriculum;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Involvement;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Section;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Skill;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.UserMember;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.ListViewInit;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.MenuDrawer;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.Messages;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.SessionWsService;

import java.text.SimpleDateFormat;

/**
 * Created by taha on 24/11/2015.
 */

public class UserMemberProfilActivity extends AppCompatActivity {

    public int Current_Position;
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
        setContentView(R.layout.activity_usermember_profil);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        int a = bundle.getInt("p");
        Current_Position = a;
        menu = new MenuDrawer(this, Current_Position);
        InitStubs();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //Intent intent = this.getIntent();
        //Bundle bundle = intent.getExtras();

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
        ListViewInit.loadListStaticPI(this, AppCtx);
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
        CreateProfil();
        fillInfoPerso();
        //selectSpinner();
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
                Intent mainIntent = new Intent(context, UserMemberProfilActivity.class);
                startActivity(mainIntent);
            }
        });

    }

    public void fillInfoPerso() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        if (usermember.getName() != null)
            fillInfoPersoByStep(usermember.getName(), R.id.editxt_ins_name);
        if (usermember.getFirstname() != null)
            fillInfoPersoByStep(usermember.getFirstname(), R.id.editxt_ins_firstname);
        if (usermember.getBirth_date() != null)
            fillInfoPersoByStep(sdf.format(usermember.getBirth_date()), R.id.editxt_ins_birthday);
        if (usermember.getZip_code() != null)
            fillInfoPersoByStep(usermember.getZip_code(), R.id.editxt_ins_zipcode);
        if (usermember.getCity() != null)
            fillInfoPersoByStep(usermember.getCity(), R.id.editxt_ins_city);
        if (usermember.getPhone() != null)
            fillInfoPersoByStep(usermember.getPhone(), R.id.editxt_ins_phone);
    }

    public void fillInfoPersoByStep(String value, int content) {
        TextView textview = (TextView) findViewById(content);
        textview.setText(value);
    }

    public void InitStubs() {
        ((ViewStub) findViewById(R.id.stub_pup0)).inflate();
        ((ViewStub) findViewById(R.id.stub_pup1)).inflate();
        ((ViewStub) findViewById(R.id.stub_pup2)).inflate();
        ((ViewStub) findViewById(R.id.stub_pup3)).inflate();
        ((ViewStub) findViewById(R.id.stub_pup4)).inflate();

        findViewById(R.id.stub_Inflated_pup1).setVisibility(View.GONE);
        findViewById(R.id.stub_Inflated_pup2).setVisibility(View.GONE);
        findViewById(R.id.stub_Inflated_pup3).setVisibility(View.GONE);
        findViewById(R.id.stub_Inflated_pup4).setVisibility(View.GONE);
    }

    public void CreateProfil() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        if (usermember.getRegistration_date() != null)
            CreateProfilByStep("Date d'inscription :", sdf.format(usermember.getRegistration_date()), R.id.content_for_registrationdate);

        if (usermember.getEmail() != null)
            CreateProfilByStep("Email :", usermember.getEmail(), R.id.content_for_email);

        if (usermember.getName() != null)
            CreateProfilByStep("Nom :", usermember.getName(), R.id.content_for_name);

        if (usermember.getFirstname() != null)
            CreateProfilByStep("Prénom :", usermember.getFirstname(), R.id.content_for_firstname);

        if (usermember.getBirth_date() != null)
            CreateProfilByStep("Date de naissance :", sdf.format(usermember.getBirth_date()), R.id.content_for_birthday);

        if (usermember.getZip_code() != null)
            CreateProfilByStep("Code Postale :", usermember.getZip_code(), R.id.content_for_zipcode);

        if (usermember.getCity() != null)
            CreateProfilByStep("Ville :", usermember.getCity(), R.id.content_for_city);

        if (usermember.getInvolvement() != null)
            CreateProfilByStep("Engagement :", usermember.getInvolvement().toString(), R.id.content_for_involevment);

        if (usermember.getSection() != null)
            CreateProfilByStep("Section :", usermember.getSection().toString(), R.id.content_for_section);

        if (usermember.getSkills() != null) {
            String listString = "";
            int i = 0;
            for (Skill s : usermember.getSkills()) {
                listString += s;
                i++;
                if (i < usermember.getSkills().size()) {
                    listString += ",\n";
                }
            }
            CreateProfilByStep("Compétences :", listString, R.id.content_for_skills);
        }
        // il faut mettre une liste
        if (usermember.getStatus() != null)
            CreateProfilByStep("Contact pour :", usermember.getStatus().toString(), R.id.content_for_contact);


        if (!usermember.getCurriculum().isEmpty()) {
            String listString = "";
            int i = 0;
            for (Curriculum s : usermember.getCurriculum()) {
                listString += s;
                i++;
                if (i < usermember.getCurriculum().size()) {
                    listString += ",\n";
                }
            }
            CreateProfilByStep("Vos Cursus :", listString, R.id.content_for_curriculum);
        }

    }

    public void CreateProfilByStep(String label, String value, int content) {
        TextView Label = new TextView(this);
        Label.setText(label);
        TextView Value = new TextView(this);
        Value.setText(value);

        Label.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
        Value.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));

        LinearLayout linearlayout = (LinearLayout) findViewById(content);
        linearlayout.addView(Label);
        linearlayout.addView(Value);
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

    public void selectSpinner() {
        selectInvolvement();
        selectSection();

    }

    public void selectInvolvement() {
        Spinner mSpinner = (Spinner) findViewById(R.id.spinner_involvement);
        Involvement in1 = AppCtx.getUserMember().getInvolvement();
        Involvement in2;


        if (in1 != null) {
            String compareValue = in1.getInvolvement_id();
            SpinnerAdapter adapter = mSpinner.getAdapter();
            for (int i = 0; i < adapter.getCount(); i++) {
                in2 = (Involvement) adapter.getItem(i);
                if (in2.getInvolvement_id() == compareValue) {
                    mSpinner.setSelection(i);
                }
            }
        }
    }

    public void selectSection() {
        Spinner mSpinner = (Spinner) findViewById(R.id.spinner_section);
        Section sec1 = AppCtx.getUserMember().getSection();
        Section sec2;


        if (sec1 != null) {
            String compareValue = sec1.getSection_id();
            SpinnerAdapter adapter = mSpinner.getAdapter();
            for (int i = 0; i < adapter.getCount(); i++) {
                sec2 = (Section) adapter.getItem(i);
                if (sec2.getSection_id() == compareValue) {
                    mSpinner.setSelection(i);
                }
            }
        }
    }
}
