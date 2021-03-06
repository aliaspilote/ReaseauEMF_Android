package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other;

import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.R;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.adaptater.DiffusionCriteriaRowContent;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Curriculum;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DegreeStudy;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DiffusionCriteria;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Discipline;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Involvement;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Section;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Skill;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.SessionWsService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar_Desk on 14/11/2015.
 */
public class ListViewInit {


    public static List<Involvement> involvementsList;
    public static List<Section> sectionList;
    public static List<DegreeStudy> degreeStudyList;
    public static List<Discipline> disciplineList;
    public static List<Skill> skillList;
    public static List<Curriculum> cursusList;
    public static ArrayAdapter<Involvement> adapter_involvement;
    public static ArrayAdapter<Section> adapter_section;
    public static ArrayAdapter<Skill> adapter_skill;
    public static ArrayAdapter<DegreeStudy> adapter_degree_study;
    public static ArrayAdapter<Discipline> adapter_discipline;
    public static DiffusionCriteriaRowContent DiffusionCriteriaAdapter;
    public static ArrayList<DiffusionCriteria> DiffusionCriteriaListViewValuesArr;
    public static List<DiffusionCriteria> CriteriaListViewTypeArr;
    public static ArrayAdapter<DiffusionCriteria> CriteriListTypeaAdapter;
    public static boolean isloaded = false;
    public static boolean isDataloaded = false;

    public ListViewInit() {
    }


    public static void PopulateCriteriasListType() {
        CriteriaListViewTypeArr = new ArrayList<>();

        CriteriaListViewTypeArr.add(new DiffusionCriteria("Nom", "nom", false, null));
        CriteriaListViewTypeArr.add(new DiffusionCriteria("Prenom", "prenom", false, null));
        CriteriaListViewTypeArr.add(new DiffusionCriteria("Telephone", "phone", false, null));
        CriteriaListViewTypeArr.add(new DiffusionCriteria("Code postal", "cp", false, null));
        CriteriaListViewTypeArr.add(new DiffusionCriteria("Ville", "ville", false, null));
        CriteriaListViewTypeArr.add(new DiffusionCriteria("Email", "mail", false, null));
        CriteriaListViewTypeArr.add(new DiffusionCriteria("Etabli./Cursus", "etabl", false, null));
        CriteriaListViewTypeArr.add(new DiffusionCriteria("Diplome/Cursus", "dipl", false, null));
        CriteriaListViewTypeArr.add(new DiffusionCriteria("Ville/Cursus", "ville_c", false, null));
        CriteriaListViewTypeArr.add(new DiffusionCriteria("CodePo./Cursus", "cp_c", false, null));
        CriteriaListViewTypeArr.add(new DiffusionCriteria("Niveau/Cursus", "bac+", true, new ArrayList<Object>(degreeStudyList)));
        CriteriaListViewTypeArr.add(new DiffusionCriteria("Engagement", "enga", true, new ArrayList<Object>(involvementsList)));
        CriteriaListViewTypeArr.add(new DiffusionCriteria("Section", "section", true, new ArrayList<Object>(sectionList)));
        CriteriaListViewTypeArr.add(new DiffusionCriteria("Discipline", "discip", true, new ArrayList<Object>(disciplineList)));
        CriteriaListViewTypeArr.add(new DiffusionCriteria("Competance", "skill", true, new ArrayList<Object>(skillList)));

    }

    public static void loadCriteriaType_List_View(Activity ctx) {


        Spinner listViewDiffCrit = (Spinner) ctx.findViewById(R.id.spin_diffusion_criteria_type);

        CriteriListTypeaAdapter = new ArrayAdapter<DiffusionCriteria>(ctx, android.R.layout.simple_list_item_1, CriteriaListViewTypeArr);

        listViewDiffCrit.setAdapter(CriteriListTypeaAdapter);
    }

    public static void loadListStaticPI(Activity ctx, SessionWsService AppContext) {
        loadListStaticData(AppContext);
        loadListStaticPI_View(ctx);
    }

    public static void loadListStaticPI_View(Activity ctx) {
        Spinner listViewInvolvement = (Spinner) ctx.findViewById(R.id.spinner_involvement);
        Spinner listViewSection = (Spinner) ctx.findViewById(R.id.spinner_section);
        ListView listViewSkill = (ListView) ctx.findViewById(R.id.listview_skill);
        adapter_involvement =
                new ArrayAdapter<Involvement>(ctx, android.R.layout.simple_list_item_1, involvementsList);
        adapter_section =
                new ArrayAdapter<Section>(ctx, android.R.layout.simple_list_item_1, sectionList);
        adapter_skill =
                new ArrayAdapter<Skill>(ctx, android.R.layout.simple_list_item_multiple_choice, skillList);
        listViewInvolvement.setAdapter(adapter_involvement);
        listViewSection.setAdapter(adapter_section);
        listViewSkill.setAdapter(adapter_skill);
        listViewSkill.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    public static void loadListStaticData(SessionWsService AppContext) {
        if (!isDataloaded) {
            involvementsList = AppContext.getDataContext().involvementsList;
            sectionList = AppContext.getDataContext().sectionList;
            degreeStudyList = AppContext.getDataContext().degreeStudyList;
            disciplineList = AppContext.getDataContext().disciplineList;
            skillList = AppContext.getDataContext().skillList;
            isloaded = true;
        }
    }

    public static void loadEmptyCursusList_View(View view, Activity ctx) {
        if (ctx != null) {
            Spinner listViewDegreeStudy = (Spinner) view.findViewById(R.id.spinner_degree_study);
            Spinner listViewDiscipline = (Spinner) view.findViewById(R.id.spinner_discipline);

            adapter_degree_study = new ArrayAdapter<DegreeStudy>(ctx, android.R.layout.simple_list_item_1, degreeStudyList);
            adapter_discipline = new ArrayAdapter<Discipline>(ctx, android.R.layout.simple_list_item_1, disciplineList);

            listViewDegreeStudy.setAdapter(adapter_degree_study);
            listViewDiscipline.setAdapter(adapter_discipline);
        }
    }

    public static int getPositionByToStringMatch(List<?> b, Object a) {
        int i = 0;
        int d = -1;
        for (Object c : b) {
            if (a.toString().equals(c.toString()))
                d = i;
            i++;
        }
        return d;
    }
}
