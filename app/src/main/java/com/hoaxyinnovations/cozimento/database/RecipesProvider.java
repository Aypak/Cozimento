package com.hoaxyinnovations.cozimento.database;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class RecipesProvider extends ContentProvider{

    private static final int RECIPES = 200;
    private static final int INGREDIENTS = 203;
    private static final int STEPS = 204;

    private static final int INGREDIENTS_FOR_RECIPE = 201;
    private static final int STEPS_FOR_RECIPE = 202;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private RecipesDbHelper mOpenHelper;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = RecipesContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, RecipesContract.PATH_RECIPES, RECIPES);
        matcher.addURI(authority, RecipesContract.PATH_INGREDIENTS , INGREDIENTS);
        matcher.addURI(authority, RecipesContract.PATH_STEPS, STEPS);
        matcher.addURI(authority, RecipesContract.PATH_INGREDIENTS + "/#", INGREDIENTS_FOR_RECIPE);
        matcher.addURI(authority, RecipesContract.PATH_STEPS + "/#", STEPS_FOR_RECIPE);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new RecipesDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case RECIPES:{
                cursor = mOpenHelper.getReadableDatabase().query(
                        RecipesContract.RecipeEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            }
            case INGREDIENTS_FOR_RECIPE:{
                String recipe_id = uri.getLastPathSegment();
                String[] selectionArguments = new String[]{recipe_id};
                cursor= mOpenHelper.getReadableDatabase().query(
                        RecipesContract.IngredientEntry.TABLE_NAME,
                        projection,
                        RecipesContract.IngredientEntry.COLUMN_RECIPE_ID+ " = ? ",
                        selectionArguments,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case STEPS_FOR_RECIPE:{
                String recipe_id = uri.getLastPathSegment();
                String[] selectionArguments = new String[]{recipe_id};
                cursor= mOpenHelper.getReadableDatabase().query(
                        RecipesContract.StepEntry.TABLE_NAME,
                        projection,
                        RecipesContract.StepEntry.COLUMN_RECIPE_ID+ " = ? ",
                        selectionArguments,
                        null,
                        null,
                        sortOrder
                );

                break;
            }
            default:{
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri resultsUri = null;
        switch (sUriMatcher.match(uri)) {
            case RECIPES:{
                int rowsInserted = 0;
                long _id = db.insert(RecipesContract.RecipeEntry.TABLE_NAME, null, values);
                if (_id != -1) {
                    rowsInserted++;
                }
                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                resultsUri = Uri.withAppendedPath(RecipesContract.BASE_CONTENT_URI, values != null ? values.getAsString(RecipesContract.RecipeEntry.COLUMN_RECIPE_ID) : null);
                break;
            }
            case INGREDIENTS:{
                int rowsInserted = 0;
                long _id = db.insert(RecipesContract.IngredientEntry.TABLE_NAME, null, values);
                if (_id != -1) {
                    rowsInserted++;
                }
                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                resultsUri = Uri.withAppendedPath(RecipesContract.BASE_CONTENT_URI, values != null ? values.getAsString(RecipesContract.IngredientEntry.COLUMN_RECIPE_ID) : null);
                break;
            }
            case STEPS:{
                int rowsInserted = 0;
                long _id = db.insert(RecipesContract.StepEntry.TABLE_NAME, null, values);
                if (_id != -1) {
                    rowsInserted++;
                }
                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                resultsUri = Uri.withAppendedPath(RecipesContract.BASE_CONTENT_URI, values != null ? values.getAsString(RecipesContract.StepEntry.COLUMN_RECIPE_ID) : null);
                break;
            }

        }
        return resultsUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numRowsDeleted;
        if (null == selection) selection = "1";

        switch (sUriMatcher.match(uri)) {
            case RECIPES:
                numRowsDeleted = mOpenHelper.getWritableDatabase().delete(
                        RecipesContract.RecipeEntry.TABLE_NAME,
                        selection,
                        selectionArgs);

                break;
            case INGREDIENTS:
                numRowsDeleted = mOpenHelper.getWritableDatabase().delete(
                        RecipesContract.IngredientEntry.TABLE_NAME,
                        selection,
                        selectionArgs);

                break;
            case STEPS:
                numRowsDeleted = mOpenHelper.getWritableDatabase().delete(
                        RecipesContract.StepEntry.TABLE_NAME,
                        selection,
                        selectionArgs);

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (numRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return numRowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }

}