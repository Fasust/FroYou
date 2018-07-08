package frozenyogurtbuilder.app;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class QrcodeGenerator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_generator);

        ImageView imageView_qrcode;
        imageView_qrcode = findViewById(R.id.imageView_qrcode);
        Bitmap bitmap = getIntent().getParcelableExtra(OrderFinal.QR_CODE);
        imageView_qrcode.setImageBitmap(bitmap);

    }
}
