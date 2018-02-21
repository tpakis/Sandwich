package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        List<String> mAlsoKnownAs = new ArrayList<>();
        List<String> mIngredients = new ArrayList<>();
        Sandwich sandwich = new Sandwich();
        try {
            JSONObject sandwichJson = new JSONObject(json);
            JSONObject sandwichName = sandwichJson.getJSONObject("name");
            String mName = sandwichName.getString("mainName");
            JSONArray sandwichKnownArraysandwichName = sandwichName.getJSONArray("alsoKnownAs");
            JSONArray sandwichIngredients = sandwichJson.getJSONArray("ingredients");
            mAlsoKnownAs = convertJSONArrayToList(sandwichKnownArraysandwichName);
            mIngredients = convertJSONArrayToList(sandwichIngredients);
            String mDescription = sandwichJson.getString("description");
            String mPlaceOfOrigin = sandwichJson.getString("placeOfOrigin");
            String mImage = sandwichJson.getString("image");
            sandwich = new Sandwich(mName, mAlsoKnownAs, mPlaceOfOrigin, mDescription, mImage, mIngredients);
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("JSONUtils", "Problem parsing JSON object", e);
        }
        return sandwich;
    }

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
