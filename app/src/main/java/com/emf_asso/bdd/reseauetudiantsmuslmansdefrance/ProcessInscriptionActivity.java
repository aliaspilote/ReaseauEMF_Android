package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.ContactPreference;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Section;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Skill;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.ProcessInscriptionService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Omar on 04/11/2015.
 */
public class ProcessInscriptionActivity extends Activity {


    private static final int NUM_PAGES = 5;
    public ViewStub stub;
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

        //Log.d("ServiceInscription", getIntent().getExtras().getSerializable("ServiceInscription", "ServiceInscription"));
        ServiceProcessInscription = (ProcessInscriptionService) bundle.getSerializable("ServiceInscription");
        ServiceProcessInscription.onStart();
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
        hideViewByNum(current_NUM_PAGES);
        if (current_NUM_PAGES == NUM_PAGES) {
            //Valider le formulaire
            current_NUM_PAGES = 1;
            displayViewByNum(current_NUM_PAGES);
            Gson gson = new Gson();
            String jsonUser = gson.toJson(ServiceProcessInscription.getInscription().getUser());
            new AlertDialog.Builder(this)
                    .setTitle("ServiceProcessInscription ")
                    .setMessage(jsonUser)
                    .show();
        } else {
            current_NUM_PAGES++;
            displayViewByNum(current_NUM_PAGES);
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
        switch (step) {
            case 1:
                ServiceProcessInscription.set_data_inscription1("aa", "aa");
                break;
            case 2:
                ServiceProcessInscription.set_data_inscription2("aa", "aa", "aa", "aa", "aa", new Date());
                break;
            case 3:
                ServiceProcessInscription.set_data_inscription3("tjrs", new Section(), new ArrayList<Skill>(), new ContactPreference());
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


}
