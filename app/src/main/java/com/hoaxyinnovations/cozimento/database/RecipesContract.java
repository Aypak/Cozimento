package com.hoaxyinnovations.cozimento.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by kapsa on 12/30/2017.
 */

public class RecipesContract {
    public static final String CONTENT_AUTHORITY = "com.hoaxyinnovations.cozimento";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_RECIPES = "recipes";
    public static final String PATH_INGREDIENTS = "ingredients";
    public static final String PATH_STEPS = "steps";

    public static final class RecipeEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_RECIPES)
                .build();
        public static final String TABLE_NAME = "recipes";

        public static final String COLUMN_RECIPE_ID = "id";
        public static final String COLUMN_RECIPE_NAME = "name";
        public static final String COLUMN_RECIPE_SERVINGS = "servings";
        public static final String COLUMN_RECIPE_IMAGE = "image";

    }

    public static final class IngredientEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_INGREDIENTS)
                .build();

        public static final String TABLE_NAME = "ingredients";

        public static final String ID = "id" ;
        public static final String COLUMN_INGREDIENT_NAME = "ingredient";
        public static final String COLUMN_INGREDIENT_QUANTITY= "quantity";
        public static final String COLUMN_INGREDIENT_MEASURE = "measure";

        public static final String COLUMN_RECIPE_ID = "recipe_id";
    }

    public static final class StepEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_STEPS)
                .build();

        public static final String TABLE_NAME = "steps";

        public static final String COLUMN_STEP_ID = "id";
        public static final String COLUMN_SHORT_DESCRIPTION ="shortDescription";
        public static final String COLUMN_DESCRIPTION ="description";
        public static final String COLUMN_VIDEOURL = "videoURL";
        public static final String COLUMN_THUMBNAILURL = "thumbnailURL";
        public static final String COLUMN_RECIPE_ID = "recipe_id";

    }

}
