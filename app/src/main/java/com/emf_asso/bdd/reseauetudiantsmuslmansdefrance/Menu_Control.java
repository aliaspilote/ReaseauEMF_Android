package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.SessionWsService;

/**
 * Created by taha on 19/11/2015.
 */
public class Menu_Control extends AppCompatActivity {

    private Context context;

    private SessionWsService AppSessionContext;

    public Menu_Control(Context context) {
        this.context = context;
    }

    public void setAppSessionContext(SessionWsService appSessionContext) {
        AppSessionContext = appSessionContext;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_about_emf:
                intent = new Intent(context, AboutEmfActivity.class);
                context.startActivity(intent);
                return true;
            case R.id.action_emf_network:
                intent = new Intent(context, EmfNetworkActivity.class);
                context.startActivity(intent);
                return true;
            case R.id.action_profil:
                intent = new Intent(context, ProfilActivity.class);
                context.startActivity(intent);
                return true;
            case R.id.testprofil:
                intent = new Intent(context, UpdateCursusListActivity.class);
                context.startActivity(intent);
                return true;
            case R.id.action_admin:
                Bundle b;
                intent = new Intent(context, AdminActivity.class);
                b = new Bundle();
                b.putSerializable("AppSessionContext", AppSessionContext);
                b.putInt("p", -1);
                intent.putExtras(b);
                context.startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
            // Bundle bundle = new Bundle();
            // bundle.putSerializable("ServiceInscription", ServiceProcessInscription);
            // intent.putExtras(bundle);
        }
    }
}
