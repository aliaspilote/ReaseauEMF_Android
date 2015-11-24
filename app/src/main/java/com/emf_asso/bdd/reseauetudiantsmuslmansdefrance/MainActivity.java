package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.FormBodyManager;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.HttpReponse;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.ProcessInscriptionService;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.SessionWsService;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.Web_Service_Controlleur;

import org.json.simple.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements ActivityConnectedWeb {

    private static final String TAG = "MainActivity";
    public final HttpReponse LastReponse = new HttpReponse();
    SessionWsService AppSessionContext;
    int duration = Toast.LENGTH_SHORT;
    private Context context = this;
    public Menu_Control menucontrol = new Menu_Control(context);
    private ProcessInscriptionService ServiceProcessInscription = new ProcessInscriptionService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            if (bundle.getSerializable("ServiceInscription") != null)
                ServiceProcessInscription = (ProcessInscriptionService) bundle.getSerializable("ServiceInscription");
            if (bundle.getSerializable("AppSessionContext") != null)
                AppSessionContext = (SessionWsService) bundle.getSerializable("AppSessionContext");
        }
    }

    public void ReceptionResponse(HttpReponse Rep) {
        DisplayToast(Messages.error_in_progress + "2/2");
        LastReponse.setHttpReponse(Rep.getResultat(), Rep.getSucces(), Rep.getAction(), Rep.getDataReponse(), Rep.getExceptionText());
        String Message = "";

        if (!LastReponse.getSucces() && LastReponse.getResultat().get("result").toString() != "true")
            Message = LastReponse.getExceptionText();
        else {
            Boolean result = false;
            String tempResultBool = LastReponse.getResultat().get("result").toString();
            if (tempResultBool.contentEquals("true"))
                result = true;
            switch (LastReponse.Action) {
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
                default:
                    Message = LastReponse.Action + " effectué\n";
                    Message += "aucun post traitement défini \n";
                    Message += LastReponse.getResultat().toString();
                    break;
            }
        }
        new AlertDialog.Builder(this)
                .setTitle("Action : " + LastReponse.getAction())
                .setMessage(Message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        // Some stuff to do when ok got clicked
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        // Some stuff to do when cancel got clicked
                    }
                })
                .show();
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

    /*


     */

    public void OnValidateForm(View  view)
    {
        afficherFormInscr();
    }

    public void OnTry(View view) throws IOException {
        Intent intent = new Intent(context, CursusFragment.class);

        Bundle bundle = new Bundle();
        //bundle.putSerializable("ServiceInscription", ServiceProcessInscription);
        //intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public String getTextByEditTextId(int id_editText) {
        return ((EditText) findViewById(id_editText)).getText().toString();
    }

    public void OnConnect(View view) {
        String mail = getTextByEditTextId(R.id.editxt_auth_email);
        String mdp = getTextByEditTextId(R.id.editxt_auth_pwd);

        if (mail.length() > 5 && mdp.length() > 6) {
            Web_Service_Controlleur wb_thread = new Web_Service_Controlleur(
                    this, FormBodyManager.auth(mail, mdp));
            wb_thread.execute();
        } else {
            DisplayToast(Messages.error_auth_length);
        }

    }

    public void successAuth(JSONObject obj) {
        JSONObject object = obj;
        Intent intent = new Intent(context, UsermemberProfileActivity.class);

        Bundle bundle = new Bundle();
        AppSessionContext = new SessionWsService(object);
        bundle.putSerializable("AppSessionContext", AppSessionContext);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void OnRegisterClick(View view) {
        Intent intent = new Intent(context, ProcessInscriptionActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("ServiceInscription", ServiceProcessInscription);
        intent.putExtras(bundle);
        context.startActivity(intent);
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


    private void afficherFormInscr()
    {
        String message = new String();
        EditText Inputs = (EditText)findViewById(R.id.editxt_name);
        message += Inputs.getText()+"\n";

        Inputs = (EditText)findViewById(R.id.editxt_firstname);
        message += Inputs.getText()+"\n";


        Inputs = (EditText)findViewById(R.id.editxt_city);
        message += Inputs.getText()+"\n";

        new AlertDialog.Builder(this)
                .setTitle("Confirmation Saisie")
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        // Some stuff to do when ok got clicked


                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        // Some stuff to do when cancel got clicked
                    }
                })
                .show();
    }


}


