package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.adaptater.DiffusionCriteriaRowContent;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DiffusionCriteria;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.SessionWsService;

import java.util.ArrayList;

public class DiffusionCriteriasActivity extends AppCompatActivity {

    public DiffusionCriteriasActivity DiffusionCriteriaRowContentListView = null;
    public ArrayList<DiffusionCriteria> DiffusionCriteriaListViewValuesArr = new ArrayList<DiffusionCriteria>();
    ListView list;
    DiffusionCriteriaRowContent adapter;
    private SessionWsService AppCtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diffusion_criterias);
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

        Intent intent = this.getIntent();
        if (intent.getSerializableExtra("AppSessionContext") != null) {
            AppCtx = (SessionWsService) intent.getSerializableExtra("AppSessionContext");
        }
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            if (bundle.getSerializable("AppSessionContext") != null) {
                AppCtx = (SessionWsService) bundle.getSerializable("AppSessionContext");
            }
        }

        DiffusionCriteriaRowContentListView = this;

        /******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
        setListData();

        //Resources res =getResources();
        list = (ListView) findViewById(R.id.listview_diffusion_criterias);  // List defined in XML ( See Below )

        /**************** Create Custom Adapter *********/
        adapter = new DiffusionCriteriaRowContent(DiffusionCriteriaRowContentListView, DiffusionCriteriaListViewValuesArr);
        list.setAdapter(adapter);
    }

    /******
     * Function to set data in ArrayList
     *************/
    public void setListData() {
        DiffusionCriteria dc1 = new DiffusionCriteria();
        dc1.setCriteria_Name("Nom");
        DiffusionCriteriaListViewValuesArr.add(dc1);
        DiffusionCriteria dc2 = new DiffusionCriteria();
        dc2.setCriteria_Name("CP");
        DiffusionCriteriaListViewValuesArr.add(dc2);
        DiffusionCriteria dc3 = new DiffusionCriteria();
        dc3.setCriteria_Name("Age");
        dc3.setSpinner_type(true);
        dc3.setValuesTest();
        DiffusionCriteriaListViewValuesArr.add(dc3);
    }

    public void onItemClick(int mPosition) {
        DiffusionCriteria tempValues = (DiffusionCriteria) DiffusionCriteriaListViewValuesArr.get(mPosition);

        Toast.makeText(DiffusionCriteriaRowContentListView,
                "" + tempValues.getCriteria_Name() + " " + tempValues.getValue().toString(),
                Toast.LENGTH_LONG)
                .show();
    }


}
