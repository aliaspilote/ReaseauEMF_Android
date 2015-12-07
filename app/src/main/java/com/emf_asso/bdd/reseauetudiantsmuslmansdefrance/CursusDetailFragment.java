package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.adaptater.CursusContent;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Curriculum;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.DataContext;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.ListViewInit;

/**
 * A fragment representing a single Cursus detail screen.
 * This fragment is either contained in a {@link CursusListActivity}
 * in two-pane mode (on tablets) or a {@link CursusDetailActivity}
 * on handsets.
 */
public class CursusDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "cursus_id";

    public java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DataContext.dateDisplayFormat);
    /**
     * The dummy content this fragment is presenting.
     */
    private Curriculum mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CursusDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = CursusContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getLabel());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cursus_detail, container, false);
        ListViewInit.loadListStaticCursus_View(rootView, getActivity(), null);
        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.cursus_editxt_entiled_diploma)).setText(mItem.getLabel());
            ((TextView) rootView.findViewById(R.id.cursus_editxt_establishment)).setText(mItem.getEstablishment());
            // TODO placé un object MAP dans la classe Discipline entre id_DB et id_Spinner
            //((Spinner) rootView.findViewById(R.id.spinner_discipline)).setSelection(mItem.getDiscipline().getDiscipline_id());
            ((Spinner) rootView.findViewById(R.id.spinner_degree_study)).setSelection(mItem.getDegree().getDegree_id());
            ((TextView) rootView.findViewById(R.id.editxt_date_begin)).setText(sdf.format(mItem.getStart_date()));
            ((TextView) rootView.findViewById(R.id.editxt_date_end)).setText(sdf.format(mItem.getEnd_date()));
            ((TextView) rootView.findViewById(R.id.cursus_editxt_city_study)).setText(mItem.getCity());
        }

        return rootView;
    }
}
