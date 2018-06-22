package frozenyogurtbuilder.app.classes;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import java.net.ConnectException;
import java.util.ArrayList;

import frozenyogurtbuilder.app.OrderProcess;
import frozenyogurtbuilder.app.R;
import frozenyogurtbuilder.app.RecipeGallery;


public class Recipe {
    private String name;
    private String desription;
    private ArrayList<Ingredient> ingredients;
    private Bitmap image = null;

    public Recipe(String name, String desription, ArrayList<Ingredient> ingredients) {
        this.name = name;
        this.desription = desription;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }
    public String getDesription() {
        return desription;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

}
