package com.example.ammar.googlebooklisting;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BooksAdapter extends ArrayAdapter<Book> {

    public BooksAdapter(@NonNull Context context, @NonNull List<Book> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View bookView = convertView;
        if (bookView == null) {
            bookView = LayoutInflater.from(getContext()).inflate(R.layout.book_item, parent, false);
        }
        TextView title = (TextView) bookView.findViewById(R.id.title);
        TextView author = (TextView) bookView.findViewById(R.id.author);
        Book book = getItem(position);
        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        return bookView;
    }
}
