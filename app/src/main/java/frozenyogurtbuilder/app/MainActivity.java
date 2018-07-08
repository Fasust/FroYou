package frozenyogurtbuilder.app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    //Buttons
    private Button btn_goTo_reGa;
    private Button btn_goTo_orChPr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        subscribeToPushNotifications();

        // go to order process
        btn_goTo_orChPr = findViewById(R.id.btn_goTo_orChPr);
        btn_goTo_orChPr.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, OrderChoosePricing.class));
            }

        });

        ImageButton imgBtn_orderProcess = findViewById(R.id.imgBtn_orderProcess);
        imgBtn_orderProcess.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this, OrderChoosePricing.class));
                    }
                }
        );


        // go to recipe gallery
        btn_goTo_reGa = findViewById(R.id.btn_goTo_reGa);
        btn_goTo_reGa.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RecipeGallery.class));
            }

        });

        ImageButton imgBtn_gallery = findViewById(R.id.imgBtn_gallery);
        imgBtn_gallery.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this, RecipeGallery.class));
                    }
                }
        );

    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_more, popup.getMenu());
        popup.show();

    }

    public boolean onPopupItemClick (MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.id_information) {

            startActivity(new Intent(MainActivity.this, Information.class));
            return true;
        }
        if (id == R.id.id_impressum) {

            startActivity(new Intent(MainActivity.this, Impressum.class));
            return true;
        }
        if (id == R.id.id_settings) {

            startActivity(new Intent(MainActivity.this, Settings.class));
            return true;
        }
        return false;
    }
    public void subscribeToPushNotifications(){
        FirebaseMessaging.getInstance().subscribeToTopic("PUSH_CHANNEL")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Sucess";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }
                        Log.d("Subcribe",msg);
                    }
                });
    }
}
