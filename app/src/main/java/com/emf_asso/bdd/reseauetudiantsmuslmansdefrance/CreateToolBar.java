package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toolbar;

/**
 * Created by taha on 19/11/2015.
 */
public class CreateToolBar extends AppCompatActivity {

    public CreateToolBar() {
    }

    public void createTB(Toolbar toolbar) {

        TextView b = new TextView(this);
        b.setText("aaaa");
        toolbar.addView(b);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("ddddddsff");

    }
}
