package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.ContactPreference;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Curriculum;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DataContext;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Involvement;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Section;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Skill;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.UserMember;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.ActivityConnectedWeb;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.CreateDate;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.CustomDatePickerDialog;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.ListViewInit;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.MenuDrawer;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.Messages;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.FormBodyManager;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.HttpReponse;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.SessionWsService;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.Web_Service_Controlleur;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by taha on 24/11/2015.
 */

public class UserMemberProfilActivity extends AppCompatActivity implements ActivityConnectedWeb {

    public final HttpReponse LastReponse = new HttpReponse();
    public int Current_Position;
    public SessionWsService AppCtx;
    public MenuDrawer menu;
    public CreateDate default_birthday_date;
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
        String sv = bundle.getString("SaveCursus");
        if (sv != null)
            if (sv.equals("save"))
                saveCursus();

        menu = new MenuDrawer(this, Current_Position);
        InitStubs();
        ListViewInit.loadListStaticPI(this, AppCtx);
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
        ImageListener();
        CreateProfil();
        fillInfoPerso();


        selectSpinner();
        if (Current_Position == -1)
            mDrawerLayout.openDrawer(GravityCompat.START);
        if (AppCtx.getToken() == null)
            gotoMainActivity();


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

    public void fillInfoPerso() {
        SimpleDateFormat sdf = new SimpleDateFormat(DataContext.dateDisplayFormat, Locale.FRANCE);
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

        displayStub(Current_Position);
    }

    void displayStub(int current_Position) {
        switch (current_Position) {
            case 1:
                menu.hideStubByPosition(0);
                menu.displayStubByPosition(1);
                break;
            case 2:
                menu.hideStubByPosition(0);
                menu.displayStubByPosition(2);
                break;
            case 3:
                menu.hideStubByPosition(0);
                menu.displayStubByPosition(3);
                break;
            case 4:
                menu.hideStubByPosition(0);
                menu.displayStubByPosition(4);
                break;
            default:
                break;
        }
    }

    public void CreateProfil() {
        SimpleDateFormat sdf = new SimpleDateFormat(DataContext.dateDisplayFormat, Locale.FRANCE);

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

        if (usermember.getPhone() != null)
            CreateProfilByStep("Téléphone :", usermember.getPhone(), R.id.content_for_phone);

        if (usermember.getInvolvement() != null)
            CreateProfilByStep("Engagement :", usermember.getInvolvement().toString(), R.id.content_for_involevment);

        if (usermember.getSection() != null)
            CreateProfilByStep("Section :", usermember.getSection().toString(), R.id.content_for_section);

        if (usermember.getSkills() != null) {
            String listString = "";
            Boolean is = false;
            int i = 0;
            for (Skill s : usermember.getSkills()) {
                if (s != null)
                    if (!s.equals("null")) {
                        listString += s;
                        is = true;
                    }

                i++;
                if (is == true && i < usermember.getSkills().size()) {
                    listString += ",\n";
                }
                is = false;
            }
            CreateProfilByStep("Compétences :", listString, R.id.content_for_skills);
        }
        if (usermember.getStatus() != null) {
            String temp = "";
            temp += "Offres d'emploi : " + (usermember.getStatus().getJobs_offers() ? "Ok" : "Non Ok") + "\n";
            temp += "Activités dans ma ville : " + (usermember.getStatus().getCity_activities() ? "Ok" : "Non Ok") + "\n";
            temp += "Activités nationales : " + (usermember.getStatus().getNational_activities() ? "Ok" : "Non Ok") + "\n";
            temp += "Appel à projet : " + (usermember.getStatus().getProject_volontary() ? "Ok" : "Non Ok") + "\n";
            CreateProfilByStep("Contact pour :", temp, R.id.content_for_contact);
        }

        if (usermember.getCurriculum() != null) {
            String listCursus = "";
            int i = 0;
            for (Curriculum c : usermember.getCurriculum()) {
                listCursus += c.getLabel() + " " + c.getDiscipline().getLabel() + " " + c.getEstablishment() + " " + sdf.format(c.getStart_date());
                i++;
                if (i < usermember.getCurriculum().size()) {
                    listCursus += ",\n";
                }
            }
            CreateProfilByStep("Cursus :", listCursus, R.id.content_for_curriculum);
        }

    }

