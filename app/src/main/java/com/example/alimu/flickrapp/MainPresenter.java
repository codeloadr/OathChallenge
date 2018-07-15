package com.example.alimu.flickrapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainPresenter extends MainContract.Presenter {
    private static Context context;
    private MainContract.View view;
    private RequestQueue requestQueue;

    private String LOG_TAG = MainPresenter.class.getSimpleName();

    @Override
    void setView(MainContract.View view) {
        this.view = view;
    }

    @Override
    protected void onViewAttached(){
    }

    @Override
    protected void onViewDetached(){
    }

    @Override
    void setContext(android.content.Context context) {
        this.context = context;
    }

    @Override
    Context getContext() {
        return context;
    }

    @Override
    void requestAPICall() {
        String API_URL = generateURL(context.getString(R.string.start_page_name));

        //Creating a request queue
        requestQueue = Volley.newRequestQueue(context);

        addRequestToQueue(API_URL);
    }

    protected void addRequestToQueue(String API_URL){
        StringRequest stringRequest;

        stringRequest = createRequest(API_URL);

        //Adding the request to request queue
        requestQueue.add(stringRequest);
    }

    protected String generateURL(String pageName){
        StringBuilder url = new StringBuilder();

        url.append(context.getString(R.string.base_url));
        url.append("&api_key=");
        url.append(context.getString(R.string.api_key));
        url.append("&page=");
        url.append(pageName);
        url.append("&format=json&nojsoncallback=1");

        return url.toString();
    }

    protected StringRequest createRequest(String API_URL){
        //Creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LinkedHashMap<String, String> pageInfoMap;
                        List<LinkedHashMap<String, String>> responseList;

                        JsonParser jsonParser = new JsonParser();
                        pageInfoMap = jsonParser.getPageInfo(response);
                        Log.i(LOG_TAG, "pageInfoMap - " + pageInfoMap);
                        responseList = jsonParser.parseResponseData(response);
                        Log.i(LOG_TAG, "responseList - " + responseList);

                        generatePhotoURLs(responseList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if error occurs
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        return stringRequest;
    }

    protected void generatePhotoURLs(List<LinkedHashMap<String, String>> responseList){
        //https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg
        List<String> imageUrlList = new ArrayList<>();

        for (LinkedHashMap<String, String> entry : responseList) {
            StringBuilder url = new StringBuilder();
            url.append("https://farm")
                .append(entry.get("farm"))
                .append(".staticflickr.com/")
                .append(entry.get("server"))
                .append("/")
                .append(entry.get("id"))
                .append("_")
                .append(entry.get("secret"))
                .append(".jpg");
            imageUrlList.add(url.toString());
        }

        Log.i(LOG_TAG, "imageUrlList - " + imageUrlList);
        view.updateRecyclerView(imageUrlList);
    }
}
