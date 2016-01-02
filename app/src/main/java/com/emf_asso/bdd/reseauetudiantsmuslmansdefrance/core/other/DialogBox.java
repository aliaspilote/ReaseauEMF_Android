package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.SessionWsService;

/**
 * Created by taha on 29/12/2015.
 */
public class DialogBox {
    public Boolean kill = false;
    public Boolean disable = false;
    private Context context;
    private Intent intent;
    private SessionWsService AppCtx;

    public DialogBox() {
    }

    public DialogBox(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public Boolean getKill() {
        return kill;
    }

    public void setKill(Boolean kill) {
        this.kill = kill;
    }

    public SessionWsService getAppCtx() {
        return AppCtx;
    }

    public void setAppCtx(SessionWsService appCtx) {
        AppCtx = appCtx;
    }

    public void createDialogBox(String title, String msg) {


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (disable == true) {
                            AppCtx.setDisableAccount();
                            AppCtx.killSession();

                        }
                        if (kill == true)
                            AppCtx.killSession();
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }


    public void DisplayToast(String text, int time) {
        if (time > 0)
            time = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, time);
        toast.show();
    }
}
