package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.adaptater;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.R;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DiffusionCriteria;

import java.util.ArrayList;

/**
 * Created by Omar on 22/12/2015.
 */
public class DiffusionCriteriaRowContent extends BaseAdapter implements View.OnClickListener {

    private static LayoutInflater inflater = null;
    public Resources res;
    DiffusionCriteria tempValues = null;
    int i = 0;
    /***********
     * Declare Used Variables
     *********/
    private Activity activity;
    private ArrayList data;

    /*************
     * CustomAdapter Constructor
     *****************/
    public DiffusionCriteriaRowContent(Activity a, ArrayList d)//Resources resLocal)
    {
        /********** Take passed values **********/
        activity = a;
        data = d;
        // res = resLocal;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = (LayoutInflater) activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /********
     * What is the size of Passed Arraylist Size
     ************/
    public int getCount() {

        if (data.size() <= 0)
            return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    /******
     * Depends upon data size called for each row , Create each ListView row
     *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if (convertView == null) {

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.row_diffusion_criteria, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.diff_crit_name = (TextView) vi.findViewById(R.id.lbl_row_diffusion_criteria_name);
            holder.diff_crit_text_value = (EditText) vi.findViewById(R.id.editxt_diffusion_criteria_value);
            holder.diff_crit_spin_value = (Spinner) vi.findViewById(R.id.spin_diffusion_criteria_value);
            holder.diff_critt_delete = (ImageButton) vi.findViewById(R.id.suppr_criteria);
            //holder.image=(ImageView)vi.findViewById(R.id.image);

            /************  Set holder with LayoutInflater ************/
            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();

        if (data.size() <= 0) {
            holder.diff_crit_name.setText("No Data");
            holder.diff_crit_text_value.setText("No Data");
            holder.diff_crit_spin_value.setContentDescription("No Data");

        } else {
            /***** Get each Model object from Arraylist ********/
            tempValues = null;
            tempValues = (DiffusionCriteria) data.get(position);

            /************  Set Model values in Holder elements ***********/

            holder.diff_crit_name.setText(tempValues.getCriteria_Name());
            if (tempValues.isSpinner_type()) {
                holder.diff_crit_text_value.setVisibility(View.GONE);
                holder.diff_crit_spin_value.setVisibility(View.VISIBLE);
                holder.diff_crit_spin_value.setAdapter(
                        new ArrayAdapter<Object>(vi.getContext(), android.R.layout.simple_list_item_multiple_choice, tempValues.getValues_List())
                );
            } else {
                holder.diff_crit_spin_value.setVisibility(View.GONE);
                holder.diff_crit_text_value.setVisibility(View.VISIBLE);
                if (tempValues != null)
                    if (tempValues.getValue() != null)
                        holder.diff_crit_text_value.setText(tempValues.getValue().toString());
            }
            /*holder.image.setImageResource(
                    res.getIdentifier(
                            "com.androidexample.customlistview:drawable/"+tempValues.getImage()
                            ,null,null));*/

            /******** Set Item Click Listner for LayoutInflater for each row *******/

            //vi.setOnClickListener(new OnItemClickListener( position ));
            holder.diff_critt_delete.setOnClickListener(new OnItemClickListener(position));
        }
        return vi;
    }

    @Override
    public void onClick(View v) {
        Log.v("CustomAdapter", "=====Row button clicked=====");
    }

    /*********
     * Create a holder Class to contain inflated xml file elements
     *********/
    public static class ViewHolder {

        public Spinner diff_crit_spin_value;
        public EditText diff_crit_text_value;
        public TextView diff_crit_name;
        public ImageButton diff_critt_delete;
        /*public TextView textWide;
        public ImageView image;*/

    }

    /*********
     * Called when Item click in ListView
     ************/
    private class OnItemClickListener implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {


            //DiffusionCriteriasActivity sct = (DiffusionCriteriasActivity)activity;

            /****  Call  onItemClick Method inside CustomListViewAndroidExample Class ( See Below )****/

            Log.v("Diff_Crit", "=====Delete button clicked====="+mPosition);
            //sct.onItemClick(mPosition);
        }
    }

}