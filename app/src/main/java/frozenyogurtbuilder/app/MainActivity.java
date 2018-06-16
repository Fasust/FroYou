package frozenyogurtbuilder.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    //Buttons
    private Button btn_goTo_reGa;
    private Button btn_goTo_orChPr;
    private Button btn_goTo_imp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_goTo_reGa = findViewById(R.id.btn_goTo_reGa) ;
        btn_goTo_reGa.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RecipeGallery.class));
            }

        });

        btn_goTo_orChPr = findViewById(R.id.btn_goTo_orChPr) ;
        btn_goTo_orChPr.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, OrderChoosePricing.class));
            }

        });

        btn_goTo_imp = findViewById(R.id.btn_goTo_imp) ;
        btn_goTo_imp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Impressum.class));
            }

        });



    }
}
