package com.example.ammar.googlebooklisting;


import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class BooksLoader extends AsyncTaskLoader<List<Book>> {

    String url;

    public BooksLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        super.onStartLoading();
    }

    @Override
    public List<Book> loadInBackground() {
        List<Book> books = QueryUtils.fetchBooksData(url);
        return books;
    }
}
