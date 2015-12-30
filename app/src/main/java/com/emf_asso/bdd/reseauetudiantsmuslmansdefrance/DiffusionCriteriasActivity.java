package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.adaptater.DiffusionCriteriaRowContent;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DiffusionCriteria;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.ActivityConnectedWeb;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.ListViewInit;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.Messages;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.HttpReponse;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.SessionWsService;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.Web_Service_Controlleur;

import static com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.FormBodyManager.syncLDF;

public class DiffusionCriteriasActivity extends AppCompatActivity implements ActivityConnectedWeb {

    public final HttpReponse LastReponse = new HttpReponse();
    public DiffusionCriteriasActivity DiffusionCriteriaCtx = null;
    public Context context;
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


        DiffusionCriteriaCtx = this;
        context = this;

        Button add_criteria = (Button) findViewById(R.id.btn_add_diff_crit);
        ImageButton cancel_ldf = (ImageButton) findViewById(R.id.btn_cancel_current_ldf);
        ImageButton suppr_ldf = (ImageButton) findViewById(R.id.btn_suppr_current_ldf);
        ImageButton save_ldf = (ImageButton) findViewById(R.id.btn_save_current_ldf);
        add_criteria.setOnClickListener(new View.OnClickListener() {
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
        cancel_ldf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder
                        .setMessage("Annuler toutes les modifications non enregistrées ?")
                        .setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                gotoLDFActivity();
                            }
                        })
                        .setNegativeButton("Retour", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });
        suppr_ldf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder
                        .setMessage("Supprimer de la bdd cette liste de diffusion ? (pas les utisateurs)")
                        .setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                AppCtx.getServiceLDF().remove_ldf(AppCtx.getServiceLDF().getCurrent_ldf());
                                gotoLDFActivity();
                            }
                        })
                        .setNegativeButton("Retour", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });
        save_ldf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder
                        .setMessage("Sauvegarder cette liste de diffusion")
                        .setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                saveAllItemRow();
                                AppCtx.getServiceLDF().update_ldf(AppCtx.getServiceLDF().getCurrent_ldf());
                                synchronizeLDF();
                                gotoLDFActivity();
                            }
                        })
                        .setNegativeButton("Retour", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });
        ldf_name = (EditText) findViewById(R.id.editxt_ldf_name);
        ldf_count = (TextView) findViewById(R.id.editxt_ldf_count);

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

    private void synchronizeLDF() {
        Web_Service_Controlleur wb_thread;
        wb_thread = new Web_Service_Controlleur(this, syncLDF(AppCtx.getServiceLDF().getCurrent_ldf()));
        wb_thread.execute();
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
                val = ((Spinner) getViewByPosition(mPosition, list).findViewById(R.id.spin_diffusion_criteria_value)).getSelectedItem();
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

    public void gotoLDFActivity() {
        Intent intent = new Intent(DiffusionCriteriaCtx, LDFActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("AppSessionContext", AppCtx);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    public void ReceptionResponse(HttpReponse Rep) {
        LastReponse.setHttpReponse(Rep.getResultat(), Rep.getSucces(), Rep.getAction(), Rep.getDataReponse(), Rep.getExceptionText());
        String Message = "";

        if (LastReponse.getResultat() == null)
            DisplayToast(LastReponse.getExceptionText());
        else if (!LastReponse.getSucces() && LastReponse.getResultat().get("result").toString() != "true")
            DisplayToast(LastReponse.getExceptionText());
        else {
            Boolean result = false;
            String tempResultBool = LastReponse.getResultat().get("result").toString();
            if (tempResultBool.contentEquals("true"))
                result = true;

            switch (LastReponse.Action) {
                case "sync_ldf":
                    if (result) {
                        if ((LastReponse.getResultat().get("result").toString().contentEquals("true"))) {
                            Message += LastReponse.getResultat().toString();
                        } else
                            Message += Messages.error_generique;
                    } else
                        Message += LastReponse.getExceptionText();
                    break;
                default:
                    Message = LastReponse.Action + " effectué\n";
                    Message += "aucun post traitement défini \n";
                    Message += LastReponse.getResultat().toString();
                    break;
            }
            if (Message.length() > 2)
                DisplayToast(Message);
        }
    }

    public void DisplayToast(String text, int time) {
        if (time > 0)
            time = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, time);
        toast.show();
    }

    public void DisplayToast(String text) {
        DisplayToast(text, 100);
    }
}
