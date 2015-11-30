package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.dummy;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Curriculum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    private static final int COUNT = 25;
    /**
     * An array of sample (dummy) items.
     */
    public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();
    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    public static void setNewCursusList(List<Curriculum> cursusList) {
        ITEMS = new ArrayList<DummyItem>();
        ITEM_MAP = new HashMap<String, DummyItem>();
        // Add some sample items.
        int id = 1;
        for (Iterator<Curriculum> i = cursusList.iterator(); i.hasNext(); ) {
            Curriculum Cursus = i.next();
            addItem(new DummyItem(Integer.toString(id), Cursus));
            id++;
        }
    }

    private static void addItem(Curriculum cursus) {
        addItem(new DummyItem(Integer.toString(cursus.getId()), cursus));

    }
    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
/*
    private static DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }*/
/*
    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }*/

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem extends Curriculum {
        public String id;
        public String content;
        public String details;

        public Curriculum Cursus;


        public DummyItem(String id, Curriculum Cursus) {
            this.id = id;
            this.Cursus = Cursus;
            this.content = Cursus.getLabel() + " " + Cursus.getDiscipline();
        }

        public DummyItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return Cursus.getLabel();
        }
    }
}
