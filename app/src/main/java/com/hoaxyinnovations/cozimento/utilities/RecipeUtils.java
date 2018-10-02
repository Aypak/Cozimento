package com.hoaxyinnovations.cozimento.utilities;

import com.hoaxyinnovations.cozimento.model.Ingredient;
import com.hoaxyinnovations.cozimento.model.Recipe;
import com.hoaxyinnovations.cozimento.model.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kapsa on 1/22/2018.
 */

public class RecipeUtils {

    /*Get a list of complete Recipe objects from json*/
    public static List<Recipe> getRecipesFromJson(String jsonResponse) {
        if (jsonResponse == null) {
            return null;
        }

        List<Recipe> recipes = new ArrayList<>();

        try {
            JSONArray recipeArray = new JSONArray(jsonResponse);

            for (int i = 0; i < recipeArray.length(); i++) {
                JSONObject currentRecipe = recipeArray.getJSONObject(i);

                Ingredient[] ingredients = getIngredientsFromJson(currentRecipe.getString("ingredients"));

                Step [] steps = getStepsFromJson(currentRecipe.getString("steps"));

                Recipe recipe = new Recipe(
                        currentRecipe.getLong("id"),
                        currentRecipe.getString("name"),
                        ingredients,
                        steps,
                        currentRecipe.getDouble("servings"),
                        currentRecipe.getString("image"));
                recipes.add(recipe);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipes;
    }


    /*Get a list of Step Objects from json*/
    private static Step[] getStepsFromJson(String json) {
        try {
            List<Step> steps = new ArrayList<>();
            JSONArray stepsArray = new JSONArray(json);
            for (int j = 0; j < stepsArray.length(); ++j) {
                JSONObject currentStep = stepsArray.getJSONObject(j);
                steps.add(new Step(
                        currentStep.getLong("id"),
                        currentStep.getString("shortDescription"),
                        currentStep.getString("description"),
                        currentStep.has("videoURL") ? currentStep.getString("videoURL") : "",
                        currentStep.has("thumbnailURL") ? currentStep.getString("thumbnailURL") : ""
                ));
            }
            return steps.toArray(new Step[steps.size()]);
        } catch (JSONException e) {
            return null;
        }

    }

    /*Get a list of Ingredient objects from json*/

    private static Ingredient[] getIngredientsFromJson(String json) {
        try {
            List<Ingredient> ingredients = new ArrayList<>();
            JSONArray ingredientsArray = new JSONArray(json);
            for (int j = 0; j < ingredientsArray.length(); ++j) {
                JSONObject currentIngredient = ingredientsArray.getJSONObject(j);
                ingredients.add(new Ingredient(
                        currentIngredient.getDouble("quantity"),
                        currentIngredient.getString("measure"),
                        currentIngredient.getString("ingredient")
                ));
            }
            return ingredients.toArray(new Ingredient[ingredients.size()]);
        } catch (JSONException e) {
            return null;
        }

    }
}
