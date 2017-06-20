package com.example.ammar.googlebooklisting;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<book>> {

    EditText searchQuery;
    ListView listView;
    ImageButton searchButton;
    ProgressBar progressBar;
    TextView statusTextView;

    final String BOOKS_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchQuery = (EditText) findViewById(R.id.search_query);
        searchButton = (ImageButton) findViewById(R.id.search_button);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        statusTextView = (TextView) findViewById(R.id.status_text_view);
        listView = (ListView) findViewById(R.id.list_view);


    }

    @Override
    public Loader<List<book>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<book>> loader, List<book> data) {

    }

    @Override
    public void onLoaderReset(Loader<List<book>> loader) {

    }
}
