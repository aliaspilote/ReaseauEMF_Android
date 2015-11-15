package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.R;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DegreeStudy;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Discipline;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Involvement;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Section;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Skill;

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

    public ListViewInit() {

    }

    public ListViewInit(Activity view, Activity ctx) {
        InitListSectionInvolvement(view, ctx);
        InitListDegreeStudyDiscipline(view, ctx);
        InitListSkill(view, ctx);
    }

    public void InitListSectionInvolvement(Activity view, Activity ctx) {
        involvementsList = new ArrayList<>();
        involvementsList.add(new Involvement("Membre Actifff", "Membre qui participe aux réunior", "1"));
        involvementsList.add(new Involvement("Membre Cool", "Il est cool", "2"));
        involvementsList.add(new Involvement("Super Actif", "Il est hyper actif", "3"));
        sectionList = new ArrayList<>();
        sectionList.add(new Section("Belfort"));
        sectionList.add(new Section("Paris"));
        sectionList.add(new Section("Lyon"));


        Spinner listViewInvolvement = (Spinner) view.findViewById(R.id.spinner_involvement);
        ArrayAdapter<Involvement> adapter_involvement = new ArrayAdapter<Involvement>(ctx,
                android.R.layout.simple_list_item_1, involvementsList);
        listViewInvolvement.setAdapter(adapter_involvement);

        Spinner listViewSection = (Spinner) view.findViewById(R.id.spinner_section);
        ArrayAdapter<Section> adapter_section = new ArrayAdapter<Section>(ctx,
                android.R.layout.simple_list_item_1, sectionList);
        listViewSection.setAdapter(adapter_section);
    }


    public void InitListDegreeStudyDiscipline(Activity view, Activity ctx) {

        degreeStudyList = new ArrayList<>();
        degreeStudyList.add(new DegreeStudy("Bac", 0, 0));
        degreeStudyList.add(new DegreeStudy("Bac +2", 1, 1));
        degreeStudyList.add(new DegreeStudy("Bac +5", 2, 2));

        disciplineList = new ArrayList<>();
        disciplineList.add(new Discipline("recherche", "recherche", 0));
        disciplineList.add(new Discipline("info", "recherche", 1));
        disciplineList.add(new Discipline("recherche", "recherche", 2));


        Spinner listViewDegreeStudy = (Spinner) view.findViewById(R.id.spinner_degree_study);
        ArrayAdapter<DegreeStudy> adapter_degree_study = new ArrayAdapter<DegreeStudy>(ctx,
                android.R.layout.simple_list_item_1, degreeStudyList);
        listViewDegreeStudy.setAdapter(adapter_degree_study);

        Spinner listViewDiscipline = (Spinner) view.findViewById(R.id.spinner_discipline);
        ArrayAdapter<Discipline> adapter_discipline = new ArrayAdapter<Discipline>(ctx,
                android.R.layout.simple_list_item_1, disciplineList);
        listViewDiscipline.setAdapter(adapter_discipline);
    }

    public void InitListSkill(Activity view, Activity ctx) {


        skillList = new ArrayList<>();
        skillList.add(new Skill("informatique", "recherche", 0));
        skillList.add(new Skill("santé", "recherche", 1));
        skillList.add(new Skill("énérgie", "recherche", 2));
        skillList.add(new Skill("informatique", "recherche", 0));
        skillList.add(new Skill("santé", "recherche", 1));
        skillList.add(new Skill("énérgie", "recherche", 2));
        skillList.add(new Skill("informatique", "recherche", 0));
        skillList.add(new Skill("santé", "recherche", 1));
        skillList.add(new Skill("énérgie", "recherche", 2));
        skillList.add(new Skill("informatique", "recherche", 0));
        skillList.add(new Skill("santé", "recherche", 1));
        skillList.add(new Skill("énérgie", "recherche", 2));
        String[] GENRES = new String[]{
                "Action", "Adventure", "Animation", "Children", "Comedy", "Documentary", "Drama",
                "Foreign", "History", "Independent", "Romance", "Sci-Fi", "Television", "Thriller"
        };


        ListView listViewSkill = (ListView) view.findViewById(R.id.listview_skill);
        ArrayAdapter<String> adapter_skill = new ArrayAdapter<String>(ctx,
                android.R.layout.simple_list_item_multiple_choice, GENRES);
        listViewSkill.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listViewSkill.setAdapter(adapter_skill);


    }
}
