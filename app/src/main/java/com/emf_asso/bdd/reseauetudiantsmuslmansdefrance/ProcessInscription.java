package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Omar on 04/11/2015.
 */
public class ProcessInscription extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.processinscription_1);
        Log.d("test", getIntent().getExtras().getString("id", "0"));
    }

    void test() {
        setContentView(R.layout.processinscription_2);
    }
}
