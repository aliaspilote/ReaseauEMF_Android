package com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.adaptater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.R;
import com.emf_asso.bdd.reseauetudiantsmuslmansdefrance.core.entity.Curriculum;

import java.util.List;

/**
 * Created by Omar on 20/11/2015.
 */
public class listViewCursusAdaptater extends ArrayAdapter<Curriculum> {
    public listViewCursusAdaptater(Context context, List<Curriculum> items) {
        super(context, R.layout.fragment_cursus_list, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.fragment_cursus_list, parent, false);

            // initialize the view holder
            viewHolder = new ViewHolder();
            //   viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
            viewHolder.labelCursusTextView = (TextView) convertView.findViewById(R.id.labelCursusTextView);
            //viewHolder.tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
            convertView.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // update the item view
        Curriculum item = getItem(position);
        //viewHolder.ivIcon.setImageDrawable(item.icon);
        viewHolder.labelCursusTextView.setText(item.getLabel());
        // viewHolder.tvDescription.setText(item.description);

        return convertView;
    }

    private static class ViewHolder {
        //ImageView ivIcon;
        TextView labelCursusTextView;
        TextView descrCursusTextView;
    }
}