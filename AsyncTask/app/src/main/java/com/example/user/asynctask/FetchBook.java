package com.example.user.asynctask;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class FetchBook extends AsyncTask <String, Void, String>  {
    private TextView mTitleText;
    private TextView mAutorText;

    public FetchBook(TextView mTitleText, TextView mAutorText){
        this.mAutorText = mAutorText;
        this.mTitleText = mTitleText;
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getBookInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try{
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray =  jsonObject.getJSONArray("items");

            for (int i = 0; i < itemsArray.length() ; i++) {
                JSONObject book = itemsArray.getJSONObject(i);
                String title = null;
                String author = null;
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try{
                    title = volumeInfo.getString("title");
                    author = volumeInfo.getString("author");
                }catch (Exception e){
                    e.printStackTrace();
                }

                if (title != null && author != null){
                    mAutorText.setText(author);
                    mTitleText.setText(title);
                    return;
                }

            }
            mTitleText.setText("Tidak ada hasil");
            mAutorText.setText("");
        }catch (Exception ex){
            mTitleText.setText("Tidak ada hasil");
            mAutorText.setText("");
            ex.printStackTrace();
        }

    }


}
