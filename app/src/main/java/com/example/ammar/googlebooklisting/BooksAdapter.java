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
        ViewHolder holder = null;
        if (bookView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            bookView = inflater.inflate(R.layout.book_item,parent,false);
            holder = new ViewHolder();
            holder.title = (TextView) bookView.findViewById(R.id.title);
            holder.author = (TextView) bookView.findViewById(R.id.author);
            bookView.setTag(holder);
        }else {
            holder = (ViewHolder) bookView.getTag();
        }

        Book book = getItem(position);
        holder.title.setText(book.getTitle());
        holder.author.setText(book.getAuthor());
        return bookView;
    }

    static class ViewHolder{
        TextView title;
        TextView author;
    }
}

