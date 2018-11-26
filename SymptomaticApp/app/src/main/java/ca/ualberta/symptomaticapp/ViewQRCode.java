package ca.ualberta.symptomaticapp;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class ViewQRCode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_qrcode);
        String username,type;
        Button returnbutton = (Button) findViewById(R.id.btn_returnback);
        if(Login.thisCaregiver == null){
            username = Login.thisUser.username;
            type = "patient";
        } else{
            username = Login.thisCaregiver.username;
            type= "caregiver";
        }
        String encoded = username + ","+type;

        MultiFormatWriter mfr = new MultiFormatWriter();
        ImageView qrdisplay = (ImageView) findViewById(R.id.iv_qrcode);
        try{
            BitMatrix bitMatrix = mfr.encode(encoded, BarcodeFormat.QR_CODE,600,600);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrdisplay.setImageBitmap(bitmap);
        }catch(WriterException e){
            e.printStackTrace();
        }

        returnbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
