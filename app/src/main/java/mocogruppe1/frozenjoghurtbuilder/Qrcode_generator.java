package mocogruppe1.frozenjoghurtbuilder;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class Qrcode_generator extends AppCompatActivity {

    private ImageView imageView_qrcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_generator);

        imageView_qrcode = (ImageView) findViewById(R.id.imageView_qrcode);
        Bitmap bitmap = getIntent().getParcelableExtra("qrcode");
        imageView_qrcode.setImageBitmap(bitmap);
    }
}
