package com.hoaxyinnovations.cozimento.ui;

import android.content.ContentProvider;
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
 * Created by kapsa on 1/4/2018.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder>{
    private Cursor mData;
    private final Context mContext;

    public IngredientsAdapter(Context context, Cursor mIngredientsData) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View ingredientsView = inflater.inflate(R.layout.ingredient_list_item,parent,false);
        return new ViewHolder(ingredientsView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mData.moveToPosition(position);
        String ingredientName = mData.getString(mData.getColumnIndex(RecipesContract.IngredientEntry.COLUMN_INGREDIENT_NAME));
        String ingredientQuantity = mData.getString(mData.getColumnIndex(RecipesContract.IngredientEntry.COLUMN_INGREDIENT_QUANTITY));
        String ingredientMeasure = mData.getString(mData.getColumnIndex(RecipesContract.IngredientEntry.COLUMN_INGREDIENT_MEASURE));

        holder.ingredientName.setText(ingredientName);
        holder.ingredientMeasure.setText(ingredientMeasure);
        holder.ingredientQuantity.setText(ingredientQuantity);

    }

    @Override
    public int getItemCount() {
        if (null == mData) return 0;
        return mData.getCount();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_ingredient_name) TextView ingredientName;
        @BindView(R.id.tv_ingredient_quantity) TextView ingredientQuantity;
        @BindView(R.id.tv_ingredient_measure) TextView ingredientMeasure;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private Context getContext() {
        return mContext;
    }

    public void swapCursor(Cursor newCursor) {
        mData = newCursor;
        notifyDataSetChanged();
    }
}