    public void CreateProfilByStep(String label, String value, int content) {
        TextView Label = new TextView(this);
        Label.setText(label);
        TextView Value = new TextView(this);
        Value.setText(value);

        //Label.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
        LinearLayout.LayoutParams lbl_layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 5f);
        lbl_layout_params.setMargins(5, 0, 0, 0);
        Label.setLayoutParams(lbl_layout_params);
        Value.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 3f));

        LinearLayout linearlayout = (LinearLayout) findViewById(content);
        linearlayout.addView(Label);
        linearlayout.addView(Value);
    }

    public void gotoMainActivity() {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public void selectSpinner() {
        selectInvolvement();
        selectSection();
        selectSkills();
        selectContactPreference();

    }

    public void selectInvolvement() {
        Spinner mSpinner = (Spinner) findViewById(R.id.spinner_involvement);
        mSpinner.setSelection(AppCtx.getUserMember().getInvolvement() != null ? ListViewInit.adapter_involvement.getPosition(AppCtx.getUserMember().getInvolvement()) : 0);
    }

    public void selectSection() {
        Spinner mSpinner = (Spinner) findViewById(R.id.spinner_section);
        mSpinner.setSelection(AppCtx.getUserMember().getSection() != null ? ListViewInit.adapter_section.getPosition(AppCtx.getUserMember().getSection()) : 0);
    }

    public void selectContactPreference() {
        Switch sw = (Switch) findViewById(R.id.switch_offer);
        sw.setChecked(AppCtx.getUserMember().getStatus().getJobs_offers());
        sw = (Switch) findViewById(R.id.switch_info_EMFcity);
        sw.setChecked(AppCtx.getUserMember().getStatus().getCity_activities());
        sw = (Switch) findViewById(R.id.switch_info_national);
        sw.setChecked(AppCtx.getUserMember().getStatus().getNational_activities());
        sw = (Switch) findViewById(R.id.switch_project);
        sw.setChecked(AppCtx.getUserMember().getStatus().getProject_volontary());
    }

    public void selectSkills() {
        ListView mList = (ListView) findViewById(R.id.listview_skill);

        List<Skill> listSkill = AppCtx.getUserMember().getSkills();
        if (listSkill != null) {
        }
        Involvement in2;
        int position;
        for (int i = 0; i < listSkill.size(); i++) {
            position = ListViewInit.adapter_skill.getPosition(AppCtx.getUserMember().getSkills().get(i));
            mList.setItemChecked(position, true);
        }


    }

    public void OnSave(View v) {

        switch (menu.Current_Position) {
            case 1:
                saveInfoPerso();
                updateinfoperso();
                gotousermemberprofile();
                break;
            case 2:
                saveEMFProfile();
                updateemfprofile();
                gotousermemberprofile();
                break;
            case 3:
                saveSkills();
                updateskills();
                gotousermemberprofile();
                break;
            case 4:
                savePassword();
                break;
            default:
                break;
        }
    }

    public void OnCancel(View v) {
        menu.startActivityByPosition(menu.Current_Position);
    }

    public void saveInfoPerso() {
        TextView textview = (TextView) (findViewById(R.id.editxt_ins_name));
        AppCtx.getUserMember().setName(textview.getText().toString());
        textview = (TextView) (findViewById(R.id.editxt_ins_firstname));
        AppCtx.getUserMember().setFirstname(textview.getText().toString());
        AppCtx.getUserMember().setBirth_date(getDateByEditTextId(R.id.editxt_ins_birthday));
        textview = (TextView) (findViewById(R.id.editxt_ins_zipcode));
        AppCtx.getUserMember().setZip_code(textview.getText().toString());
        textview = (TextView) (findViewById(R.id.editxt_ins_city));
        AppCtx.getUserMember().setCity(textview.getText().toString());
        textview = (TextView) (findViewById(R.id.editxt_ins_phone));
        AppCtx.getUserMember().setPhone(textview.getText().toString());
    }

    void saveEMFProfile() {
        Spinner spinner = (Spinner) (findViewById(R.id.spinner_involvement));
        AppCtx.getUserMember().setInvolvement((Involvement) spinner.getSelectedItem());
        spinner = (Spinner) (findViewById(R.id.spinner_section));
        AppCtx.getUserMember().setSection((Section) spinner.getSelectedItem());
        ContactPreference contact = new ContactPreference();
        Switch sw = (Switch) (findViewById(R.id.switch_offer));
        contact.setJobs_offers(sw.isChecked());
        sw = (Switch) (findViewById(R.id.switch_info_EMFcity));
        contact.setCity_activities(sw.isChecked());
        sw = (Switch) (findViewById(R.id.switch_info_national));
        contact.setNational_activities(sw.isChecked());
        sw = (Switch) (findViewById(R.id.switch_project));
        contact.setProject_volontary(sw.isChecked());
        AppCtx.getUserMember().setStatus(contact);
    }

    public void saveSkills() {
        ListView listview = (ListView) findViewById(R.id.listview_skill);
        SparseBooleanArray checked = listview.getCheckedItemPositions();
        ArrayList<Skill> selectedItems = new ArrayList<>();
        for (int i = 0; i < checked.size(); i++) {
            int position = checked.keyAt(i);
            if (checked.valueAt(i))
                if (position != -1)
                    selectedItems.add((Skill) listview.getAdapter().getItem(position));
        }
        AppCtx.getUserMember().setSkills(selectedItems);
    }

    public void savePassword() {
        TextView pwd = (TextView) findViewById(R.id.editxt_upd_pwd_current);
        TextView pwd1 = (TextView) findViewById(R.id.editxt_upd_pwd1);
        TextView pwd2 = (TextView) findViewById(R.id.editxt_upd_pwd2);
        TextView lbl = (TextView) findViewById(R.id.lbl_legend_error);
        lbl.setText("");
        if (pwd.getText().toString().length() < 6
                || pwd1.getText().toString().length() < 6
                || pwd2.getText().toString().length() < 6)
            lbl.setText("Saisie insuffisantes");

        else {
            if (pwd1.getText().toString().equals(pwd2.getText().toString()))
                checkPwd(pwd.getText().toString());
            else
                lbl.setText("Les mots de passes saisies différents");
        }


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

    public void onChooseBirthday(View v) {
        showDate(default_birthday_date, R.id.icon_choose_birthday, R.id.editxt_ins_birthday);

    }

    public void showDate(CreateDate cr, int img, int txt) {
        cr = new CreateDate();
        cr.initDate();
        cr.setIb((ImageButton) findViewById(img));
        cr.setEt((EditText) findViewById(txt));
        CustomDatePickerDialog dp = new CustomDatePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog,
                cr.getDatePickerListener(),
                cr.getYear(),
                cr.getMonth(),
                cr.getDay());

        DatePickerDialog obj = dp.getPicker();

        obj.show();
    }

    public Date getDateByEditTextId(int id_editText) {
        Date dt = null;
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DataContext.dateDisplayFormat, Locale.FRANCE);
        try {
            dt = dateFormatter.parse(((EditText) findViewById(id_editText)).getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }

    public void updateinfoperso() {
        // Requete http, resultat bool mot clés, Action : save_infoperso, Resultat : resultat_infoperso
        Web_Service_Controlleur wb_thread;
        wb_thread = new Web_Service_Controlleur(this, FormBodyManager.update_infoperso(AppCtx));
        wb_thread.execute();
    }

    public void updateemfprofile() {
        // Requete http, resultat bool mot clés, Action : save_emfprofile, Resultat : resultat_emfprofile
        Web_Service_Controlleur wb_thread;
        wb_thread = new Web_Service_Controlleur(this, FormBodyManager.update_emfprofile(AppCtx));
        wb_thread.execute();

    }

    public void updateskills() {
        // Requete http, resultat bool mot clés, Action : save_skills, Resultat : resultat_skills
        Web_Service_Controlleur wb_thread;
        wb_thread = new Web_Service_Controlleur(this, FormBodyManager.update_skills(AppCtx));
        wb_thread.execute();

    }

    public void checkPwd(String old_pwd) {
        // Requete http, resultat bool mot clés, Action : check_pwd, Resultat : old_pwd_match
        Web_Service_Controlleur wb_thread;
        wb_thread = new Web_Service_Controlleur(this, FormBodyManager.check_pwd(AppCtx, old_pwd));
        wb_thread.execute();

    }

    public void changePwd(String pwd) {
        // Requete http, resultat bool mot clés, Action : change_pwd, Resultat : resultat
        Web_Service_Controlleur wb_thread;
        wb_thread = new Web_Service_Controlleur(this, FormBodyManager.change_pwd(AppCtx, pwd));
        wb_thread.execute();

    }

    public void saveCursus() {
        // Requete http, resultat bool mot clés, Action : save_cursus, Resultat : resultat_cursus
        if (AppCtx != null) {
            Web_Service_Controlleur wb_thread;
            wb_thread = new Web_Service_Controlleur(this, FormBodyManager.update_cursus(AppCtx));
            wb_thread.execute();
        } else
            DisplayToast("Error, null AppCtx");

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
            TextView lbl;
            // code personaliser pour cette activité
            switch (LastReponse.Action) {
                case "check_pwd":
                    lbl = (TextView) findViewById(R.id.lbl_legend_error);
                    if (result) {
                        if ((LastReponse.getResultat().get("check_pwd").toString().contentEquals("true"))) {
                            Message += "";
                            TextView pwd1 = (TextView) findViewById(R.id.editxt_upd_pwd1);
                            changePwd(pwd1.getText().toString());
                            gotousermemberprofile();
                        } else {
                            Message += "Ancien mot de passe saisie incorrecte";
                            lbl.setText("Ancien mot de passe saisie incorrecte");
                        }
                    } else
                        Message += LastReponse.getExceptionText();
                    break;
                case "change_pwd":
                    lbl = (TextView) findViewById(R.id.lbl_legend_error);
                    if (result) {
                        if ((LastReponse.getResultat().get("change_pwd").toString().contentEquals("true"))) {
                            Message += "Mot de passe modifié avec succès";
                            lbl.setText("");
                        } else
                            Message += Messages.error_generique;
                    } else
                        Message += LastReponse.getExceptionText();
                    break;
                case "save_infoperso":
                    if (result) {
                        if ((LastReponse.getResultat().get("update_infoperso").toString().contentEquals("true")))
                            Message += "Profil modifié avec succès";
                        else
                            Message += Messages.error_generique;
                    } else
                        Message += LastReponse.getExceptionText();
                    break;
                case "save_emfprofile":
                    if (result) {
                        if ((LastReponse.getResultat().get("update_emfprofile").toString().contentEquals("true")))
                            Message += "Profil modifié avec succès";
                        else
                            Message += Messages.error_generique;
                    } else
                        Message += LastReponse.getExceptionText();
                    break;
                case "save_skills":
                    if (result) {
                        if ((LastReponse.getResultat().get("save_skills").toString().contentEquals("true")))
                            Message += "Compétances modifiés avec succès";
                        else
                            Message += Messages.error_generique;
                    } else
                        Message += LastReponse.getExceptionText();
                    break;
                case "save_cursus":
                    if (result) {
                        if ((LastReponse.getResultat().get("save_cursus").toString().contentEquals("true")))
                            Message += "Cursus modifiés avec succès";
                        else
                            Message += Messages.error_generique;
                    } else
                        Message += LastReponse.getExceptionText();
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
        DisplayToast(text, 0);
    }

}
