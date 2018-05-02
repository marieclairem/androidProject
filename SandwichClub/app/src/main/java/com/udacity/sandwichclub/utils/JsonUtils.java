package com.udacity.sandwichclub.utils;
import android.util.Log;
import android.text.TextUtils;
import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String TAG = JsonUtils.class.getSimpleName();

    private JsonUtils() {
    }

    public static Sandwich parseSandwichJson(String sandwichJson) {

        if (TextUtils.isEmpty(sandwichJson)) {
            return null;
        }

        // Initialize a Sandwich Object
        Sandwich sandwichObj = null;


        try {


            JSONObject mainJsonObject = new JSONObject(sandwichJson);


            JSONObject name = mainJsonObject.getJSONObject("name");


            String mainName = name.getString("mainName");

            List<String> alsoKnownAsList = new ArrayList<>();

            JSONArray alsoKnownAsArray = name.getJSONArray("alsoKnownAs");
            int countAlsoKnownAsArray = alsoKnownAsArray.length();

            for (int i = 0; i < countAlsoKnownAsArray; i++) {
                String otherName = alsoKnownAsArray.getString(i);
                alsoKnownAsList.add(otherName);
            }


            String placeOfOrigin = mainJsonObject.getString("placeOfOrigin");


            String description = mainJsonObject.getString("description");


            String image = mainJsonObject.getString("image");

            List<String> ingredientsList = new ArrayList<>();

            JSONArray ingredientsArray = mainJsonObject.getJSONArray("ingredients");
            int countIngredientsArray = ingredientsArray.length();


            for (int j = 0; j < countIngredientsArray; j++) {
                String ingredient = ingredientsArray.getString(j);
                ingredientsList.add(ingredient);
            }


            sandwichObj = new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);


        } catch (JSONException e) {

            Log.e("JsonUtils", "Problem parsing the Sandwich JSON Object", e);
        }


        return sandwichObj;
    }
}
