package frozenyogurtbuilder.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import frozenyogurtbuilder.app.classes.FSImageLoader;
import frozenyogurtbuilder.app.classes.Recipe;

public class RecipeDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipedetail);

        //Get Recepie
        Bundle data = getIntent().getExtras();
        Recipe recipe = data.getParcelable(RecipeGallery.RECIPE_KEY);
        Bitmap tmp_bitmap = data.getParcelable(Share_order.PHOTO_TMP);

        //Find Views
        TextView txtName = findViewById(R.id.textView_recepieName);
        TextView txtDescription = findViewById(R.id.textView_descritopn);
        TextView txtIngridents = findViewById(R.id.textview_ingrididentsList);
        final ImageView image = findViewById(R.id.imageView_recipePicture);

        //Button btn_toGallery = findViewById(R.id.btn_toGallery);


        //set Views
        image.setImageBitmap(tmp_bitmap);
        txtName.setText(recipe.getName());
        txtDescription.setText(recipe.getDesription());
        txtIngridents.setText(recipe.getIngredients());
        if (recipe.getImagePath() != null) {
            FSImageLoader loader = new FSImageLoader(recipe.getImagePath(), new FSImageLoader.onFishLoading() {
                @Override
                public void onComplete(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    image.setImageBitmap(bitmap);
                }

                @Override
                public void onFail() {

                }
            });
            loader.load();

        }

        /*
        btn_toGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeDetail.this, RecipeGallery.class);
                startActivity(intent);
            }
        });
        */

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(RecipeDetail.this, RecipeGallery.class));
        finish();

    }

}
