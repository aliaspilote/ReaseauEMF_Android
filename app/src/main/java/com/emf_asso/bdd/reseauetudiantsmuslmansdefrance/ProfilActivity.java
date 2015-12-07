package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Involvement;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Section;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Skill;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.UserMember;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Taha on 20/11/2015.
 */
public class ProfilActivity extends AppCompatActivity {

    private UserMember usermember;
    private Context context = this;
    public Menu_Control menucontrol = new Menu_Control(context);
    private Activity activity = this;

    public UserMember getUsermember() {
        return usermember;
    }

    public void setUsermember(UserMember usermember) {
        this.usermember = usermember;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ImageListener();

        // juste pour le test
        UserMember usertemp = new UserMember();
        usertemp.setCity("Fes");
        usertemp.setFirstname("Taha");
        usertemp.setName("Merrika");
        usertemp.setPhone("9999999999");
        usertemp.setBirth_date(new Date(1991, 06, 01));
        Section sec = new Section();
        sec.setLabel("Belfort");
        usertemp.setSection(sec);
        Involvement inv = new Involvement();
        inv.setLabel("Actif");
        usertemp.setInvolvement(inv);
        List<Skill> listeskills = new ArrayList<Skill>();
        listeskills.add(new Skill("info", "info", "1"));
        usertemp.setSkills(listeskills);
        usertemp.setZip_code("90000");

        setUsermember(usertemp);
        // fin test

        CreateProfil();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return menucontrol.onOptionsItemSelected(item);
    }

    public void ImageListener() {
        ImageView home_icon;
        home_icon = (ImageView) this.findViewById(R.id.icon_home);
        // set a onclick listener for when the button gets clicked
        home_icon.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {
                Intent mainIntent = new Intent(context, MainActivity.class);
                startActivity(mainIntent);
            }
        });

    }

    public void CreateProfil() {
        if (usermember.getRegistration_date() != null)
            CreateProfilByStep("Date d'inscription :", usermember.getRegistration_date().toString(), R.id.content_for_registrationdate);

        if (usermember.getEmail() != null)
            CreateProfilByStep("Email :", usermember.getEmail(), R.id.content_for_email);

        if (usermember.getName() != null)
            CreateProfilByStep("Nom :", usermember.getName(), R.id.content_for_name);

        if (usermember.getFirstname() != null)
            CreateProfilByStep("Prénom :", usermember.getFirstname(), R.id.content_for_firstname);

        if (usermember.getBirth_date() != null)
            CreateProfilByStep("Date de naissance :", usermember.getBirth_date().toString(), R.id.content_for_birthday);

        if (usermember.getZip_code() != null)
            CreateProfilByStep("Code Postale :", usermember.getZip_code(), R.id.content_for_zipcode);

        if (usermember.getCity() != null)
            CreateProfilByStep("Ville :", usermember.getCity(), R.id.content_for_city);

        if (usermember.getInvolvement() != null)
            CreateProfilByStep("Engagement :", usermember.getInvolvement().toString(), R.id.content_for_involevment);

        if (usermember.getSection() != null)
            CreateProfilByStep("Section :", usermember.getSection().toString(), R.id.content_for_section);

        if (usermember.getSkills() != null) {
            String listString = "";
            int i = 0;

            for (Skill s : usermember.getSkills()) {
                listString += s;
                i++;
                if (i < usermember.getSkills().size()) {
                    listString += ",\n";
                }

            }


            CreateProfilByStep("Compétences :", listString, R.id.content_for_skills);
        }

        // il faut mettre une liste
        if (usermember.getStatus() != null)
            CreateProfilByStep("Contact pour :", usermember.getStatus().toString(), R.id.content_for_contact);


    }

    public void CreateProfilByStep(String label, String value, int content) {
        TextView Label = new TextView(this);
        Label.setText(label);
        TextView Value = new TextView(this);
        Value.setText(value);

        Label.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
        Value.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));

        LinearLayout linearlayout = (LinearLayout) findViewById(content);
        linearlayout.addView(Label);
        linearlayout.addView(Value);
    }



}
