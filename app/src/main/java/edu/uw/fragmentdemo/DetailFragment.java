package edu.uw.fragmentdemo;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends DialogFragment {


    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Bundle args = getArguments();

        ((TextView)rootView.findViewById(R.id.txtMovieTitle)).setText(args.getString("title"));
        TextView imdbText = ((TextView)rootView.findViewById(R.id.txtMovieIMDB));

        imdbText.append(args.getString("imdb"));
        Linkify.addLinks(imdbText, Linkify.WEB_URLS);

        return rootView;
    }

}
