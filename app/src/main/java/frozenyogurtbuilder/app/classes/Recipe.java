package frozenyogurtbuilder.app.classes;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.ConnectException;
import java.util.ArrayList;

public class Recipe implements Parcelable {
    private String name;
    private String desription;
    private String ingredients;
    private Bitmap image = null;

    public Recipe(String name, String desription, String ingredients) {
        this.name = name;
        this.desription = desription;
        this.ingredients = ingredients;
    }


    protected Recipe(Parcel in) {
        name = in.readString();
        desription = in.readString();
        ingredients = in.readString();
        image = in.readParcelable(Bitmap.class.getClassLoader());
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(desription);
        parcel.writeString(ingredients);
        parcel.writeParcelable(image, i);
    }

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

    //GETTERS AND SETTERS
    public String getName() {
        return name;
    }
    public String getDesription() {
        return desription;
    }
    public String getIngredients() {
        return ingredients;
    }
    public Bitmap getImage() {
        return image;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }

}
