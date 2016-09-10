package yosumo.src.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.googlecode.tesseract.android.TessBaseAPI;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import yosumo.src.R;
import yosumo.src.debug.Debugger;

/**
 * Created by a-pol_000 on 9/7/2016.
 * QR1. Procesamiento de imageness
 * Clase encargada de manejar el procesamiento de imagenes
 * MOD 20160907 - AFP - Adición principales funcionalidades
 * MOD 20160909 - AFP - Implementación de persistencia de la factura.
 * MOD 20160909 - AFP - Implementación de tess data reconocimiento de factura.
 */
public class ImgProcessingActivity extends AppCompatActivity {
    private static final String lang = "eng";
    private static final String TAG = ImgProcessingActivity.class.getSimpleName();
    private Button btnAction;
    private ImageView imageView;
    private Uri file;
    private Bitmap factura = null;
    private TessBaseAPI tessBaseApi;
    File folder_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    TextView textView;
    String result = "empty";
    /**
     *
     */
    private String accion="NONE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imgprocessing);

        accion="TOMAR_FOTO";
        btnAction = (Button) findViewById(R.id.btnAction);
        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);
    }

    /**
     *  Método que se llama una vez se ha tomado la fotografia
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(accion=="TOMAR_FOTO"){
            if (requestCode == 100 && file!=null) {
                if (resultCode == RESULT_OK) {
                    //Se reduce el tamano de la imagen porque no es posible hacerle render
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    factura = BitmapFactory.decodeFile(file.getPath() ,bmOptions);
                    factura = Bitmap.createScaledBitmap(factura,(int)(factura.getWidth()*0.4),(int)(factura.getHeight()*0.4),true);
                    imageView.setImageBitmap(factura);

                    controladorProcesoFactura("NEXT");

                    Log.d("btnAction",btnAction.getText()+"");
                }
            }
        }
    }

    /**
     * Metodo que se ejecuta en la interfaz. Cambia el estado de
     * @param view
     */
    public void action(View view){
        Log.d("action",accion);
        if(accion.equalsIgnoreCase("TOMAR_FOTO")){
            tomarFoto(view);
        }else if(accion.equalsIgnoreCase("PROCESAR_FOTO")){
        try{procesarImagen(view);}
        catch(Exception e){
            Log.d("Errorprocesando",e.getMessage());
        }
        }
    }

    /**
     * Metodo que registra la factura
     * @param view
     */
    public void tomarFoto(View view) {
        Log.d("registrar","s");

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
        Log.d("registrar",file.getPath());
        startActivityForResult(intent, 100);
    }


    /**
     *
     * @return File La foto tomada
     */
    private  File getOutputMediaFile(){
        File mediaStorageDir = new File(folder_path , this.getResources().getString(R.string.folder_app));
        Log.d("folder media", mediaStorageDir.getAbsolutePath());
        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null; // Se debe manejar este error
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        // Se registra el nombre de la ultima factura registrada
        //todo persistencia del nombre, para que se recupere de errores
        //
        return new File(mediaStorageDir.getPath() + File.separator + this.getResources().getString(R.string.sufijo_factura)+ timeStamp + ".jpg");
    }


    /**
     * Metodo que reescala un bitmap. Si es muy grande no es posible mostarlo correctamente
     * @param realImage
     * @param maxImageSize
     * @param filter
     * @return
     */
    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize, boolean filter) {
        float ratio = Math.min((float) maxImageSize / realImage.getWidth(), (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width, height, filter);
        return newBitmap;
    }

    /**
     * Metodo que usa
     */
    public String procesarImagen(View view) throws Exception {

        Log.d("ini Procesar folder. ",folder_path.getPath());
        Log.d("ini Procesar folder. ",folder_path.getAbsolutePath());

        TessBaseAPI tessBaseApi = new TessBaseAPI();
        tessBaseApi.init(folder_path.getPath()+"/YoSumo/", "eng");
        tessBaseApi.setImage(factura);
        String extractedText = tessBaseApi.getUTF8Text();
        tessBaseApi.end();


        textView.setText(extractedText);
        Log.d("Procesar",extractedText);
        controladorProcesoFactura("NEXT");
        return extractedText;

        //return "a";
    }

    /**
     *
     * @param estado
     */
    void controladorProcesoFactura(String estado){
        // Cunado viene vacio hace next
        if(estado.equalsIgnoreCase("NEXT")){
            if(accion.equalsIgnoreCase("TOMAR_FOTO")){
                accion = "PROCESAR_FOTO";
                btnAction.setText("Procesar Factura");
            }else if(accion.equalsIgnoreCase("PROCESAR_FOTO")){
                accion = "TOMAR_FOTO";
                btnAction.setText("Registrar Factura");

            }
        }else if(estado.equalsIgnoreCase("")){

        }
    }

}