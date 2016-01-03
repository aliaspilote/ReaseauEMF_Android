package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DiffusionCriteria;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DiffusionList;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.ListViewInit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar on 27/12/2015.
 */
public class LdfService implements Serializable {

    private List<DiffusionList> ldfList;
    private DiffusionList current_ldf;
    private int current_position;
    private boolean startedService = false;

    public void onStart(SessionWsService AppCtx) {
        if (!startedService) {
            current_position = 0;
            current_ldf = null;
            ldfList = new ArrayList<>();
            startedService = true;
            ListViewInit.loadListStaticData(AppCtx);
            ListViewInit.PopulateCriteriasListType();
        }
    }

    public List<DiffusionList> getLdfList() {
        return ldfList;
    }

    public void setLdfList(List<DiffusionList> ldfList) {
        this.ldfList = ldfList;
    }

    public DiffusionList getCurrent_ldf() {
        return current_ldf;
    }

    public void setCurrent_ldf(DiffusionList current_ldf) {
        this.current_ldf = current_ldf;
    }

    public int getCurrent_position() {
        return current_position;
    }

    public void setCurrent_position(int current_position) {
        this.current_position = current_position;
    }

    public void add_ldf(DiffusionList ldf) {
        ldfList.add(ldf);
    }

    public void remove_ldf(DiffusionList ldf) {
        if (ldfList.contains(ldf))
            ldfList.remove(ldf);
    }

    public void update_ldf(DiffusionList newldf) {
        if (ldfList.contains(newldf))
            ldfList.set(ldfList.indexOf(newldf), newldf);
        else
            ldfList.add(newldf);
    }

    public void update_ldf(int position, DiffusionList newldf) {
        ldfList.set(position, newldf);
    }

    public DiffusionList get_ldf(int position) {
        return ldfList.get(position);
    }

    public DiffusionList get_ldf_byId(String id) {
        DiffusionList ldf = null;
        for (DiffusionList l : ldfList) {
            if (id.equals(l.getId()))
                ldf = l;
        }
        return ldf;
    }

    public DiffusionCriteria get_criteria_currentldf(int pos) {
        return current_ldf.DiffusionCriteriaListViewValuesArr.get(pos);
    }

    public void add_criteria_currentldf(DiffusionCriteria criteria) {
        current_ldf.DiffusionCriteriaListViewValuesArr.add(criteria);
    }

    public void remove_criteria_currentldf(DiffusionCriteria criteria) {
        current_ldf.DiffusionCriteriaListViewValuesArr.remove(criteria);
    }

    public void update_criteria_currentldf(int position, DiffusionCriteria criteria) {
        current_ldf.DiffusionCriteriaListViewValuesArr.set(position, criteria);
    }

    public void updateVal_criteria_currentldf(int position, Object criteriaVal) {
        DiffusionCriteria df = get_criteria_currentldf(position);
        df.setValue(criteriaVal);
        update_criteria_currentldf(position, df);
    }

    public int size_ldf() {
        return ldfList.size();
    }

    public DiffusionCriteria get_new_criteria_byName(String ref) {
        DiffusionCriteria c = null;
        for (DiffusionCriteria crit : ListViewInit.CriteriaListViewTypeArr) {

            String A = ref;
            String B = crit.getCriteria_Ref();
            if (A.equals(B))
                c = crit;
        }
        return c;
    }

    public void setLDF_From_DB(JSONObject JsonResult) {
        ldfList.clear();
        try {
            JSONObject ldf = (JSONObject) JsonResult.get("ldf");
            JSONObject criterias = (JSONObject) JsonResult.get("criterias");
            int count_ldf = 0;
            int count_criterias = 0;
            try {
                count_ldf = Integer.parseInt((ldf.get("count")).toString());
                count_criterias = Integer.parseInt((criterias.get("count")).toString());
            } catch (Exception e) {
            }

            JSONArray ldf_data = (JSONArray) ldf.get("data");
            JSONArray criterias_data = (JSONArray) criterias.get("data");
            try {
                DiffusionList l;
                if (count_ldf > 0) {
                    for (int i = 0; i < count_ldf; i++) {
                        JSONObject oneldfjson = (JSONObject) ldf_data.get(i);
                        l = new DiffusionList();
                        l.setId(oneldfjson.get("num_ldf").toString());
                        l.setLabel(oneldfjson.get("nom_ldf").toString());
                        l.setCount(oneldfjson.get("count_ldf").toString());
                        add_ldf(l);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (count_ldf > 0) {

                    DiffusionList l_c = new DiffusionList();
                    DiffusionCriteria crit;
                    String previous_id = "";
                    for (int i = 0; i < count_criterias; i++) {
                        JSONObject oneldfjson = (JSONObject) criterias_data.get(i);
                        String id = oneldfjson.get("num_ldf").toString();
                        String ref = oneldfjson.get("ref_critere").toString();
                        String val = oneldfjson.get("val_critere").toString();
                        DiffusionCriteria TypeCrit = get_new_criteria_byName(ref);
                        crit = new DiffusionCriteria(TypeCrit);
                        crit.buildValueFromString(val);
                        if (previous_id.toString() != id.toString())
                            l_c = get_ldf_byId(id);
                        l_c.add_criterias(crit);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

