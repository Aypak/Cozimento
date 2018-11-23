package com.hoaxyinnovations.cozimento;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.hoaxyinnovations.cozimento.database.RecipesContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by kapsa on 1/7/2018.
 */

public class RecipeStepFragment extends Fragment {
    private SimpleExoPlayer mExoPlayer;
    private static MediaSessionCompat mMediaSession;
    private MediaSource mVideoSource;
    String shortDescription;
    String description;
    String videoURL;
    String thumbnailURL;
    @BindView(R.id.playerView) SimpleExoPlayerView mPlayerView;
    @BindView(R.id.stepDescription) TextView stepDescription;
    private Dialog mFullScreenDialog;
    private boolean mExoPlayerFullscreen = false;
    private FrameLayout mFullScreenButton;
    private ImageView mFullScreenIcon;
    private final String STATE_RESUME_WINDOW = "resumeWindow";
    private final String STATE_RESUME_POSITION = "resumePosition";
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";
    private final String STATE_VIDEO_URL = "videoURL";
    private final String STATE_SHORTDESCRIPTION= "shortDescription";

    private final String STATE_PLAYER_READY_STATE = "playerReadyState";
    private final String STATE_PLAYER_POSITION = "playerReadyState";



    private int mResumeWindow;
    private long mResumePosition;
    private long mPlayerPosition;
    private boolean mPlayerReadyState;


    public RecipeStepFragment(){
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Timber.plant(new Timber.DebugTree());


        if (savedInstanceState != null) {
            mResumeWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW);
            mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION);
            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
            videoURL = savedInstanceState.getString(STATE_VIDEO_URL);
            description = savedInstanceState.getString(STATE_SHORTDESCRIPTION);
            shortDescription = savedInstanceState.getString(STATE_SHORTDESCRIPTION);

            mPlayerReadyState = savedInstanceState.getBoolean(STATE_PLAYER_READY_STATE);
            mPlayerPosition = savedInstanceState.getLong(STATE_PLAYER_POSITION);

        }
        final View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        ButterKnife.bind(this,rootView);

        stepDescription.setText(description);

        if (videoURL.length() == 0 || getVideoURL()==null || videoURL == null){
            mPlayerView.setVisibility(View.GONE);
        }



        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putInt(STATE_RESUME_WINDOW, mResumeWindow);
        outState.putLong(STATE_RESUME_POSITION, mResumePosition);
        outState.putBoolean(STATE_PLAYER_FULLSCREEN, mExoPlayerFullscreen);
        outState.putString(STATE_VIDEO_URL, videoURL);
        outState.putString(STATE_SHORTDESCRIPTION,shortDescription);

        outState.putBoolean(STATE_PLAYER_READY_STATE, mPlayerReadyState);
        outState.putLong(STATE_PLAYER_POSITION,mPlayerPosition);

        super.onSaveInstanceState(outState);
    }

    public void initializePlayer() {
        /*if (mExoPlayer == null) {

        }*/
        // Create an instance of the ExoPlayer.
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
        mPlayerView.setPlayer(mExoPlayer);

        // Set the ExoPlayer.EventListener to this activity.
            /*mExoPlayer.addListener(getActivity());*/
        String userAgent = Util.getUserAgent(getActivity(), "Cozimento");
        DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(userAgent, null, DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS, DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, true);
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getActivity(), null, httpDataSourceFactory);
        mVideoSource = new ExtractorMediaSource(getVideoURL(), new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);




        // Prepare the MediaSource.

        mExoPlayer.prepare(mVideoSource);
        mExoPlayer.setPlayWhenReady(true);

        boolean haveResumePosition = mResumeWindow != C.INDEX_UNSET;

        if (haveResumePosition) {
            /*mPlayerView.getPlayer().seekTo(mResumeWindow, mResumePosition);*/
            mPlayerView.getPlayer().seekTo(mResumePosition);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mExoPlayer != null){
            releasePlayer();
        }
    }

    @Override
    public void onResume() {

        super.onResume();

        if (Util.SDK_INT <= 23 || mPlayerView == null) {

            mPlayerView = (SimpleExoPlayerView) getView().findViewById(R.id.playerView);
            initFullscreenDialog();
            initFullscreenButton();

            String userAgent = Util.getUserAgent(getActivity(), "Cozimento");
            DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(userAgent, null, DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS, DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, true);
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getActivity(), null, httpDataSourceFactory);
            mVideoSource = new ExtractorMediaSource(getVideoURL(), new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);

        }

        initializePlayer();

        if (mExoPlayerFullscreen) {
            ((ViewGroup) mPlayerView.getParent()).removeView(mPlayerView);
            mFullScreenDialog.addContentView(mPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_fullscreen_skrink));
            mFullScreenDialog.show();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT <= 23) {
            mPlayerView.getPlayer().release();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {

        super.onPause();

        if (Util.SDK_INT <= 23) {
            mPlayerView.getPlayer().release();
        }

        if (mPlayerView != null && mPlayerView.getPlayer() != null) {
            mResumeWindow = mPlayerView.getPlayer().getCurrentWindowIndex();
            mResumePosition = Math.max(0, mPlayerView.getPlayer().getContentPosition());

            mPlayerPosition = mExoPlayer.getCurrentPosition();


            mPlayerView.getPlayer().release();
        }

        if (mFullScreenDialog != null)
            mFullScreenDialog.dismiss();
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

    private void initFullscreenDialog() {

        mFullScreenDialog = new Dialog(getActivity(), android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                if (mExoPlayerFullscreen)
                    closeFullscreenDialog();
                super.onBackPressed();
            }
        };
    }
    private void openFullscreenDialog() {

        ((ViewGroup) mPlayerView.getParent()).removeView(mPlayerView);
        mFullScreenDialog.addContentView(mPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_fullscreen_skrink));
        mExoPlayerFullscreen = true;
        mFullScreenDialog.show();
    }


    private void closeFullscreenDialog() {

        ((ViewGroup) mPlayerView.getParent()).removeView(mPlayerView);
        ((FrameLayout) getView().findViewById(R.id.main_media_frame)).addView(mPlayerView);
        mExoPlayerFullscreen = false;
        mFullScreenDialog.dismiss();
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_fullscreen_expand));
    }

    private void initFullscreenButton() {

        PlaybackControlView controlView = mPlayerView.findViewById(R.id.exo_controller);
        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
        mFullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mExoPlayerFullscreen)
                    openFullscreenDialog();
                else
                    closeFullscreenDialog();
            }
        });
        Log.d("FULLSCREEN_BTN","fullscreen button clicked");
    }

}
