package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.adaptater;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.DiffusionCriteriasActivity;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.R;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.SessionWsService;

import java.util.ArrayList;

/**
 * Created by Omar on 22/12/2015.
 */
public class DiffusionCriteriaRowContent extends BaseAdapter implements View.OnClickListener {

    private static LayoutInflater inflater = null;
    public Resources res;
    //DiffusionCriteria tempValues = null;
    int i = 0;
    /***********
     * Declare Used Variables
     *********/
    private Activity activity;
    //private ArrayList data;
    private SessionWsService AppCtx;

    /*************
     * CustomAdapter Constructor
     *****************/
    public DiffusionCriteriaRowContent(Activity a, ArrayList d, SessionWsService AppCtx)//Resources resLocal)
    {
        /********** Take passed values **********/
        activity = a;
        // data = d;
        this.AppCtx = AppCtx;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = (LayoutInflater) activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /********
     * What is the size of Passed Arraylist Size
     ************/
    public int getCount() {

        if (AppCtx.getServiceLDF().getCurrent_ldf().size() <= 0)
            return 0;
        return AppCtx.getServiceLDF().getCurrent_ldf().size();
    }

    public Object getItem(int position) {
        return AppCtx.getServiceLDF().getCurrent_ldf().get_criteria_byInt(position);
    }

    public long getItemId(int position) {

        return position;
    }

    /******
     * Depends upon data size called for each row , Create each ListView row
     *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        final ViewHolder holder;
        if (convertView == null) {

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.row_diffusion_criteria, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.currentPos = position;
            holder.diff_crit_name = (TextView) vi.findViewById(R.id.lbl_row_diffusion_criteria_name);
            holder.diff_crit_text_value = (EditText) vi.findViewById(R.id.editxt_diffusion_criteria_value);
            holder.diff_crit_spin_value = (Spinner) vi.findViewById(R.id.spin_diffusion_criteria_value);
            holder.diff_critt_delete = (ImageButton) vi.findViewById(R.id.suppr_criteria);
            holder.diff_critt_save = (ImageButton) vi.findViewById(R.id.save_criteria);

            //holder.image=(ImageView)vi.findViewById(R.id.image);
           /* holder.diff_crit_text_value.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {
                    if (s != null)
                    {
                        AppCtx.getServiceLDF().updateVal_criteria_currentldf(holder.currentPos, s.toString());
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {  }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                }
            });
*/
            //holder.diff_crit_spin_value.addTextChangedListener(new CustomTextWatcher(text2,wed));
            /************  Set holder with LayoutInflater ************/
            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();

        if (AppCtx.getServiceLDF().getCurrent_ldf().size() <= 0) {
            holder.diff_crit_name.setText("pas de critères ajoutés");
            holder.diff_crit_spin_value.setContentDescription("pas de critères ajoutés");
            holder.diff_crit_text_value.setVisibility(View.GONE);
            holder.diff_critt_delete.setVisibility(View.GONE);
            holder.diff_critt_save.setVisibility(View.GONE);
        } else {
            /***** Get each Model object from Arraylist ********/
            //tempValues = AppCtx.getServiceLDF().getCurrent_ldf().get_criteria_byInt(position);
            /************  Set Model values in Holder elements ***********/
            holder.diff_crit_name.setText(AppCtx.getServiceLDF().getCurrent_ldf().get_criteria_byInt(position).getCriteria_Name());
            holder.diff_critt_delete.setVisibility(View.VISIBLE);
            holder.diff_critt_save.setVisibility(View.VISIBLE);
            holder.diff_crit_text_value.setVisibility(View.VISIBLE);

            if (AppCtx.getServiceLDF().getCurrent_ldf().get_criteria_byInt(position).isSpinner_type()) {
                holder.diff_crit_text_value.setVisibility(View.GONE);
                holder.diff_crit_spin_value.setVisibility(View.VISIBLE);
                holder.diff_crit_spin_value.setAdapter(
                        new ArrayAdapter<Object>(vi.getContext(), android.R.layout.simple_list_item_1,
                                AppCtx.getServiceLDF().getCurrent_ldf().get_criteria_byInt(position).getValues_List())
                );
                if (AppCtx.getServiceLDF().getCurrent_ldf().get_criteria_byInt(position) != null)
                    if (AppCtx.getServiceLDF().getCurrent_ldf().get_criteria_byInt(position).getValue() != null)
                        holder.diff_crit_spin_value.setSelection(
                                AppCtx.getServiceLDF().getCurrent_ldf().get_criteria_byInt(position).getValues_List().
                                        indexOf(AppCtx.getServiceLDF().getCurrent_ldf().get_criteria_byInt(position).getValue().toString()));
            } else {
                holder.diff_crit_spin_value.setVisibility(View.GONE);
                holder.diff_crit_text_value.setVisibility(View.VISIBLE);
                if (AppCtx.getServiceLDF().getCurrent_ldf().get_criteria_byInt(position) != null)
                    if (AppCtx.getServiceLDF().getCurrent_ldf().get_criteria_byInt(position).getValue() != null)
                        holder.diff_crit_text_value.setText(
                                AppCtx.getServiceLDF().getCurrent_ldf().get_criteria_byInt(position).getValue().toString());
            }

            /******** Set Item Click Listner for LayoutInflater for each row *******/

            //vi.setOnClickListener(new OnItemClickListener( position ));
            holder.diff_critt_delete.setOnClickListener(new OnItemClickListener(false, position));
            // holder.diff_critt_save.setOnClickListener(new OnItemClickListener(true, position));
        }
        return vi;
    }

    public void onItemSelected(AdapterView<?> listView, View view, int position, long id) {
        if (position == 1) {
            // listView.setItemsCanFocus(true);

            // Use afterDescendants, because I don't want the ListView to steal focus
            listView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
            ((EditText) view.findViewById(R.id.editxt_diffusion_criteria_value)).requestFocus();
        } else {
            if (!listView.isFocused()) {
                // listView.setItemsCanFocus(false);

                // Use beforeDescendants so that the EditText doesn't re-take focus
                listView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
                listView.requestFocus();
            }
        }
    }

    public void onNothingSelected(AdapterView<?> listView) {
        // This happens when you start scrolling, so we need to prevent it from staying
        // in the afterDescendants mode if the EditText was focused
        listView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
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
        public ImageButton diff_critt_save;
        public int currentPos;
        /*public TextView textWide;
        public ImageView image;*/

    }

    /*********
     * Called when Item click in ListView
     ************/
    private class OnItemClickListener implements View.OnClickListener {
        private int mPosition;
        private boolean save;

        OnItemClickListener(boolean b, int position) {
            mPosition = position;
            save = b;
        }

        @Override
        public void onClick(View arg0) {
            DiffusionCriteriasActivity sct = (DiffusionCriteriasActivity) activity;
            /****  Call  onItemClick Method inside CustomListViewAndroidExample Class ( See Below )****/
            //Log.v("Diff_Crit", "=====Delete button clicked=====" + mPosition);
            if (!save)
                sct.onItemClickDelete(mPosition);
            else
                sct.onItemClickSave(mPosition);
        }
    }
}