package frozenyogurtbuilder.app.classes;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private String desription;
    private ArrayList<Ingredient> ingredients;

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
}
