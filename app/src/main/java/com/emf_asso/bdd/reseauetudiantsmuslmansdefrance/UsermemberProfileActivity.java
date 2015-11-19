package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.ActivityConnectedWeb;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.HttpReponse;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.SessionWsService;

import org.json.simple.JSONObject;

public class UsermemberProfileActivity extends AppCompatActivity implements ActivityConnectedWeb {
    public HttpReponse LastReponse = new HttpReponse();
    TextView Name;
    TextView Email;
    TextView Password;
    TextView Token;
    SessionWsService AppSessionContext = new SessionWsService();
    private Context context = this;
    private Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usermember_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        AppSessionContext = (SessionWsService) bundle.getSerializable("AppSessionContext");

        Name = (TextView) findViewById(R.id.editxt_name);
        Email = (TextView) findViewById(R.id.editxt_mail);
        Password = (TextView) findViewById(R.id.editxt_Claire_pswd);
        Token = (TextView) findViewById(R.id.editext_token);
        RefreshProfile();

    }

    public void ReceptionResponse(HttpReponse Rep) {
        JSONObject Resultat = Rep.getResultat();
        LastReponse.setHttpReponse(Resultat, Rep.getSucces(), Rep.getAction(), Rep.getDataReponse(), Rep.getExceptionText());
        String Message = "";

        if (!LastReponse.getSucces() && Resultat.get("TryParse") == "false")
            Message = LastReponse.getExceptionText();
        else {
            switch (LastReponse.Action) {
                case "check_mail":
                    Message = LastReponse.Action + " effectué\n";
                    Message += "Contenu de la réponse : \n";
                    Message += Resultat.toString();
                    break;
                case "auth":
                    Message = LastReponse.Action + " réussi\n";
                    Message += "Contenu de la réponse : \n";
                    Message += Resultat.toString();
                    break;
                default:
                    Message = LastReponse.Action + " effectué\n";
                    Message += "aucun post traitement défini \n";
                    Message += Resultat.toString();
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

    public void OnUpdate(View view) {
        RefreshProfile();
    }

    public void OnCancel(View view) {
        gotoMainActivity();
    }


    public void gotoMainActivity() {
        Intent intent = new Intent(context, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("AppSessionContext", AppSessionContext);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    public void RefreshProfile() {
        Name.setText(AppSessionContext.getUserMember().getName());
        Email.setText(AppSessionContext.getUserMember().getEmail());
        Password.setText(AppSessionContext.getUserMember().getHashed_pwd());
        Token.setText(AppSessionContext.getToken());
    }
}
