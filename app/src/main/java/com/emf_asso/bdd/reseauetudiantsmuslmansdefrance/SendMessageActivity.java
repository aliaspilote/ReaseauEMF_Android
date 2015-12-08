package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Skill;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.UserMember;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by taha on 24/11/2015.
 */

public class SendMessageActivity extends AppCompatActivity {

    public int Current_Position = -1;
    private UserMember usermember;
    private Context context = this;
    public Menu_Control menucontrol = new Menu_Control(context);
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
        setContentView(R.layout.activity_admin_crud_list);
        // InitStubs();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        //mDrawerList = (ListView) findViewById(R.id.navList);
        addDrawerItems();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        setupDrawer();


        ImageListener();

        // juste pour le test
        // fillUserMember();
        // fin test

        // CreateProfil();

        //fillInfoPerso();
        //mDrawerToggle.onDrawerOpened(mDrawerLayout);

        //mDrawerLayout.openDrawer(GravityCompat.START);

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


    private void addDrawerItems() {
        maListViewPerso = (ListView) findViewById(R.id.navList);

        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;

        map = new HashMap<String, String>();
        map.put("title", "Informations Personnelles");
        map.put("img", String.valueOf(R.drawable.ic_info_perso));
        listItem.add(map);
        map = new HashMap<String, String>();
        map.put("title", "Profil EMF");
        map.put("img", String.valueOf(R.drawable.ic_profil_emf));
        listItem.add(map);
        map = new HashMap<String, String>();
        map.put("title", "Changer mot de passe");
        map.put("img", String.valueOf(R.drawable.ic_change_pwd));
        listItem.add(map);
        map = new HashMap<String, String>();
        map.put("title", "Mes Cursus");
        map.put("img", String.valueOf(R.drawable.ic_cursus));
        listItem.add(map);
        map = new HashMap<String, String>();
        map.put("title", "Désactiver le compte");
        map.put("img", String.valueOf(R.drawable.ic_disable_profil));
        listItem.add(map);
        map = new HashMap<String, String>();
        map.put("title", "Gestion des listes");
        map.put("img", String.valueOf(R.drawable.ic_list));
        listItem.add(map);
        map = new HashMap<String, String>();
        map.put("title", "Envoyer un message");
        map.put("img", String.valueOf(R.drawable.ic_message));
        listItem.add(map);
        map = new HashMap<String, String>();
        map.put("title", "Rechercher un profil");
        map.put("img", String.valueOf(R.drawable.ic_search));
        listItem.add(map);
        map = new HashMap<String, String>();
        map.put("title", "Gestion des admins");
        map.put("img", String.valueOf(R.drawable.ic_admin));
        listItem.add(map);

        map = new HashMap<String, String>();
        map.put("title", "Déconnexion");
        map.put("img", String.valueOf(R.drawable.ic_disconnect));
        listItem.add(map);


        SimpleAdapter mSchedule = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.listview_item,
                new String[]{"img", "title"}, new int[]{R.id.img_menu_item, R.id.title_menu_item});
        maListViewPerso.setAdapter(mSchedule);


        maListViewPerso.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(position);

                MenuAction(position);
                mDrawerLayout.closeDrawers();


            }
        });

    }

    public void InitStubs() {

        ((ViewStub) findViewById(R.id.stub_pup)).inflate();
        ((ViewStub) findViewById(R.id.stub_pup0)).inflate();
        ((ViewStub) findViewById(R.id.stub_pup1)).inflate();
        ((ViewStub) findViewById(R.id.stub_pup2)).inflate();

        findViewById(R.id.stub_Inflated_pup0).setVisibility(View.GONE);
        findViewById(R.id.stub_Inflated_pup1).setVisibility(View.GONE);
        findViewById(R.id.stub_Inflated_pup2).setVisibility(View.GONE);

    }

    public void hideStubByPosition(int position) {
        switch (position) {
            case -1:
                findViewById(R.id.stub_Inflated_pup).setVisibility(View.GONE);
                break;
            case 0:
                findViewById(R.id.stub_Inflated_pup0).setVisibility(View.GONE);
                break;
            case 1:
                findViewById(R.id.stub_Inflated_pup1).setVisibility(View.GONE);
                break;
            case 2:
                findViewById(R.id.stub_Inflated_pup2).setVisibility(View.GONE);
                break;


            default:

                break;
        }
    }

    public void displayStubByPosition(int position) {
        switch (position) {
            case 0:
                findViewById(R.id.stub_Inflated_pup0).setVisibility(View.VISIBLE);
                break;
            case 1:
                findViewById(R.id.stub_Inflated_pup1).setVisibility(View.VISIBLE);
                break;
            case 2:
                findViewById(R.id.stub_Inflated_pup2).setVisibility(View.VISIBLE);
                break;


            default:
                // findViewById(R.id.stub_Inflated_pup1).setVisibility(View.VISIBLE);
                break;
        }
    }

    public void MenuAction(int position) {
       /* if(Current_Position==-1)
        {
            hideStubByPosition(0);

        }
        ListView list=(ListView)findViewById(R.id.navList);
        int count=list.getAdapter().getCount();
        for(int i=0;i<count;i++)
        {
            if(i!=position)
            hideStubByPosition(position);
        }*/
        // hideStubByPosition(Current_Position);
        //  displayStubByPosition(position);
        // Current_Position = position;

        Intent intent;
        switch (position) {
            case 0:
                intent = new Intent(context, AdminActivity.class);
                context.startActivity(intent);
                break;

            case 5:
                intent = new Intent(context, SendMessageActivity.class);
                context.startActivity(intent);
                break;

            case 6:
                intent = new Intent(context, SendMessageActivity.class);
                context.startActivity(intent);
                break;
        }
        // Toast.makeText(getApplicationContext(), " number of Item" + position, Toast.LENGTH_LONG).show();


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
                Intent mainIntent = new Intent(context, SendMessageActivity.class);
                startActivity(mainIntent);
            }
        });

    }

    public void CreateProfil() {
        if (usermember.getRegistration_date() != null)
            CreateProfilByStep("Date d'inscription :", usermember.getRegistration_date().toString(), R.id.content_for_registrationdate);

        if (usermember.getEmail() != null)
            CreateProfilByStep("Email :", usermember.getEmail(), R.id.content_for_email);

        if (usermember.getName() != null)
            CreateProfilByStep("Nom :", usermember.getName(), R.id.content_for_name);

        if (usermember.getFirstname() != null)
            CreateProfilByStep("Prénom :", usermember.getFirstname(), R.id.content_for_firstname);

        if (usermember.getBirth_date() != null)
            CreateProfilByStep("Date de naissance :", usermember.getBirth_date().toString(), R.id.content_for_birthday);

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


}
