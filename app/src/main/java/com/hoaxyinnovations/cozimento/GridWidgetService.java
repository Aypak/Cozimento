/**
 * Created by kapsa on 11/22/2018.
 */

/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.hoaxyinnovations.cozimento;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;


import com.hoaxyinnovations.cozimento.database.RecipesContract;

import static com.hoaxyinnovations.cozimento.database.RecipesContract.BASE_CONTENT_URI;
import static com.hoaxyinnovations.cozimento.database.RecipesContract.PATH_RECIPES;


public class GridWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }
}

class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    Cursor mCursor;

    public GridRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;

    }

    @Override
    public void onCreate() {

    }

    //called on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {
        // Get all recipes
        Uri RECIPE_URL = BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPES).build();
        if (mCursor != null) mCursor.close();
        mCursor = mContext.getContentResolver().query(
                RECIPE_URL,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onDestroy() {
        mCursor.close();
    }

    @Override
    public int getCount() {
        if (mCursor == null) return 0;
        return mCursor.getCount();
    }

    /**
     * This method acts like the onBindViewHolder method in an Adapter
     *
     * @param position The current position of the item in the GridView to be displayed
     * @return The RemoteViews object to display for the provided postion
     */
    @Override
    public RemoteViews getViewAt(int position) {
        if (mCursor == null || mCursor.getCount() == 0) return null;
        mCursor.moveToPosition(position);
        int recipeIDIndex = mCursor.getColumnIndex(RecipesContract.RecipeEntry.COLUMN_RECIPE_ID);
        int recipeNameIndex = mCursor.getColumnIndex(RecipesContract.RecipeEntry.COLUMN_RECIPE_NAME);


        String recipeID = mCursor.getString(recipeIDIndex);
        String recipeName = mCursor.getString(recipeNameIndex);


        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget);

        views.setTextViewText(R.id.widget_recipe_name, recipeName);

        // Fill in the onClick PendingIntent Template using the specific recipe Id for each item individually
        Bundle extras = new Bundle();
        extras.putString(DetailActivity.EXTRA_RECIPE_ID, recipeID);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        views.setOnClickFillInIntent(R.id.widget_recipe_name, fillInIntent);

        return views;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1; // Treat all items in the GridView the same
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}

