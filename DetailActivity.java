package com.udacity.sandwichclub;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private Sandwich sandwichObj;
    private TextView sandwich_also_know_tv;
    private TextView sandwich_origin_tv;
    private TextView sandwich_description_tv;
    private TextView sandwich_ingredients_tv;
private ImageView sandwich_Image_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        sandwich_Image_tv = findViewById(R.id.image_iv);
        sandwich_also_know_tv = findViewById(R.id.also_known_tv);
        sandwich_origin_tv = findViewById(R.id.origin_tv);
        sandwich_description_tv = findViewById(R.id.description_tv);
        sandwich_ingredients_tv = findViewById(R.id.ingredients_tv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }


        assert intent != null;
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        try {
            sandwichObj = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (sandwiches == null) {
            closeOnError();
            return;
        }

        populateUI();


        setTitle(sandwichObj.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }


    private void populateUI() {

        Picasso.with(this)
                .load(sandwichObj.getImage())
                .into(sandwich_Image_tv);

        if (sandwichObj.getPlaceOfOrigin().isEmpty()) {
            sandwich_origin_tv.setText(R.string.detail_error_message);
        } else {
            sandwich_origin_tv.setText(sandwichObj.getPlaceOfOrigin());
        }
        if (sandwichObj.getAlsoKnownAs().isEmpty()) {
            sandwich_also_know_tv.setText(R.string.detail_error_message);
        } else if (sandwichObj.getAlsoKnownAs() != null){
             if(sandwichObj.getAlsoKnownAs().size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(sandwichObj.getAlsoKnownAs().get(0));

            for (int j = 1; j < sandwichObj.getAlsoKnownAs().size(); j++) {
                stringBuilder.append("\n");
                stringBuilder.append(sandwichObj.getAlsoKnownAs().get(j));
            }
            sandwich_also_know_tv.setText(stringBuilder.toString());
        }

        sandwich_description_tv.setText(sandwichObj.getDescription());

        if (sandwichObj.getIngredients() != null){

            if( sandwichObj.getIngredients().size() > 0) {

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(sandwichObj.getIngredients().get(0));

                for (int j = 1; j < sandwichObj.getIngredients().size(); j++) {
                    stringBuilder.append("\n");

                    stringBuilder.append(sandwichObj.getIngredients().get(j));
                }
                sandwich_ingredients_tv.setText(stringBuilder.toString());
            }
        }
    }


    }


}



