package com.example.youssefwagih.mychurchapp.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youssefwagih.mychurchapp.R;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

public class FeedDetailsActivity extends AppCompatActivity {
    private String TAG = FeedDetailsActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private TextView newsTitleTV;
    private ImageView newsIV;
    private TextView itemDescriptionTV;
    private TextView numOfViewsTV;
    private TextView likesTV;
    private TextView postDateTV;
    private Context context;

    HashMap<String, String> newsDetails;
    private String newsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_details);

        newsTitleTV = (TextView) findViewById(R.id.newsTitleTV);
        newsIV = (ImageView) findViewById(R.id.newsIV);
        itemDescriptionTV = (TextView) findViewById(R.id.itemDescriptionTV);
        numOfViewsTV = (TextView) findViewById(R.id.numOfViewsTV);
        likesTV = (TextView) findViewById(R.id.likesTV);
        postDateTV = (TextView) findViewById(R.id.postDateTV);

        context = getApplicationContext();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        itemDescriptionTV.setMovementMethod(new ScrollingMovementMethod());

        Intent intent = getIntent();
        String newsId = intent.getStringExtra("Nid");

        String[] myTaskParams = { newsId };
        new GetNewsDetails().execute(myTaskParams);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
/*        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.news_details, menu);*/



        return true;
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetNewsDetails extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(FeedDetailsActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(String...pParams) {
            String response = null;
            String newsNId = pParams[0];
            // URL to get news JSON
            String urlStr = "http://egyptinnovate.com/en/api/v01/safe/GetNewsDetails?nid=" + newsNId;
            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                // read the response
                InputStream in = new BufferedInputStream(conn.getInputStream());


                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();

                String line;
                try {
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append('\n');
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                response = sb.toString();
            } catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException: " + e.getMessage());
            } catch (ProtocolException e) {
                Log.e(TAG, "ProtocolException: " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "IOException: " + e.getMessage());
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }

            Log.e(TAG, "Response from url: " + response);

            if (response != null) {
                try {
                    JSONObject jsonObj = new JSONObject(response);

                    JSONObject newsItem = jsonObj.getJSONObject("newsItem");
                    String newsTitle = newsItem.getString("NewsTitle");
                    String itemDescription = newsItem.getString("ItemDescription");
                    String Nid = newsItem.getString("Nid");
                    String postDate = newsItem.getString("PostDate");
                    String imageUrl = newsItem.getString("ImageUrl");
                    String newsType = newsItem.getString("NewsType");
                    String numofViews = newsItem.getString("NumofViews");
                    String likes = newsItem.getString("Likes");

                    // hash map for single NewsDetails
                    newsDetails = new HashMap<>();

                    newsDetails.put("NewsTitle", newsTitle);
                    newsDetails.put("Nid", Nid);
                    newsDetails.put("PostDate", postDate);
                    newsDetails.put("ImageUrl", imageUrl);
                    newsDetails.put("NewsType", newsType);
                    newsDetails.put("NumofViews", numofViews);
                    newsDetails.put("Likes", likes);
                    newsDetails.put("ItemDescription", itemDescription);
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            newsTitleTV.setText(newsDetails.get("NewsTitle"));
            itemDescriptionTV.setText(newsDetails.get("ItemDescription"));
            numOfViewsTV.setText(newsDetails.get("NumofViews") + "views");
            likesTV.setText("Likes(" + newsDetails.get("Likes") + ")");
            postDateTV.setText(newsDetails.get("PostDate"));

        }

    }
}
