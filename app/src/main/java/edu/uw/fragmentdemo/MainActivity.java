package edu.uw.fragmentdemo;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements MovieFragment.OnMovieSelectedListener {

    private static final String TAG = "MainActivity";

    private static final String MOVIE_FRAGMENT_TAG = "MOVIE_FRAGMENT";
    private static final String DETAIL_FRAGMENT_TAG = "DETAIL_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.container, new MovieFragment(), MOVIE_FRAGMENT_TAG);
        ft.commit();
    }


    //respond to search button clicking
    public void handleSearchClick(View v){
        Log.v(TAG, "Button handled");

        EditText text = (EditText)findViewById(R.id.txtSearch);
        String searchTerm = text.getText().toString();

        MovieFragment movieFragment = (MovieFragment)getSupportFragmentManager().findFragmentByTag(MOVIE_FRAGMENT_TAG);

        movieFragment.fetchData(searchTerm);
    }

    @Override
    public void onMovieSelected(Movie movie) {

        Bundle bundle = new Bundle();
        bundle.putString("title",movie.toString());
        bundle.putString("imdb",movie.imdbId);

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(bundle);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment, DETAIL_FRAGMENT_TAG);
        ft.addToBackStack(null);
        ft.commit();

        //show as dialog. This doesn't look great :p
//        fragment.show(getSupportFragmentManager(), "dialog");

    }
}
