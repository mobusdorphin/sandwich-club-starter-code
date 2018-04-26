package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView originTextView = (TextView) findViewById(R.id.origin_tv);
        TextView descriptionTextView = (TextView) findViewById(R.id.description_tv);
        LinearLayout akaList = (LinearLayout) findViewById(R.id.aka_ll);
        LinearLayout ingredientList = (LinearLayout) findViewById(R.id.ingredients_ll);
//        ListView akaListView = (ListView) findViewById(R.id.also_known_lv);

        originTextView.setText(
                sandwich.getPlaceOfOrigin().equals("")?"Unknown":sandwich.getPlaceOfOrigin());
        descriptionTextView.setText(sandwich.getDescription());
        if (!sandwich.getAlsoKnownAs().isEmpty()) {
            for (String s : sandwich.getAlsoKnownAs()) {
                TextView textView = new TextView(this);
                textView.setText(s);
                akaList.addView(textView);
            }
        } else {
            TextView textView = new TextView(this);
            textView.setText("N/A");
            akaList.addView(textView);
        }

        if (!sandwich.getIngredients().isEmpty()) {
            for (String s : sandwich.getIngredients()) {
                TextView textView = new TextView(this);
                textView.setText(s);
                ingredientList.addView(textView);
            }
        } else {
            TextView textView = new TextView(this);
            textView.setText("N/A");
            ingredientList.addView(textView);
        }
///        List<String> ingredientArray = (!sandwich.getIngredients().isEmpty()) ?
//                sandwich.getIngredients() : Collections.singletonList("N/A");
//        ingredientsListView.setAdapter(new ArrayAdapter<>(this,
//                            android.R.layout.simple_list_item_1, ingredientArray));

//        List<String> akaArray = (!sandwich.getAlsoKnownAs().isEmpty())?
//                sandwich.getAlsoKnownAs() : Collections.singletonList("N/A");
//        akaListView.setAdapter(new ArrayAdapter<>(this,
//                android.R.layout.simple_list_item_1, akaArray));

    }

}
