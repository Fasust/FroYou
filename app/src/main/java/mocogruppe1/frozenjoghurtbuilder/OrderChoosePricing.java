package mocogruppe1.frozenjoghurtbuilder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OrderChoosePricing extends AppCompatActivity {

    //Buttons
    private Button btn_goTo_orPr;

    //Intent
    public final static String SIZE_KEY = "size";
    private int size;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = new Intent(OrderChoosePricing.this, OrderProcess.class);

        buildLayout();
    }

    private void buildLayout(){
        setContentView(R.layout.activity_order_choose_pricing);

        //Action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Groessen Auswahl");

        //Buttons and OnClickListener
        View.OnClickListener click = new  View.OnClickListener(){
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.btn_small:
                        size = 1;
                        break;

                    case R.id.btn_medium:
                        size = 2;
                        break;

                    case R.id.btn_large:
                        size = 3;
                        break;

                    default:
                        break;
                }
                intent.putExtra(SIZE_KEY,size);
                startActivity(intent);
            }
        };

        btn_goTo_orPr = findViewById(R.id.btn_large);
        btn_goTo_orPr.setOnClickListener(click);
        btn_goTo_orPr = findViewById(R.id.btn_medium);
        btn_goTo_orPr.setOnClickListener(click);
        btn_goTo_orPr = findViewById(R.id.btn_small);
        btn_goTo_orPr.setOnClickListener(click);

    }
}
