package com.udacity.sandwichclub.utils;
import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final  String SANDWICH_NAME = "name";
    private static final  String SANDWICH_MAIN_NAME = "mainName";
    private static final  String SANDWICH_ALSO_KNOWN_AS = "alsoKnownAs";
    private static final  String SANDWICH_PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final  String SANDWICH_DESCRIPTION = "description";
    private static final  String SANDWICH_IMAGE = "image";
    private static final  String SANDWICH_INGREDIENTS = "ingredients";
    private static final String ERROR_MESSAGE = "detail_error_message";

    public static Sandwich parseSandwichJson(String json)throws JSONException {

        JSONObject mainJsonObject = new JSONObject(json);
        if (mainJsonObject.has(ERROR_MESSAGE)) {
            int error = mainJsonObject.getInt(ERROR_MESSAGE);
            switch (error) {
                case HttpURLConnection.HTTP_OK:
                    break;
                   default:
                    return null;
            }
        }
        List<String> alsoKnownAsList = new ArrayList<>();
        List<String> ingredientsList = new ArrayList<>();
         JSONObject sandwichName = mainJsonObject.getJSONObject(SANDWICH_NAME);
       String mainName = sandwichName.optString(SANDWICH_MAIN_NAME);
        alsoKnownAsList = jsonArrayList(sandwichName.getJSONArray(SANDWICH_ALSO_KNOWN_AS));
        ingredientsList = jsonArrayList(mainJsonObject.getJSONArray(SANDWICH_INGREDIENTS));
          String sandwichPlaceOfOrigin = mainJsonObject.getString(SANDWICH_PLACE_OF_ORIGIN);
        String  sandwichDescription = mainJsonObject.getString(SANDWICH_DESCRIPTION);
        String sandwichImage = mainJsonObject.getString(SANDWICH_IMAGE);

        return new Sandwich(mainName,alsoKnownAsList,sandwichPlaceOfOrigin,sandwichDescription,sandwichImage,ingredientsList);
        }

    private static List<String> jsonArrayList(JSONArray sandwichJsonArray) throws JSONException{
        List<String> sandwichList = new ArrayList<>(0);

        while (sandwichJsonArray!=null){
              for (int j=0; j<sandwichJsonArray.length();j++)
              {
                  sandwichList.add(sandwichJsonArray.getString(j));
                            }
        return sandwichList;
    }
        return sandwichList;
    }
}
