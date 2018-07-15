package com.example.alimu.flickrapp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class JsonParser {
    private String LOG_TAG = JsonParser.class.getSimpleName();

    public LinkedHashMap<String, String> getPageInfo(String response){
        JSONObject photosObj = null;
        LinkedHashMap<String, String> pageInfoMap = new LinkedHashMap<>();

        try {
            //Getting json object from the response
            JSONObject mainObj = new JSONObject(response);

            if (!mainObj.isNull("photos")) {
                photosObj = new JSONObject(mainObj.getString("photos"));
            }

            if (!photosObj.isNull("page")) {
                pageInfoMap.put("page", photosObj.getString("page"));
            }

            if (!photosObj.isNull("pages")) {
                pageInfoMap.put("pages", photosObj.getString("pages"));
            }

            if (!photosObj.isNull("perpage")) {
                pageInfoMap.put("perPage", photosObj.getString("perpage"));
            }

            if (!photosObj.isNull("total")) {
                pageInfoMap.put("total", photosObj.getString("total"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return pageInfoMap;
    }
    
    public List<LinkedHashMap<String, String>> parseResponseData(String response) {
        JSONObject photosObj = null;
        JSONArray jsonArray = null;
        
        try {
            JSONObject mainObj = new JSONObject(response);

            if (!mainObj.isNull("photos")) {
                photosObj = new JSONObject(mainObj.getString("photos"));
            }

            if (!photosObj.isNull("photo")) {
                jsonArray = photosObj.getJSONArray("photo");
            }
        } catch (JSONException e) {
            Log.i(LOG_TAG,"parse error");
            e.printStackTrace();
        }
        
        return getResponseDetails(jsonArray);
    }

    private List<LinkedHashMap<String, String>> getResponseDetails(JSONArray jsonArray) {
        int detailsCount = jsonArray.length();
        List<LinkedHashMap<String, String>> responseList = new ArrayList<>();
        LinkedHashMap<String, String> responseMap;

        Log.i(LOG_TAG,"getResponseDetails detailsCount:"+detailsCount);
        
        for (int i = 0; i < detailsCount; i++) {
            try {
                responseMap = getResponseDetail((JSONObject) jsonArray.get(i));
                responseList.add(responseMap);
                //Log.i(LOG_TAG, "Adding places");

            } catch (JSONException e) {
                Log.i(LOG_TAG,"Error in Adding places");
                e.printStackTrace();
            }
        }
        return responseList;
    }

    private LinkedHashMap<String, String> getResponseDetail(JSONObject responseDetailJson) {
        LinkedHashMap<String, String> responseMap = new LinkedHashMap<String, String>();

        String id = "-1";
        String owner = "-1";
        String secret = "-1";
        String server = "-1";
        String farm = "-1";
        String title = "-1";
        String isPublic = "-1";
        String isFriend = "-1";
        String isFamily = "-1";

        try {
            if (!responseDetailJson.isNull("id")) {
                id = responseDetailJson.getString("id");
            }

            if (!responseDetailJson.isNull("owner")) {
                owner = responseDetailJson.getString("owner");
            }

            if (!responseDetailJson.isNull("secret")) {
                secret = responseDetailJson.getString("secret");
            }

            if (!responseDetailJson.isNull("server")) {
                server = responseDetailJson.getString("server");
            }

            if (!responseDetailJson.isNull("farm")) {
                farm = responseDetailJson.getString("farm");
            }

            if (!responseDetailJson.isNull("title")) {
                title = responseDetailJson.getString("title");
            }

            if (!responseDetailJson.isNull("ispublic")) {
                isPublic = responseDetailJson.getString("ispublic");
            }

            if (!responseDetailJson.isNull("isfriend")) {
                isFriend = responseDetailJson.getString("isfriend");
            }

            if (!responseDetailJson.isNull("isfamily")) {
                isFamily = responseDetailJson.getString("isfamily");
            }

            responseMap.put("id", id);
            responseMap.put("owner", owner);
            responseMap.put("secret", secret);
            responseMap.put("server", server);
            responseMap.put("farm", farm);
            responseMap.put("title", title);
            responseMap.put("isPublic", isPublic);
            responseMap.put("isFriend", isFriend);
            responseMap.put("isFamily", isFamily);

        } catch (JSONException e) {
            Log.i(LOG_TAG,"Error");
            e.printStackTrace();
        }
        
        return responseMap;
    }
}
