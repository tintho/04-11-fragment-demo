package edu.uw.fragmentdemo;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A fragment to download and display a list of movies
 */
public class MovieFragment extends Fragment {

    private static final String TAG = "MovieFragment";
    private static final String SEARCH_TERM = "SearchTerm";

    private ArrayAdapter<Movie> adapter;

    private OnMovieSelectedListener callback;

    private String searchTerm;


    //Interaction interface
    public interface OnMovieSelectedListener {
        public void onMovieSelected(Movie movie);
    }


    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);

        //controller
        adapter = new ArrayAdapter<Movie>(getActivity(),
                R.layout.list_item, R.id.txtItem, new ArrayList<Movie>());

        ListView listView = (ListView)rootView.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = (Movie)parent.getItemAtPosition(position);
                Log.v(TAG, "You clicked on: "+movie);

                callback.onMovieSelected(movie);
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(searchTerm != null){ //rerun the search if we're restarted
            fetchData(searchTerm);
        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (OnMovieSelectedListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnMovieSelectedListener");
        }
    }

    //helper method for downloading the data via the MovieDowloadTask
    public void fetchData(String searchTerm){
        Log.v(TAG, "You searched for: "+searchTerm);
        MovieDownloadTask task = new MovieDownloadTask();
        task.execute(searchTerm);
        this.searchTerm = searchTerm; //for restoring state
    }

    //A task to download movie data from the internet on a background thread
    public class MovieDownloadTask extends AsyncTask<String, Void, ArrayList<Movie>> {

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {

            ArrayList<Movie> data = MovieDownloader.downloadMovieData(params[0]);

            return data;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);

            adapter.clear();
            for(Movie movie : movies){
                adapter.add(movie);
            }
        }
    }


}
