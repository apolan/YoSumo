package yosumo.src.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import yosumo.src.R;
/**
 * Created by a-pol_000 on 9/7/2016.
 * QR1. Procesamiento de imageness
 * Clase encargada de manejar el procesamiento de imagenes
 * MOD 20160907 - AFP - Adición principales funcionalidades
 */
public class ImgProcessingActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    String mCurrentPhotoPath;
    private Button takePictureButton;
    private ImageView imageView;
    private Uri file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imgprocessing);
        takePictureButton = (Button) findViewById(R.id.btnFoto);
        imageView = (ImageView) findViewById(R.id.imageView);



    }

    public void takePicture(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

        startActivityForResult(intent, 100);
    }



    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                Log.d("CameraDemo", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                imageView.setImageURI(file);
            }
        }
    }
}