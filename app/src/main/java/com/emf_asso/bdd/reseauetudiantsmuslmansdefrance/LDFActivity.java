package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.adaptater.LdfRowContent;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DiffusionCriteria;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DiffusionList;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.UserMember;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.ActivityConnectedWeb;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.ListViewInit;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.MenuDrawer;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.Messages;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.HttpReponse;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.SessionWsService;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.Web_Service_Controlleur;

import static com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.FormBodyManager.get_ldf;

public class LDFActivity extends AppCompatActivity implements ActivityConnectedWeb {


    public final HttpReponse LastReponse = new HttpReponse();
    public LDFActivity LDFActivityCtx = null;
    public LdfRowContent adapter;
    public ListView list;
    public int Current_Position;
    public SessionWsService AppCtx;
    public MenuDrawer menu;
    private Context context = this;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    private UserMember usermember;
    private ListView maListViewPerso;


    public void setUsermember(UserMember usermember) {
        this.usermember = usermember;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ldf);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ImageButton Add_ldf = (ImageButton) findViewById(R.id.btn_add_ldf);
        Add_ldf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ajouter une nouvelle liste", Snackbar.LENGTH_LONG)
                        .setAction("Ajouter", null).show();
                AppCtx.getServiceLDF().setCurrent_ldf(new DiffusionList());
                gotoDiffusionCriteriasActivity();
            }
        });
        //
        ImageButton Refresh_ldf = (ImageButton) findViewById(R.id.btn_refresh_ldf);
        Refresh_ldf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Rafraichir les listes de diffusion", Snackbar.LENGTH_LONG)
                        .setAction("Rafraichir", null).show();
                refreshLDF();
                adapter.notifyDataSetChanged();
            }
        });


        if (savedInstanceState != null) {
            AppCtx = (SessionWsService) savedInstanceState.getSerializable("AppSessionContext");
        }
        Intent intent = this.getIntent();

        if (!(AppCtx != null)) {
            if (intent.getSerializableExtra("AppSessionContext") != null) {
                AppCtx = (SessionWsService) intent.getSerializableExtra("AppSessionContext");
            }
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                if (bundle.getSerializable("AppSessionContext") != null) {
                    AppCtx = (SessionWsService) bundle.getSerializable("AppSessionContext");
                }
            }
        }
        Bundle bundle = intent.getExtras();
        int a = bundle.getInt("p");
        Current_Position = a;


        menu = new MenuDrawer(this, Current_Position);
        setUsermember(AppCtx.getUserMember());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        maListViewPerso = (ListView) findViewById(R.id.navList);
        menu.setMaListViewPerso(maListViewPerso);
        menu.setmDrawerLayout(mDrawerLayout);
        menu.setAppCtx(AppCtx);
        menu.setContext(context);
        menu.addDrawerItems();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mActivityTitle = getTitle().toString();
        setupDrawer();


        AppCtx.getServiceLDF().onStart(AppCtx);

        //loadDataLdf();
        // Set Here the current LDF
        LDFActivityCtx = this;
        ListViewInit.loadListStaticData(AppCtx);
        //ListViewInit.loadCriteriaType_List_View(LDFActivityCtx);

        /******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
        list = (ListView) findViewById(R.id.listview_ldf);  // List defined in XML ( See Below )
        list.setItemsCanFocus(true);
        /**************** Create Custom Adapter *********/
        adapter = new LdfRowContent(LDFActivityCtx, AppCtx);
        list.setAdapter(adapter);


        ImageListener();


        if (Current_Position == -1)
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




    /******
     * Function to set data in ArrayList
     *************/
    public void saveAllItemRow() {
        for (int i = 0; i < AppCtx.getServiceLDF().size_ldf(); i++)
            onItemClickSave(i);
    }

    public void onItemClickDelete(int mPosition) {
        if (mPosition >= 0) {
            DiffusionCriteria dc_to_delete = AppCtx.getServiceLDF().getCurrent_ldf().DiffusionCriteriaListViewValuesArr.get(mPosition);
            AppCtx.getServiceLDF().remove_criteria_currentldf(dc_to_delete);
            adapter.notifyDataSetChanged();
        }
    }

    public void onItemClickSave(int mPosition) {
        if (mPosition >= 0) {
            Object val;/*
            DiffusionCriteria dc_to_save = AppCtx.getServiceLDF().getCurrent_ldf().DiffusionCriteriaListViewValuesArr.get(mPosition);
            if (dc_to_save == null)
                return;
            if (dc_to_save.isSpinner_type())
                val = ((Spinner) getViewByPosition(mPosition, list).findViewById(R.id.spin_diffusion_criteria_type)).getSelectedItem();
            else
                val = ((EditText) getViewByPosition(mPosition, list).findViewById(R.id.editxt_diffusion_criteria_value)).getText().toString();
            dc_to_save.setValue(val);
            AppCtx.getServiceLDF().update_criteria_currentldf(mPosition, dc_to_save);*/
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("AppSessionContext", AppCtx);
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    public void gotoDiffusionCriteriasActivity() {
        Intent intent = new Intent(context, DiffusionCriteriasActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("AppSessionContext", AppCtx);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private void refreshLDF() {
        Web_Service_Controlleur wb_thread;
        wb_thread = new Web_Service_Controlleur(this, get_ldf(AppCtx.getUserMember() != null ? AppCtx.getUserMember().getEmail() : "", AppCtx.getToken()));
        wb_thread.execute();
    }

    @Override
    public void ReceptionResponse(HttpReponse Rep) {
        LastReponse.setHttpReponse(Rep.getResultat(), Rep.getSucces(), Rep.getAction(), Rep.getDataReponse(), Rep.getExceptionText());
        String Message = "";

        if (LastReponse.getResultat() == null)
            DisplayToast(LastReponse.getExceptionText());
        else if (!LastReponse.getSucces() && LastReponse.getResultat().get("result").toString() != "true")
            DisplayToast(LastReponse.getExceptionText());
        else {
            Boolean result = false;
            String tempResultBool = LastReponse.getResultat().get("result").toString();
            if (tempResultBool.contentEquals("true"))
                result = true;

            // code personaliser pour cette activité
            switch (LastReponse.Action) {
                case "get_ldf":
                    if (result) {
                        if ((LastReponse.getResultat().get("result").toString().contentEquals("true"))) {
                            Message += Messages.success_w8_load_data;
                            AppCtx.getServiceLDF().setLDF_From_DB(LastReponse.getResultat());
                            adapter.notifyDataSetChanged();

                        } else
                            Message += Messages.error_generique;
                    } else {
                        Message += Messages.error_generique;
                        Message += LastReponse.getExceptionText() + "";
                        Message += LastReponse.getResultat().get("data_debug").toString();
                    }
                    break;
                default:
                    Message = LastReponse.Action + " : \n";
                    Message += "aucun post traitement défini \n";
                    Message += LastReponse.getResultat().toString();
                    break;
            }
            if (Message.length() > 2)
                DisplayToast(Message);
        }
    }


    public void DisplayToast(String text, int time) {
        if (time > 0)
            time = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, time);
        toast.show();
    }

    public void DisplayToast(String text) {
        DisplayToast(text, 100);
    }
}
