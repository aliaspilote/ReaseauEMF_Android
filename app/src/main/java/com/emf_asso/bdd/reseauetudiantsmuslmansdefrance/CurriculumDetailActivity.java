package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DataContext;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DegreeStudy;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Discipline;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.CreateDate;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.CustomDatePickerDialog;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.SessionWsService;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.dummy.DummyContent;

/**
 * An activity representing a single Curriculum detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link CurriculumListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link CurriculumDetailFragment}.
 */
public class CurriculumDetailActivity extends AppCompatActivity {
    public CreateDate start_curriculum_date;
    public CreateDate end_curriculum_date;
    public Context context;
    public SessionWsService AppSessionContext;
    CurriculumDetailFragment fragment;
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DataContext.dateDisplayFormat);
    //SimpleDateFormat dateFormatter = new SimpleDateFormat(DataContext.dateDisplayFormat, Locale.FRANCE);
    private DummyContent.DummyItem selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculum_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        context = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putString(CurriculumDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(CurriculumDetailFragment.ARG_ITEM_ID));
            fragment = new CurriculumDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.curriculum_detail_container, fragment)
                    .commit();
        }


        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            if (bundle.getSerializable("AppSessionContext") != null) {
                AppSessionContext = (SessionWsService) bundle.getSerializable("AppSessionContext");
            } else
                AppSessionContext = new SessionWsService();

            if (bundle.getSerializable("selectedItem") != null) {
                selectedItem = (DummyContent.DummyItem) bundle.getSerializable("selectedItem");
            } else
                selectedItem = new DummyContent.DummyItem();
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

    public void OnSave(View view) {
        Object discipline_selected = ((Spinner) fragment.getView().findViewById(R.id.spinner_discipline)).getSelectedItem();
        if (discipline_selected != null)
            selectedItem.Cursus.setDiscipline((Discipline) discipline_selected);

        Object degree_study_selected = ((Spinner) fragment.getView().findViewById(R.id.spinner_degree_study)).getSelectedItem();
        if (degree_study_selected != null)
            selectedItem.Cursus.setDegree((DegreeStudy) degree_study_selected);

        String label_diploma = ((TextView) fragment.getView().findViewById(R.id.cursus_editxt_entiled_diploma)).getText().toString();
        if (label_diploma != null) {
            selectedItem.Cursus.setLabel(label_diploma);
            selectedItem.content = label_diploma;
        }

        String cursus_city = ((TextView) fragment.getView().findViewById(R.id.cursus_editxt_city_study)).getText().toString();
        if (cursus_city != null)
            selectedItem.Cursus.setCity(cursus_city);

        String establi = ((TextView) fragment.getView().findViewById(R.id.cursus_editxt_establishment)).getText().toString();
        if (establi != null)
            selectedItem.Cursus.setEstablishment(establi);

        try {
            String date_begin = ((TextView) fragment.getView().findViewById(R.id.editxt_date_begin)).getText().toString();
            if (date_begin != null)
                selectedItem.Cursus.setStart_date(sdf.parse(date_begin));

            String date_end = ((TextView) fragment.getView().findViewById(R.id.editxt_date_end)).getText().toString();
            if (date_end != null)
                selectedItem.Cursus.setEnd_date(sdf.parse(date_end));
        } catch (Exception e) {
            selectedItem.Cursus.setEnd_date(null);
            selectedItem.Cursus.setStart_date(null);
        }

        DisplayToast(selectedItem.Cursus.getLabel() + " ajouter");


        AppSessionContext.getServiceProcessInscription().getInscription().getUser().getCurriculum().add(selectedItem.Cursus);
        Intent intent = new Intent(this, CurriculumListActivity.class);
        intent.putExtra("AppContextFromDetails", AppSessionContext);
        NavUtils.navigateUpTo(this, intent);
    }

    public void OnDelete(View view) {
        DummyContent.ITEM_MAP.remove(selectedItem.id);
        DummyContent.ITEMS.remove(selectedItem);
        DisplayToast(selectedItem.Cursus.getLabel() + " supprimé");

        AppSessionContext.getServiceProcessInscription().getInscription().getUser().getCurriculum().remove(selectedItem.Cursus);

        Intent intent = new Intent(this, CurriculumListActivity.class);
        intent.putExtra("AppContextFromDetails", AppSessionContext);
        NavUtils.navigateUpTo(this, intent);
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
            Intent intent = new Intent(this, CurriculumListActivity.class);
            intent.putExtra("AppContextFromDetails", AppSessionContext);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }

        super.getIntent().putExtra("AppContextFromDetails", AppSessionContext);
        return super.onOptionsItemSelected(item);
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
