package com.udacity.sandwichclub;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private Sandwich sandwichObj;
    //ActivityDetailBiding bind;
    //private TextView sandwich_also_know_tv;
    @BindView(R.id.also_known_tv)
    TextView sandwich_also_know_tv;
    //private TextView sandwich_origin_tv;
    @BindView(R.id.origin_tv)
    TextView sandwich_origin_tv;
    //private TextView sandwich_description_tv;
    @BindView(R.id.description_tv)
    TextView sandwich_description_tv;
    //private TextView sandwich_ingredients_tv;
    @BindView(R.id.ingredients_tv)
    TextView sandwich_ingredients_tv;
    @BindView(R.id.image_iv)
    TextView sandwich_Image_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // @BindView(R.id.also_known_tv)sandwich_also_know_tv;
        //  ActivityDetailBiding binding= DataBindingUtil.setContentView(this,R.layout.activity_detail);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        // ImageView sandwich_Image_tv = findViewById(R.id.image_iv);
        //  ImageView sandwich_image_view_error_tv=findViewById(R.id.image_view_error_tv);

        //  sandwich_also_know_tv = findViewById(R.id.also_known_tv);
        //  sandwich_origin_tv = findViewById(R.id.origin_tv);
        // sandwich_description_tv = findViewById(R.id.description_tv);
        //  sandwich_ingredients_tv = findViewById(R.id.ingredients_tv);

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

        sandwichObj = JsonUtils.parseSandwichJson(json);
        if (sandwichObj == null) {
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this).setLoggingEnabled(true);


       /* Picasso.with(this)
                .load(sandwichObj.getImage())
                //.into();
                  setTitle(sandwichObj.getMainName());
*/

//Picasso.with(this).load(imgUrl).into(detailsBinding.imgViewId)`.
      /*  Picasso
                .with(this)
                .load(sandwichObj.getImage())
                //.placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.no_image_available) // will be displayed if the image cannot be loaded
                .into(sandwich_image_view_error_tv);*/
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    //populating the fields from data received as JSON
    private void populateUI() {
        if (sandwichObj.getPlaceOfOrigin().isEmpty()) {
            sandwich_origin_tv.setText(R.string.orgin_error_message);
        } else {
            sandwich_origin_tv.setText(sandwichObj.getPlaceOfOrigin());
        }


        if (sandwichObj.getAlsoKnownAs().isEmpty()) {
            sandwich_also_know_tv.setText(R.string.other_name_error_message);
        } else if ((sandwichObj.getAlsoKnownAs() != null) && (sandwichObj.getAlsoKnownAs().size() > 0)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(sandwichObj.getAlsoKnownAs().get(0));
            for (int j = 1; j < sandwichObj.getAlsoKnownAs().size(); j++) {
                stringBuilder.append("\n");
                stringBuilder.append(sandwichObj.getAlsoKnownAs().get(j));
            }
            sandwich_also_know_tv.setText(stringBuilder.toString());
        }

        if (sandwichObj.getDescription().isEmpty()) {
            sandwich_description_tv.setText(R.string.description_error_message);
        } else {
            sandwich_description_tv.setText(sandwichObj.getDescription());
        }
        if ((sandwichObj.getIngredients() != null) && (sandwichObj.getIngredients().size() > 0)) {

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(sandwichObj.getIngredients().get(0));

            for (int j = 1; j < sandwichObj.getIngredients().size(); j++) {
                stringBuilder.append("\n");

                stringBuilder.append(sandwichObj.getIngredients().get(j));
            }
            sandwich_ingredients_tv.setText(stringBuilder.toString());
        } else {
            sandwich_ingredients_tv.setText(R.string.ingredients_error_message);
        }
    }


}






