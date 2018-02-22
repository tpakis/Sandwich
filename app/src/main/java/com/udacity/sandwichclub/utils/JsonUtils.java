package com.udacity.sandwichclub.utils;


import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    public static final String JSON_NAME = "name";
    public static final String JSON_MAINNAME = "mainName";
    public static final String JSON_ALSOKNOWNAS = "alsoKnownAs";
    public static final String JSON_INGREDIENTS = "ingredients";
    public static final String JSON_DESCRIPTION = "description";
    public static final String JSON_PLACEOFORIGIN = "placeOfOrigin";
    public static final String JSON_IMAGE = "image";
    public static final String TAG = "JsonUtils";

    /**
     *
     * @param json string of a specific sandwich Item
     * @return Sandwich object
     */
    public static Sandwich parseSandwichJson(String json) {
        List<String> mAlsoKnownAs;
        List<String> mIngredients;
        Sandwich sandwich = new Sandwich();
        try {
            JSONObject sandwichJson = new JSONObject(json);
            JSONObject sandwichName = sandwichJson.getJSONObject(JSON_NAME);
            String mName = sandwichName.optString(JSON_MAINNAME);
            JSONArray sandwichKnownArraysandwichName = sandwichName.getJSONArray(JSON_ALSOKNOWNAS);
            JSONArray sandwichIngredients = sandwichJson.getJSONArray(JSON_INGREDIENTS);
            mAlsoKnownAs = convertJSONArrayToList(sandwichKnownArraysandwichName);
            mIngredients = convertJSONArrayToList(sandwichIngredients);
            String mDescription = sandwichJson.optString(JSON_DESCRIPTION);
            String mPlaceOfOrigin = sandwichJson.optString(JSON_PLACEOFORIGIN);
            String mImage = sandwichJson.optString(JSON_IMAGE);
            sandwich = new Sandwich(mName, mAlsoKnownAs, mPlaceOfOrigin, mDescription, mImage, mIngredients);
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(TAG,"Problem parsing JSON object" , e);
        }
        return sandwich;
    }

    /**
     *
     * @param jsonArray A JSON Array containg a list of Strings
     * @return a List of Strings
     */
    private static List<String> convertJSONArrayToList(JSONArray jsonArray) {
        List<String> retList = new ArrayList<String>();
        try {
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    retList.add(jsonArray.getString(i));
                }
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("convertJSONArrayToList", "Problem parsing JSON object", e);
        }
        return retList;
    }
}
