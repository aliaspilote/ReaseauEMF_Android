package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.R;

/**
 * Created by taha on 13/12/2015.
 */
public class MenuDrawer extends AppCompatActivity {
    public MenuDrawer() {
    }

    public void InitStubs() {
        ((ViewStub) findViewById(R.id.stub_pup0)).inflate();
        ((ViewStub) findViewById(R.id.stub_pup1)).inflate();
        ((ViewStub) findViewById(R.id.stub_pup2)).inflate();
        ((ViewStub) findViewById(R.id.stub_pup3)).inflate();
        ((ViewStub) findViewById(R.id.stub_pup4)).inflate();

        findViewById(R.id.stub_Inflated_pup1).setVisibility(View.GONE);
        findViewById(R.id.stub_Inflated_pup2).setVisibility(View.GONE);
        findViewById(R.id.stub_Inflated_pup3).setVisibility(View.GONE);
        findViewById(R.id.stub_Inflated_pup4).setVisibility(View.GONE);
    }
}
