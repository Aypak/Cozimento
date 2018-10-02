package com.hoaxyinnovations.cozimento.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kapsa on 1/22/2018.
 */

public class Step implements Parcelable{
    private long id;

    private String shortDescription;

    private String description;

    private URL videoURL;

    private URL thumbnailURL;

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Step)) {
            return false;
        }
        Step otherStep = (Step) obj;
        return
                id == otherStep.id &&
                        shortDescription.equals(otherStep.shortDescription) &&
                        description.equals(otherStep.description);
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    public Step(long id, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        try {
            this.videoURL = new URL(videoURL);
        } catch (MalformedURLException e) {
            this.videoURL = null;
        }

        try {
            this.thumbnailURL = new URL(thumbnailURL);
        } catch (MalformedURLException e) {
            this.thumbnailURL = null;
        }

    }

    public Step(Parcel in) {
        id = in.readLong();
        shortDescription = in.readString();
        description = in.readString();
        try {
            videoURL = new URL(in.readString());
        } catch (MalformedURLException e) {
            videoURL = null;
        }

        try {
            thumbnailURL = new URL(in.readString());
        } catch (MalformedURLException e) {
            thumbnailURL = null;
        }

    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(shortDescription);
        parcel.writeString(description);
        parcel.writeString(videoURL == null ? "" : videoURL.toString());
        parcel.writeString(thumbnailURL == null ? "" : thumbnailURL.toString());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public URL getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(URL videoURL) {
        this.videoURL = videoURL;
    }

    public URL getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(URL thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
