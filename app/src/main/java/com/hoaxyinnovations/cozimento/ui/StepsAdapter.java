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
 * Created by kapsa on 1/5/2018.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder>{
    private Cursor mData;
    private Context mContext;

    public StepsAdapter(Context context, Cursor mStepsData) {
        this.mContext = context;

    }

    @Override
    public StepsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View stepsView = inflater.inflate(R.layout.step_list_item,parent,false);
        return new ViewHolder(stepsView);
    }

    @Override
    public void onBindViewHolder(StepsAdapter.ViewHolder holder, int position) {
        mData.moveToPosition(position);
        String stepName = mData.getString(mData.getColumnIndex(RecipesContract.StepEntry.COLUMN_SHORT_DESCRIPTION));

        holder.stepName.setText(stepName);
    }

    @Override
    public int getItemCount() {
        if (null == mData) return 0;
        return mData.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_step_name) TextView stepName;
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
