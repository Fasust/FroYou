package frozenyogurtbuilder.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MenuImprint extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impressum);

        Button btn_goTo_privacy;
        btn_goTo_privacy = findViewById(R.id.btn_goTo_privacy);
        btn_goTo_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://docs.google.com/document/d/1GGUF8aZaRnqmyE2o-ZFcGlyVy2f0fw2nlYLq7iQ-TQc/edit?usp=sharing"));
                startActivity(browserIntent);
            }
        });

    }
}
