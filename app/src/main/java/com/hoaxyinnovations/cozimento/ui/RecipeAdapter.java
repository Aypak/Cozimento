package com.hoaxyinnovations.cozimento.ui;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hoaxyinnovations.cozimento.R;
import com.hoaxyinnovations.cozimento.database.RecipesContract;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kapsa on 12/29/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>{
    private Cursor mData;
    private Context mContext;

    public RecipeAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View recipesView = inflater.inflate(R.layout.recipe_list_item,parent,false);
        return new ViewHolder(recipesView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mData.moveToPosition(position);
        String recipeName = mData.getString(mData.getColumnIndex(RecipesContract.RecipeEntry.COLUMN_RECIPE_NAME));
        String recipeServings = mData.getString(mData.getColumnIndex(RecipesContract.RecipeEntry.COLUMN_RECIPE_SERVINGS));

        holder.recipeName.setText(recipeName);
        holder.recipeServings.setText(recipeServings);

    }

    @Override
    public int getItemCount() {
        if (null == mData) return 0;
        return mData.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        mData = newCursor;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.tv_recipe_name) TextView recipeName;
        @BindView(R.id.tv_servings) TextView recipeServings;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
