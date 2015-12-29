package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.adaptater;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.LDFActivity;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.R;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.services.SessionWsService;

/**
 * Created by Omar on 29/12/2015.
 */
public class LdfRowContent extends BaseAdapter implements View.OnClickListener {

    private static LayoutInflater inflater = null;
    public Resources res;
    //DiffusionCriteria tempValues = null;
    int i = 0;
    /***********
     * Declare Used Variables
     *********/
    private Activity activity;
    private SessionWsService AppCtx;

    /*************
     * CustomAdapter Constructor
     *****************/
    public LdfRowContent(Activity a, SessionWsService AppCtx) {
        /********** Take passed values **********/
        activity = a;
        this.AppCtx = AppCtx;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = (LayoutInflater) activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /********
     * What is the size of Passed Arraylist Size
     ************/
    public int getCount() {

        if (AppCtx.getServiceLDF().size_ldf() <= 0)
            return 0;
        return AppCtx.getServiceLDF().size_ldf();
    }

    public Object getItem(int position) {
        return AppCtx.getServiceLDF().get_ldf(position);
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
            vi = inflater.inflate(R.layout.row_ldf, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.currentPos = position;
            holder.ldf_name = (TextView) vi.findViewById(R.id.lbl_row_ldf_name);
            holder.ldf_count = (TextView) vi.findViewById(R.id.lbl_ldf_row_result_count);
            holder.ldf_send_mail = (ImageButton) vi.findViewById(R.id.send_mail_ldf);
            /************  Set holder with LayoutInflater ************/
            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();

        if (AppCtx.getServiceLDF().size_ldf() <= 0) {
            holder.ldf_name.setText("Aucune liste de diffusion");
            holder.ldf_count.setText("-");
            holder.ldf_send_mail.setVisibility(View.GONE);
        } else {
            /***** Get each Model object from Arraylist ********/
            //tempValues = AppCtx.getServiceLDF().getCurrent_ldf().get_criteria_byInt(position);
            /************  Set Model values in Holder elements ***********/
            holder.ldf_name.setText(AppCtx.getServiceLDF().get_ldf(position).getLabel() != null ? AppCtx.getServiceLDF().get_ldf(position).getLabel().toString() : "");
            holder.ldf_count.setText(AppCtx.getServiceLDF().get_ldf(position).getCount() != null ? AppCtx.getServiceLDF().get_ldf(position).getCount().toString() : "-");
            holder.ldf_send_mail.setVisibility(View.VISIBLE);

            /******** Set Item Click Listner for LayoutInflater for each row *******/

            vi.setOnClickListener(new OnRowClickListener(position, (LDFActivity) activity));
            holder.ldf_send_mail.setOnClickListener(new OnSendClickListener(position));
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

        public TextView ldf_name;
        public TextView ldf_count;
        public ImageButton ldf_send_mail;
        public int currentPos;
        /*public TextView textWide;
        public ImageView image;*/
    }

    /*********
     * Called when Item click in ListView
     ************/
    private class OnRowClickListener implements View.OnClickListener {
        private int mPosition;
        private LDFActivity mActivity;

        OnRowClickListener(int position, LDFActivity activity) {
            mPosition = position;
            mActivity = activity;
        }

        @Override
        public void onClick(View arg0) {
            AppCtx.getServiceLDF().setCurrent_ldf(AppCtx.getServiceLDF().get_ldf(mPosition));
            mActivity.gotoDiffusionCriteriasActivity();
            Log.v("LDF ROW Action", "=====Row clicked=====" + mPosition);
        }
    }

    private class OnSendClickListener implements View.OnClickListener {
        private int mPosition;

        OnSendClickListener(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {
            //DiffusionCriteriasActivity sct = (DiffusionCriteriasActivity) activity;
            Log.v("LDF ROW Action", "=====Send button clicked=====" + mPosition);
            //sct.onItemClickSave(mPosition);
        }
    }
}
