package com.hoaxyinnovations.cozimento.utilities;

import android.content.ContentValues;
import android.content.Context;

import com.hoaxyinnovations.cozimento.database.RecipesContract;
import com.hoaxyinnovations.cozimento.database.RecipesProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kapsa on 12/29/2017.
 */

public class JsonUtils {
    public static void refreshRecipes(Context context,String recipeString) throws JSONException {
        final String RECIPE_ID = RecipesContract.RecipeEntry.COLUMN_RECIPE_ID;
        final String RECIPE_ID_FK = "recipe_id";
        final String RECIPE_NAME = RecipesContract.RecipeEntry.COLUMN_RECIPE_NAME;
        final String RECIPE_SERVINGS = RecipesContract.RecipeEntry.COLUMN_RECIPE_SERVINGS;
        final String RECIPE_IMAGE = RecipesContract.RecipeEntry.COLUMN_RECIPE_IMAGE;
        final String RECIPE_INGREDIENTS = "ingredients";
        final String RECIPE_STEPS = "steps";

        final String INGREDIENT_NAME = RecipesContract.IngredientEntry.COLUMN_INGREDIENT_NAME;
        final String INGREDIENT_QUANTITY = RecipesContract.IngredientEntry.COLUMN_INGREDIENT_QUANTITY;
        final String INGREDIENT_MEASURE = RecipesContract.IngredientEntry.COLUMN_INGREDIENT_MEASURE;

        final String STEP_ID = RecipesContract.StepEntry.COLUMN_STEP_ID;
        final String STEP_SHORT_DESCRIPTION = RecipesContract.StepEntry.COLUMN_SHORT_DESCRIPTION;
        final String STEP_DESCRIPTION = RecipesContract.StepEntry.COLUMN_DESCRIPTION;
        final String VIDEOURL = RecipesContract.StepEntry.COLUMN_VIDEOURL;
        final String THUMBNAILURL = RecipesContract.StepEntry.COLUMN_THUMBNAILURL;

        JSONArray recipeArray = new JSONArray(recipeString);

        for(int i=0;i<recipeArray.length();i++){
            JSONObject recipeObject = recipeArray.getJSONObject(i);

            JSONArray ingredientsArray = recipeObject.getJSONArray(RECIPE_INGREDIENTS);

            JSONArray stepsArray = recipeObject.getJSONArray(RECIPE_STEPS);

            ContentValues recipeContentValues = new ContentValues();
            recipeContentValues.put(RECIPE_ID,recipeObject.getString(RECIPE_ID));
            recipeContentValues.put(RECIPE_NAME,recipeObject.getString(RECIPE_NAME));
            recipeContentValues.put(RECIPE_SERVINGS,recipeObject.getString(RECIPE_SERVINGS));
            recipeContentValues.put(RECIPE_IMAGE,recipeObject.getString(RECIPE_IMAGE));

            context.getContentResolver().insert(RecipesContract.RecipeEntry.CONTENT_URI,recipeContentValues);


            for(int j=0;j<ingredientsArray.length();j++){
                JSONObject ingredient = ingredientsArray.getJSONObject(j);
                ContentValues ingredientContentValues = new ContentValues();
                ingredientContentValues.put(INGREDIENT_NAME,ingredient.getString(INGREDIENT_NAME));
                ingredientContentValues.put(INGREDIENT_QUANTITY,ingredient.getString(INGREDIENT_QUANTITY));
                ingredientContentValues.put(INGREDIENT_MEASURE,ingredient.getString(INGREDIENT_MEASURE));
                ingredientContentValues.put(RECIPE_ID_FK,recipeObject.getString(RECIPE_ID));

                context.getContentResolver().insert(RecipesContract.IngredientEntry.CONTENT_URI,ingredientContentValues);

            }

            for(int k=0;k<stepsArray.length();k++){
                JSONObject step = stepsArray.getJSONObject(k);
                ContentValues stepsContentValues = new ContentValues();
                stepsContentValues.put(STEP_ID,step.getString(STEP_ID));
                stepsContentValues.put(STEP_SHORT_DESCRIPTION,step.getString(STEP_SHORT_DESCRIPTION));
                stepsContentValues.put(STEP_DESCRIPTION,step.getString(STEP_DESCRIPTION));
                stepsContentValues.put(VIDEOURL,step.getString(VIDEOURL));
                stepsContentValues.put(THUMBNAILURL,step.getString(THUMBNAILURL));
                stepsContentValues.put(RECIPE_ID_FK,recipeObject.getString(RECIPE_ID));

                context.getContentResolver().insert(RecipesContract.StepEntry.CONTENT_URI,stepsContentValues);

            }



        }



    }
}
