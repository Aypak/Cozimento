package com.hoaxyinnovations.cozimento;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by kapsa on 12/31/2017.
 */


public class StepsFragment extends android.support.v4.app.Fragment{
    private Context mContext;
    Cursor mStepsData;
    IngredientsAdapter mIngredientsAdapter;
    StepsAdapter mStepsAdapter;
    OnStepSelectedListener mListener;



    public void setmStepsData(Cursor mStepsData) {
        this.mStepsData = mStepsData;
    }


    public StepsFragment(){

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        RecyclerView stepsRV = (RecyclerView) rootView.findViewById(R.id.steps_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);


        mStepsAdapter = new StepsAdapter(getContext(),mStepsData,mListener);
        stepsRV.setLayoutManager(linearLayoutManager);
        stepsRV.setAdapter(mStepsAdapter);



        return rootView;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnStepSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnStepSelectedListener");
        }

    }

    public Context getContext() {
        return mContext;
    }






}
