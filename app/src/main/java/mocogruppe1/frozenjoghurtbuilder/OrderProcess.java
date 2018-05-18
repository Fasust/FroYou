package mocogruppe1.frozenjoghurtbuilder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class OrderProcess extends AppCompatActivity {

    FloatingActionButton btn_goTo_orFi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderprocess);

        //Action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Bestellung Prozess");


        btn_goTo_orFi = findViewById(R.id.btn_goTo_orFi) ;
        btn_goTo_orFi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderProcess.this, OrderFinal.class));
            }

        });
    }
}
