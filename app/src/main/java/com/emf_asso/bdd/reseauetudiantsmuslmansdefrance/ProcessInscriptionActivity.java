package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.TextView;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.ContactPreference;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Involvement;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Section;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Skill;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.Messages;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.ProcessInscriptionService;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Omar on 04/11/2015.
 */
public class ProcessInscriptionActivity extends Activity {


    private static final int NUM_PAGES = 5;
    public ViewStub stub;
    public List<Involvement> involvementsList;
    public List<Section> sectionList;
    private Context context = this;
    private int current_NUM_PAGES;
    private ProcessInscriptionService ServiceProcessInscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processinscription);

        InitStubs();
        current_NUM_PAGES = 1;
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        ServiceProcessInscription = (ProcessInscriptionService) bundle.getSerializable("ServiceInscription");
        ServiceProcessInscription.onStart();
        InitListSectionInvolvement();

    }

    public void InitListSectionInvolvement() {
        involvementsList = new ArrayList<>();
        involvementsList.add(new Involvement("Membre Actif", "Membre qui participe aux réunior", "1"));
        involvementsList.add(new Involvement("Membre Cool", "Il est cool", "2"));
        involvementsList.add(new Involvement("Super Actif", "Il est hyper actif", "3"));
        sectionList = new ArrayList<>();
        sectionList.add(new Section("Belfort"));
        sectionList.add(new Section("Paris"));
        sectionList.add(new Section("Lyon"));
    }

    public void InitStubs() {

        ((ViewStub) findViewById(R.id.stub_pi1)).inflate();
        ((ViewStub) findViewById(R.id.stub_pi2)).inflate();
        ((ViewStub) findViewById(R.id.stub_pi3)).inflate();
        ((ViewStub) findViewById(R.id.stub_pi4_1)).inflate();
        ((ViewStub) findViewById(R.id.stub_pi4_2)).inflate();

        findViewById(R.id.stub_Inflated2).setVisibility(View.GONE);
        findViewById(R.id.stub_Inflated3).setVisibility(View.GONE);
        findViewById(R.id.stub_Inflated4_1).setVisibility(View.GONE);
        findViewById(R.id.stub_Inflated4_2).setVisibility(View.GONE);

    }

    public void OnNext(View view) {
        setDataOn_ServiceByStep(current_NUM_PAGES);
        if (current_NUM_PAGES == NUM_PAGES) // Si nous somme a la dernière page du formulaire d'inscription
        {
            hideViewByNum(current_NUM_PAGES);
            //Revisualiser les infos saisies à confirmer
            //Valider le formulaire
            //Puis afficher le profile
            current_NUM_PAGES = 1;
            displayViewByNum(current_NUM_PAGES);
            Gson gson = new Gson();
            String jsonUser = gson.toJson(ServiceProcessInscription.getInscription().getUser());
            new AlertDialog.Builder(context)
                    .setTitle(" Validation Inscritpion ")
                    .setMessage(jsonUser)
                    .show();
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
                break;
            case 3:
                ServiceProcessInscription.set_data_inscription3(ServiceProcessInscription.getInscription(),
                        "tjrs", new Section(), new ArrayList<Skill>(), new ContactPreference());
                ServiceProcessInscription.validated_screen3(ServiceProcessInscription.getInscription());
                lbl_error = (TextView) findViewById(R.id.lbl_ins3_legend_error);
                if (ServiceProcessInscription.getErrors(step) != "")
                    lbl_error.setText(ServiceProcessInscription.getErrors(step));
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
                findViewById(R.id.stub_Inflated4_1).setVisibility(View.GONE);
                break;
            case 5:
                findViewById(R.id.stub_Inflated4_2).setVisibility(View.GONE);
                break;
            default:

                break;
        }
    }

    public void displayViewByNum(int numView) {
        switch (numView) {
            case 1:
                findViewById(R.id.stub_Inflated1).setVisibility(View.VISIBLE);
                //stub = (ViewStub) findViewById(R.id.stub_pi1);
                break;
            case 2:
                findViewById(R.id.stub_Inflated2).setVisibility(View.VISIBLE);
                //stub = (ViewStub) findViewById(R.id.stub_pi2);
                break;
            case 3:
                findViewById(R.id.stub_Inflated3).setVisibility(View.VISIBLE);
                // stub = (ViewStub) findViewById(R.id.stub_pi3);
                break;
            case 4:
                findViewById(R.id.stub_Inflated4_1).setVisibility(View.VISIBLE);
                // stub = (ViewStub) findViewById(R.id.stub_pi4_1);
                break;
            case 5:
                findViewById(R.id.stub_Inflated4_2).setVisibility(View.VISIBLE);
                //stub = (ViewStub) findViewById(R.id.stub_pi4_2);
                break;
            default:
                findViewById(R.id.stub_Inflated1).setVisibility(View.VISIBLE);
                //stub = (ViewStub) findViewById(R.id.stub_pi1);
                break;
        }
    }

    public void gotoMainActivity() {
        Intent intent = new Intent(context, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("ServiceInscription", ServiceProcessInscription);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void infoBox(String Titre, String Message) {
        new AlertDialog.Builder(this)
                .setTitle(Titre)
                .setMessage(Message)
                .show();
    }

    public String getTextByEditTextId(int id_editText) {
        return ((EditText) findViewById(id_editText)).getText().toString();
    }

    public Date getDateByEditTextId(int id_editText) {
        Date dt = null;
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
        try {
            dt = dateFormatter.parse(((EditText) findViewById(id_editText)).getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }


}
