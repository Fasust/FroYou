package frozenyogurtbuilder.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import frozenyogurtbuilder.app.classes.RecepieViewHolder;
import frozenyogurtbuilder.app.classes.Recipe;

public class RecipeDetail extends AppCompatActivity {
    //Firestroage
    private FirebaseStorage storage;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipedetail);
        final Context context = this;
        initStorage();

        //Get Recepie
        Bundle data = getIntent().getExtras();
        final Recipe recipe = data.getParcelable(RecepieViewHolder.RECIPE_KEY);
        Bitmap sharedImage = getIntent().getParcelableExtra(OrderShare.SHAREDIMAGE_KEY);

        //Find Views
        TextView txtName = findViewById(R.id.textView_recepieName);
        TextView txtDescription = findViewById(R.id.textView_descritopn);
        TextView txtIngridents = findViewById(R.id.textview_ingrididentsList);
        final ImageView image = findViewById(R.id.imageView_recipePicture);

        if( sharedImage != null ) {
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.detail_successfullShared), Toast.LENGTH_SHORT);
            toast.show();
            image.setImageBitmap(sharedImage);
        }else{
            StorageReference recImageRef = storageRef.child(recipe.getImagePath());
            Glide.with(this)
                    .load(recImageRef)
                    .into(image);
        }

        //set Views
        txtName.setText(recipe.getName());
        txtDescription.setText(recipe.getDescription());
        txtIngridents.setText(recipe.getIngredients());


        //Buttons-----------------------------------------

        FloatingActionButton btn_toQrCode = findViewById(R.id.btn_toQrCode);
        btn_toQrCode.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

                        try {
                            BitMatrix bitMatrix = multiFormatWriter.encode(recipe.getIngredients(), BarcodeFormat.QR_CODE, 300, 300);
                            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                            Intent intent = new Intent(context, QrcodeGenerator.class);
                            intent.putExtra(OrderFinal.QR_CODE,bitmap);
                            context.startActivity(intent);
                        } catch(WriterException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        Button btn_toGallery = findViewById(R.id.btn_toGallery);
        btn_toGallery.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(RecipeDetail.this, RecipeGallery.class));
                    }
                }
        );

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(RecipeDetail.this, RecipeGallery.class));
        finish();

    }
    private void initStorage(){
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

    }
}
