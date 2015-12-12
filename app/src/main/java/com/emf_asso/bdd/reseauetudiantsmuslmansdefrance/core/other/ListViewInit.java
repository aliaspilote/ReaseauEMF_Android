package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other;

import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.R;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Curriculum;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DegreeStudy;
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

    public ListViewInit() {


    }

    public ListViewInit(Activity ctx, SessionWsService AppContext) {
        loadListStaticPI_View(ctx, AppContext);
    }

    public static List<Curriculum> InitListCursus() {
        cursusList = new ArrayList<>();
        /*cursusList.add(new Curriculum("Master MathInfo", new Date(2014, 5, 9), new Date(2015, 6, 8),
                new Discipline("Mathematique", "Recherche algorithmique", 3),
                "UFR MATH", "Strabourg",
                new DegreeStudy("Bac +5", 5, 3)));
        cursusList.add(new Curriculum("Licence LEA", new Date(2014, 5, 9), new Date(2015, 6, 8),
                new Discipline("Anglais", "Recherche algorithmique", 2),
                "Université Louis Paster", "Marseille",
                new DegreeStudy("Bac +3", 3, 2)));
        cursusList.add(new Curriculum("BTS SIO", new Date(2014, 5, 9), new Date(2015, 6, 8),
                new Discipline("Informatique", "Recherche algorithmique", 1),
                "UTBM", "Belfort",
                new DegreeStudy("Bac +1", 1, 1)));*/
        return cursusList;
    }

    public static void loadListStaticPI_View(Activity ctx, SessionWsService AppContext) {

        involvementsList = AppContext.getDataContext().involvementsList;
        sectionList = AppContext.getDataContext().sectionList;
        degreeStudyList = AppContext.getDataContext().degreeStudyList;
        disciplineList = AppContext.getDataContext().disciplineList;
        skillList = AppContext.getDataContext().skillList;

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

    //adapter.getPosition(compareValue)
    public static void loadListStaticCursus_View(View view, Activity ctx, SessionWsService appSessionContext) {
        // TO DO For test :
        /*if (appSessionContext != null) {
            if (appSessionContext.getServiceProcessInscription().getInscription().getUser().getCurriculum() != null)
                appSessionContext.getServiceProcessInscription().getInscription().getUser().getCurriculum().clear();
            appSessionContext.getServiceProcessInscription().getInscription().getUser().setCursuses(InitListCursus());
        }*/
        if (ctx != null) {
            Spinner listViewDegreeStudy = (Spinner) view.findViewById(R.id.spinner_degree_study);
            Spinner listViewDiscipline = (Spinner) view.findViewById(R.id.spinner_discipline);

            adapter_degree_study = new ArrayAdapter<DegreeStudy>(ctx, android.R.layout.simple_list_item_1, degreeStudyList);
            adapter_discipline = new ArrayAdapter<Discipline>(ctx, android.R.layout.simple_list_item_1, disciplineList);

            listViewDegreeStudy.setAdapter(adapter_degree_study);
            listViewDiscipline.setAdapter(adapter_discipline);
        }

    }
}
