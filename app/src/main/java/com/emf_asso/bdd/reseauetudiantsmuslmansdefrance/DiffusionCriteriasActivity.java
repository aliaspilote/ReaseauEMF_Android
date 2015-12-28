package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.adaptater.DiffusionCriteriaRowContent;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DiffusionCriteria;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.ListViewInit;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.SessionWsService;

public class DiffusionCriteriasActivity extends AppCompatActivity {

    public DiffusionCriteriasActivity DiffusionCriteriaCtx = null;
    public DiffusionCriteriaRowContent adapter;
    ListView list;
    EditText ldf_name;
    TextView ldf_count;
    private SessionWsService AppCtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diffusion_criterias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button fab = (Button) findViewById(R.id.btn_add_diff_crit);
        ldf_name = (EditText) findViewById(R.id.editxt_ldf_name);
        ldf_count = (TextView) findViewById(R.id.editxt_ldf_count);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiffusionCriteria dc_to_add = (DiffusionCriteria)
                        ((Spinner) findViewById(R.id.spin_diffusion_criteria_type)).getSelectedItem();
                AppCtx.getServiceLDF().add_criteria_currentldf(new DiffusionCriteria(dc_to_add));
                saveAllItemRow();
                adapter.notifyDataSetChanged();
                Snackbar.make(view, "Critère \'" + dc_to_add.toString() + "\' ajouté.", Snackbar.LENGTH_LONG)
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
        // Set Here the current LDF
        DiffusionCriteriaCtx = this;
        ListViewInit.loadListStaticData(AppCtx);
        ListViewInit.loadCriteriaType_List_View(DiffusionCriteriaCtx);

        if (AppCtx.getServiceLDF().getCurrent_ldf().getLabel() != null)
            ldf_name.setText(AppCtx.getServiceLDF().getCurrent_ldf().getLabel());
        if (AppCtx.getServiceLDF().getCurrent_ldf().getCount() != null)
            ldf_count.setText(AppCtx.getServiceLDF().getCurrent_ldf().getCount());

        /******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
        //setListData();
        list = (ListView) findViewById(R.id.listview_diffusion_criterias);  // List defined in XML ( See Below )
        list.setItemsCanFocus(true);
        /**************** Create Custom Adapter *********/
        adapter = new DiffusionCriteriaRowContent(DiffusionCriteriaCtx, AppCtx.getServiceLDF().getCurrent_ldf().DiffusionCriteriaListViewValuesArr, AppCtx);
        list.setAdapter(adapter);

        ldf_name.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null) {
                    AppCtx.getServiceLDF().getCurrent_ldf().setLabel(s.toString());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });
    }

    /******
     * Function to set data in ArrayList
     *************/
    public void saveAllItemRow() {
        for (int i = 0; i < AppCtx.getServiceLDF().getCurrent_ldf().DiffusionCriteriaListViewValuesArr.size(); i++)
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
            Object val;
            DiffusionCriteria dc_to_save = AppCtx.getServiceLDF().getCurrent_ldf().DiffusionCriteriaListViewValuesArr.get(mPosition);
            if (dc_to_save == null)
                return;
            if (dc_to_save.isSpinner_type())
                val = ((Spinner) getViewByPosition(mPosition, list).findViewById(R.id.spin_diffusion_criteria_type)).getSelectedItem();
            else
                val = ((EditText) getViewByPosition(mPosition, list).findViewById(R.id.editxt_diffusion_criteria_value)).getText().toString();
            dc_to_save.setValue(val);
            AppCtx.getServiceLDF().update_criteria_currentldf(mPosition, dc_to_save);
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
