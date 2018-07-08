package frozenyogurtbuilder.app.classes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import frozenyogurtbuilder.app.R;
import frozenyogurtbuilder.app.RecipeDetail;
import frozenyogurtbuilder.app.classes.Firebase.GlideApp;

import static android.support.v4.content.ContextCompat.startActivity;

public class RecepieViewHolder extends RecyclerView.ViewHolder {
    public static String RECIPE_KEY = "recipe";
    private TextView name;
    private ImageButton image;

    //Firestroage
    private FirebaseStorage storage;
    private StorageReference storageRef;

    public RecepieViewHolder(View itemView) {
        super(itemView);
        initStorage();

        name = itemView.findViewById(R.id.txt_recipeName);
        image = itemView.findViewById(R.id.imageView_recipePicture);
    }
    public void setData(final Recipe recipe, final Context context){
        name.setText(recipe.getName());

        //Bind Image to Button------------------------------------------
        if(recipe.getImagePath() != null){
            StorageReference recImageRef = storageRef.child(recipe.getImagePath());

            GlideApp.with(context)
                    .load(recImageRef)
                    .into(image);
        }

        //ON Click------------------------------------------------------
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RecipeDetail.class);
                intent.putExtra(RECIPE_KEY,recipe);
                startActivity(context,intent,null);
            }
        });
    }
    private void initStorage(){
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

    }
}
