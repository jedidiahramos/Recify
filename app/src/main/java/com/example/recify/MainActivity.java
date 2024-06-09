package com.example.recify;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Recipe[] recipeDatabase;
    Ingredient[] ingredientDatabase;
    Trie trie;
    ArrayList<Recipe> suggestionList;
    EditText srchIngredient;
    EditText srchAmount;
    ArrayList<Ingredient> userIngredients;
    TextView txtRecipeCount;
    LinearLayout linResults;
    LinearLayout linIngredients;
    Button bubble1;
    Button bubble2;
    Button bubble3;
    Random rand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //RECIPE DATABASE
        recipeDatabase = new Recipe[] {
            new Recipe("Fish Fillet",R.drawable.fishfillet,
                    new String[]{"fish","flour","oil","pepper"},
                    new String[]{"1 pound","1/2 cup","2 cups","1/4 teaspoon"},
                    "The fillets fry up perfectly cooked inside, with a beautiful golden crust.",
                    "Nutrition Facts (per serving):\n\n" +
                            "Calories: 340\n" +
                            "Fat: 19g\n" +
                            "Carbs: 12g\n" +
                            "Protein: 29g\n",
                    "Procedure:\n\n" +
                            "1. Gather the ingredients.\n\n" +
                            "2. In a shallow bowl or deep plate, combine the flour, paprika, salt, and pepper and mix well.\n\n" +
                            "3. Dip 1 or 2 fish fillets into the flour mixture. Make sure they are completely coated on all " +
                            "sides and gently shake off any excess. Repeat the process with the remaining fillets.\n\n" +
                            "4. Pour oil into a skillet and heat over medium-high heat to 375 F.\n\n" +
                            "5. Working in batches, fry the fish in the hot oil for about 6 minutes on each side, or until " +
                            "golden brown, crispy, and cooked through. Remove and set on a cooling rack to let drain.\n\n" +
                            "6. Serve hot with lemon wedges, tartar sauce, or homemade remoulade sauce, if desired. Enjoy."),
            new Recipe("Fried Rice",R.drawable.friedrice,
                    new String[]{"rice","oil","onion","garlic","egg"},
                    new String[]{"4 cups","1/2 teaspoon","3","3 cloves","2"},
                    "This easy fried rice recipe only takes 15 minutes to make.",
                    "Nutrition Facts (per serving):\n\n" +
                            "Calories: 152\n" +
                            "Fat: 7g\n" +
                            "Carbs: 19g\n" +
                            "Protein: 4g\n",
                    "Procedure:\n\n" +
                            "1. Heat 1/2 tablespoon of butter in a large saute pan over medium-high heat until melted. " +
                            "Add egg, and cook until scrambled, stirring occasionally. Remove egg, and transfer to a " +
                            "separate plate.\n\n" +
                            "2. Add an additional 1 tablespoon butter to the pan and heat until melted. " +
                            "Add carrots, onion, peas and garlic, and season with a generous pinch of salt and pepper. " +
                            "Saute for about 5 minutes or until the onion and carrots are soft. " +
                            "Increase heat to high, add in the remaining 1 1/2 tablespoons of butter, and stir until melted. " +
                            "Immediately add the rice, green onions, soy sauce and oyster sauce (if using), " +
                            "and stir until combined. Continue sauteing for an additional 3 minutes to fry the rice, " +
                            "stirring occasionally. Then add in the eggs and stir to combine. " +
                            "Remove from heat, and stir in the sesame oil until combined. " +
                            "Taste and season with extra soy sauce, if needed.\n\n" +
                            "3. Serve immediately, or refrigerate in a sealed container for up to 3 days."),
            new Recipe("Sushi",R.drawable.sushi,
                    new String[]{"rice","vinegar","seaweed","crab meat","avocado","cucumber"},
                    new String[]{"2/3 cup","3 tablespoons","4 sheets","1/2 pound","1","1/2"},
                    "Sushi rolls can be filled with any ingredients you choose.",
                    "Nutrition Facts (per serving):\n\n" +
                            "Calories: 152\n" +
                            "Fat: 4g\n" +
                            "Carbs: 26g\n" +
                            "Protein: 4g\n",
                    "Procedure:\n\n" +
                            "1. Preheat the oven to 300 degrees F (150 degrees C).\n\n" +
                            "2. Bring water to a boil in a medium pot; stir in rice. Reduce heat to medium-low, cover, " +
                            "and simmer until rice is tender and water has been absorbed, 20 to 25 minutes.\n\n" +
                            "3. Mix rice vinegar, sugar, and salt in a small bowl. Gently stir into cooked rice in the pot and set aside.\n\n" +
                            "4. Lay nori sheets on a baking sheet.\n\n" +
                            "5. Heat nori in the preheated oven until warm, 1 to 2 minutes.\n\n" +
                            "6. Center 1 nori sheet on a bamboo sushi mat. Use wet hands to spread a thin layer of rice on top. " +
                            "Arrange 1/4 of the crabmeat, avocado, cucumber, and pickled ginger over rice in a line down the center. " +
                            "Lift one end of the mat and roll it tightly over filling to make a complete roll. " +
                            "Repeat with remaining ingredients.\n\n" +
                            "7. Use a wet, sharp knife to cut each roll into 4 to 6 slices."),
            new Recipe("Poached Egg",R.drawable.poachedegg,
                    new String[]{"egg"},
                    new String[]{"1"},
                    "Cook it perfectly so the whites are set and yolk is still runny.",
                    "Nutrition Facts (per serving):\n\n" +
                            "Calories: 72\n" +
                            "Fat: 5g\n" +
                            "Carbs: 0g\n" +
                            "Protein: 6g\n",
                    "Procedure:\n\n" +
                            "1. Bring a pot of water to a gentle boil, then salt the water. " +
                            "Meanwhile, crack an egg (or 2, or 3) into a small cup.\n\n" +
                            "2. With a spoon, begin stirring the boiling water in a large, circular motion.\n\n" +
                            "3. When the water is swirling like a tornado, add the eggs. " +
                            "The swirling water will help the egg white wrap around itself as it cooks.\n\n" +
                            "4. Cook for about 2 1/2 to 3 minutes.\n\n" +
                            "5. Using a slotted spoon, remove the egg (or eggs) to a plate."),
            new Recipe("Scrambled Egg",R.drawable.scrambledegg,
                    new String[]{"egg","oil"},
                    new String[]{"1","2 tablespoons"},
                    "A quick breakfast packed with protein.",
                    "Nutrition Facts (per serving):\n\n" +
                            "Calories: 168\n" +
                            "Fat: 12g\n" +
                            "Carbs: 1g\n" +
                            "Protein: 13g\n",
                    "Procedure:\n\n" +
                            "1. Crack eggs into a bowl, add a pinch of salt and whisk until well blended.\n\n" +
                            "2. When the butter begins to bubble, pour in the eggs and immediately use a silicone spatula to " +
                            "swirl in small circles around the pan, without stopping, until the eggs look slightly thickened " +
                            "and very small curds begin to form, about 30 seconds.\n\n" +
                            "3. Change from making circles to making long sweeps across the pan until you see larger, creamy curds; about 20 seconds.\n\n" +
                            "4. When the eggs are softly set and slightly runny in places, remove the pan from the heat and leave for a " +
                            "few seconds to finish cooking. Give a final stir and serve immediately. Serve with an extra sprinkle of salt, " +
                            "a grind of black pepper and a few fresh chopped herbs (if desired)."),
            new Recipe("Paella",R.drawable.paella,
                    new String[]{"rice","olive oil","onion","tomato","chicken","shrimp"},
                    new String[]{"2 cups","1/4 cup","1","3","4 pieces","1/2 pound"},
                    "Paella is a classic Spanish dish featuring saffron rice with chorizo, chicken, and seafood.",
                    "Nutrition Facts (per serving):\n\n" +
                            "Calories: 736\n" +
                            "Fat: 35g\n" +
                            "Carbs: 46g\n" +
                            "Protein: 56g\n",
                    "Procedure:\n\n" +
                            "1. Add olive oil to a skillet over medium heat. Add the onion, bell peppers and garlic and cook until " +
                            "onion is translucent. Add chopped tomato, bay leaf, paprika, saffron salt and pepper. " +
                            "Stir and cook for 5 minutes. Add white wine and cook for 10 minutes. Taste and add salt if needed.\n\n" +
                            "2. Add chicken pieces, 2 tablespoons chopped parsley and rice to the pot. Cook for 1 minute.\n\n" +
                            "3. Pour the broth slowly all around the pan and jiggle the pan to get the rice into an even layer. " +
                            "(Do not stir the mixture going forward).\n\n" +
                            "4. Bring mixture to a boil. Reduce heat to medium low. Give the pan a gentle shake back and forth just once " +
                            "or twice during cooking. (We do not ever stir the rice, so that a crispy crust forms at the bottom, " +
                            "called a socarrat).\n\n" +
                            "5. Cook for about 15-18 minutes (uncovered), then nestle the shrimp, mussels and calamari into the mixture, " +
                            "sprinkle peas on top and continue to cook (without stirring) for about 5 more minutes. " +
                            "Watch for most of the liquid to be absorbed and the rice at the top nearly tender. " +
                            "(If for some reason your rice is still not cooked, add 1/4 cup more water or broth and continue cooking).\n\n" +
                            "6. Remove pan from heat and cover pan with a lid or tinfoil. Place a kitchen towel over the lid and allow to " +
                            "rest for 10 minutes.\n\n" +
                            "7. Garnish with fresh parsley and lemon slices. Serve."),
            new Recipe("Brownies",R.drawable.brownies,
                    new String[]{"flour","sugar","egg","oil","vanilla"},
                    new String[]{"3/4 cup","1 1/2 cups","2","1/2 cup","1/2 teaspoon"},
                    "With crispy edges, fudgy middles, and rich chocolate flavor, these homemade brownies will disappear in no time.",
                    "Nutrition Facts (per serving):\n\n" +
                            "Calories: 183\n" +
                            "Fat: 9g\n" +
                            "Carbs: 26g\n" +
                            "Protein: 2g\n",
                    "Procedure:\n\n" +
                            "1. Preheat the oven to 325 F. Lightly spray an 8x8 baking dish (not a 9x9 dish or your brownies will " +
                            "overcook) with cooking spray and line it with parchment paper. Spray the parchment paper.\n\n" +
                            "2. In a medium bowl, combine the sugar, flour, cocoa powder, powdered sugar, chocolate chips, and salt.\n\n" +
                            "3. In a large bowl, whisk together the eggs, olive oil, water, and vanilla.\n\n" +
                            "4. Sprinkle the dry mix over the wet mix and stir until just combined.\n\n" +
                            "5. Pour the batter into the prepared pan (it will be thick - that is ok) and use a spatula to smooth the top. " +
                            "Bake for 40 to 48 minutes, or until a toothpick comes out with only a few crumbs attached " +
                            "(note: it is better to pull the brownies out early than to leave them in too long). Cool completely before slicing. " +
                            "Store in an airtight container at room temperature for up to 3 days."),
            new Recipe("Cookies",R.drawable.cookies,
                    new String[]{"flour","sugar","brown sugar","butter","vanilla","chocolate chips"},
                    new String[]{"3 cups","1 cup","1 cup","1 cup","2 teaspoons","2 cups"},
                    "This chocolate chip cookie recipe makes delicious cookies with crisp edges and chewy middles.",
                    "Nutrition Facts (per serving):\n\n" +
                            "Calories: 146\n" +
                            "Fat: 8g\n" +
                            "Carbs: 19g\n" +
                            "Protein: 2g\n",
                    "Procedure:\n\n" +
                            "1. Heat oven to 375°F. In small bowl, mix flour, baking soda and salt; set aside.\n\n" +
                            "2. In large bowl, beat softened butter and sugars with electric mixer on medium speed, " +
                            "or mix with spoon about 1 minute or until fluffy, scraping side of bowl occasionally.\n\n" +
                            "3. Beat in egg and vanilla until smooth. Stir in flour mixture just until blended (dough will be stiff). " +
                            "Stir in chocolate chips and nuts.\n\n" +
                            "4. Onto ungreased cookie sheets, drop dough by rounded tablespoonfuls 2 inches apart.\n\n" +
                            "5. Bake 8 to 10 minutes or until light brown (centers will be soft). Cool 2 minutes; remove from cookie sheet " +
                            "to cooling rack. Cool completely, about 30 minutes. Store covered in airtight container."),
            new Recipe("French Toast",R.drawable.frenchtoast,
                    new String[]{"milk","egg","bread","butter"},
                    new String[]{"2/3 cup","2","6 slices","1 tablespoon"},
                    "This fabulous French toast recipe works with many types of bread.",
                    "Nutrition Facts (per serving):\n\n" +
                            "Calories: 240\n" +
                            "Fat: 6g\n" +
                            "Carbs: 34g\n" +
                            "Protein: 11g\n",
                    "Procedure:\n\n" +
                            "1. Gather all ingredients.\n" +
                            "2. Whisk milk, eggs, vanilla, cinnamon, and salt together in a shallow bowl.\n\n" +
                            "3. Lightly butter a griddle or skillet and heat over medium-high heat.\n\n" +
                            "4. Dunk bread in the egg mixture, soaking both sides.\n\n" +
                            "5. Transfer to the hot skillet and cook until golden, 3 to 4 minutes per side.\n\n" +
                            "6. Serve hot."),
            new Recipe("Pancakes",R.drawable.pancakes,
                    new String[]{"flour","sugar","milk","butter","egg"},
                    new String[]{"1 1/2 cups","1 tablespoon","1 1/4 cups","3 tablespoons","1"},
                    "Perfect pancakes are easier to make than you think.",
                    "Nutrition Facts (per serving):\n\n" +
                            "Calories: 158\n" +
                            "Fat: 6g\n" +
                            "Carbs: 22g\n" +
                            "Protein: 5g\n",
                    "Procedure:\n\n" +
                            "1. Sift flour, baking powder, sugar, and salt together in a large bowl. " +
                            "Make a well in the center and add milk, melted butter, and egg; mix until smooth.\n\n" +
                            "2. Heat a lightly oiled griddle or pan over medium-high heat. Pour or scoop the batter onto the griddle, " +
                            "using approximately 1/4 cup for each pancake; cook until bubbles form and the edges are dry, " +
                            "about 2 to 3 minutes. Flip and cook until browned on the other side. Repeat with remaining batter.")
        };

        //SUGGESTION LIST
        suggestionList = new ArrayList<>();

        //USER INGREDIENTS
        userIngredients = new ArrayList<>();

        //INGREDIENT ARRAYLIST
        ArrayList<String> ingredientList = new ArrayList<>();
        for (Recipe recipe : recipeDatabase) {
            for (String ingredient : recipe.ingredients) {
                //PREVENT DUPLICATES
                if (!ingredientList.contains(ingredient)) {
                    ingredientList.add(ingredient);
                }
            }
        }

        //TRIE DICTIONARY
        trie = new Trie();
        trie.root = new TrieNode();

        //INGREDIENT DATABASE
        int ingredientListSize = ingredientList.size();
        ingredientDatabase = new Ingredient[ingredientListSize];
        for (int i = 0; i < ingredientListSize; i++) {
            String ingredientName = ingredientList.get(i);
            ArrayList<Recipe> recipeList = new ArrayList<>();
            //FIND INGREDIENTS IN RECIPE DATABASE
            for (Recipe recipe : recipeDatabase) {
                boolean recipe_contains_ingredient = false;
                for (String ingredient : recipe.ingredients) {
                    if (ingredient.equals(ingredientName)) {
                        recipe_contains_ingredient = true;
                        break;
                    }
                }
                if (recipe_contains_ingredient) {
                    recipeList.add(recipe);
                }
            }
            //ADD TO INGREDIENT DATABASE
            Ingredient ingredient;
            ingredient = new Ingredient(ingredientName);
            int recipeListSize = recipeList.size();
            ingredient.recipes = new Recipe[recipeListSize];
            ingredient.recipes = recipeList.toArray(ingredient.recipes);
            ingredientDatabase[i] = ingredient;
            //ADD TO DICTIONARY
            trie.insert(ingredient);
        }

        //DEALLOCATE INGREDIENT ARRAYLIST
        ingredientList.clear();

        //PREPARE UI
        rand = new Random();

        linResults = (LinearLayout) findViewById(R.id.linResults);
        linResults.removeAllViews();

        linIngredients = (LinearLayout) findViewById(R.id.linIngredients);
        linIngredients.removeAllViews();

        for (Recipe r : recipeDatabase) {
            View recipeCard = getLayoutInflater().inflate(R.layout.result_recipe_row, null);

            ImageView imgRecipe = (ImageView) recipeCard.findViewById(R.id.imgRecipe);
            TextView txtRecipe = (TextView) recipeCard.findViewById(R.id.txtRecipe);
            TextView txtMatch = (TextView) recipeCard.findViewById(R.id.txtMatch);
            TextView txtDescription = (TextView) recipeCard.findViewById(R.id.txtDescription);

            imgRecipe.setImageResource(r.recipeImage);
            txtRecipe.setText(r.recipeName);
            txtMatch.setText("0/" + Integer.toString(r.ingredientCount) + " matches");
            txtDescription.setText(r.recipeDescription);

            linResults.addView(recipeCard);

            Space spc = new Space(this);
            spc.setMinimumHeight(10);
            linResults.addView(spc);

            recipeCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this,RecipePage.class);
                    intent.putExtra("image",Integer.toString(r.recipeImage));
                    intent.putExtra("name",r.recipeName);
                    intent.putExtra("description",r.recipeDescription);
                    intent.putExtra("nutrition",r.nutrition);
                    intent.putExtra("ingredients",r.ingredients);
                    intent.putExtra("quantities",r.quantities);
                    intent.putExtra("procedure",r.procedure);
                    startActivity(intent);
                }
            });
        }

        txtRecipeCount = (TextView) findViewById(R.id.txtRecipeCount);
        txtRecipeCount.setText("You can make " + Integer.toString(recipeDatabase.length) + " recipes");


        for (Recipe r : suggestionList) {
            r.matchCount = 0;
            r.matchPercentage = 0;
        }

        bubble1 = (Button) findViewById(R.id.bubble1);
        bubble2 = (Button) findViewById(R.id.bubble2);
        bubble3 = (Button) findViewById(R.id.bubble3);
        bubble1.setText("rice");
        bubble2.setText("egg");
        bubble3.setText("oil");
        bubble1.setOnClickListener(this);
        bubble2.setOnClickListener(this);
        bubble3.setOnClickListener(this);

        Button btnRandom = (Button) findViewById(R.id.btnRandom);
        btnRandom.setOnClickListener(this);

        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        Button btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        srchIngredient = (EditText) findViewById(R.id.srchIngredient);
        srchIngredient.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //NO ACTION
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //NO ACTION
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //RESET SUGGESTIONS
                for (Recipe r : suggestionList) {
                    r.matchCount = 0;
                    r.matchPercentage = 0;
                }
                String input = srchIngredient.getText().toString().toLowerCase();
                Ingredient ingredient = trie.search(input);
                //TALLY MATCHES
                if (ingredient != null) {
                    bubble1.setText(ingredient.ingredientName);
                    if (!userIngredients.contains(ingredient)) {
                        for (Recipe r : ingredient.recipes) {
                            r.matchCount++;
                        }
                    }
                    for (Ingredient i : userIngredients) {
                        for (Recipe r : i.recipes) {
                            r.matchCount++;
                        }
                    }
                    suggestionList.clear();
                    for (Recipe r : recipeDatabase) {
                        if (r.matchCount > 0 && !suggestionList.contains(r)) {
                            suggestionList.add(r);
                            int divisor = userIngredients.size();
                            if (!userIngredients.contains(ingredient)) {
                                divisor++;
                            }
                            r.matchPercentage = 1.0 * r.matchCount / divisor;
                            r.matchPercentage *= 100;
                        }
                    }
                    int suggestionCount = suggestionList.size();
                    //SORT SUGGESTIONS
                    for (int i = 1; i < suggestionCount; i++) {
                        int j = i;
                        while (j > 0 && suggestionList.get(j).matchPercentage > suggestionList.get(j-1).matchPercentage) {
                            Collections.swap(suggestionList,j,j-1);
                            j--;
                        }
                    }
                    //DISPLAY SUGGESTIONS
                    linResults.removeAllViews();
                    for (Recipe r : suggestionList) {
                        View recipeCard = getLayoutInflater().inflate(R.layout.result_recipe_row, null);

                        ImageView imgRecipe = (ImageView) recipeCard.findViewById(R.id.imgRecipe);
                        TextView txtRecipe = (TextView) recipeCard.findViewById(R.id.txtRecipe);
                        TextView txtMatch = (TextView) recipeCard.findViewById(R.id.txtMatch);
                        TextView txtDescription = (TextView) recipeCard.findViewById(R.id.txtDescription);

                        imgRecipe.setImageResource(r.recipeImage);
                        txtRecipe.setText(r.recipeName);
                        txtMatch.setText(Integer.toString(r.matchCount) + "/" + Integer.toString(r.ingredientCount) + " matches");
                        txtDescription.setText(r.recipeDescription);

                        linResults.addView(recipeCard);

                        Space spc = new Space(MainActivity.this);
                        spc.setMinimumHeight(10);
                        linResults.addView(spc);

                        recipeCard.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(MainActivity.this,RecipePage.class);
                                intent.putExtra("image",Integer.toString(r.recipeImage));
                                intent.putExtra("name",r.recipeName);
                                intent.putExtra("description",r.recipeDescription);
                                intent.putExtra("nutrition",r.nutrition);
                                intent.putExtra("ingredients",r.ingredients);
                                intent.putExtra("quantities",r.quantities);
                                intent.putExtra("procedure",r.procedure);
                                startActivity(intent);
                            }
                        });
                    }
                    txtRecipeCount.setText("You can make " + Integer.toString(suggestionList.size()) + " recipes");
                }
            }
        });
        srchAmount = (EditText) findViewById(R.id.srchAmount);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btnAdd:
                String ingredient1 = srchIngredient.getText().toString().toLowerCase();
                String amount1 = srchAmount.getText().toString();

                Ingredient ingredient = trie.search(ingredient1);

                if (ingredient1.length() == 0 || ingredient == null || userIngredients.contains(ingredient)) {
                    return;
                }

                ingredient1 = ingredient.ingredientName;

                Space spc = new Space(this);
                spc.setMinimumHeight(10);
                linIngredients.addView(spc);

                View ingredientCard = getLayoutInflater().inflate(R.layout.user_ingredient_row, null);

                TextView cardIngredient = (TextView) ingredientCard.findViewById(R.id.txtIngredient);
                cardIngredient.setText(ingredient1);
                TextView cardAmount = (TextView) ingredientCard.findViewById(R.id.txtAmount);
                cardAmount.setText(amount1);
                Button cardTrash = (Button) ingredientCard.findViewById(R.id.btnTrash);

                linIngredients.addView(ingredientCard);
                Toast.makeText(MainActivity.this,"Ingredient added",Toast.LENGTH_SHORT).show();

                srchIngredient.setText("");
                srchAmount.setText("");

                cardTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        linIngredients.removeView(spc);
                        linIngredients.removeView(ingredientCard);
                        userIngredients.remove(ingredient);

                        //RESET SUGGESTIONS
                        for (Recipe r : suggestionList) {
                            r.matchCount = 0;
                            r.matchPercentage = 0;
                        }
                        String input = srchIngredient.getText().toString().toLowerCase();
                        Ingredient ingredient = trie.search(input);
                        //TALLY MATCHES
                        if (ingredient != null) {
                            bubble1.setText(ingredient.ingredientName);
                            for (Recipe r : ingredient.recipes) {
                                r.matchCount++;
                            }
                        }
                        for (Ingredient i : userIngredients) {
                            for (Recipe r : i.recipes) {
                                r.matchCount++;
                            }
                        }
                        suggestionList.clear();
                        for (Recipe r : recipeDatabase) {
                            if (r.matchCount > 0 && !suggestionList.contains(r)) {
                                suggestionList.add(r);
                                int divisor = userIngredients.size();
                                if (ingredient != null && !userIngredients.contains(ingredient)) {
                                    divisor++;
                                }
                                r.matchPercentage = 1.0 * r.matchCount / divisor;
                                r.matchPercentage *= 100;
                            }
                        }
                        int suggestionCount = suggestionList.size();
                        //SORT SUGGESTIONS
                        for (int i = 1; i < suggestionCount; i++) {
                            int j = i - 1;
                            while (j >= 0 && suggestionList.get(i).matchPercentage > suggestionList.get(j).matchPercentage) {
                                Collections.swap(suggestionList,j+1,j);
                                j--;
                            }
                        }
                        //DISPLAY SUGGESTIONS
                        linResults.removeAllViews();
                        for (Recipe r : suggestionList) {
                            View recipeCard = getLayoutInflater().inflate(R.layout.result_recipe_row, null);

                            ImageView imgRecipe = (ImageView) recipeCard.findViewById(R.id.imgRecipe);
                            TextView txtRecipe = (TextView) recipeCard.findViewById(R.id.txtRecipe);
                            TextView txtMatch = (TextView) recipeCard.findViewById(R.id.txtMatch);
                            TextView txtDescription = (TextView) recipeCard.findViewById(R.id.txtDescription);

                            imgRecipe.setImageResource(r.recipeImage);
                            txtRecipe.setText(r.recipeName);
                            txtMatch.setText(Integer.toString(r.matchCount) + "/" + Integer.toString(r.ingredientCount) + " matches");
                            txtDescription.setText(r.recipeDescription);

                            linResults.addView(recipeCard);

                            Space spc = new Space(MainActivity.this);
                            spc.setMinimumHeight(10);
                            linResults.addView(spc);

                            recipeCard.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(MainActivity.this,RecipePage.class);
                                    intent.putExtra("image",Integer.toString(r.recipeImage));
                                    intent.putExtra("name",r.recipeName);
                                    intent.putExtra("description",r.recipeDescription);
                                    intent.putExtra("nutrition",r.nutrition);
                                    intent.putExtra("ingredients",r.ingredients);
                                    intent.putExtra("quantities",r.quantities);
                                    intent.putExtra("procedure",r.procedure);
                                    startActivity(intent);
                                }
                            });
                        }
                        txtRecipeCount.setText("You can make " + Integer.toString(suggestionList.size()) + " recipes");
                    }
                });

                userIngredients.add(ingredient);
                break;
            case R.id.btnClear:
                userIngredients.clear();
                LinearLayout linIngredients = (LinearLayout) findViewById(R.id.linIngredients);
                linIngredients.removeAllViews();

                //RESET SUGGESTIONS
                for (Recipe r : suggestionList) {
                    r.matchCount = 0;
                    r.matchPercentage = 0;
                }
                String input = srchIngredient.getText().toString().toLowerCase();
                ingredient = trie.search(input);
                //TALLY MATCHES
                if (ingredient != null) {
                    bubble1.setText(ingredient.ingredientName);
                    for (Recipe r : ingredient.recipes) {
                        r.matchCount++;
                    }
                }
                for (Ingredient i : userIngredients) {
                    for (Recipe r : i.recipes) {
                        r.matchCount++;
                    }
                }
                suggestionList.clear();
                for (Recipe r : recipeDatabase) {
                    if (r.matchCount > 0 && !suggestionList.contains(r)) {
                        suggestionList.add(r);
                        int divisor = userIngredients.size();
                        if (ingredient != null && !userIngredients.contains(ingredient)) {
                            divisor++;
                        }
                        r.matchPercentage = 1.0 * r.matchCount / divisor;
                        r.matchPercentage *= 100;
                    }
                }
                int suggestionCount = suggestionList.size();
                //SORT SUGGESTIONS
                for (int i = 1; i < suggestionCount; i++) {
                    int j = i - 1;
                    while (j >= 0 && suggestionList.get(i).matchPercentage > suggestionList.get(j).matchPercentage) {
                        Collections.swap(suggestionList,j+1,j);
                        j--;
                    }
                }
                //DISPLAY SUGGESTIONS
                linResults.removeAllViews();
                for (Recipe r : suggestionList) {
                    View recipeCard = getLayoutInflater().inflate(R.layout.result_recipe_row, null);

                    ImageView imgRecipe = (ImageView) recipeCard.findViewById(R.id.imgRecipe);
                    TextView txtRecipe = (TextView) recipeCard.findViewById(R.id.txtRecipe);
                    TextView txtMatch = (TextView) recipeCard.findViewById(R.id.txtMatch);
                    TextView txtDescription = (TextView) recipeCard.findViewById(R.id.txtDescription);

                    imgRecipe.setImageResource(r.recipeImage);
                    txtRecipe.setText(r.recipeName);
                    txtMatch.setText(Integer.toString(r.matchCount) + "/" + Integer.toString(r.ingredientCount) + " matches");
                    txtDescription.setText(r.recipeDescription);

                    linResults.addView(recipeCard);

                    Space spce = new Space(MainActivity.this);
                    spce.setMinimumHeight(10);
                    linResults.addView(spce);

                    recipeCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(MainActivity.this,RecipePage.class);
                            intent.putExtra("image",Integer.toString(r.recipeImage));
                            intent.putExtra("name",r.recipeName);
                            intent.putExtra("description",r.recipeDescription);
                            intent.putExtra("nutrition",r.nutrition);
                            intent.putExtra("ingredients",r.ingredients);
                            intent.putExtra("quantities",r.quantities);
                            intent.putExtra("procedure",r.procedure);
                            startActivity(intent);
                        }
                    });
                }
                txtRecipeCount.setText("You can make " + Integer.toString(suggestionList.size()) + " recipes");

                break;
            case R.id.bubble1:
                srchIngredient.setText(bubble1.getText().toString());
                break;
            case R.id.bubble2:
                srchIngredient.setText(bubble2.getText().toString());
                break;
            case R.id.bubble3:
                srchIngredient.setText(bubble3.getText().toString());
                break;
            case R.id.btnRandom:
                suggestionList.clear();
                for (Recipe r : recipeDatabase) {
                    r.matchPercentage = rand.nextDouble();
                    r.matchPercentage *= 100;
                    suggestionList.add(r);
                }
                suggestionCount = suggestionList.size();
                //SORT SUGGESTIONS
                for (int i = 1; i < suggestionCount; i++) {
                    int j = i - 1;
                    while (j >= 0 && suggestionList.get(i).matchPercentage > suggestionList.get(j).matchPercentage) {
                        Collections.swap(suggestionList,j+1,j);
                        j--;
                    }
                }
                //SORT SUGGESTIONS AGAIN
                for (int i = 1; i < suggestionCount; i++) {
                    int j = i - 1;
                    while (j >= 0 && suggestionList.get(i).matchPercentage > suggestionList.get(j).matchPercentage) {
                        Collections.swap(suggestionList,j+1,j);
                        j--;
                    }
                }
                //SORT SUGGESTIONS YET AGAIN
                for (int i = 1; i < suggestionCount; i++) {
                    int j = i - 1;
                    while (j >= 0 && suggestionList.get(i).matchPercentage > suggestionList.get(j).matchPercentage) {
                        Collections.swap(suggestionList,j+1,j);
                        j--;
                    }
                }
                //DISPLAY SUGGESTIONS
                linResults.removeAllViews();
                for (Recipe r : suggestionList) {
                    View recipeCard = getLayoutInflater().inflate(R.layout.result_recipe_row, null);

                    ImageView imgRecipe = (ImageView) recipeCard.findViewById(R.id.imgRecipe);
                    TextView txtRecipe = (TextView) recipeCard.findViewById(R.id.txtRecipe);
                    TextView txtMatch = (TextView) recipeCard.findViewById(R.id.txtMatch);
                    TextView txtDescription = (TextView) recipeCard.findViewById(R.id.txtDescription);

                    imgRecipe.setImageResource(r.recipeImage);
                    txtRecipe.setText(r.recipeName);
                    txtMatch.setText(Integer.toString(r.matchCount) + "/" + Integer.toString(r.ingredientCount) + " matches");
                    txtDescription.setText(r.recipeDescription);

                    linResults.addView(recipeCard);

                    Space spce = new Space(MainActivity.this);
                    spce.setMinimumHeight(10);
                    linResults.addView(spce);

                    recipeCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(MainActivity.this,RecipePage.class);
                            intent.putExtra("image",Integer.toString(r.recipeImage));
                            intent.putExtra("name",r.recipeName);
                            intent.putExtra("description",r.recipeDescription);
                            intent.putExtra("nutrition",r.nutrition);
                            intent.putExtra("ingredients",r.ingredients);
                            intent.putExtra("quantities",r.quantities);
                            intent.putExtra("procedure",r.procedure);
                            startActivity(intent);
                        }
                    });
                }
                txtRecipeCount.setText("You can make " + Integer.toString(suggestionList.size()) + " recipes");
                break;
        }
    }
}