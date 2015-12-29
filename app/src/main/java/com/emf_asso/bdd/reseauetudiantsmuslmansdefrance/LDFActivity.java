package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.adaptater.LdfRowContent;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DiffusionCriteria;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DiffusionList;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.ListViewInit;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.SessionWsService;

public class LDFActivity extends AppCompatActivity {


    public LDFActivity LDFActivityCtx = null;
    public LdfRowContent adapter;
    public ListView list;
    private SessionWsService AppCtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ldf);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton Add_ldf = (FloatingActionButton) findViewById(R.id.btn_add_ldf);
        Add_ldf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ajouter une nouvelle liste", Snackbar.LENGTH_LONG)
                        .setAction("Ajouter", null).show();
            }
        });

        if (savedInstanceState != null) {
            AppCtx = (SessionWsService) savedInstanceState.getSerializable("AppSessionContext");
        }
        if (!(AppCtx != null)) {
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
        }
        AppCtx.getServiceLDF().onStart();
        loadDataLdf();

        // Set Here the current LDF
        LDFActivityCtx = this;
        ListViewInit.loadListStaticData(AppCtx);
        //ListViewInit.loadCriteriaType_List_View(LDFActivityCtx);

        /******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
        list = (ListView) findViewById(R.id.listview_ldf);  // List defined in XML ( See Below )
        list.setItemsCanFocus(true);
        /**************** Create Custom Adapter *********/
        adapter = new LdfRowContent(LDFActivityCtx, AppCtx);
        list.setAdapter(adapter);

    }

    public void loadDataLdf() {
        DiffusionList ldf1 = new DiffusionList("List 1", "1", "20");
        DiffusionList ldf2 = new DiffusionList("List 2", "2", "40");
        DiffusionList ldf3 = new DiffusionList("List 3", "3", "60");
        Object val1 = "Omar";
        Object val2 = "Latreche";
        Object val3 = "age";
        Object val4 = "90000";
        DiffusionCriteria dc1 = new DiffusionCriteria("Code postal", "zip_code", false, null);
        DiffusionCriteria dc2 = new DiffusionCriteria("Nom", "name", false, null);
        DiffusionCriteria dc3 = new DiffusionCriteria("Prénom", "firstname", false, null);
        DiffusionCriteria dc4 = new DiffusionCriteria("Age", "age", false, null);
        dc1.setValue(val1);
        dc2.setValue(val2);
        dc3.setValue(val3);
        dc4.setValue(val4);
        ldf1.DiffusionCriteriaListViewValuesArr.add(dc1);
        ldf2.DiffusionCriteriaListViewValuesArr.add(dc2);
        ldf2.DiffusionCriteriaListViewValuesArr.add(dc3);
        ldf3.DiffusionCriteriaListViewValuesArr.add(dc4);
        AppCtx.getServiceLDF().add_ldf(ldf1);
        AppCtx.getServiceLDF().add_ldf(ldf2);
        AppCtx.getServiceLDF().add_ldf(ldf3);
    }

    /******
     * Function to set data in ArrayList
     *************/
    public void saveAllItemRow() {
        for (int i = 0; i < AppCtx.getServiceLDF().size_ldf(); i++)
            onItemClickSave(i);
    }

    public void onItemClickDelete(int mPosition) {
        if (mPosition >= 0) {
            DiffusionCriteria dc_to_delete = AppCtx.getServiceLDF().getCurrent_ldf().DiffusionCriteriaListViewValuesArr.get(mPosition);
            AppCtx.getServiceLDF().remove_criteria_currentldf(dc_to_delete);
            adapter.notifyDataSetChanged();
        }
    }

    public void onItemClickSave(int mPosition) {
        if (mPosition >= 0) {
            Object val;/*
            DiffusionCriteria dc_to_save = AppCtx.getServiceLDF().getCurrent_ldf().DiffusionCriteriaListViewValuesArr.get(mPosition);
            if (dc_to_save == null)
                return;
            if (dc_to_save.isSpinner_type())
                val = ((Spinner) getViewByPosition(mPosition, list).findViewById(R.id.spin_diffusion_criteria_type)).getSelectedItem();
            else
                val = ((EditText) getViewByPosition(mPosition, list).findViewById(R.id.editxt_diffusion_criteria_value)).getText().toString();
            dc_to_save.setValue(val);
            AppCtx.getServiceLDF().update_criteria_currentldf(mPosition, dc_to_save);*/
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("AppSessionContext", AppCtx);
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

}
