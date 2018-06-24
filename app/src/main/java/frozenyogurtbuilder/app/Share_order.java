package frozenyogurtbuilder.app;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import frozenyogurtbuilder.app.classes.Recipe;

public class Share_order extends AppCompatActivity {

    //Firestore
    private StorageReference mStorageRef;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference recipeCollection = db.collection("recipes");

    private ImageButton btn_useCamera;
    private ImageView imageView_picture;
    static final int REQUEST_IMAGE_CAPTURE = 1111;

    private TextView textView_creationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_share_order);

        final String shareList = getIntent().getExtras().getString(OrderFinal.ORDER_SHARE);
        textView_creationText = findViewById(R.id.textView_descritopn);
        textView_creationText.setText(shareList);

        final EditText nameEdit = findViewById(R.id.editText_name);
        Button share = findViewById(R.id.btn_share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEdit.getText().toString();
                String description = "Test Decription";
                String ingridients = shareList;
                Recipe recipe = new Recipe(name,description,ingridients);

                recipeCollection.add( recipe.toHash());
            }
        });


        btn_useCamera = (ImageButton) findViewById(R.id.btn_useCamera);
        imageView_picture = (ImageView) findViewById(R.id.imageView_picture);

        btn_useCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //cameraIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION,ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data != null) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                data.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION,ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                imageView_picture.setImageBitmap(photo);
            }
        }
    }
}
