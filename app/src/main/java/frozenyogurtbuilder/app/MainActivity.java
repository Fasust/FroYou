package frozenyogurtbuilder.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

public class MainActivity extends AppCompatActivity {

    //Buttons
    private Button btn_goTo_reGa;
    private Button btn_goTo_orChPr;
    private Button btn_goTo_imp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_goTo_reGa = findViewById(R.id.btn_goTo_reGa);
        btn_goTo_reGa.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RecipeGallery.class));
            }

        });

        btn_goTo_orChPr = findViewById(R.id.btn_goTo_orChPr);
        btn_goTo_orChPr.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, OrderChoosePricing.class));
            }

        });

        btn_goTo_imp = findViewById(R.id.btn_goTo_imp);
        btn_goTo_imp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Impressum.class));
            }

        });

    }

   /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.popup_more, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.id_impressum) {

            startActivity(new Intent(MainActivity.this, Impressum.class));
            return true;
        }
        return false;
    }*/

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_more, popup.getMenu());
        popup.show();

    }

    public boolean onPopupItemClick (MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.id_impressum) {

            startActivity(new Intent(MainActivity.this, Impressum.class));
            return true;
        }
        return false;
    }
}
