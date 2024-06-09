package com.example.recify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class RecipePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_page);

        ImageView recImage = (ImageView) findViewById(R.id.recImage);
        TextView recName = (TextView) findViewById(R.id.recName);
        TextView recDescription = (TextView) findViewById(R.id.recDescription);
        TextView recContents = (TextView) findViewById(R.id.recContents);

        recImage.setImageResource(Integer.parseInt(getIntent().getStringExtra("image")));
        recName.setText(getIntent().getStringExtra("name"));
        recDescription.setText(getIntent().getStringExtra("description"));
        String[] ingredients = getIntent().getStringArrayExtra("ingredients");
        String[] quantities = getIntent().getStringArrayExtra("quantities");
        int ingredientCount = ingredients.length;
        StringBuilder contents = new StringBuilder(getIntent().getStringExtra("nutrition") + "\nIngredients:\n\n");
        for (int i = 0; i < ingredientCount; i++) {
            switch (ingredients[i]) {
                case "olive oil":
                    contents.append("olive oil/vegetable oil - " + quantities[i] + "\n");
                    break;
                case "butter":
                    contents.append("butter/oil - " + quantities[i] + "\n");
                    break;
                default:
                    contents.append(ingredients[i] + " - " + quantities[i] + "\n");
                    break;
            }
        }
        contents.append("\n" + getIntent().getStringExtra("procedure") + "\n");
        recContents.setText(contents.toString());
    }
}