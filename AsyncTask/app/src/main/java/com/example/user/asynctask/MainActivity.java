package com.example.user.asynctask;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText mBookTitle;
    private TextView mTitle, mAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBookTitle = findViewById(R.id.bookInput);
        mTitle = findViewById(R.id.titleText);
        mAuthor = findViewById(R.id.authorText);
    }

    public void searchBooks(View view) {
        String queryString = mBookTitle.getText().toString();

        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
        InputMethodManager.HIDE_NOT_ALWAYS);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() && queryString.length() != 0){
            new FetchBook(mTitle, mAuthor).execute(queryString);
            mAuthor.setText("");
            mTitle.setText("Loading...");
        }else {
            if(queryString.length() ==0){
                mAuthor.setText("");
                mTitle.setText("Tolong isi data");
            }else{
                mAuthor.setText("");
                mTitle.setText("Cek koneksi");
            }
        }

        Log.i(TAG, "searchBooks: " + queryString);
        if (queryString.length()!=0) {
            new FetchBook(mTitle, mAuthor).execute(queryString);
        }else{
            Toast.makeText(this, "Tolong isi data pencarian", Toast.LENGTH_SHORT).show();
        }
    }
}
