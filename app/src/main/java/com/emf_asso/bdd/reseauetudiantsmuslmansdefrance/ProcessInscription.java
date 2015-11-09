package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.app.Activity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;

/**
 * Created by Omar on 04/11/2015.
 */
public class ProcessInscription extends Activity {

    View content_PI_view;
    Layout content_pi_1_layout;
    Layout content_pi_2_layout;
    Layout content_pi_3_layout;
    Layout content_pi_41_layout;
    Layout content_pi_42_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processinscription);
        Log.d("test", getIntent().getExtras().getString("id", "0"));

        ViewStub stub = (ViewStub) findViewById(R.id.stub_pi1);
        stub.inflate();
    }

    public void switch1_2(View view) {
        ViewStub stub = (ViewStub) findViewById(R.id.stub_pi2);
        stub.inflate();
    }
}
