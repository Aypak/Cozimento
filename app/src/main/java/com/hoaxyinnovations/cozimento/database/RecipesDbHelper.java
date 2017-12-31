package com.hoaxyinnovations.cozimento.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kapsa on 12/30/2017.
 */

public class RecipesDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "recipes.db";
    private static final int DATABASE_VERSION = 1;

    public RecipesDbHelper(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
         final String INGREDIENTS = "CREATE TABLE IF NOT EXISTS ingredients ("
                + RecipesContract.IngredientEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + RecipesContract.IngredientEntry.COLUMN_INGREDIENT_NAME + " TEXT NOT NULL,"
                + RecipesContract.IngredientEntry.COLUMN_INGREDIENT_MEASURE+ " TEXT NOT NULL,"
                + RecipesContract.IngredientEntry.COLUMN_INGREDIENT_QUANTITY + " INTEGER NOT NULL,"
                + RecipesContract.IngredientEntry.COLUMN_RECIPE_ID + " INTEGER NOT NULL REFERENCES recipes(id),"
                + " CONSTRAINT UNQ_TAG_FOR_INGREDIENTS UNIQUE ( recipe_id, ingredient ) ON CONFLICT REPLACE)";

        final String STEPS = "CREATE TABLE IF NOT EXISTS steps ("
                + RecipesContract.StepEntry.COLUMN_STEP_ID + " INTEGER,"
                + RecipesContract.StepEntry.COLUMN_SHORT_DESCRIPTION+ " TEXT,"
                + RecipesContract.StepEntry.COLUMN_DESCRIPTION+ " TEXT,"
                + RecipesContract.StepEntry.COLUMN_VIDEOURL + " TEXT,"
                + RecipesContract.StepEntry.COLUMN_THUMBNAILURL + " TEXT,"
                + RecipesContract.StepEntry.COLUMN_RECIPE_ID + " INTEGER NOT NULL REFERENCES recipes(id),"
                + " CONSTRAINT UNQ_TAG_FOR_STEPS UNIQUE ( recipe_id, id, shortDescription ) ON CONFLICT REPLACE)";

        final String RECIPES = "CREATE TABLE recipes ("
                + RecipesContract.RecipeEntry.COLUMN_RECIPE_ID + " INTEGER PRIMARY KEY,"
                + RecipesContract.RecipeEntry.COLUMN_RECIPE_NAME+ " TEXT NOT NULL,"
                + RecipesContract.RecipeEntry.COLUMN_RECIPE_SERVINGS + " INTEGER,"
                + RecipesContract.RecipeEntry.COLUMN_RECIPE_IMAGE + " INTEGER)";

        sqLiteDatabase.execSQL(RECIPES);
        sqLiteDatabase.execSQL(INGREDIENTS);
        sqLiteDatabase.execSQL(STEPS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RecipesContract.IngredientEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RecipesContract.StepEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RecipesContract.RecipeEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
