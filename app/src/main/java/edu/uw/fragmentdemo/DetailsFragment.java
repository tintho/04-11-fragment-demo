package edu.uw.fragmentdemo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {


    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Bundle bundle = this.getArguments();

        if(bundle!=null) {
            TextView txtTitle = (TextView) rootView.findViewById(R.id.txtMovieTitle);
            txtTitle.setText(bundle.getString("title"));

            TextView txtImdb = (TextView) rootView.findViewById(R.id.txtMovieIMDB);
            //txtImdb.setText(bundle.getString("imdb"));
            txtImdb.append("title/"+ bundle.getString("imdb"));
            Linkify.addLinks(txtImdb, Linkify.WEB_URLS);
        }
        return rootView;
    }

}
