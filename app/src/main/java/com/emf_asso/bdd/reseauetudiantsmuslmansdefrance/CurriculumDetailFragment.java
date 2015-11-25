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

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.other.CreateDate;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.dummy.DummyContent;

/**
 * A fragment representing a single Curriculum detail screen.
 * This fragment is either contained in a {@link CurriculumListActivity}
 * in two-pane mode (on tablets) or a {@link CurriculumDetailActivity}
 * on handsets.
 */
public class CurriculumDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public CreateDate start_curriculum_date;
    public CreateDate end_curriculum_date;
    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CurriculumDetailFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.content);
            }
            // ListViewInit.InitListDegreeStudyDiscipline(this.getActivity(), this.getActivity());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_curriculum_detail, container, false);
        //ListViewInit.InitListDegreeStudyDiscipline(rootView, this.getActivity());
        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            // ((TextView) rootView.findViewById(R.id.curriculum_detail)).setText(mItem.details);
            ((Spinner) rootView.findViewById(R.id.spinner_discipline)).setSelection(mItem.Cursus.getDiscipline().getDiscipline_id());
            ((Spinner) rootView.findViewById(R.id.spinner_degree_study)).setSelection(mItem.Cursus.getDegree().getDegree_id());
            ((TextView) rootView.findViewById(R.id.editxt_date_begin)).setText(mItem.Cursus.getStart_date().toString());
            ((TextView) rootView.findViewById(R.id.editxt_date_end)).setText(mItem.Cursus.getEnd_date().toString());
            ((TextView) rootView.findViewById(R.id.cursus_editxt_entiled_diploma)).setText(mItem.Cursus.getLabel());
            ((TextView) rootView.findViewById(R.id.cursus_editxt_establishment)).setText(mItem.Cursus.getEstablishment());
            ((TextView) rootView.findViewById(R.id.cursus_editxt_city_study)).setText(mItem.Cursus.getCity());
        }

        return rootView;
    }


}
