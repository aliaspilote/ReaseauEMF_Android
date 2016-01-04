package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.adaptater.CursusContent;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Curriculum;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DataContext;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DegreeStudy;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Discipline;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.CreateDate;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.CustomDatePickerDialog;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.SessionWsService;

/**
 * An activity representing a single Cursus detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link CursusListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link CursusDetailFragment}.
 */
public class CursusDetailActivity extends AppCompatActivity {

    public CreateDate start_curriculum_date;
    public CreateDate end_curriculum_date;
    private String current_ARG_ITEM_ID;
    private CursusDetailFragment fragment;
    private Curriculum currentCursus;
    private java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DataContext.dateDisplayFormat);
    private SessionWsService AppCtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursus_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        Intent intent = this.getIntent();
        if (intent.getSerializableExtra("AppSessionContext") != null)
            AppCtx = (SessionWsService) intent.getSerializableExtra("AppSessionContext");
        else
            AppCtx = new SessionWsService();

        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            current_ARG_ITEM_ID = getIntent().getStringExtra(CursusDetailFragment.ARG_ITEM_ID);
            Bundle arguments = new Bundle();
            arguments.putString(CursusDetailFragment.ARG_ITEM_ID, current_ARG_ITEM_ID);
            fragment = new CursusDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.cursus_detail_container, fragment)
                    .commit();
        }

        Button deleteBtn = (Button) findViewById(R.id.btn_delete);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCursus(current_ARG_ITEM_ID);
                backToListCursus();
            }
        });

        Button saveBtn = (Button) findViewById(R.id.btn_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCursus(current_ARG_ITEM_ID);
                backToListCursus();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            backToListCursus();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void backToListCursus() {
        Intent intent = new Intent(this, CursusListActivity.class);
        intent.putExtra("AppSessionContext", AppCtx);
        NavUtils.navigateUpTo(this, intent);
    }

    public void deleteCursus(String current_ARG_ITEM_ID) {
        CursusContent.removeItem(current_ARG_ITEM_ID);
        if (AppCtx.inProfileView)
            AppCtx.getUserMember().setCurriculum(CursusContent.pullCursusList());
        else
            AppCtx.getServiceProcessInscription().getInscription().getUser().setCurriculum(CursusContent.pullCursusList());
    }

    public void saveCursus(String current_ARG_ITEM_ID) {
        currentCursus = CursusContent.ITEM_MAP.get(current_ARG_ITEM_ID);

        String label_diploma = ((TextView) fragment.getView().findViewById(R.id.cursus_editxt_entiled_diploma)).getText().toString();
        if (label_diploma != null)
            currentCursus.setLabel(label_diploma);
        String label_establi = ((TextView) fragment.getView().findViewById(R.id.cursus_editxt_establishment)).getText().toString();
        if (label_establi != null)
            currentCursus.setEstablishment(label_establi);

        Object discipline_selected = ((Spinner) fragment.getView().findViewById(R.id.spinner_discipline)).getSelectedItem();
        if (discipline_selected != null)
            currentCursus.setDiscipline((Discipline) discipline_selected);

        Object degree_study_selected = ((Spinner) fragment.getView().findViewById(R.id.spinner_degree_study)).getSelectedItem();
        if (degree_study_selected != null)
            currentCursus.setDegree((DegreeStudy) degree_study_selected);

        String cursus_city = ((TextView) fragment.getView().findViewById(R.id.cursus_editxt_city_study)).getText().toString();
        if (cursus_city != null)
            currentCursus.setCity(cursus_city);

        try {
            String date_begin = ((TextView) fragment.getView().findViewById(R.id.editxt_date_begin)).getText().toString();
            if (date_begin != null)
                currentCursus.setStart_date(sdf.parse(date_begin));

            String date_end = ((TextView) fragment.getView().findViewById(R.id.editxt_date_end)).getText().toString();
            if (date_end != null)
                currentCursus.setEnd_date(sdf.parse(date_end));
        } catch (Exception e) {
            currentCursus.setEnd_date(null);
            currentCursus.setStart_date(null);
        }
        if (AppCtx.inProfileView)
            AppCtx.getUserMember().setCurriculum(CursusContent.pullCursusList());
        else
            AppCtx.getServiceProcessInscription().getInscription().getUser().setCurriculum(CursusContent.pullCursusList());

    }

    public void saveCursus(Curriculum cursus) {
        CursusContent.updateItem(cursus);
    }


    public void onChooseCurriculumDateBegin(View view) {
        showDate(start_curriculum_date, R.id.icon_choose_curriculumDateBegin, R.id.editxt_date_begin);

    }

    public void onChooseCurriculumDateEnd(View view) {
        showDate(end_curriculum_date, R.id.icon_choose_birthday, R.id.editxt_date_end);

    }

    public void showDate(CreateDate cr, int img, int txt) {
        cr = new CreateDate();
        cr.initDate();
        cr.setIb((ImageButton) findViewById(img));
        cr.setEt((EditText) findViewById(txt));
        CustomDatePickerDialog dp = new CustomDatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog,
                cr.getDatePickerListener(),
                cr.getYear(),
                cr.getMonth(),
                cr.getDay());

        DatePickerDialog obj = dp.getPicker();

        obj.show();

    }
}
