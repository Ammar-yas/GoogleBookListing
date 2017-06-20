package com.example.ammar.googlebooklisting;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    EditText searchQuery;
    ListView listView;
    ImageButton searchButton;
    ProgressBar progressBar;
    TextView statusTextView;
    BooksAdapter booksAdapter;

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

        booksAdapter = new BooksAdapter(this, new ArrayList<Book>());
        listView.setAdapter(booksAdapter);


    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        return new BooksLoader(this, BOOKS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        progressBar.setVisibility(View.GONE);
        booksAdapter.clear();

        if (books != null && !books.isEmpty()) {
            booksAdapter.addAll(books);
        } else {
            statusTextView.setText(getText(R.string.no_books));
            listView.setEmptyView(statusTextView);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        booksAdapter.clear();
    }
}
