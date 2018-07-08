package frozenyogurtbuilder.app.classes;

import android.content.Context;

import frozenyogurtbuilder.app.R;

public class IngredientSelectBox extends AlertSelectBox<Ingredient> {
    public IngredientSelectBox(Context context, String bannerTxt, AfterSelctListener<Ingredient> listener) {
        super(context, bannerTxt, R.drawable.logo_icon, listener);
    }
}