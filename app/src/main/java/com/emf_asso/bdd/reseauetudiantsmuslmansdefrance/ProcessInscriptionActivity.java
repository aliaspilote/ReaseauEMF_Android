package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.ProcessInscriptionService;
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
    private static final int NUM_PAGES = 5;
    public ViewStub stub;
    public HttpReponse LastReponse = new HttpReponse();
    public CreateDate birthday_date;
    public CreateDate start_curriculum_date;
    public CreateDate end_curriculum_date;
    public String tag;
    public java.text.SimpleDateFormat sdfdisplay = new java.text.SimpleDateFormat(DataContext.dateDisplayFormat);
    private ListViewInit ManagerListView;
    private Context context = this;
    public Menu_Control menucontrol = new Menu_Control(context);
    private Activity activity = this;
    private int current_NUM_PAGES;
    private ProcessInscriptionService ServiceProcessInscription;
    private SessionWsService AppSessionContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processinscription);
        this.testInternetConnection.setContext(context);
        InitStubs();
        ImageListener();
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            if (bundle.getSerializable("AppSessionContext") != null) {
                AppSessionContext = (SessionWsService) bundle.getSerializable("AppSessionContext");
                ServiceProcessInscription = AppSessionContext.getServiceProcessInscription();
                ListViewInit.loadListStaticPI_View(this, AppSessionContext);
            }
            if (!(current_NUM_PAGES>1)) // au cas ou l'on revient de l'activité Cursus
                current_NUM_PAGES = 1;
        }
        ServiceProcessInscription.onStart();
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

        TextView twError = (TextView) findViewById(R.id.txtview_submit_legend_viewResult);

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
                    TextView twResult = (TextView) findViewById(R.id.txtview_submit_legend_viewResult);
                    twResult.setText("Résultats :\n");
                    if (result)
                        Message += Messages.error_addUser_success;
                    else if (tempResultBool.contentEquals("isExisting"))
                        Message += Messages.error_is_Existing;
                    else
                        Message = twError.getText() + LastReponse.getExceptionText();
                    twResult.setText(Message);
                    break;
                default:
                    Message = Messages.error_unknow_action;
                    Message += LastReponse.getResultat().toString();
                    twError.setText(Message);
                    break;
            }
            if (Message.length() > 2)
                DisplayToast(Message);
        }
    }

    public void OnNext(View view) {
        setDataOn_ServiceByStep(current_NUM_PAGES);
        if (current_NUM_PAGES == NUM_PAGES - 1) // Si nous somme a la dernière page du formulaire d'inscription
        {
            gotoCursusActivity();
        } else {
            if (ServiceProcessInscription.getErrors(current_NUM_PAGES) != "") {
                new AlertDialog.Builder(context).setTitle(Messages.error_inscription_Titre).setMessage(ServiceProcessInscription.getErrors(current_NUM_PAGES))
                        .setPositiveButton(Messages.error_continu, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                // Some stuff to do when ok got clicked
                                hideViewByNum(current_NUM_PAGES);
                                current_NUM_PAGES++;
                                displayViewByNum(current_NUM_PAGES);
                            }
                        }).setNegativeButton(Messages.error_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        //*Some stuff to do when cancel got clicked */
                    }
                })
                        .show();
            } else {
                hideViewByNum(current_NUM_PAGES);
                current_NUM_PAGES++;
                displayViewByNum(current_NUM_PAGES);
            }
        }
    }

    public void OnPrevious(View view) {
        setDataOn_ServiceByStep(current_NUM_PAGES);
        hideViewByNum(current_NUM_PAGES);
        if (current_NUM_PAGES == 1) {
            gotoMainActivity();
        } else {
            current_NUM_PAGES--;
            displayViewByNum(current_NUM_PAGES);
        }
    }

    public void OnValidateInscrption(View view) throws IOException {

        Web_Service_Controlleur wb_thread = new Web_Service_Controlleur(
                this, FormBodyManager.addUser(ServiceProcessInscription.getInscription().getUser()));
        wb_thread.execute();

    }

    public void OnCancelInscrption(View view) {
        current_NUM_PAGES = 1;
        setContentView(R.layout.activity_processinscription);
        InitStubs();
    }

    @Override
    public void onBackPressed() {
        if (current_NUM_PAGES == 1) {
            gotoMainActivity();
        } else {
            displayViewByNum(current_NUM_PAGES - 1);
        }
    }

    public void setDataOn_ServiceByStep(int step) {

        TextView lbl_error;

        switch (step) {
            case 1:
                ServiceProcessInscription.set_data_inscription1(ServiceProcessInscription.getInscription(),
                        getTextByEditTextId(R.id.editxt_ins_email),
                        getTextByEditTextId(R.id.editxt_ins_pwd),
                        getTextByEditTextId(R.id.editxt_ins_repeat_pwd));
                ServiceProcessInscription.validated_screen1(ServiceProcessInscription.getInscription());
                lbl_error = (TextView) findViewById(R.id.lbl_ins1_legend_error);
                if (ServiceProcessInscription.getErrors(step) != "")
                    lbl_error.setText(ServiceProcessInscription.getErrors(step));
                else
                    lbl_error.setText("");
                break;
            case 2:
                ServiceProcessInscription.set_data_inscription2(ServiceProcessInscription.getInscription(),
                        getTextByEditTextId(R.id.editxt_ins_name),
                        getTextByEditTextId(R.id.editxt_ins_firstname),
                        getTextByEditTextId(R.id.editxt_ins_zipcode),
                        getTextByEditTextId(R.id.editxt_ins_city),
                        getTextByEditTextId(R.id.editxt_ins_phone),
                        getDateByEditTextId(R.id.editxt_ins_birthday));
                ServiceProcessInscription.validated_screen2(ServiceProcessInscription.getInscription());
                lbl_error = (TextView) findViewById(R.id.lbl_ins2_legend_error);
                if (ServiceProcessInscription.getErrors(step) != "")
                    lbl_error.setText(ServiceProcessInscription.getErrors(step));
                else
                    lbl_error.setText("");
                break;
            case 3:
                ServiceProcessInscription.set_data_inscription3(ServiceProcessInscription.getInscription(),
                        (Involvement) getObjSelectedBySpinnerId(R.id.spinner_involvement),
                        (Section) getObjSelectedBySpinnerId(R.id.spinner_section),
                        getContactPreferenceSelectedByIds(R.id.switch_offer, R.id.switch_info_EMFcity, R.id.switch_info_national, R.id.switch_project),
                        (Discipline) getObjSelectedBySpinnerId(R.id.spinner_discipline));
                ServiceProcessInscription.validated_screen3(ServiceProcessInscription.getInscription());
                lbl_error = (TextView) findViewById(R.id.lbl_ins3_legend_error);
                if (ServiceProcessInscription.getErrors(step) != "")
                    lbl_error.setText(ServiceProcessInscription.getErrors(step));
                else
                    lbl_error.setText("");
            case 4:
                ServiceProcessInscription.set_data_inscription4(ServiceProcessInscription.getInscription(),
                        getListSkillsSelected(R.id.listview_skill));
            case 5:
                ServiceProcessInscription.set_data_inscription5(ServiceProcessInscription.getInscription(),
                        CursusContent.ITEMS);
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
                CreateProfil(AppSessionContext.getServiceProcessInscription().getInscription().getUser());
                break;
            default:
                findViewById(R.id.stub_Inflated1).setVisibility(View.VISIBLE);
                break;
        }
    }

    public void gotoMainActivity() {
        Intent intent = new Intent(context, MainActivity.class);
        Bundle bundle = new Bundle();

        bundle.putSerializable("AppSessionContext", AppSessionContext);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void gotoCursusActivity() {

        Intent intent = new Intent(context, CursusListActivity.class);
        //Bundle bundle = new Bundle();
        //bundle.putSerializable("AppSessionContext", AppSessionContext);
        //intent.putExtras(bundle);
        startActivityForResult(intent, 1);
        //context.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == PICK_REQUEST_CURSUS) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
            }
            hideViewByNum(current_NUM_PAGES);
            current_NUM_PAGES++;
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

        return new ContactPreference(job.isActivated(), cityacti.isActivated(), nationalacti.isActivated(), projet.isActivated());
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
        showDate(birthday_date, R.id.icon_choose_birthday, R.id.editxt_ins_birthday);

    }


    public void onChooseCurriculumDateBegin(View v) {
        showDate(start_curriculum_date, R.id.icon_choose_curriculumDateBegin, R.id.editxt_date_begin);

    }

    public void onChooseCurriculumDateEnd(View v) {
        showDate(end_curriculum_date, R.id.icon_choose_birthday, R.id.editxt_date_end);

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

        Label.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
        Value.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));

        LinearLayout linearlayout = (LinearLayout) findViewById(content);
        linearlayout.addView(Label);
        linearlayout.addView(Value);
    }

    public void CreateProfil(UserMember usermember) {
        if (usermember.getRegistration_date() != null)
            CreateProfilByStep("Date d'inscription :", sdfdisplay.format(usermember.getRegistration_date()), R.id.content_for_registrationdate);

        if (usermember.getEmail() != null)
            CreateProfilByStep("Email :", usermember.getEmail(), R.id.content_for_email);

        if (usermember.getName() != null)
            CreateProfilByStep("Nom :", usermember.getName(), R.id.content_for_name);

        if (usermember.getFirstname() != null)
            CreateProfilByStep("Prénom :", usermember.getFirstname(), R.id.content_for_firstname);

        if (usermember.getBirth_date() != null)
            CreateProfilByStep("Date de naissance :", sdfdisplay.format(usermember.getBirth_date()), R.id.content_for_birthday);

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


}
