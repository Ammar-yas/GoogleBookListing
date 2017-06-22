package com.example.ammar.googlebooklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    Button searchButton;
    ProgressBar progressBar;
    TextView statusTextView;
    BooksAdapter booksAdapter;
    String BooksRequestUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchQuery = (EditText) findViewById(R.id.search_query);
        searchButton = (Button) findViewById(R.id.search_button);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        statusTextView = (TextView) findViewById(R.id.status_text_view);
        listView = (ListView) findViewById(R.id.list_view);
        booksAdapter = new BooksAdapter(this, new ArrayList<Book>());
        listView.setAdapter(booksAdapter);
        final LoaderManager loaderManager = getLoaderManager();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected()) {
                    String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=";
                    progressBar.setVisibility(View.VISIBLE);
                    BooksRequestUrl = apiUrl + searchQuery.getText();
                    if (loaderManager.getLoader(1) != null) {
                        loaderManager.restartLoader(1, null, MainActivity.this);
                    }else{
                        loaderManager.initLoader(1, null, MainActivity.this);
                    }
                } else {
                    booksAdapter.clear();
                    statusTextView.setText(getText(R.string.no_internet));
                    listView.setEmptyView(statusTextView);
                }
            }
        });
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        return new BooksLoader(this, BooksRequestUrl);
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

    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean connected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return connected;
    }
}