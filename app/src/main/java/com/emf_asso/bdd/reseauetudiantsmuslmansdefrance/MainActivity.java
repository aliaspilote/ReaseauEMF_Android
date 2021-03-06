package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.ActivityConnectedWeb;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.DialogBox;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.Messages;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.CheckContentService;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.HttpReponse;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.SessionWsService;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.Web_Service_Controlleur;

import org.json.simple.JSONObject;

import java.io.IOException;

import static com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.FormBodyManager.auth;
import static com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.FormBodyManager.forgotPswd;
import static com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.FormBodyManager.getAction;
import static com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.FormBodyManager.get_user;

public class MainActivity extends AppCompatActivity implements ActivityConnectedWeb {

    private static final String SPF_LOGINS = "emflogin"; //  <---Name shared preference
    private static final String USERMAIL = "emfmail";  //  <--- To save username
    private static final String PASSWORD = "emfpassword";  //  <--- To save password
    private static final String CHECK_REMEMBER = "check_remember";  //  <--- To save password
    public final HttpReponse LastReponse = new HttpReponse();
    CheckBox CheckRememberBox;
    EditText EditTextMail;
    EditText EditTextPswd;
    private SessionWsService AppSessionContext;
    private Context context = this;
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
        if (savedInstanceState != null) {
            AppSessionContext = (SessionWsService) savedInstanceState.getSerializable("AppSessionContext");
        } else if (!(AppSessionContext != null)) {
            if (bundle != null) {
                if (bundle.getSerializable("AppSessionContext") != null)
                    AppSessionContext = (SessionWsService) bundle.getSerializable("AppSessionContext");
            }
        }
        if (AppSessionContext == null)
            AppSessionContext = new SessionWsService();

        if (intent.getStringExtra("MailExisting") != null) {
            ((EditText) findViewById(R.id.editxt_auth_email)).setText((String) bundle.getSerializable("MailExisting"));
            DisplayToast(Messages.error_is_Existing_PI, 60000);
        }
        TextView ForgotPsw = (TextView) findViewById(R.id.txtview_forget_pwd);
        ForgotPsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String mail = getTextByEditTextId(R.id.editxt_auth_email);
                if (CheckContentService.checkEmail(mail)) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Récupérer mot de passe");
                    alertDialogBuilder
                            .setMessage("Un nouveau mot de passe sera envoyé sur votre boîte mail. ")
                            .setCancelable(false)
                            .setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    forgotenPswd(mail);
                                }
                            })
                            .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    Snackbar.make(view, "Email saisi incorret", Snackbar.LENGTH_LONG)
                            .setAction("Email incorret", null).show();
                }

            }
        });
        loadListsFromWebService();

        CheckRememberBox = (CheckBox) findViewById(R.id.checkbox_remember_me);
        EditTextMail = (EditText) findViewById(R.id.editxt_auth_email);
        EditTextMail.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    EditTextPswd.getText().clear();
                }
            }
        });
        EditTextPswd = (EditText) findViewById(R.id.editxt_auth_pwd);
        SharedPreferences settings = getSharedPreferences(SPF_LOGINS, 0);
        if (settings.getString(CHECK_REMEMBER, "").contentEquals("true")) {
            EditTextMail.setText(settings.getString(USERMAIL, ""));
            EditTextPswd.setText(settings.getString(PASSWORD, ""));
            CheckRememberBox.setChecked(true);
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Quitter l'application");
        alertDialogBuilder
                .setMessage("Voulez-vous vraiment quitter l'application")
                .setCancelable(false)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        moveTaskToBack(true);
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

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
                            Message += Messages.success_w8_load_data;
                        } else
                            Message += Messages.error_load_profil;
                    } else
                        Message += LastReponse.getExceptionText();
                    break;
                case "forgot_pswd":
                    if (result) {
                        if ((LastReponse.getResultat().get("isExisting").toString().contentEquals("true")))
                            Message += "Mot de passe envoyé à : " + LastReponse.getResultat().get("mail").toString();
                        else
                            Message += Messages.error_isnot_Existing;
                    } else {
                        Message += LastReponse.getExceptionText() + "";
                        Message += Messages.error_generique;
                    }
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
                        //Message += LastReponse.getExceptionText()+"";
                        //Message += LastReponse.getResultat().get("data_debug").toString();
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


    public void OnTry(View view) throws IOException {

        Intent intent = new Intent(context, LDFActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("AppSessionContext", AppSessionContext);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    public String getTextByEditTextId(int id_editText) {
        return ((EditText) findViewById(id_editText)).getText().toString();
    }

    public void OnTryConnect(View view) {
        SharedPreferences settings = getSharedPreferences(SPF_LOGINS, Context.MODE_PRIVATE);
        if (!CheckRememberBox.isChecked()) {
            settings.edit().clear().commit();
            settings.edit().putString(CHECK_REMEMBER, "false").commit();
        } else {
            settings.edit().putString(CHECK_REMEMBER, "true").commit();
        }

        String mail = getTextByEditTextId(R.id.editxt_auth_email);
        String mdp = getTextByEditTextId(R.id.editxt_auth_pwd);

        if (mail.length() > 5 && mdp.length() > 5) {
            Web_Service_Controlleur wb_thread = new Web_Service_Controlleur(
                    this, auth(mail, mdp));
            wb_thread.execute();
        } else {
            DisplayToast(Messages.error_auth_length);
        }

    }

    public void successAuth(JSONObject obj) {

        if (CheckRememberBox.isChecked()) {
            SharedPreferences settings = getSharedPreferences(SPF_LOGINS, 0);
            settings.edit().
                    putString(USERMAIL, EditTextMail.getText().toString() + "")
                    .putString(PASSWORD, EditTextPswd.getText().toString() + "")
                    .commit();
        }
        JSONObject object = obj;
        AppSessionContext.setSession(object);
        Web_Service_Controlleur wb_thread = new Web_Service_Controlleur(
                this, get_user(AppSessionContext.getUserMember().getEmail(), AppSessionContext.getToken()));
        wb_thread.execute();
    }

    public void forgotenPswd(String mail) {
        Web_Service_Controlleur wb_thread = new Web_Service_Controlleur(this, forgotPswd(mail));
        wb_thread.execute();
    }

    public void startProfileActivity(JSONObject obj) {
        AppSessionContext.BeInProfileView();
        AppSessionContext.setUser_From_DB(obj);
        AppSessionContext.BeInProfileView();
        Intent intent = new Intent(context, UserMemberProfilActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("p", -1);
        bundle.putSerializable("AppSessionContext", AppSessionContext);
        intent.putExtras(bundle);
        if (AppSessionContext.getUserMember().isEnable() == false) {
            DialogBox dialog = new DialogBox(context, intent);
            dialog.createDialogBox("Compte désactivé", "Voulez vous l'activer ?");
        } else
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


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("AppSessionContext", AppSessionContext);
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


