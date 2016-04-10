package edu.uw.fragmentdemo;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * A class for downloading movie data from the internet.
 * Code adapted from Google.
 *
 * @author Joel Ross
 * @version 2 (ArrayList-based)
 */
public class MovieDownloader {

    private static final String TAG = "MovieDownloader";

    //Returns ArrayList of Movies, rather than String[]
    public static ArrayList<Movie> downloadMovieData(String movie) {

        //construct the url for the omdbapi API
        String urlString = "";
        try {
            urlString = "http://www.omdbapi.com/?s=" + URLEncoder.encode(movie, "UTF-8") + "&type=movie";
        }catch(UnsupportedEncodingException uee){
            return null;
        }

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        ArrayList<Movie> movies = null;

        try {

            URL url = new URL(urlString);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line = reader.readLine();
            while (line != null) {
                buffer.append(line + "\n");
                line = reader.readLine();
            }

            if (buffer.length() == 0) {
                return null;
            }
            String results = buffer.toString();

            movies = parseMovieJSONData(results);
            if(movies == null)
                movies = new ArrayList<Movie>();

            Log.v(TAG, movies.toString()); //for debugging purposes
        }
        catch (IOException e) {
            return null;
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e) {
                }
            }
        }

        return movies;
    }

    /**
     * Parses a JSON-format String (from OMDB search results) into a list of Movie objects
     */
    public static ArrayList<Movie> parseMovieJSONData(String json){
        ArrayList<Movie> movies = new ArrayList<Movie>();

        try {
            JSONArray moviesJsonArray = new JSONObject(json).getJSONArray("Search"); //get array from "search" key
            for(int i=0; i<moviesJsonArray.length(); i++){
                JSONObject movieJsonObject = moviesJsonArray.getJSONObject(i); //get ith object from array
                Movie movie = new Movie();
                movie.title = movieJsonObject.getString("Title"); //get title from object
                movie.year = Integer.parseInt(movieJsonObject.getString("Year")); //get year from object
                movie.imdbId = movieJsonObject.getString("imdbID"); //get imdb from object
                movie.posterUrl = movieJsonObject.getString("Poster"); //get poster from object

                movies.add(movie);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing json", e);
            return null;
        }

        return movies;
    }
}
