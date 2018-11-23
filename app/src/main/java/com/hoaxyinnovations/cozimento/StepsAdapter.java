package com.hoaxyinnovations.cozimento;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hoaxyinnovations.cozimento.database.RecipesContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by kapsa on 1/21/2018.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder>{
    private Cursor mData;
    private Context mContext;

    public OnStepSelectedListener onStepSelectedListener;



    public StepsAdapter(Context context, Cursor mStepsData, OnStepSelectedListener onStepSelectedListener) {
        this.mContext = context;
        this.onStepSelectedListener = onStepSelectedListener;

    }

    @Override
    public StepsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View stepsView = inflater.inflate(R.layout.step_list_item,parent,false);
        return new StepsAdapter.ViewHolder(stepsView);
    }

    @Override
    public void onBindViewHolder(StepsAdapter.ViewHolder holder, int position) {
        mData.moveToPosition(position);
        String stepName = mData.getString(mData.getColumnIndex(RecipesContract.StepEntry.COLUMN_SHORT_DESCRIPTION));
        int stepId = mData.getInt(mData.getColumnIndex(RecipesContract.StepEntry.COLUMN_STEP_ID));


        holder.shortDescription.setText(stepName);

        holder.recipeID = mData.getString(mData.getColumnIndex(RecipesContract.StepEntry.COLUMN_RECIPE_ID));
        holder.shortDescriptionText = mData.getString(mData.getColumnIndex(RecipesContract.StepEntry.COLUMN_SHORT_DESCRIPTION));
        holder.descriptionText = mData.getString(mData.getColumnIndex(RecipesContract.StepEntry.COLUMN_DESCRIPTION));
        holder.videoURL = mData.getString(mData.getColumnIndex(RecipesContract.StepEntry.COLUMN_VIDEOURL));
        holder.thumbnailURL = mData.getString(mData.getColumnIndex(RecipesContract.StepEntry.COLUMN_THUMBNAILURL));
    }

    @Override
    public int getItemCount() {
        if (null == mData) return 0;
        return mData.getCount();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.tv_step_name)
        TextView shortDescription;
        int stepId;
        String shortDescriptionText;
        String descriptionText;
        String videoURL;
        String thumbnailURL;
        String recipeID;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            shortDescription.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Timber.d(this.stepId + ' ' + this.videoURL);
            Bundle stepDetailsBundle = new Bundle();
            stepDetailsBundle.putString("step_videoURL",this.videoURL);
            stepDetailsBundle.putString("step_description",this.descriptionText);
            stepDetailsBundle.putString("step_short_description",this.shortDescriptionText);
            stepDetailsBundle.putString("step_thumbnailURL",this.thumbnailURL);

            onStepSelectedListener.getStepID(stepDetailsBundle);


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
