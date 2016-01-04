package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.AboutEmfActivity;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.CursusListActivity;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.LDFActivity;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.MainActivity;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.R;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.SendMessageActivity;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.UserMemberProfilActivity;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.SessionWsService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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

    public MenuDrawer() {
    }

    public MenuDrawer(Activity activity, int Current_Position) {
        this.activity = activity;
        this.Current_Position = Current_Position;
    }

    public MenuDrawer(Activity act, Context ctx, SessionWsService AppCtx, DrawerLayout dLayout, ListView navList, int currtPos) {
        Current_Position = currtPos;
        activity = act;
        context = ctx;
        this.AppCtx = AppCtx;
        mDrawerLayout = dLayout;
        maListViewPerso = navList;
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


        final Map<Integer, Integer> Index_Name = new HashMap<Integer, Integer>();
        Integer dynamiquePostion = 0;
        maListViewPerso = (ListView) this.activity.findViewById(R.id.navList);
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;

        map = new HashMap<String, String>();
        map.put("title", "A propos");
        map.put("img", String.valueOf(R.drawable.ic_propos));
        listItem.add(map);
        Index_Name.put(dynamiquePostion++, 7);

        map = new HashMap<String, String>();
        map.put("title", "Vue Profil");
        map.put("img", String.valueOf(R.drawable.ic_info_g));
        listItem.add(map);
        Index_Name.put(dynamiquePostion++, 0);

        map = new HashMap<String, String>();
        map.put("title", "Identité");
        map.put("img", String.valueOf(R.drawable.ic_info_perso));
        listItem.add(map);
        Index_Name.put(dynamiquePostion++, 1);

        map = new HashMap<String, String>();
        map.put("title", "Profil EMF");
        map.put("img", String.valueOf(R.drawable.ic_profil_emf));
        listItem.add(map);
        Index_Name.put(dynamiquePostion++, 2);

        map = new HashMap<String, String>();
        map.put("title", "Compétences");
        map.put("img", String.valueOf(R.drawable.ic_skills));
        listItem.add(map);
        Index_Name.put(dynamiquePostion++, 3);

        map = new HashMap<String, String>();
        map.put("title", "Mot de passe");
        map.put("img", String.valueOf(R.drawable.ic_change_pwd));
        listItem.add(map);
        Index_Name.put(dynamiquePostion++, 4);

        map = new HashMap<String, String>();
        map.put("title", "Mes Cursus");
        map.put("img", String.valueOf(R.drawable.ic_cursus));
        listItem.add(map);
        Index_Name.put(dynamiquePostion++, 5);


        if (AppCtx.getUserMember().isAdmin() == true) {
            map = new HashMap<String, String>();
            map.put("title", "Listes diffusion");
            map.put("img", String.valueOf(R.drawable.ic_list));
            listItem.add(map);
            Index_Name.put(dynamiquePostion++, 8);

            map = new HashMap<String, String>();
            map.put("title", "Envoyer un message");
            map.put("img", String.valueOf(R.drawable.ic_message));
            listItem.add(map);
            Index_Name.put(dynamiquePostion++, 9);

            map = new HashMap<String, String>();
            map.put("title", "Rechercher un profil");
            map.put("img", String.valueOf(R.drawable.ic_search));
            listItem.add(map);
            Index_Name.put(dynamiquePostion++, 10);

            map = new HashMap<String, String>();
            map.put("title", "Gestion des admins");
            map.put("img", String.valueOf(R.drawable.ic_admin));
            listItem.add(map);
            Index_Name.put(dynamiquePostion++, 11);
        }

        map = new HashMap<String, String>();
        map.put("title", "Désactiver le compte");
        map.put("img", String.valueOf(R.drawable.ic_disable_profil));
        listItem.add(map);
        Index_Name.put(dynamiquePostion++, 6);

        map = new HashMap<String, String>();
        map.put("title", "Déconnexion");
        map.put("img", String.valueOf(R.drawable.ic_disconnect));
        listItem.add(map);
        Index_Name.put(dynamiquePostion++, 12);

        SimpleAdapter mSchedule = new SimpleAdapter(activity.getBaseContext(), listItem, R.layout.listview_item,
                new String[]{"img", "title"}, new int[]{R.id.img_menu_item, R.id.title_menu_item});
        maListViewPerso.setAdapter(mSchedule);

        maListViewPerso.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                //HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(position);
                int pos = Index_Name.get(position);
                MenuAction(pos);
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
                break;
        }
    }

    public void startActivityByPosition(int position) {
        Intent intent;
        Bundle b;
        b = new Bundle();
        b.putInt("p", position);
        b.putSerializable("AppSessionContext", AppCtx);
        DialogBox dialog;
        switch (position) {
            case 0:
                intent = new Intent(this.context, UserMemberProfilActivity.class);
                intent.putExtras(b);
                this.context.startActivity(intent);
                break;
            case 1:
                intent = new Intent(this.context, UserMemberProfilActivity.class);
                intent.putExtras(b);
                this.context.startActivity(intent);
                break;
            case 2:
                intent = new Intent(this.context, UserMemberProfilActivity.class);
                intent.putExtras(b);
                this.context.startActivity(intent);
                break;
            case 3:
                intent = new Intent(this.context, UserMemberProfilActivity.class);
                intent.putExtras(b);
                this.context.startActivity(intent);
                break;
            case 4:
                intent = new Intent(this.context, UserMemberProfilActivity.class);
                intent.putExtras(b);
                this.context.startActivity(intent);
                break;
            case 5:
                intent = new Intent(this.context, CursusListActivity.class);
                intent.putExtras(b);
                this.context.startActivity(intent);
                break;
            case 6:
                intent = new Intent(this.context, MainActivity.class);
                intent.putExtras(b);
                dialog = new DialogBox(context, intent);
                dialog.setAppCtx(AppCtx);
                dialog.setDisable(true);
                dialog.createDialogBox("Désactiver votre compte", "Voulez-vous vraiment continuer ?");
                break;

            case 7:
                intent = new Intent(this.context, AboutEmfActivity.class);
                intent.putExtras(b);
                this.context.startActivity(intent);
                break;
            case 8:
                intent = new Intent(this.context, LDFActivity.class);
                    intent.putExtras(b);
                this.context.startActivity(intent);
                break;
            case 9:
                intent = new Intent(this.context, SendMessageActivity.class);
                intent.putExtras(b);
                this.context.startActivity(intent);
                break;
            case 10:
                DisplayToast("'Rechercher un profil' en cours de developpement : postion " + position, 4000);
                break;
            case 11:
                DisplayToast("'Gerer les admins' en cours de developpement : postion " + position, 4000);
                break;
            case 12:
                intent = new Intent(this.context, MainActivity.class);
                intent.putExtras(b);
                DialogBox dialogDec = new DialogBox(context, intent);
                dialogDec.setAppCtx(AppCtx);
                dialogDec.setKill(true);
                dialogDec.createDialogBox("Déconnexion", "Voulez-vous vraiment vous déconnecter ?");
                break;
            default:
                //intent = new Intent(this.context, MainActivity.class);
                //this.context.startActivity(intent);
                DisplayToast("Action en cours de developpement : postion " + position, 4000);
                break;
        }

    }

    public void MenuAction(int position) {
        DialogBox dialog;
        final int pos = position;
        if (Current_Position < 5 && position < 5) {
            if (Current_Position < 0)
                hideStubByPosition(0);
            else
                hideStubByPosition(Current_Position);
            displayStubByPosition(position);
            Current_Position = position;


        } else {
            if (Current_Position == 9) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Envoyer le message");
                alertDialogBuilder
                        .setMessage("Voulez vous vraiment quitter l'envoie du message ?")
                        .setCancelable(false)
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivityByPosition(pos);
                                Current_Position = pos;

                            }
                        })
                        .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            } else {
                startActivityByPosition(position);
                Current_Position = position;

            }

        }

        // DisplayToast("Currenttttttt = " + String.valueOf(Current_Position) + " || position = " + String.valueOf(position), 3000);
    }

    public void DisplayToast(String text, int time) {
        if (time > 0)
            time = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this.context, text, time);
        toast.show();
    }



}
