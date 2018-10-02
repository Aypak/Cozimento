package com.hoaxyinnovations.cozimento;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.hoaxyinnovations.cozimento.database.RecipesContract;
import com.hoaxyinnovations.cozimento.utilities.JsonUtils;
import com.hoaxyinnovations.cozimento.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>{

    private static final int ID_RECIPES_LOADER = 1;

    @BindView(R.id.recipes_recyclerview) RecyclerView mRecyclerView;
    @BindView(R.id.pb_loading_indicator) ProgressBar mLoadingIndicator;
    private RecipeAdapter mRecipeAdapter;
    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Timber.plant(new Timber.DebugTree());

        ButterKnife.bind(this);

        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecipeAdapter = new RecipeAdapter(this);

        mRecyclerView.setAdapter(mRecipeAdapter);

        LoaderManager.LoaderCallbacks<Cursor> callback = MainActivity.this;

        getSupportLoaderManager().initLoader(ID_RECIPES_LOADER, null, callback);


    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        mLoadingIndicator.setVisibility(View.VISIBLE);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String bakingJSONURLString = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
                    URL bakingJSONURL = new URL(bakingJSONURLString);
                    JsonUtils.refreshRecipes(mContext, NetworkUtils.getResponseFromHttpUrl(bakingJSONURL));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        switch (id) {

            case ID_RECIPES_LOADER:
                Uri recipesUri = RecipesContract.RecipeEntry.CONTENT_URI;

                return new CursorLoader(this,
                        recipesUri,
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
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mRecipeAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mRecipeAdapter.swapCursor(null);

    }
}
