package edu.uw.fragmentdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MoviesFragment.onMovieSelectedListener{

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.container, new MoviesFragment(), "MoviesFragment");
        ft.commit();

    }



    //respond to search button clicking
    public void handleSearchClick(View v){
        Log.v(TAG, "Button handled");

        EditText text = (EditText)findViewById(R.id.txtSearch);
        String searchTerm = text.getText().toString();

        MoviesFragment fragment = (MoviesFragment) getSupportFragmentManager().findFragmentByTag("MoviesFragment");

        if(fragment == null) {
            fragment = new MoviesFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment, null)
                    .commit();
        }

        fragment.fetchData(searchTerm);
    }

    @Override
    public void movieSelected(Movie movie) {

        Bundle bundle = new Bundle();
        bundle.putString("title", movie.toString());
        bundle.putString("imdb", movie.imdbId);

        DetailsFragment detailFragment = new DetailsFragment();
        detailFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, detailFragment, null)
                .addToBackStack(null)
                .commit();
    }
}
