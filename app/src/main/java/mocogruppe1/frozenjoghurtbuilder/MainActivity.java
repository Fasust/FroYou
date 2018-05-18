package mocogruppe1.frozenjoghurtbuilder;

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

    //connection to Database (testdata) + reference to testdata for using the database
    private FirebaseDatabase testbase = FirebaseDatabase.getInstance();
    private DatabaseReference testref = testbase.getReference();

    //Buttons
    private Button btn_goTo_reGa;
    private Button btn_goTo_orPr;

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

        btn_goTo_orPr = findViewById(R.id.btn_goTo_orPr) ;
        btn_goTo_orPr.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, OrderProcess.class));
            }

        });

        /* START Fire Base Test-----------------------------------------------------------------
        final TextView testdata = (TextView)findViewById(R.id.testdata);

        //set a Listener on reference to recognize changes in our database
        testref.addValueEventListener(new ValueEventListener() {
            @Override
            //at changes, we get out of our database the change value as a string and put it from newvalue into testdata
            public void onDataChange(DataSnapshot dataSnapshot) {
                String newvalue = dataSnapshot.getValue(String.class);
                testdata.setText(newvalue);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        END Fire Base Test-----------------------------------------------------------------*/

    }
}
