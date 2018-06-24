package frozenyogurtbuilder.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import frozenyogurtbuilder.app.classes.Recipe;

public class RecipeDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipedetail);

        //Get Recepie
        Bundle data = getIntent().getExtras();
        Recipe recipe = data.getParcelable(RecipeGallery.RECIPE_KEY);

        //Find Views
        TextView txtName = findViewById(R.id.textView_recepieName);
        TextView txtDescription = findViewById(R.id.textView_descritopn);
        TextView txtIngridents = findViewById(R.id.textview_ingrididentsList);
        ImageView image = findViewById(R.id.imageView_recipePicture);


        //set Views
        txtName.setText(recipe.getName());
        txtDescription.setText(recipe.getDesription());
        txtIngridents.setText(recipe.getIngredients());
        if(image != null){
            image.setImageBitmap(recipe.getImage());
        }

    }
}
