package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final int MINIMUM_TEXT_SIZE = 2;
    @BindView(R.id.image_iv)
    ImageView ingredientsIv;
    @BindView(R.id.name_label)
    TextView nameLabel;
    @BindView(R.id.also_known_tv)
    TextView alsoKnownTv;
    @BindView(R.id.origin_tv)
    TextView originTv;
    @BindView(R.id.ingredients_tv)
    TextView ingredientsTv;
    @BindView(R.id.description_tv)
    TextView descriptionTv;
    @BindString(R.string.not_apllicable)
    String notApplicable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        Log.v("sandwich", sandwich.toString());
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        Picasso.with(this)
                .load(sandwich.getImage())
                .error(R.mipmap.ic_launcher)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
        nameLabel.setText(sandwich.getMainName());
        alsoKnownTv.setText(sandwich.getAlsoKnownAs()
                .toString()
                .replace("[", "")
                .replace("]", ""));
        if (alsoKnownTv.length() < MINIMUM_TEXT_SIZE) {
            alsoKnownTv.setText(notApplicable);
        }
        ingredientsTv.setText(sandwich.getIngredients().toString()
                .replace("[", "")
                .replace("]", ""));
        originTv.setText(sandwich.getPlaceOfOrigin());
        if (originTv.length() < MINIMUM_TEXT_SIZE) {
            originTv.setText(notApplicable);
        }
        descriptionTv.setText(sandwich.getDescription());
    }
}
