package yosumo.src.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Debug;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import yosumo.src.R;
import yosumo.src.debug.Debugger;

/**
 * Created by a-pol_000 on 9/7/2016.
 * QR1. Procesamiento de facturas
 * Clase encargada de manejar el procesamiento de facturas de la aplicaión
 * MOD 20160907 - AFP - Adición principales funcionalidades
 * MOD 20160909 - AFP - Implementación de persistencia de la factura.
 * MOD 20160909 - AFP - Implementación de tess data reconocimiento de factura.
 * MOD 20160911 - AFP - Limpieza del codigo.
 *                      Adicion AsyncTASK
 *                      Convert bitmap en Blanco y negro
 *
 */
public class ImgProcessingActivity extends AppCompatActivity {

    private static final String TAG = ImgProcessingActivity.class.getSimpleName();
    private File folder_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

    private Uri file;

    private TessBaseAPI tessBaseApi;
    private String ACTION;
    private Debugger debug;
    private Bitmap factura = null;

    // Componentes de interfaz
    private Button btnAction;
    private ImageView imageView;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imgprocessing);

        btnAction = (Button) findViewById(R.id.btnAction);
        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);

        debug = new Debugger(this.getBaseContext());
        debug.debugConsole(TAG+":",folder_path.getAbsolutePath());
        ACTION="TOMAR_FOTO";
    }

    /**
     *  Método que se llama una vez se ha tomado la fotografia
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(ACTION.equalsIgnoreCase("TOMAR_FOTO")){
            if (requestCode == 100 && file!=null) {
                if (resultCode == RESULT_OK) {
                    //Se reduce el tamano de la imagen porque no es posible hacerle render
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    factura = BitmapFactory.decodeFile(file.getPath() ,bmOptions);
                    factura = Bitmap.createScaledBitmap(factura,(int)(factura.getWidth()*0.4),(int)(factura.getHeight()*0.4),true);
                    imageView.setImageBitmap(factura);
                    debug.addResultado("SizePic:"+factura.getByteCount());

                    controladorProcesoFactura("NEXT");
                }
            }
        }
    }


    /**
     * Metodo que registra la factura
     * @param view
     */
    public void tomarFoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File mediaStorageDir = new File(folder_path , this.getResources().getString(R.string.folder_app));
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        mediaStorageDir = new File(mediaStorageDir.getPath() + File.separator + this.getResources().getString(R.string.sufijo_factura)+ timeStamp + ".jpg");
        debug.addResultado("PicFolder:"+mediaStorageDir.getAbsolutePath());
        debug.debugConsole(TAG+":folder media",mediaStorageDir.getAbsolutePath());

        file = Uri.fromFile(mediaStorageDir);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
        startActivityForResult(intent, 100);
    }


    /**
     * Metodo que realiza la extracción del texto de un bitmap
     */
    public String procesarImagen(View view) throws Exception {
        controladorProcesoFactura("NEXT");

        // Nueva async task
        new ProcessImage().execute(" ");
        findViewById(R.id.btnAction).setVisibility(View.INVISIBLE);
        return " ";
    }



    /**
     *
     * @param estado
     */
    void controladorProcesoFactura(String estado){
        // Cunado viene vacio hace next
        if(estado.equalsIgnoreCase("NEXT")){
            if(ACTION.equalsIgnoreCase("TOMAR_FOTO")){
                ACTION = "PROCESAR_FOTO";
                textView.setText("");
                btnAction.setText("Procesar Factura");

            }else if(ACTION.equalsIgnoreCase("PROCESAR_FOTO")){
                ACTION = "EXTRACT_TEXTO";


            }else if(ACTION.equalsIgnoreCase("EXTRACT_TEXTO")){
                ACTION = "TOMAR_FOTO";

                btnAction.setText("Procesar Nueva Factura");

            }
        }else if(estado.equalsIgnoreCase("")){
            // not implemented
        }
    }


    /**
     * Metodo que se ejecuta en la interfaz. Cambia el estado de
     * @param view
     */
    public void action(View view){
        Log.d("action",ACTION);
        if(ACTION.equalsIgnoreCase("TOMAR_FOTO")){
            debug.clearResultados();
            tomarFoto(view);
        }else if(ACTION.equalsIgnoreCase("PROCESAR_FOTO")){
            try{
                findViewById(R.id.btnAction).setVisibility(View.INVISIBLE);
                procesarImagen(view);
                debug.showResultado();
                findViewById(R.id.btnAction).setVisibility(View.VISIBLE);
            }
            catch(Exception e){
                Log.d("Error procesando",e.getMessage());
            }
        }
    }



    // Async Task Class
    class ProcessImage extends AsyncTask<String, String, String> {
        String extractedText;


        @Override
        protected String doInBackground(String... param) {
            TessBaseAPI tessBaseApi = new TessBaseAPI();
            tessBaseApi.init(folder_path.getPath()+"/YoSumo/", "eng");
            tessBaseApi.setImage(factura);

            String extractedText = tessBaseApi.getUTF8Text();
            tessBaseApi.end();
            Log.d("processing"," de");
            return extractedText;
        }

        @Override
        protected void onProgressUpdate(String... params) {
            Log.d("processing"," 1");
        }

        @Override
        protected void onPostExecute(String params) {
            textView.setText(extractedText);
            debug.addResultado("tess-extract:"+extractedText);
            controladorProcesoFactura("NEXT");
        }
    }

}