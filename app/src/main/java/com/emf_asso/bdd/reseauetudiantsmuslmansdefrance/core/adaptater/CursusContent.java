package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.adaptater;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Curriculum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Omar_Desk on 04/12/2015.
 */
public class CursusContent {
    public static List<Curriculum> ITEMS = new ArrayList<Curriculum>();

    public static Map<String, Curriculum> ITEM_MAP = new HashMap<String, Curriculum>();

    public static void addItem(Curriculum item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId(), item);
    }

    public static void removeItem(String cursus_id) {
        ITEMS.clear();
        ITEM_MAP.remove(cursus_id);
        for (Map.Entry<String, Curriculum> entry : ITEM_MAP.entrySet()) {
            Curriculum item = entry.getValue();
            item.setId(entry.getKey());
            addItem(item);
        }
    }

    public static void updateItem(Curriculum cursus) {
        ITEMS.clear();
        ITEM_MAP.remove(cursus.id);
        addItem(cursus);
        for (Map.Entry<String, Curriculum> entry : ITEM_MAP.entrySet()) {
            Curriculum item = entry.getValue();
            item.setId(entry.getKey());
            addItem(item);
        }
    }
}
