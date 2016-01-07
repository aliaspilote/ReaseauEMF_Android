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
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DiffusionList;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.MessageMail;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.ActivityConnectedWeb;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.MenuDrawer;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.Messages;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.FormBodyManager;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.HttpReponse;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.SessionWsService;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.Web_Service_Controlleur;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by taha on 24/11/2015.
 */

public class SendMessageActivity extends AppCompatActivity implements ActivityConnectedWeb {

    public final HttpReponse LastReponse = new HttpReponse();
    public int Current_Position;
    public SessionWsService AppCtx;
    public MenuDrawer menu;
    ArrayAdapter<DiffusionList> adapter_diffusion_list;
    ListView listView;
    private Context context = this;
    private Activity activity = this;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    private ListView maListViewPerso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        int a = bundle.getInt("p");
        Current_Position = a;
        menu = new MenuDrawer(this, Current_Position);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        menu.setMaListViewPerso(maListViewPerso);
        menu.setmDrawerLayout(mDrawerLayout);
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

        if (savedInstanceState != null) {
            AppCtx = (SessionWsService) savedInstanceState.getSerializable("AppSessionContext");
        }
        menu.setAppCtx(AppCtx);
        menu.setContext(context);
        menu.addDrawerItems();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        AppCtx.getServiceLDF().onStart(AppCtx);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        setupDrawer();
        ImageListener();

        listView = (ListView) findViewById(R.id.listview_destination);

        refreshLDF();
        initAlias();
        //initlist();
    }

    public void initAlias() {
        Spinner spinner = (Spinner) (findViewById(R.id.spinner_alias));
        ArrayAdapter<String> arrayAdapter;
        String temp = AppCtx.getUserMember().getEmail();
        String tab[] = {temp};
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tab);
        spinner.setAdapter(arrayAdapter);
    }

    public void initlist() {
        ListView listView = (ListView) findViewById(R.id.listview_destination);
        int position;
        if (!AppCtx.getServiceLDF().getNumSelectedLdf().isEmpty()) {
            for (Iterator i = AppCtx.getServiceLDF().getNumSelectedLdf().keySet().iterator(); i.hasNext(); ) {
                String cle = (String) i.next();
                if (AppCtx.getServiceLDF().getNumSelectedLdf().get(cle) == true) {
                    position = adapter_diffusion_list.getPosition(AppCtx.getServiceLDF().get_ldf_byId(cle));
                    listView.setItemChecked(position, true);
                }
            }
        }
    }

    public void sendMsg() {
        TextView objet = (TextView) findViewById(R.id.editxt_subject);
        TextView msg = (TextView) findViewById(R.id.editxt_msg);
        TextView abs = (TextView) findViewById(R.id.editxt_m_abstract);
        TextView note = (TextView) findViewById(R.id.editxt_note);
        Spinner sender = (Spinner) findViewById(R.id.spinner_alias);
        ListView listView = (ListView) findViewById(R.id.listview_destination);
        Boolean check = false;
        if (!adapter_diffusion_list.isEmpty()) {
            for (int i = 0; i < listView.getCount(); i++) {
                if (listView.isItemChecked(i)) {
                    check = true;
                    AppCtx.getServiceLDF().getNumSelectedLdf().put(adapter_diffusion_list.getItem(i).getId(), true);
                } else
                    AppCtx.getServiceLDF().getNumSelectedLdf().put(adapter_diffusion_list.getItem(i).getId(), false);
            }
        }
        if (check == false) {
            DisplayToast("Destinataire(s) manquant(s)");
        } else {
            if (objet.getText().length() < 2)
                DisplayToast("Objet du message manquant");
            else if (msg.getText().length() < 2)
                DisplayToast("Contenu du message manquant");
            else if (sender.getSelectedItem().toString().length() < 2)
                DisplayToast("Emmetteur du message manquant");
            else {
                MessageMail messageMail = new MessageMail();
                messageMail.setObject(objet.getText().toString());
                messageMail.setCorps(msg.getText().toString());
                messageMail.setSender(sender.getSelectedItem().toString());
                messageMail.setM_abstract(abs.getText().toString());
                messageMail.setNote(note.getText().toString());
                AppCtx.getServiceLDF().setMessage(messageMail);
                sendMail();
            }
        }
    }

    private void refreshLDF() {
        Web_Service_Controlleur wb_thread;
        wb_thread = new Web_Service_Controlleur(this, FormBodyManager.get_ldf(AppCtx.getUserMember() != null ? AppCtx.getUserMember().getEmail() : "", AppCtx.getToken()));
        wb_thread.execute();
    }

    private void sendMail() {
        Web_Service_Controlleur wb_thread;
        wb_thread = new Web_Service_Controlleur(this, FormBodyManager.sendMailLDF(AppCtx));
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
                            adapter_diffusion_list = new ArrayAdapter<DiffusionList>(this, android.R.layout.simple_list_item_multiple_choice, AppCtx.getServiceLDF().getLdfList());
                            listView.setAdapter(adapter_diffusion_list);
                            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                            if (AppCtx.getServiceLDF().getNumSelectedLdf() != null)
                                for (Map.Entry<String, Boolean> entry : AppCtx.getServiceLDF().getNumSelectedLdf().entrySet()) {
                                    if (entry.getValue())
                                        listView.setItemChecked(
                                                adapter_diffusion_list.
                                                        getPosition(AppCtx.getServiceLDF().get_ldf_byId(entry.getKey())), true);
                                }
                        } else
                            Message += Messages.error_generique;
                    } else {
                        Message += Messages.error_generique;
                        Message += LastReponse.getExceptionText() + "";
                        Message += LastReponse.getResultat().get("data_debug").toString();
                    }
                    break;
                case "send_mail_ldf":
                    if (result) {
                        if ((LastReponse.getResultat().get("result").toString().contentEquals("true"))) {
                            Message += "Messages envoyés.";
                            clear();

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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (mDrawerToggle.onOptionsItemSelected(item)) {
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

    public void clear() {

        TextView objet = (TextView) findViewById(R.id.editxt_subject);
        TextView msg = (TextView) findViewById(R.id.editxt_msg);
        TextView note = (TextView) findViewById(R.id.editxt_note);
        TextView abstact = (TextView) findViewById(R.id.editxt_m_abstract);

        ListView listView = (ListView) findViewById(R.id.listview_destination);
        listView.setAdapter(adapter_diffusion_list);
        listView.clearChoices();
        objet.setText("");
        msg.setText("");
        note.setText("");
        abstact.setText("");
        AppCtx.getServiceLDF().setMessage(new MessageMail());
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
        ImageView send_icon;
        send_icon = (ImageView) this.findViewById(R.id.btn_send);
        // set a onclick listener for when the button gets clicked
        send_icon.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                sendMsg();
            }
        });
        ImageView remove_icon;
        remove_icon = (ImageView) this.findViewById(R.id.btn_remove);
        // set a onclick listener for when the button gets clicked
        remove_icon.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                clear();
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


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // ici taha save contenu message
        outState.putSerializable("AppSessionContext", AppCtx);
    }


}
