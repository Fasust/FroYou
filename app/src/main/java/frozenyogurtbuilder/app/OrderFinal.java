package frozenyogurtbuilder.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class OrderFinal extends AppCompatActivity {

    private Button btn_goTo_qrCode;
    private Button btn_goTo_makePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderfinal);

        final Context context = this;

        btn_goTo_qrCode = findViewById(R.id.btn_goTo_qrCode);
        btn_goTo_qrCode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String orderList = getIntent().getExtras().getString(OrderProcess.ORDER_KEY);
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(orderList, BarcodeFormat.QR_CODE, 300, 300);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    Intent intent = new Intent(context, Qrcode_generator.class);
                    intent.putExtra("qrcode",bitmap);
                    context.startActivity(intent);
                } catch(WriterException e) {
                    e.printStackTrace();
                }
            }

        });


        btn_goTo_makePicture = findViewById(R.id.btn_goTo_makePicture);
        btn_goTo_makePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPicture = new Intent(OrderFinal.this, Share_order.class);
                //intentPicture.putExtra();
                startActivity(intentPicture);
            }
        });
    }
}
