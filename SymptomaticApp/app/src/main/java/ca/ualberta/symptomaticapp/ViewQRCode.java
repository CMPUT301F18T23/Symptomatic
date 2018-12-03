package ca.ualberta.symptomaticapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        Toolbar toolbar = findViewById(R.id.viewQR_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("QR Code");
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

        createPatientQRCode(encoded);
//        MultiFormatWriter mfr = new MultiFormatWriter();
//        ImageView qrdisplay = (ImageView) findViewById(R.id.iv_qrcode);
//        try{
//            BitMatrix bitMatrix = mfr.encode(encoded, BarcodeFormat.QR_CODE,600,600);
//            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
//            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
//            qrdisplay.setImageBitmap(bitmap);
//        }catch(WriterException e){
//            e.printStackTrace();
//        }

        returnbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void createPatientQRCode(String encoded) {
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_qr_menu, menu);
        return true;
    }
    public void viewHome(MenuItem menu) {
        Intent intent = new Intent(ViewQRCode.this, MainActivity.class);
        startActivity(intent);
    }

    public void viewEditAccount(MenuItem menu){
        Intent intent = new Intent(ViewQRCode.this, EditAccountActivity.class);
        startActivity(intent);
    }


    public void viewAddProblem(MenuItem menu){
        Intent intent = new Intent(ViewQRCode.this, AddProblemActivity.class);
        startActivity(intent);
    }

    public void viewViewProblems(MenuItem menu) {
        Intent intent = new Intent(ViewQRCode.this, ListProblemsActivity.class);
        startActivity(intent);
    }

    public void viewLogout(MenuItem menu){
        Login.thisCaregiver = null;
        Login.thisUser = null;
        Intent intent = new Intent(ViewQRCode.this, MainActivity.class);
        startActivity(intent);
    }

}
