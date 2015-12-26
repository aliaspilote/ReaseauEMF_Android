package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.ActivityConnectedWeb;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.Messages;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.HttpReponse;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.SessionWsService;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.Web_Service_Controlleur;

import org.json.simple.JSONObject;

import java.io.IOException;

import static com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.FormBodyManager.auth;
import static com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.FormBodyManager.getAction;
import static com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.FormBodyManager.get_user;

public class MainActivity extends AppCompatActivity implements ActivityConnectedWeb {

    public final HttpReponse LastReponse = new HttpReponse();
    private SessionWsService AppSessionContext;
    private Context context = this;
    public Menu_Control menucontrol = new Menu_Control(context);
    private ProgressDialog progress;
    private int progressVal = 0;
    private int progressValMax = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.testInternetConnection.setContext(context);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            if (bundle.getSerializable("AppSessionContext") != null)
                AppSessionContext = (SessionWsService) bundle.getSerializable("AppSessionContext");
        }
        if (AppSessionContext == null)
            AppSessionContext = new SessionWsService();


        loadListsFromWebService();

        menucontrol.setAppSessionContext(AppSessionContext);
    }

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
                case "get_user":
                    if (result) {
                        if ((LastReponse.getResultat().get("result").toString().contentEquals("true"))) {
                            //Message += Messages.error_is_Existing;
                            //AppSessionContext.setUser_From_DB(LastReponse.getResultat());
                            startProfileActivity(LastReponse.getResultat());
                            Message += LastReponse.getResultat().toString();
                        }
                        else
                            Message += Messages.error_load_profil;
                    } else
                        Message += LastReponse.getExceptionText();
                    break;
                case "check_mail":
                    if (result) {
                        if ((LastReponse.getResultat().get("isExisting").toString().contentEquals("true")))
                            Message += Messages.error_is_Existing;
                        else
                            Message += Messages.error_isnot_Existing;
                    } else
                        Message += LastReponse.getExceptionText();
                    break;
                case "auth":
                    if (result)
                        successAuth(LastReponse.getResultat());
                    else {
                        Message += Messages.error_auth;
                        Message += LastReponse.getExceptionText();
                        Message += LastReponse.getResultat().get("data_debug").toString();
                    }
                    break;
                case "get_degree_study":
                    if (result) {
                        loadListProgressBar(1);
                        AppSessionContext.getDataContext().setDegreeStudyList(LastReponse.getResultat());
                    } else {
                        Message += Messages.error_auth;
                        Message += LastReponse.getExceptionText();
                        progress.cancel();
                    }
                    break;
                case "get_involvements":
                    if (result) {
                        loadListProgressBar(1);
                        AppSessionContext.getDataContext().setInvolvementsList(LastReponse.getResultat());
                    } else {
                        Message += Messages.error_auth;
                        Message += LastReponse.getExceptionText();
                        progress.cancel();
                    }
                    break;
                case "get_skills":
                    if (result) {
                        loadListProgressBar(1);
                        AppSessionContext.getDataContext().setSkillList(LastReponse.getResultat());
                    } else {
                        Message += Messages.error_auth;
                        Message += LastReponse.getExceptionText();
                        progress.cancel();
                    }
                    break;
                case "get_disciplines":
                    if (result) {
                        loadListProgressBar(1);
                        AppSessionContext.getDataContext().setDisciplineList(LastReponse.getResultat());
                    } else {
                        Message += Messages.error_auth;
                        Message += LastReponse.getExceptionText();
                        progress.cancel();
                    }
                    break;
                case "get_sections":
                    if (result) {
                        loadListProgressBar(1);
                        AppSessionContext.getDataContext().setSectionList(LastReponse.getResultat());
                    } else {
                        Message += Messages.error_auth;
                        Message += LastReponse.getExceptionText();
                        progress.cancel();
                    }
                    break;
                default:
                    Message = LastReponse.Action + " effectué\n";
                    Message += "aucun post traitement défini \n";
                    Message += LastReponse.getResultat().toString();
                    break;
            }
            if (Message.length() > 2)
                DisplayToast(Message);
        }
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

    public void OnTry(View view) throws IOException {

        Intent intent = new Intent(context, DiffusionCriteriasActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("AppSessionContext", AppSessionContext);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public String getTextByEditTextId(int id_editText) {
        return ((EditText) findViewById(id_editText)).getText().toString();
    }

    public void OnTryConnect(View view) {
        String mail = getTextByEditTextId(R.id.editxt_auth_email);
        String mdp = getTextByEditTextId(R.id.editxt_auth_pwd);

        if (mail.length() > 5 && mdp.length() > 6) {
            Web_Service_Controlleur wb_thread = new Web_Service_Controlleur(
                    this, auth(mail, mdp));
            wb_thread.execute();
        } else {
            DisplayToast(Messages.error_auth_length);
        }

    }

    public void successAuth(JSONObject obj) {
        JSONObject object = obj;
        AppSessionContext.setSession(object);
        Web_Service_Controlleur wb_thread = new Web_Service_Controlleur(
                this, get_user(AppSessionContext.getUserMember().getEmail(), AppSessionContext.getToken()));
        wb_thread.execute();
    }

    public void startProfileActivity(JSONObject obj) {
        AppSessionContext.resetLocationAllView();
        AppSessionContext.inProfileView = true;
        AppSessionContext.setUser_From_DB(obj);
        Intent intent = new Intent(context, UserMemberProfilActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("p", -1);
        bundle.putSerializable("AppSessionContext", AppSessionContext);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void OnRegisterClick(View view) {
        AppSessionContext.resetLocationAllView();
        AppSessionContext.inProssInscrView = true;
        Intent intent = new Intent(context, ProcessInscriptionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("AppSessionContext", AppSessionContext);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void loadListsFromWebService() {
        progressValMax = 0;
        if (!AppSessionContext.getDataContext().involvementDL_OK)
            progressValMax++;

        if (!AppSessionContext.getDataContext().sectionDL_OK)
            progressValMax++;

        if (!AppSessionContext.getDataContext().skillLDL_OK)
            progressValMax++;

        if (!AppSessionContext.getDataContext().degreeStudyDL_OK)
            progressValMax++;

        if (!AppSessionContext.getDataContext().disciplineDL_OK)
            progressValMax++;

        loadListProgressBar(0);
        Web_Service_Controlleur wb_thread;

        if (!AppSessionContext.getDataContext().sectionDL_OK) {
            wb_thread = new Web_Service_Controlleur(this, getAction("get_sections"));
            wb_thread.execute();
        }

        if (!AppSessionContext.getDataContext().disciplineDL_OK) {
            wb_thread = new Web_Service_Controlleur(this, getAction("get_disciplines"));
            wb_thread.execute();
        }
        if (!AppSessionContext.getDataContext().involvementDL_OK) {
            wb_thread = new Web_Service_Controlleur(this, getAction("get_involvements"));
            wb_thread.execute();
        }
        if (!AppSessionContext.getDataContext().skillLDL_OK) {
            wb_thread = new Web_Service_Controlleur(this, getAction("get_skills"));
            wb_thread.execute();
        }
        if (!AppSessionContext.getDataContext().degreeStudyDL_OK) {
            wb_thread = new Web_Service_Controlleur(this, getAction("get_degree_study"));
            wb_thread.execute();
        }
    }

    public void loadListProgressBar(int progressJump) {
        if (progressValMax > 0) {
            if (progressJump == 0) {
                progressVal = 0;
                progress = new ProgressDialog(this);
                progress.setMessage(Messages.success_w8_load_data);
                progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progress.setMax(progressValMax);
                progress.setProgress(progressVal);
                progress.show();
            } else {
                progressVal++;
                progress.setProgress(progressVal);
            }
            if (progressVal >= progressValMax) {
                progress.cancel();
                progressValMax = 0;
            }
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


