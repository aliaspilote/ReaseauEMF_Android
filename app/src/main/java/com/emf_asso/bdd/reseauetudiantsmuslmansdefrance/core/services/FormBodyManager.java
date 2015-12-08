package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.ContactPreference;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Curriculum;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DataContext;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Skill;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.UserMember;
import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

import java.util.List;

/**
 * Created by Omar_Desk on 02/11/2015.
 */
public class FormBodyManager {

    public static java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DataContext.dateMysqlFormat);

    private static String deuxPts = ":";
    private static Character quotes = '"';
    private static String virgule = ",";

    public static RequestBody checkmail(String mail) {
        RequestBody formBody = new FormEncodingBuilder()
                .add("action", "check_mail")
                .add("mail", mail)
                .build();
        return formBody;
    }

    public static RequestBody addUser(UserMember user) {

        Gson gson = new Gson();
        //gson.toJson

        //RequestBody formBody = new FormEncodingBuilder()
        FormEncodingBuilder formBody = new FormEncodingBuilder()
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
                .add("involvement", (user.getInvolvement() != null ? user.getInvolvement().getInvolvement_id() : ""));
        formBody =listSkillsTojson(user.getSkills(),formBody);
             // .add("listCursus", listCursusTojson(user.getCurriculum()))

        //.add("civility", user.getCivility() + "") à ajouter dans le formulaire
        //.add("dicipline", user.getDicipline().getDiscipline_id()) Disciple n'est pas implémanté ici mais dans les cursus
        //.add("niveau", "")Implémanté dans le cursus
        return formBody.build();
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
        StringBuilder lesCursusJson = new StringBuilder();
        if (listCursus != null) {
            lesCursusJson.append("{cursus:[");
            int i = 0;
            for (Curriculum c : listCursus) {
                lesCursusJson.append("{");
                lesCursusJson.append(quotes );lesCursusJson.append( "label" );lesCursusJson.append( quotes );lesCursusJson.append( deuxPts );lesCursusJson.append( quotes );lesCursusJson.append( c.getLabel() );lesCursusJson.append( quotes );lesCursusJson.append( virgule);
                lesCursusJson.append(quotes );lesCursusJson.append( "establishment" );lesCursusJson.append( quotes );lesCursusJson.append( deuxPts );lesCursusJson.append( quotes );lesCursusJson.append( c.getEstablishment() );lesCursusJson.append( quotes );lesCursusJson.append( virgule);
                lesCursusJson.append(quotes );lesCursusJson.append( "city" );lesCursusJson.append( quotes );lesCursusJson.append( deuxPts );lesCursusJson.append( quotes );lesCursusJson.append( c.getCity() );lesCursusJson.append( quotes );lesCursusJson.append( virgule);
                lesCursusJson.append(quotes );lesCursusJson.append( "degree_id" );lesCursusJson.append( quotes );lesCursusJson.append( deuxPts );lesCursusJson.append( quotes );lesCursusJson.append( c.getDegree().getDegree_id() );lesCursusJson.append( quotes );lesCursusJson.append( virgule);
                lesCursusJson.append(quotes );lesCursusJson.append( "discipline_id" );lesCursusJson.append( quotes );lesCursusJson.append( deuxPts );lesCursusJson.append( quotes );lesCursusJson.append( c.getDiscipline().getDiscipline_id() );lesCursusJson.append( quotes );lesCursusJson.append( virgule);
                lesCursusJson.append(quotes );lesCursusJson.append( "start_date" );lesCursusJson.append( quotes );lesCursusJson.append( deuxPts );lesCursusJson.append( quotes );lesCursusJson.append( sdf.format(c.getStart_date()) );lesCursusJson.append( quotes );lesCursusJson.append( virgule);
                lesCursusJson.append(quotes );lesCursusJson.append( "end_date" );lesCursusJson.append( quotes );lesCursusJson.append( deuxPts );lesCursusJson.append( quotes );lesCursusJson.append( sdf.format(c.getEnd_date()) );lesCursusJson.append( quotes );lesCursusJson.append( virgule);
                lesCursusJson.append(quotes );lesCursusJson.append( "end_date" );lesCursusJson.append( quotes );lesCursusJson.append( deuxPts );lesCursusJson.append( quotes );lesCursusJson.append( c.getId() );lesCursusJson.append( quotes);
                i++;
                lesCursusJson.append("}");
                if (i < listCursus.size())
                    lesCursusJson.append( ",");
            }
            lesCursusJson.append("],");
            lesCursusJson.append( quotes );lesCursusJson.append( "count" );lesCursusJson.append( quotes );lesCursusJson.append( deuxPts );lesCursusJson.append( quotes );lesCursusJson.append( Integer.toString(i) );lesCursusJson.append( quotes);
            lesCursusJson.append("}");
        }
        return lesCursusJson.toString();




        /*for (Curriculum c : listCursus) {
            lesCursusJson += "{";
            lesCursusJson += quotes + "label" + quotes + deuxPts + quotes + c.getLabel() + quotes + virgule;
            lesCursusJson += quotes + "establishment" + quotes + deuxPts + quotes + c.getEstablishment() + quotes + virgule;
            lesCursusJson += quotes + "city" + quotes + deuxPts + quotes + c.getCity() + quotes + virgule;
            lesCursusJson += quotes + "degree_id" + quotes + deuxPts + quotes + c.getDegree().getDegree_id() + quotes + virgule;
            lesCursusJson += quotes + "discipline_id" + quotes + deuxPts + quotes + c.getDiscipline().getDiscipline_id() + quotes + virgule;
            lesCursusJson += quotes + "start_date" + quotes + deuxPts + quotes + sdf.format(c.getStart_date()) + quotes + virgule;
            lesCursusJson += quotes + "end_date" + quotes + deuxPts + quotes + sdf.format(c.getEnd_date()) + quotes + virgule;
            lesCursusJson += quotes + "end_date" + quotes + deuxPts + quotes + c.getId() + quotes;
            i++;
            lesCursusJson += "}";
            if (i < listCursus.size())
                lesCursusJson += ",";
        }
        lesCursusJson += "],";
        lesCursusJson += quotes + "count" + quotes + deuxPts + quotes + Integer.toString(i) + quotes;
        lesCursusJson += "}";
    }
    return lesCursusJson.replace("\\", "");*/
    }

    private static String listContactPreferenceTojson(ContactPreference contectPreference) {
        String ContactPreferenceJson = "";

        return ContactPreferenceJson.replace("\\", "");
    }

    private static FormEncodingBuilder listSkillsTojson(List<Skill> listSkills,FormEncodingBuilder form) {
        String lesSkillsJson = "";
        if (lesSkillsJson != null) {
            form.add("skills_count",listSkills.size()+"" );
            int i = 0;
            for (Skill s : listSkills) {
                i++;
                form.add("skill_id_"+i,s.getSkill_id()+"" );
            }
        }
        return form;
    }
}
