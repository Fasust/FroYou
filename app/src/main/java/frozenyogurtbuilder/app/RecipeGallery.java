package frozenyogurtbuilder.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import frozenyogurtbuilder.app.classes.BaseErrorMessage;
import frozenyogurtbuilder.app.classes.FSImageLoader;
import frozenyogurtbuilder.app.classes.FSLoader;
import frozenyogurtbuilder.app.classes.FSRecepieLoader;
import frozenyogurtbuilder.app.classes.Ingredient;
import frozenyogurtbuilder.app.classes.Recipe;

public class RecipeGallery extends AppCompatActivity {

    public static String RECIPE_KEY = "recipe";
    private ArrayList<Recipe> recipeList = new ArrayList<>();

    //View
    private ProgressBar progressBar;

    //Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference recipeCollection = db.collection("recipes");

    //Firestroage
    private FirebaseStorage storage;
    private StorageReference storageRef;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipegallery);
        initStorage();
        buildProgressbar();

        FSRecepieLoader loader = new FSRecepieLoader(recipeCollection, new FSLoader.TaskListner<ArrayList<Recipe>>() {
            @Override
            public void onComplete(ArrayList<Recipe> result) {
                progressBar.setVisibility(View.INVISIBLE);
                recipeList = result;
                builListView();
            }

            @Override
            public void onFail() {
                BaseErrorMessage loadingError = new BaseErrorMessage("Es ist ein Fehler beim Laden der Datenbank aufgetreten","Sorry",RecipeGallery.this);
                loadingError.show();
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
                final Recipe recipe = getItem(position);
                // Check if an existing view is being reused, otherwise inflate the view
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_recipegallery, parent, false);
                }

                //SET Text-----------------------------------------------------------------
                TextView name = convertView.findViewById(R.id.txt_recipeName);
                name.setText(recipe.getName());

                //SET Imagebutton----------------------------------------------------------
                final ImageButton imageButton = convertView.findViewById(R.id.imageView_recipePicture);

                final ConstraintLayout recipeBlock = convertView.findViewById(R.id.recipeBlock);
                if(recipe.getImagePath() != null) {
                    FSImageLoader loader = new FSImageLoader(recipe.getImagePath(), new FSImageLoader.onFishLoading() {
                        @Override
                        public void onComplete(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            imageButton.setImageBitmap(bitmap);
                        }

                        @Override
                        public void onFail() {

                        }
                    });
                    loader.load();

                }

                //ON Click------------------------------------------------------
                recipeBlock.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(RecipeGallery.this, RecipeDetail.class);
                        intent.putExtra(RECIPE_KEY,recipe);
                        startActivity(intent);
                    }
                });


                // Return the completed view to render on screen
                return convertView;
            }
        };
        listView.setAdapter(adapter);

    }
    private void initStorage(){
        //mAuth = FirebaseAuth.getInstance();
        // mAuth.signInAnonymously();

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

    }
    private void buildProgressbar(){
        progressBar = findViewById(R.id.gallery_progressBar);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(RecipeGallery.this, MainActivity.class));
        finish();

    }

}
