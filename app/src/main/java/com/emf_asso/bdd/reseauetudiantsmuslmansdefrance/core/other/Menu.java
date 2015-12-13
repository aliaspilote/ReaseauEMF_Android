package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by taha on 13/12/2015.
 */
public class Menu extends AppCompatActivity {
    public int Current_Position = 0;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    private ListView maListViewPerso;

    public Menu() {

    }

    public Menu(int current_Position, ActionBarDrawerToggle mDrawerToggle, DrawerLayout mDrawerLayout, String mActivityTitle, ListView maListViewPerso) {
        Current_Position = current_Position;
        this.mDrawerToggle = mDrawerToggle;
        this.mDrawerLayout = mDrawerLayout;
        this.mActivityTitle = mActivityTitle;
        this.maListViewPerso = maListViewPerso;
    }

    public DrawerLayout getmDrawerLayout() {
        return mDrawerLayout;
    }

    public void setmDrawerLayout(DrawerLayout mDrawerLayout) {
        this.mDrawerLayout = mDrawerLayout;
    }

    public String getmActivityTitle() {
        return mActivityTitle;
    }

    public void setmActivityTitle(String mActivityTitle) {
        this.mActivityTitle = mActivityTitle;
    }

    public ListView getMaListViewPerso() {
        return maListViewPerso;
    }

    public void setMaListViewPerso(ListView maListViewPerso) {
        this.maListViewPerso = maListViewPerso;
    }

    public ActionBarDrawerToggle getmDrawerToggle() {
        return mDrawerToggle;
    }

    public void setmDrawerToggle(ActionBarDrawerToggle mDrawerToggle) {
        this.mDrawerToggle = mDrawerToggle;
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
        map.put("title", "Informations Générales");
        map.put("img", String.valueOf(R.drawable.ic_info_perso));
        listItem.add(map);
        map = new HashMap<String, String>();
        map.put("title", "Informations Personnelles");
        map.put("img", String.valueOf(R.drawable.ic_info_perso));
        listItem.add(map);
        map = new HashMap<String, String>();
        map.put("title", "Profil EMF");
        map.put("img", String.valueOf(R.drawable.ic_profil_emf));
        listItem.add(map);
        map = new HashMap<String, String>();
        map.put("title", "Compétences/Talents");
        map.put("img", String.valueOf(R.drawable.ic_skills));
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

    public void hideStubByPosition(int position) {
        switch (position) {
            case 0:
                findViewById(R.id.stub_Inflated_pup0).setVisibility(View.GONE);
                break;
            case 1:
                findViewById(R.id.stub_Inflated_pup1).setVisibility(View.GONE);
                break;
            case 2:
                findViewById(R.id.stub_Inflated_pup2).setVisibility(View.GONE);
                break;
            case 3:
                findViewById(R.id.stub_Inflated_pup3).setVisibility(View.GONE);
                break;
            case 4:
                findViewById(R.id.stub_Inflated_pup4).setVisibility(View.GONE);
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
            case 3:
                findViewById(R.id.stub_Inflated_pup3).setVisibility(View.VISIBLE);
                break;
            case 4:
                findViewById(R.id.stub_Inflated_pup4).setVisibility(View.VISIBLE);
                break;

            default:
                // findViewById(R.id.stub_Inflated_pup1).setVisibility(View.VISIBLE);
                break;
        }
    }

    public void MenuAction(int position) {
        hideStubByPosition(Current_Position);
        displayStubByPosition(position);
        Current_Position = position;
    }
}
