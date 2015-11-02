package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public Web_Service_Controlleur wb_thread = new Web_Service_Controlleur(this);
    public String reponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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

    public Object var;
    public void OnCheckMail(View  view) throws IOException {

        var = wb_thread.execute("action=check_mail&var=" + ((EditText) findViewById(R.id.editxt_check_mail)).getText());

    }

    public void OnControl(View view)
    {
        try {
            reponse = ((Web_Service_Controlleur) var).get();
        }
        catch (Exception e)
        {

        }
        new AlertDialog.Builder(this)
                .setTitle("Compte déjà inscrit ?")
                .setMessage(reponse)
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
