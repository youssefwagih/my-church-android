package com.example.youssefwagih.mychurchapp;

/**
 * Created by youssef.wagih on 12/21/2016.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.HashMap;

public class TabFragment1 extends Fragment {
    private Context context;
    private ListView feedListView;
    private ArrayList<HashMap<String, String>> feedsList;
    private View view;
    private String TAG = TabFragment1.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_fragment_1, container, false);
        context = getActivity().getApplicationContext();
        feedListView = (ListView) view.findViewById(R.id.feedsLV);
        feedsList = new ArrayList<>();

        new GetNews().execute();

        return view;
    }

    private class GetNews extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
/*            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();*/

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            String response = null;

            // URL to get news JSON
            String urlStr = "http://egyptinnovate.com/en/api/v01/safe/GetNews";
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

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("News");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String newsTitle = c.getString("NewsTitle");
                        String Nid = c.getString("Nid");
                        String postDate = c.getString("PostDate");
                        String imageUrl = c.getString("ImageUrl");
                        String newsType = c.getString("NewsType");
                        String numofViews = c.getString("NumofViews");
                        String likes = c.getString("Likes");

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("NewsTitle", newsTitle);
                        contact.put("Nid", Nid);
                        contact.put("PostDate", postDate);
                        contact.put("ImageUrl", imageUrl);
                        contact.put("NewsType", newsType);
                        contact.put("NumofViews", numofViews);
                        contact.put("Likes", likes);

                        // adding contact to contact list
                        feedsList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
/*                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });*/

                }
            } else {
/*                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });*/

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
/*            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();*/
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new CustomFeedsListAdapter(context, R.layout.list_item, feedsList);
            feedListView.setAdapter(adapter);
        }

    }
}