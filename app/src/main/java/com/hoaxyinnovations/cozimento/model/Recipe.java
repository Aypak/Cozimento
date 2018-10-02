package com.hoaxyinnovations.cozimento.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;

/**
 * Created by kapsa on 1/22/2018.
 */

    public class Recipe implements Parcelable {

    private long id;

    private String name;

    private Ingredient[] ingredients;

    private Step[] steps;

    private double servings;

    private String image;

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public Recipe() {
    }

    public Recipe(long id, String name, Ingredient[] ingredients, Step[] steps, double servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    public Recipe(Parcel in) {
        id = in.readLong();
        name = in.readString();

        Parcelable[] objArray = in.readParcelableArray(Ingredient.class.getClassLoader());
        ingredients = Arrays.copyOf(objArray, objArray.length, Ingredient[].class);

        objArray = in.readParcelableArray(Step.class.getClassLoader());
        steps = Arrays.copyOf(objArray, objArray.length, Step[].class);

        servings = in.readDouble();
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeParcelableArray(ingredients, flags);
        parcel.writeParcelableArray(steps, flags);
        parcel.writeDouble(servings);
        parcel.writeString(image);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ingredient[] getIngredients() {
        return ingredients;
    }

    public String getIngredientsString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(ingredients);
        } catch (Exception e) {
            return "";
        }
    }

    public String getStepsString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(steps);
        } catch (Exception e) {
            return "";
        }
    }

    public void setIngredients(Ingredient [] ingredients) {
        this.ingredients = ingredients;
    }

    public Step[] getSteps() {
        return steps;
    }

    public void setSteps(Step[] steps) {
        this.steps = steps;
    }

    public double getServings() {
        return servings;
    }

    public void setServings(double servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

}