package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.adaptater.DiffusionCriteriaRowContent;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DiffusionCriteria;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.ListViewInit;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.SessionWsService;

import java.util.ArrayList;

public class DiffusionCriteriasActivity extends AppCompatActivity {

    public DiffusionCriteriasActivity DiffusionCriteriaCtx = null;
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

        Button fab = (Button) findViewById(R.id.btn_add_diff_crit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiffusionCriteria dc_to_add = (DiffusionCriteria) ((Spinner) findViewById(R.id.spin_diffusion_criteria_type)).getSelectedItem();

                DiffusionCriteriaListViewValuesArr.add(dc_to_add);
                adapter.notifyDataSetChanged();
                Snackbar.make(view, "Critère \'" + dc_to_add.toString() + "\' ajouté.", Snackbar.LENGTH_LONG)
                        .setAction("Ajouter", null).show();
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

        DiffusionCriteriaCtx = this;
        ListViewInit.loadListStaticData(AppCtx);
        ListViewInit.loadCriteriaType_List_View(DiffusionCriteriaCtx);

        /******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
        //setListData();
        list = (ListView) findViewById(R.id.listview_diffusion_criterias);  // List defined in XML ( See Below )
        list.setItemsCanFocus(true);
        /**************** Create Custom Adapter *********/
        adapter = new DiffusionCriteriaRowContent(DiffusionCriteriaCtx, DiffusionCriteriaListViewValuesArr);
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
        if (mPosition >= 0) {
            DiffusionCriteria dc_to_delete = DiffusionCriteriaListViewValuesArr.get(mPosition);
            DiffusionCriteriaListViewValuesArr.remove(dc_to_delete);
            adapter.notifyDataSetChanged();
        }
        /*final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);
        Snackbar.make(viewGroup ,tempValues.getCriteria_Name() + " " +tempValues.getValue()!= null ? tempValues.getValue().toString() : ""+" supprimmé", Snackbar.LENGTH_LONG)
                .setAction("Ajouter", null).show();*/
    }


}
