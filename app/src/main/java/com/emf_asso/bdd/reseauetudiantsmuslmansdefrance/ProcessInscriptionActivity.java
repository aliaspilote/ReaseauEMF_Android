package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.adaptater.CursusContent;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.ContactPreference;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Curriculum;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DataContext;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Discipline;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Involvement;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Section;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Skill;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.UserMember;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.ActivityConnectedWeb;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.CreateDate;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.CustomDatePickerDialog;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.ListViewInit;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.Messages;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.FormBodyManager;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.HttpReponse;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.SessionWsService;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.Web_Service_Controlleur;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Omar on 04/11/2015.
 */

public class ProcessInscriptionActivity extends Activity implements ActivityConnectedWeb {


    static final int PICK_REQUEST_CURSUS = 1;
    static final int PICK_REQUEST_BackCURSUS = 2;
    private static final int NUM_PAGES = 5;
    public HttpReponse LastReponse = new HttpReponse();
    public CreateDate default_birthday_date;
    public CreateDate default_start_curriculum_date;
    public CreateDate default_end_curriculum_date;
    public java.text.SimpleDateFormat sdfdisplay = new java.text.SimpleDateFormat(DataContext.dateDisplayFormat);
    private Context context = this;
    public Menu_Control menucontrol = new Menu_Control(context);
    private int current_NUM_PAGES = 1;
    private SessionWsService AppCtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processinscription);
        this.testInternetConnection.setContext(context);
        InitStubs();
        ImageListener();
        Boolean intentExtrat = false;
        Intent intent = this.getIntent();
        if (intent.getSerializableExtra("AppSessionContext") != null) {
            AppCtx = (SessionWsService) intent.getSerializableExtra("AppSessionContext");
            intentExtrat = true;
        }
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            if (bundle.getSerializable("AppSessionContext") != null) {
                AppCtx = (SessionWsService) bundle.getSerializable("AppSessionContext");
                intentExtrat = true;
            }
        }
        if (intentExtrat)
            ListViewInit.loadListStaticPI(this, AppCtx);
        else
            if (!(current_NUM_PAGES>1)) // au cas ou l'on revient de l'activité Cursus
                current_NUM_PAGES = 1;
        AppCtx.getServiceProcessInscription().onStart();
        AppCtx.BeInProssInscrView();
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
        return menucontrol.onOptionsItemSelected(item);
    }

    public void InitStubs() {

        ((ViewStub) findViewById(R.id.stub_pi1)).inflate();
        ((ViewStub) findViewById(R.id.stub_pi2)).inflate();
        ((ViewStub) findViewById(R.id.stub_pi3)).inflate();
        ((ViewStub) findViewById(R.id.stub_pi4)).inflate();
        ((ViewStub) findViewById(R.id.stub_pi5)).inflate();

        findViewById(R.id.stub_Inflated2).setVisibility(View.GONE);
        findViewById(R.id.stub_Inflated3).setVisibility(View.GONE);
        findViewById(R.id.stub_Inflated4).setVisibility(View.GONE);
        findViewById(R.id.stub_Inflated5).setVisibility(View.GONE);
    }

    public void ReceptionResponse(HttpReponse Rep) {

        TextView twError = (TextView) findViewById(R.id.lbl_ins5_legend_error);

        LastReponse.setHttpReponse(Rep.getResultat(), Rep.getSucces(), Rep.getAction(), Rep.getDataReponse(), Rep.getExceptionText());
        String Message = "";

        if (!LastReponse.getSucces() && LastReponse.getResultat().get("result").toString() != "true")
            DisplayToast(LastReponse.getExceptionText());
        else {
            Boolean result = false;
            String tempResultBool = LastReponse.getResultat().get("result").toString();
            if (tempResultBool.contentEquals("true"))
                result = true;

            switch (LastReponse.Action) {
                case "add_user":
                    TextView twResult = (TextView) findViewById(R.id.lbl_ins5_legend_error);
                    twResult.setText("Résultats :\n");
                    if (result) {
                        Message += Messages.error_addUser_success;
                        CloseInscription();
                    } else if (tempResultBool.contentEquals("isExisting")) {
                        Message += Messages.error_is_Existing;
                        twResult.setText(Message);
                    }
                    else {
                        Message = twError.getText() + LastReponse.getExceptionText();
                        twResult.setText(Message);
                    }
                    break;
                case "try":
                    TextView TryResult = (TextView) findViewById(R.id.lbl_ins5_legend_error);
                    if (result)
                        Message += LastReponse.getResultat().toString();
                    else
                        Message = twError.getText() + LastReponse.getExceptionText();
                    TryResult.setText(Message);
                    break;
                default:
                    Message = Messages.error_unknow_action;
                    Message += LastReponse.getResultat().toString();
                    twError.setText(Message);
                    break;
            }
            if (Message.length() > 2)
                DisplayToast(Message, 3000);
        }
    }

    public void OnNext(View view) {
        if (current_NUM_PAGES > NUM_PAGES)
            current_NUM_PAGES = NUM_PAGES;
        else if (current_NUM_PAGES < 0)
            current_NUM_PAGES = 1;

        setDataOn_ServiceByStep(current_NUM_PAGES);
        if (current_NUM_PAGES == NUM_PAGES - 1) // Si nous somme a la dernière page du formulaire d'inscription
        {
            gotoCursusActivity();
        } else {
            if (AppCtx.getServiceProcessInscription().getErrors(current_NUM_PAGES) != "") {
                DisplayToast(AppCtx.getServiceProcessInscription().getErrors(current_NUM_PAGES));
            } else {
                hideViewByNum(current_NUM_PAGES);
                current_NUM_PAGES++;
                displayViewByNum(current_NUM_PAGES);
            }
        }
    }

    public void OnPrevious(View view) {
        if (current_NUM_PAGES > NUM_PAGES)
            current_NUM_PAGES = NUM_PAGES;
        else if (current_NUM_PAGES < 0)
            current_NUM_PAGES = 1;

        setDataOn_ServiceByStep(current_NUM_PAGES);
        hideViewByNum(current_NUM_PAGES);
        if (current_NUM_PAGES == 1) {
            gotoMainActivity();
        } else if (current_NUM_PAGES == NUM_PAGES) // Si nous somme a la dernière page du formulaire d'inscription
        {
            gotoCursusActivity();
        } else {
            current_NUM_PAGES--;
            displayViewByNum(current_NUM_PAGES);
        }
    }

    public void OnValidateInscrption(View view) throws IOException {
        Web_Service_Controlleur wb_thread = new Web_Service_Controlleur(
                this, FormBodyManager.addUser( AppCtx.getServiceProcessInscription().getInscription().getUser()));
        wb_thread.execute();
    }

    public void CloseInscription() {
        AppCtx.getServiceProcessInscription().setOnGoingInscr(false);
        CursusContent.clearCursus();
        gotoMainActivity();
    }

    public void OnCancelInscrption(View view) {
        current_NUM_PAGES = 1;
        setContentView(R.layout.activity_processinscription);
        InitStubs();
    }

    @Override
    public void onBackPressed() {
        OnPrevious(null);
    }

    public void setDataOn_ServiceByStep(int step) {

        TextView lbl_error;

        switch (step) {
            case 1:
                AppCtx.getServiceProcessInscription().set_data_inscription1(AppCtx.getServiceProcessInscription().getInscription(),
                        getTextByEditTextId(R.id.editxt_ins_email),
                        getTextByEditTextId(R.id.editxt_ins_pwd),
                        getTextByEditTextId(R.id.editxt_ins_repeat_pwd));
                AppCtx.getServiceProcessInscription().validated_screen1(AppCtx.getServiceProcessInscription().getInscription());
                lbl_error = (TextView) findViewById(R.id.lbl_ins1_legend_error);
                if (AppCtx.getServiceProcessInscription().getErrors(step) != "")
                    lbl_error.setText(AppCtx.getServiceProcessInscription().getErrors(step));
                else
                    lbl_error.setText("");
                break;
            case 2:
                AppCtx.getServiceProcessInscription().set_data_inscription2(AppCtx.getServiceProcessInscription().getInscription(),
                        getTextByEditTextId(R.id.editxt_ins_name),
                        getTextByEditTextId(R.id.editxt_ins_firstname),
                        getTextByEditTextId(R.id.editxt_ins_zipcode),
                        getTextByEditTextId(R.id.editxt_ins_city),
                        getTextByEditTextId(R.id.editxt_ins_phone),
                        getDateByEditTextId(R.id.editxt_ins_birthday));
                AppCtx.getServiceProcessInscription().validated_screen2(AppCtx.getServiceProcessInscription().getInscription());
                lbl_error = (TextView) findViewById(R.id.lbl_ins2_legend_error);
                if (AppCtx.getServiceProcessInscription().getErrors(step) != "")
                    lbl_error.setText(AppCtx.getServiceProcessInscription().getErrors(step));
                else
                    lbl_error.setText("");
                break;
            case 3:
                AppCtx.getServiceProcessInscription().set_data_inscription3(AppCtx.getServiceProcessInscription().getInscription(),
                        (Involvement) getObjSelectedBySpinnerId(R.id.spinner_involvement),
                        (Section) getObjSelectedBySpinnerId(R.id.spinner_section),
                        getContactPreferenceSelectedByIds(R.id.switch_offer, R.id.switch_info_EMFcity, R.id.switch_info_national, R.id.switch_project),
                        (Discipline) getObjSelectedBySpinnerId(R.id.spinner_discipline));
                AppCtx.getServiceProcessInscription().validated_screen3(AppCtx.getServiceProcessInscription().getInscription());
                lbl_error = (TextView) findViewById(R.id.lbl_ins3_legend_error);
                if (AppCtx.getServiceProcessInscription().getErrors(step) != "")
                    lbl_error.setText(AppCtx.getServiceProcessInscription().getErrors(step));
                else
                    lbl_error.setText("");
                break;
            case 4:
                AppCtx.getServiceProcessInscription().set_data_inscription4(AppCtx.getServiceProcessInscription().getInscription(),
                        getListSkillsSelected(R.id.listview_skill));
                AppCtx.getServiceProcessInscription().validated_screen4(AppCtx.getServiceProcessInscription().getInscription());
                break;
            case 5:
                AppCtx.getServiceProcessInscription().set_data_inscription5(AppCtx.getServiceProcessInscription().getInscription(),
                        CursusContent.ITEMS);
                AppCtx.getServiceProcessInscription().validated_screen5(AppCtx.getServiceProcessInscription().getInscription());
                break;
            default:
                break;
        }
    }

    public void hideViewByNum(int numView) {
        switch (numView) {
            case 1:
                findViewById(R.id.stub_Inflated1).setVisibility(View.GONE);
                break;
            case 2:
                findViewById(R.id.stub_Inflated2).setVisibility(View.GONE);
                break;
            case 3:
                findViewById(R.id.stub_Inflated3).setVisibility(View.GONE);
                break;
            case 4:
                findViewById(R.id.stub_Inflated4).setVisibility(View.GONE);
                break;
            case 5:
                findViewById(R.id.stub_Inflated5).setVisibility(View.GONE);
                break;
            default:

                break;
        }
    }

    public void displayViewByNum(int numView) {
        switch (numView) {
            case 1:
                findViewById(R.id.stub_Inflated1).setVisibility(View.VISIBLE);
                break;
            case 2:
                findViewById(R.id.stub_Inflated2).setVisibility(View.VISIBLE);
                break;
            case 3:
                findViewById(R.id.stub_Inflated3).setVisibility(View.VISIBLE);
                break;
            case 4:
                findViewById(R.id.stub_Inflated4).setVisibility(View.VISIBLE);
                break;
            case 5:
                findViewById(R.id.stub_Inflated5).setVisibility(View.VISIBLE);
                CreateProfil(AppCtx.getServiceProcessInscription().getInscription().getUser());
                break;
            default:
                findViewById(R.id.stub_Inflated1).setVisibility(View.VISIBLE);
                break;
        }
    }

    public void gotoMainActivity() {
        Intent intent = new Intent(context, MainActivity.class);
        Bundle bundle = new Bundle();

        bundle.putSerializable("AppSessionContext", AppCtx);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void gotoCursusActivity() {
        Intent intent = new Intent(context, CursusListActivity.class);
        intent.putExtra("AppSessionContext", AppCtx);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            resultCode = data.getIntExtra("Result", 1);
        }
        if (resultCode != PICK_REQUEST_BackCURSUS) {
            Intent intent = this.getIntent();
            if (intent.getSerializableExtra("AppSessionContext") != null)
                AppCtx = (SessionWsService) intent.getSerializableExtra("AppSessionContext");
            if (!(AppCtx.getServiceProcessInscription().getInscription().getUser().getCurriculum() != null))
                AppCtx.getServiceProcessInscription().getInscription().getUser().setCursuses(CursusContent.ITEMS);
            else {
                if (AppCtx.getServiceProcessInscription().getInscription().getUser().getCurriculum().size() < 1) {
                    if (CursusContent.ITEMS.size() > 0)
                        AppCtx.getServiceProcessInscription().getInscription().getUser().setCursuses(CursusContent.ITEMS);
                }
            }
            current_NUM_PAGES = 4;
            hideViewByNum(current_NUM_PAGES);
            current_NUM_PAGES++;
            displayViewByNum(current_NUM_PAGES);
        }
        if (resultCode == PICK_REQUEST_BackCURSUS) {
            current_NUM_PAGES = 5;
            hideViewByNum(current_NUM_PAGES);
            current_NUM_PAGES--;
            displayViewByNum(current_NUM_PAGES);
        }
    }
    public void infoBox(String Titre, String Message) {
        new AlertDialog.Builder(this)
                .setTitle(Titre)
                .setMessage(Message)
                .show();
    }

    public ContactPreference getContactPreferenceSelectedByIds(int id_jobs_offers, int id_city_activities, int id_national_activities, int id__project_volontary) {
        Switch job = (Switch) findViewById(id_jobs_offers);
        Switch cityacti = (Switch) findViewById(id_city_activities);
        Switch nationalacti = (Switch) findViewById(id_national_activities);
        Switch projet = (Switch) findViewById(id__project_volontary);

        return new ContactPreference(job.isChecked(), cityacti.isChecked(), nationalacti.isChecked(), projet.isChecked());
    }

    public Object getObjSelectedBySpinnerId(int id_Spinner) {
        if (((Spinner) findViewById(id_Spinner)) != null)
            return ((Spinner) findViewById(id_Spinner)).getSelectedItem();
        else return null;
    }

    public Object getObjSelectedByListViewId(int id_listView) {

        return ((ListView) findViewById(id_listView)).getSelectedItem();
    }

    public ArrayList<Skill> getListSkillsSelected(int id_listView) {
        SparseBooleanArray checked = ((ListView) findViewById(id_listView)).getCheckedItemPositions();
        ArrayList<Skill> selectedItems = new ArrayList<>();
        for (int i = 0; i < checked.size(); i++) {
            // Item position in adapter
            int position = checked.keyAt(i);
            // Add sport if it is checked i.e.) == TRUE!
            if (checked.valueAt(i))
                selectedItems.add(ListViewInit.skillList.get(position));
        }
        return selectedItems;
    }

    public String getTextByEditTextId(int id_editText) {
        return ((EditText) findViewById(id_editText)).getText().toString();
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

    public void DisplayToast(String text, int time) {
        if (time > 0)
            time = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, time);
        toast.show();
    }

    public void DisplayToast(String text) {
        DisplayToast(text, 0);
    }

    public void ImageListener() {
        ImageView home_icon;
        home_icon = (ImageView) this.findViewById(R.id.icon_home);
        // set a onclick listener for when the button gets clicked
        home_icon.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                gotoMainActivity();
            }
        });

    }
    public void onChooseBirthday(View v) {
        showDate(default_birthday_date, R.id.icon_choose_birthday, R.id.editxt_ins_birthday);

    }


    public void onChooseCurriculumDateBegin(View v) {
        showDate(default_start_curriculum_date, R.id.icon_choose_curriculumDateBegin, R.id.editxt_date_begin);

    }

    public void onChooseCurriculumDateEnd(View v) {
        showDate(default_end_curriculum_date, R.id.icon_choose_birthday, R.id.editxt_date_end);

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

    public void CreateProfilByStep(String label, String value, int content) {
        TextView Label = new TextView(this);
        Label.setText(label);
        TextView Value = new TextView(this);
        Value.setText(value);
        LinearLayout.LayoutParams lbl_layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 7f);
        lbl_layout_params.setMargins(5, 0, 0, 0);
        Label.setLayoutParams(lbl_layout_params);
        Value.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 3f));

        LinearLayout linearlayout = (LinearLayout) findViewById(content);
        linearlayout.removeAllViews();
        linearlayout.addView(Label);
        linearlayout.addView(Value);
    }

    public void CreateProfil(UserMember usermember) {
        if (usermember.getRegistration_date() != null)
            CreateProfilByStep("Date inscription :", sdfdisplay.format(usermember.getRegistration_date()), R.id.content_for_registrationdate);

        if (usermember.getEmail() != null)
            CreateProfilByStep("Email :", usermember.getEmail(), R.id.content_for_email);

        if (usermember.getName() != null)
            CreateProfilByStep("Nom :", usermember.getName(), R.id.content_for_name);

        if (usermember.getFirstname() != null)
            CreateProfilByStep("Prénom :", usermember.getFirstname(), R.id.content_for_firstname);

        if (usermember.getBirth_date() != null)
            CreateProfilByStep("Date naissance :", sdfdisplay.format(usermember.getBirth_date()), R.id.content_for_birthday);

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
                listCursus += c.getLabel() + " " + c.getDiscipline().getLabel() + " " + c.getEstablishment() + " " + sdfdisplay.format(c.getStart_date());
                i++;
                if (i < usermember.getCurriculum().size()) {
                    listCursus += ",\n";
                }
            }
            CreateProfilByStep("Cursus :", listCursus, R.id.content_for_cursus);
        }


    }


}
/*
new AlertDialog.Builder(context).setTitle(Messages.error_inscription_Titre).setMessage(AppCtx.getServiceProcessInscription().getErrors(current_NUM_PAGES))
                        .setPositiveButton(Messages.error_continu, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                // Some stuff to do when ok got clicked
                                hideViewByNum(current_NUM_PAGES);
                                current_NUM_PAGES++;
                                displayViewByNum(current_NUM_PAGES);
                            }
                        }).setNegativeButton(Messages.error_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {}})
                    .show();
 */