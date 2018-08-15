package frozenyogurtbuilder.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.UUID;

import frozenyogurtbuilder.app.classes.Recipe;

public class OrderShare extends AppCompatActivity {

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
    private boolean canUseCamera;
    private String ingridientsList;

    public static String RECIPE_KEY = "recipe";
    public static String SHAREDIMAGE_KEY = "shared";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_share_order);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED) {
            Log.d("PERMISSION", "Camera granted");
            canUseCamera = true;
        } else {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 500);
            }
        }

        imageView_picture = findViewById(R.id.imageView_picture);
        nameEdit = findViewById(R.id.editText_name);
        descEdit = findViewById(R.id.editText_desc);

        initFirebase();
        buildIngridentsList();
        buildButtons();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 500) {
            canUseCamera = true;
        } else {
            // Message dass Kamera nicht verwendet werden darf
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        initFirebase();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data != null) {
                Bundle extras = data.getExtras();
                photo = (Bitmap) extras.get("data");
                //data.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                imageView_picture.setImageBitmap(photo);
            }
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Camera failed", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void initFirebase() {
        //Authentication
        mAuth = FirebaseAuth.getInstance();

        mAuth.signInAnonymously();

        //Storage
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

    }

    private void buildButtons() {
        btn_useCamera = findViewById(R.id.btn_useCamera);
        btn_useCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(canUseCamera) {
                    if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                        //cameraIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
            }
        });

        Button share = findViewById(R.id.btn_share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check
                if (TextUtils.isEmpty(nameEdit.getText())) {
                    nameEdit.setError(getString(R.string.noRecipeName));
                    return;

                } else if (photo == null) {
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.share_pleasePhoto), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 220);
                    toast.show();
                    return;
                }

                //Logic
                String name = nameEdit.getText().toString();
                String description = descEdit.getText().toString();
                String ingridients = ingridientsList;

                String imagePath = "recipe_images/" + UUID.randomUUID().toString();
                uploadImage(photo, imagePath); //Upload Image

                Recipe recipe = new Recipe(name, description, ingridients, imagePath);
                Map hash = recipe.toHash();
                hash.put("timestamp", System.currentTimeMillis());
                recipeCollection.add(hash); //Upload Recepie

                //Start Activity
                Intent intent = new Intent(OrderShare.this, RecipeDetail.class);
                intent.putExtra(RECIPE_KEY, recipe);
                intent.putExtra(SHAREDIMAGE_KEY, photo);

                startActivity(intent);
            }
        });
    }

    private void buildIngridentsList() {
        ingridientsList = getIntent().getExtras().getString(OrderFinal.ORDER_SHARE);
        textView_creationText = findViewById(R.id.textView_descritopn);
        textView_creationText.setText(ingridientsList);
    }

    private void uploadImage(Bitmap image, String path) {
        StorageReference imageRef = storageRef.child(path);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        imageRef.putBytes(data);
    }
}
