package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        String mainName;
        List<String> alsoKnownAs = new ArrayList<String>();
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients = new ArrayList<String>();

        final String SANDWICH_NAME = "name";
        final String SANDWICH_MAIN_NAME = "mainName";
        final String SANDWICH_AKA = "alsoKnownAs";
        final String SANDWICH_ORIGIN = "placeOfOrigin";
        final String SANDWICH_DESCRIPTION = "description";
        final String SANDWICH_IMG = "image";
        final String SANDWICH_INGREDIENTS = "ingredients";

        JSONObject sandwichJson = new JSONObject(json);
        JSONObject name = sandwichJson.getJSONObject(SANDWICH_NAME);
        mainName = name.getString(SANDWICH_MAIN_NAME);
        JSONArray akaArray = name.getJSONArray(SANDWICH_AKA);
        if (akaArray.length() > 0) {
            for (int i = 0; i < akaArray.length(); i++) {
                alsoKnownAs.add(akaArray.getString(i));
            }
        }
        placeOfOrigin = sandwichJson.getString(SANDWICH_ORIGIN);
        description = sandwichJson.getString(SANDWICH_DESCRIPTION);
        image = sandwichJson.getString(SANDWICH_IMG);
        JSONArray ingredientArray = sandwichJson.getJSONArray(SANDWICH_INGREDIENTS);
        if (ingredientArray.length() > 0) {
            for (int i = 0; i < ingredientArray.length(); i++) {
                ingredients.add(ingredientArray.getString(i));
            }
        }

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
