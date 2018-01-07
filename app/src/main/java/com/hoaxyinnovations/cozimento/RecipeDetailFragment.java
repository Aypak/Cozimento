package com.hoaxyinnovations.cozimento;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hoaxyinnovations.cozimento.ui.IngredientsAdapter;
import com.hoaxyinnovations.cozimento.ui.StepsAdapter;


/**
 * Created by kapsa on 12/31/2017.
 */


public class RecipeDetailFragment extends android.support.v4.app.Fragment{
    private Context mContext;
    Cursor mIngredientsData;
    Cursor mStepsData;
    IngredientsAdapter mIngredientsAdapter;
    StepsAdapter mStepsAdapter;


    public void setmIngredientsData(Cursor mIngredientsData) {
        this.mIngredientsData = mIngredientsData;
    }
    public void setmStepsData(Cursor mStepsData) {
        this.mStepsData = mStepsData;
    }


    public RecipeDetailFragment(){

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        RecyclerView ingredientsRV = (RecyclerView) rootView.findViewById(R.id.ingredients_recyclerview);
        RecyclerView stepsRV = (RecyclerView) rootView.findViewById(R.id.steps_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);

        mIngredientsAdapter = new IngredientsAdapter(getContext(),mIngredientsData);

        ingredientsRV.setLayoutManager(linearLayoutManager);
        ingredientsRV.setAdapter(mIngredientsAdapter);

        mStepsAdapter = new StepsAdapter(getContext(),mStepsData);
        stepsRV.setLayoutManager(linearLayoutManager1);
        stepsRV.setAdapter(mStepsAdapter);



        return rootView;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public Context getContext() {
        return mContext;
    }


}
