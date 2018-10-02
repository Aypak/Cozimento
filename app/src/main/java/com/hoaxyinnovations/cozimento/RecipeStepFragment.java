package com.hoaxyinnovations.cozimento;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.hoaxyinnovations.cozimento.database.RecipesContract;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kapsa on 1/7/2018.
 */

public class RecipeStepFragment extends Fragment {
    private SimpleExoPlayer mExoPlayer;
    private static MediaSessionCompat mMediaSession;
    String shortDescription;
    String description;
    String videoURL;
    String thumbnailURL;
    @BindView(R.id.playerView) SimpleExoPlayerView mPlayerView;
    @BindView(R.id.stepDescription) TextView stepDescription;
    @BindView(R.id.shortDescription) TextView stepshortDescription;



    public RecipeStepFragment(){
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        ButterKnife.bind(this,rootView);

        stepDescription.setText(description);
        stepshortDescription.setText(shortDescription);

        if(videoURL.length() > 0 || getVideoURL()!=null){
            initializePlayer(getVideoURL());
        }
        else{
            mPlayerView.setVisibility(View.GONE);
        }



        return rootView;
    }

    public void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            /*mExoPlayer.addListener(getActivity());*/

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getActivity(), "Cozimento");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mExoPlayer != null){
            releasePlayer();
        }
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    public Uri getVideoURL(){
        if(videoURL.length()==0 || videoURL== null){
            return null;
        }
        else{
            return Uri.parse(videoURL);
        }
    }



    public void setStepDetails(Bundle stepDetailExtras) {
        shortDescription = stepDetailExtras.getString("step_short_description");
        description = stepDetailExtras.getString("step_description");
        videoURL = stepDetailExtras.getString("step_videoURL");
        thumbnailURL = stepDetailExtras.getString("step_thumbnailURL");
    }
}
