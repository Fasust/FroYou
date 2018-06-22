package frozenyogurtbuilder.app;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import frozenyogurtbuilder.app.classes.FSLoader;
import frozenyogurtbuilder.app.classes.FSRecepieLoader;
import frozenyogurtbuilder.app.classes.Ingredient;
import frozenyogurtbuilder.app.classes.Recipe;

public class RecipeGallery extends AppCompatActivity {

    private ArrayList<Recipe> recipeList = new ArrayList<>();

    //Firestore
    private StorageReference mStorageRef;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference recipeCollection = db.collection("recipes");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipegallery);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        FSRecepieLoader loader = new FSRecepieLoader(recipeCollection, new FSLoader.TaskListner<ArrayList<Recipe>>() {
            @Override
            public void onComplete(ArrayList<Recipe> result) {
                recipeList = result;
                builListView();
            }

            @Override
            public void onFail() {

            }
        });
        loader.execute();

    }

    private void builListView(){
        ListView listView = findViewById(R.id.recipeDetail_listview);
        ArrayAdapter<Recipe> adapter = new ArrayAdapter<Recipe>(RecipeGallery.this, R.id.fragment_ingridient, recipeList){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Get the data item for this position
                Recipe recipe = getItem(position);
                // Check if an existing view is being reused, otherwise inflate the view
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_recipegallery, parent, false);
                }

                TextView name = convertView.findViewById(R.id.txt_recipeName);
                name.setText(recipe.getName());

                ImageButton imageButton = convertView.findViewById(R.id.imageView_recipePicture);
                if(recipe.getImage() != null) {
                    imageButton.setImageBitmap(recipe.getImage());
                }
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(RecipeGallery.this, RecipeDetail.class);
                        startActivity(intent);
                    }
                });


                // Return the completed view to render on screen
                return convertView;
            }
        };
        listView.setAdapter(adapter);

    }
}
