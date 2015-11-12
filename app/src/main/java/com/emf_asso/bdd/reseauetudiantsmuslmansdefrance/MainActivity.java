package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.FormBodyManager;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.HttpReponse;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.ProcessInscriptionService;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.Web_Service_Controlleur;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public HttpReponse LastReponse;
    private Context context = this;
    private ProcessInscriptionService ServiceProcessInscription = new ProcessInscriptionService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //ViewModel viewModel = new ViewModel();
        //viewModel.data.set("test");
        //ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        //binding.setViewModel(viewModel);
    }

    public void ReceptionResponse(HttpReponse Rep) {
        LastReponse = Rep;
        String Message = "";

        if (!LastReponse.getSucces())
            Message = LastReponse.getExceptionText();
        else {
            switch (LastReponse.Action) {
                case "check_mail":
                    Message = LastReponse.Action + " effectué\n";
                    Message += "Contenu de la réponse : \n";
                    Message += LastReponse.getResultat().toString();
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void OnValidateForm(View  view)
    {
        afficherFormInscr();
    }

    public void OnCheckMail(View  view) throws IOException {
        Web_Service_Controlleur wb_thread = new Web_Service_Controlleur(this, FormBodyManager.checkmail(
                ((EditText) findViewById(R.id.editxt_check_mail)).getText().toString()));
        wb_thread.execute();
    }


    public void OnRegisterClick(View view) {
        Intent intent = new Intent(context, ProcessInscriptionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("ServiceInscription", ServiceProcessInscription);
        intent.putExtras(bundle);
        context.startActivity(intent);
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


