package com.hoaxyinnovations.cozimento;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.hoaxyinnovations.cozimento.database.RecipesContract;

import timber.log.Timber;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,OnStepSelectedListener{
    final int ID_INGREDIENTS_LOADER = 100;
    final int ID_STEPS_LOADER = 101;
    private Cursor ingredientsData;
    private Cursor stepsData;
    final String TAG = DetailActivity.class.getSimpleName();
    private StepsFragment stepsFragment;
    IngredientFragment ingredientFragment;
    RecipeStepFragment recipeStepFragment;
    boolean mTwoPane;
    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(findViewById(R.id.land_step_detail_container) != null){
            mTwoPane = true;
            FrameLayout recipeDetailContainer = (FrameLayout) findViewById(R.id.recipe_detail_container);
            recipeDetailContainer.setVisibility(View.GONE);
            Timber.plant(new Timber.DebugTree());


            LoaderManager.LoaderCallbacks<Cursor> callback = DetailActivity.this;

            getSupportLoaderManager().initLoader(ID_INGREDIENTS_LOADER, null, callback);
            getSupportLoaderManager().initLoader(ID_STEPS_LOADER, null, callback);

            stepsFragment = new StepsFragment();
            ingredientFragment = new IngredientFragment();


            ingredientFragment.setmIngredientsData(ingredientsData);

            stepsFragment.setmStepsData(stepsData);

            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.ingredient_container, ingredientFragment)
                    .add(R.id.land_step_detail_container,stepsFragment)
                    .commit();
        }
        else{
            mTwoPane = false;
            Timber.plant(new Timber.DebugTree());


            LoaderManager.LoaderCallbacks<Cursor> callback = DetailActivity.this;

            getSupportLoaderManager().initLoader(ID_INGREDIENTS_LOADER, null, callback);
            getSupportLoaderManager().initLoader(ID_STEPS_LOADER, null, callback);

            stepsFragment = new StepsFragment();
            ingredientFragment = new IngredientFragment();


            ingredientFragment.setmIngredientsData(ingredientsData);

            stepsFragment.setmStepsData(stepsData);

            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.ingredient_container, ingredientFragment)
                    .add(R.id.recipe_detail_container,stepsFragment)
                    .commit();

        }





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
                ingredientFragment.mIngredientsAdapter.swapCursor(ingredientsData);
                break;
            case ID_STEPS_LOADER:
                Timber.d("steps data loaded");
                stepsData = data;
                stepsFragment.mStepsAdapter.swapCursor(stepsData);
                break;
        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    @Override
    public void getStepID(Bundle stepDetailsl) {

        /*Timber.d(stepDetailsl.getString("step_videoURL")+ ": retrieved in detailactivity");
        Timber.d(stepDetailsl.getString("step_description")+ ": retrieved in detailactivity");

*/
        if(mTwoPane){
            recipeStepFragment = new RecipeStepFragment();
            recipeStepFragment.setStepDetails(stepDetailsl);

            fragmentManager.beginTransaction()
                    .replace(R.id.land_step_description_container,recipeStepFragment)
                    .commit();
        }
        else{
            Context context = this;
            Class destinationClass = StepDetailActivity.class;
            Intent intent = new Intent(context,destinationClass);
            intent.putExtras(stepDetailsl);
            Timber.d("Extras sent to recipedetails activity");
            context.startActivity(intent);
        }

    }
}
