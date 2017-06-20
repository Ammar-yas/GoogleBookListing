package com.example.ammar.googlebooklisting;


import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private QueryUtils() {
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("URL: ", "create url method error");
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("URL", "Error response code: " + httpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("JSON", "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if (inputStream != null) {

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Book> extractFeatureFromJson(String BooksJSON) {

        if (TextUtils.isEmpty(BooksJSON)) {
            return null;
        }

        List<Book> books = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(BooksJSON);
            JSONArray booksArray = baseJsonResponse.getJSONArray("items");

            for (int i = 0; i < booksArray.length(); i++) {

                JSONObject currentBookJson = booksArray.getJSONObject(i).getJSONObject("volumeInfo");
                JSONArray authorsJson = currentBookJson.getJSONArray("authors");

                String title = currentBookJson.getString("title");
                String authors = "";
                for (int x = 0; x < authorsJson.length(); x++) {
                    if (x >= 1) {
                        authors += ", ";
                    }
                    authors += authorsJson.getString(x);
                }
                Book newBook = new Book(title, authors);
                books.add(newBook);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
            ;
        }
        return books;
    }

    public static List<Book> fetchBooksData(String requestUrl) {

        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("LOG_TAG", "Problem making the HTTP request.", e);
        }
        List<Book> Books = extractFeatureFromJson(jsonResponse);
        return Books;
    }
}
