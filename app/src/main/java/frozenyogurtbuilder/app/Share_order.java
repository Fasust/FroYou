package frozenyogurtbuilder.app;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import frozenyogurtbuilder.app.classes.Recipe;

public class Share_order extends AppCompatActivity {

    //Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference recipeCollection = db.collection("recipes");

    //Firestroage
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private FirebaseAuth mAuth;

    //Views
    private TextView textView_creationText;
    private ImageButton btn_useCamera;
    private ImageView imageView_picture;
    private EditText nameEdit;
    private EditText descEdit;

    static final int REQUEST_IMAGE_CAPTURE = 1111;
    private Bitmap photo = null;
    private String ingridientsList;

    public static String RECIPE_KEY = "recipe";
    public static String JUSTSHARED_KEY = "shared";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_share_order);

        imageView_picture = findViewById(R.id.imageView_picture);
        nameEdit = findViewById(R.id.editText_name);
        descEdit = findViewById(R.id.editText_desc);

        initFirebase();
        buildIngridentsList();
        buildButtons();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data != null) {
                Bundle extras = data.getExtras();
                photo = (Bitmap) extras.get("data");
                data.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION,ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                imageView_picture.setImageBitmap(photo);
            }
        }
    }

    private void initFirebase(){
        //Authentication
        mAuth = FirebaseAuth.getInstance();

        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            // If sign in fails, display a message to the user
                            Toast.makeText(Share_order.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        //Storage
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

    }
    private void buildButtons(){
        btn_useCamera = (ImageButton) findViewById(R.id.btn_useCamera);
        btn_useCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //cameraIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION,ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);

            }
        });

        Button share = findViewById(R.id.btn_share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check
                if( TextUtils.isEmpty(nameEdit.getText())){
                    nameEdit.setError( "First name is required!" );
                    return;

                } else if(photo == null) {
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.share_pleasePhoto), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0 , 220);
                    toast.show();
                    return;
                }

                //Logic
                String name = nameEdit.getText().toString();
                String description = descEdit.getText().toString();
                String ingridients = ingridientsList;

                String imagePath = "recipe_images/" + UUID.randomUUID().toString();
                uploadImage(photo, imagePath); //Upload Image

                Recipe recipe = new Recipe(name,description,ingridients,imagePath);
                Map hash = recipe.toHash();
                hash.put("timestamp",System.currentTimeMillis());
                recipeCollection.add(hash); //Upload Recepie

                //Start Activity
                Intent intent = new Intent(Share_order.this, RecipeDetail.class);
                intent.putExtra(RECIPE_KEY, recipe);
                intent.putExtra(JUSTSHARED_KEY,true);
                startActivity(intent);
            }
        });
    }
    private void buildIngridentsList(){
        ingridientsList = getIntent().getExtras().getString(OrderFinal.ORDER_SHARE);
        textView_creationText = findViewById(R.id.textView_descritopn);
        textView_creationText.setText(ingridientsList);
    }
    private void uploadImage(Bitmap image,String path){
        StorageReference imageRef = storageRef.child(path);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        initFirebase();
    }

}
