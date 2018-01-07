package com.hoaxyinnovations.cozimento;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.hoaxyinnovations.cozimento.database.RecipesContract;

import timber.log.Timber;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    final int ID_INGREDIENTS_LOADER = 100;
    final int ID_STEPS_LOADER = 101;
    private Cursor ingredientsData;
    private Cursor stepsData;
    final String TAG = DetailActivity.class.getSimpleName();
    private RecipeDetailFragment recipeDetailFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Timber.plant(new Timber.DebugTree());

        LoaderManager.LoaderCallbacks<Cursor> callback = DetailActivity.this;

        getSupportLoaderManager().initLoader(ID_INGREDIENTS_LOADER, null, callback);
        getSupportLoaderManager().initLoader(ID_STEPS_LOADER, null, callback);

        recipeDetailFragment = new RecipeDetailFragment();

        recipeDetailFragment.setmIngredientsData(ingredientsData);
        recipeDetailFragment.setmStepsData(stepsData);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.recipe_detail_container,recipeDetailFragment).commit();



    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String recipe_id = getIntent().getStringExtra("recipe_id");
        switch (id){
            case ID_INGREDIENTS_LOADER:
                Uri ingredientsUri = RecipesContract.IngredientEntry.CONTENT_URI.buildUpon().appendPath(recipe_id).build();

                return new CursorLoader(this,
                        ingredientsUri,
                        null,
                        null,
                        null,
                        null);



            case ID_STEPS_LOADER:
                Uri stepsUri = RecipesContract.StepEntry.CONTENT_URI.buildUpon().appendPath(recipe_id).build();

                return new CursorLoader(this,
                        stepsUri,
                        null,
                        null,
                        null,
                        null);
                
            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()){
            case ID_INGREDIENTS_LOADER:
                Timber.d("ingredients data loaded");
                ingredientsData = data;
                recipeDetailFragment.mIngredientsAdapter.swapCursor(ingredientsData);
                break;
            case ID_STEPS_LOADER:
                Timber.d("steps data loaded");
                stepsData = data;
                recipeDetailFragment.mStepsAdapter.swapCursor(stepsData);

                break;
        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
