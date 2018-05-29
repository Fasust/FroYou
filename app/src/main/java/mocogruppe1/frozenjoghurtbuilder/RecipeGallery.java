package mocogruppe1.frozenjoghurtbuilder;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class RecipeGallery extends AppCompatActivity {

    //Buttons
    ImageButton btn_goTo_reDe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipegallery);

        //Action Bar
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("Rezept Galerie");

        btn_goTo_reDe = findViewById(R.id.btn_goTo_reDe) ;
        btn_goTo_reDe.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(RecipeGallery.this, RecipeDetail.class));
            }

        });

    }
}
