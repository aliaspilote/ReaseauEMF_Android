package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.R;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.UpdateCurusuListActivity;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.SessionWsService;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by taha on 13/12/2015.
 */
public class MenuDrawer extends AppCompatActivity {
    public Activity activity;
    public int Current_Position;
    Context context;
    SessionWsService AppCtx;
    private DrawerLayout mDrawerLayout;
    private ListView maListViewPerso;

    public MenuDrawer(Activity activity, int Current_Position) {
        this.activity = activity;
        this.Current_Position = Current_Position;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setAppCtx(SessionWsService appCtx) {
        AppCtx = appCtx;
    }

    public void setmDrawerLayout(DrawerLayout mDrawerLayout) {
        this.mDrawerLayout = mDrawerLayout;
    }

    public void setMaListViewPerso(ListView maListViewPerso) {
        this.maListViewPerso = maListViewPerso;
    }

    public void addDrawerItems() {

        maListViewPerso = (ListView) this.activity.findViewById(R.id.navList);
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
        map.put("title", "A propos");
        map.put("img", String.valueOf(R.drawable.ic_disable_profil));
        listItem.add(map);

        map = new HashMap<String, String>();
        map.put("title", "Déconnexion");
        map.put("img", String.valueOf(R.drawable.ic_disconnect));
        listItem.add(map);

        SimpleAdapter mSchedule = new SimpleAdapter(activity.getBaseContext(), listItem, R.layout.listview_item,
                new String[]{"img", "title"}, new int[]{R.id.img_menu_item, R.id.title_menu_item});
        maListViewPerso.setAdapter(mSchedule);

        maListViewPerso.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                //HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(position);
                MenuAction(position);
                mDrawerLayout.closeDrawers();
            }
        });
    }


    public void hideStubByPosition(int position) {
        switch (position) {
            case 0:
                this.activity.findViewById(R.id.stub_Inflated_pup0).setVisibility(View.GONE);
                break;
            case 1:
                this.activity.findViewById(R.id.stub_Inflated_pup1).setVisibility(View.GONE);
                break;
            case 2:
                this.activity.findViewById(R.id.stub_Inflated_pup2).setVisibility(View.GONE);
                break;
            case 3:
                this.activity.findViewById(R.id.stub_Inflated_pup3).setVisibility(View.GONE);
                break;
            case 4:
                this.activity.findViewById(R.id.stub_Inflated_pup4).setVisibility(View.GONE);
                break;
            case 5:
                Intent intent = new Intent(context, UpdateCurusuListActivity.class);
                // context.startActivity(intent);

            default:

                break;
        }
    }

    public void displayStubByPosition(int position) {
        switch (position) {
            case 0:
                this.activity.findViewById(R.id.stub_Inflated_pup0).setVisibility(View.VISIBLE);
                break;
            case 1:
                this.activity.findViewById(R.id.stub_Inflated_pup1).setVisibility(View.VISIBLE);
                break;
            case 2:
                this.activity.findViewById(R.id.stub_Inflated_pup2).setVisibility(View.VISIBLE);
                break;
            case 3:
                this.activity.findViewById(R.id.stub_Inflated_pup3).setVisibility(View.VISIBLE);
                break;
            case 4:
                this.activity.findViewById(R.id.stub_Inflated_pup4).setVisibility(View.VISIBLE);
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