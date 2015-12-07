package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.ContactPreference;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Curriculum;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DataContext;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Skill;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.UserMember;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

import java.util.List;

/**
 * Created by Omar_Desk on 02/11/2015.
 */
public class FormBodyManager {

    public static java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DataContext.dateMysqlFormat);

    public static RequestBody checkmail(String mail) {
        RequestBody formBody = new FormEncodingBuilder()
                .add("action", "check_mail")
                .add("mail", mail)
                .build();
        return formBody;
    }

    public static RequestBody addUser(UserMember user) {

        RequestBody formBody = new FormEncodingBuilder()
                .add("action", "try")
                .add("mail", user.getEmail() + "")
                .add("name", user.getName() + "")
                .add("firstname", user.getFirstname() + "")
                .add("zip_code", user.getZip_code() + "")
                .add("city", user.getCity() + "")
                .add("section", (user.getSection() != null ? user.getSection().getSection_id() : ""))
                .add("phone", user.getPhone() + "")
                .add("hashed_pwd", user.getHashed_pwd() + "")
                .add("birth_date", (user.getBirth_date() != null ? sdf.format(user.getBirth_date()) : ""))
                .add("registration_date", (user.getBirth_date() != null ? sdf.format(user.getRegistration_date()) : ""))
                .add("involvement", (user.getInvolvement() != null ? user.getInvolvement().getInvolvement_id() : ""))
                .add("skills", listSkillsTojson(user.getSkills()))
                .add("listCursus", listCursusTojson(user.getCurriculum()))
                .build();
        //.add("civility", user.getCivility() + "") à ajouter dans le formulaire
        //.add("dicipline", user.getDicipline().getDiscipline_id()) Disciple n'est pas implémanté ici mais dans les cursus
        //.add("niveau", "")Implémanté dans le cursus
        return formBody;
    }

    public static RequestBody auth(String mail, String password) {
        RequestBody formBody = new FormEncodingBuilder()
                .add("action", "auth")
                .add("mail", mail + "")
                .add("hashed_pwd", password + "")
                .build();
        return formBody;
    }

    public static RequestBody getAction(String actionName) {
        RequestBody formBody = new FormEncodingBuilder()
                .add("action", actionName)
                .build();
        return formBody;
    }

    private static String listCursusTojson(List<Curriculum> listCursus) {
        String lesCursusJson = "";
        if (listCursus != null) {
            lesCursusJson = "{\n" + "  \"cursus\":[";
            int i = 0;
            for (Curriculum c : listCursus) {
                lesCursusJson += "\n" + "    {";
                lesCursusJson += '"' + "label" + '"' + ':' + '"' + c.getLabel() + '"' + ',' + "\n";
                lesCursusJson += '"' + "establishment" + '"' + ':' + '"' + c.getEstablishment() + '"' + ',' + "\n";
                lesCursusJson += '"' + "city" + '"' + ':' + '"' + c.getCity() + '"' + ',' + "\n";
                lesCursusJson += '"' + "degree_id" + '"' + ':' + '"' + c.getDegree().getDegree_id() + '"' + ',' + "\n";
                lesCursusJson += '"' + "discipline_id" + '"' + ':' + '"' + c.getDiscipline().getDiscipline_id() + '"' + ',' + "\n";
                lesCursusJson += '"' + "start_date" + '"' + ':' + '"' + sdf.format(c.getStart_date()) + '"' + ',' + "\n";
                lesCursusJson += '"' + "end_date" + '"' + ':' + '"' + sdf.format(c.getEnd_date()) + '"' + ',' + "\n";
                lesCursusJson += '"' + "end_date" + '"' + ':' + '"' + c.getId() + '"' + "\n";
                i++;
                lesCursusJson += "\n" + "    }";
                if (i < listCursus.size())
                    lesCursusJson += ",";
            }
            lesCursusJson += "\n" + "  ]" + ",";
            lesCursusJson += '"' + "count" + '"' + ':' + '"' + Integer.toString(i) + '"' + "\n";
            lesCursusJson += "\n" + "}";
        }
        return lesCursusJson;
    }

    private static String listContactPreferenceTojson(ContactPreference contectPreference) {
        String ContactPreferenceJson = "";

        return ContactPreferenceJson;
    }

    private static String listSkillsTojson(List<Skill> listSkills) {
        String lesSkillsJson = "";
        if (lesSkillsJson != null) {
            lesSkillsJson = "{\n" + "  \"cursus\":[";
            int i = 0;
            for (Skill s : listSkills) {
                lesSkillsJson += "\n" + "    {";
                lesSkillsJson += '"' + "skill_id" + '"' + ':' + '"' + s.getSkill_id() + '"' + "\n";
                i++;
                lesSkillsJson += "\n" + "    }";
                if (i < listSkills.size())
                    lesSkillsJson += ",";
            }
            lesSkillsJson += "\n" + "  ]" + ",";
            lesSkillsJson += '"' + "count" + '"' + ':' + '"' + Integer.toString(i) + '"' + "\n";
            lesSkillsJson += "\n" + "}";
        }
        return lesSkillsJson;
    }
}
