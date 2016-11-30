package yosumo.src.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.googlecode.tesseract.android.TessBaseAPI;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import yosumo.src.R;
import yosumo.src.commons.Debugger;
import yosumo.src.logic.FacturaVirtual;
import yosumo.src.logic.Impuesto;

/**
 * Created by a-pol_000 on 9/7/2016.
 * QR1. Procesamiento de facturas
 * Clase encargada de manejar el procesamiento de facturas de la aplicaión
 * MOD 20160907 - AFP - Adición principales funcionalidades
 * MOD 20160909 - AFP - Implementación de persistencia de la facturaVirtual.
 * MOD 20160909 - AFP - Implementación de tess data reconocimiento de facturaVirtual.
 * MOD 20160911 - AFP - Limpieza del codigo.
 * MOD 20160914 - AFP - Adicion AsyncTASK
 * Convert bitmap en Blanco y negro
 * Reformulacion de problema. Lectura de facturaVirtual por partes
 * 1. Capturar cabecera
 * 2. Capturar impuesto
 * MOD 20160918 - AFP - Implementación expresiones regular
 * Adicion modo load from bmp: para probar mas rapido
 */
public class ImgProcessingActivity extends AppCompatActivity {

    private static final String TAG = ImgProcessingActivity.class.getSimpleName();
    private File folder_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

    private Uri file;

    private TessBaseAPI tessBaseApi;
    private String ACTION = "none";
    private Debugger debug = null;
    private Bitmap bmpFacturaHead = null;
    private Bitmap bmpFacturaBody = null;

    private FacturaVirtual factura = null;
    // Componentes de interfaz
    private Button btnAction = null;
    private ImageView imageView = null;
    private TextView textView = null;
    private double rateProportion = 0.4;

    // Elementos de animacion
    private ImageView animationDraw = null;
    private Bitmap bitmapAnimation = null;
    private Canvas canvas = null;
    private String status_process = null;
    String extractedTextHead = "";
    String extractedTextBody = "";
    private String modoPick = "bmp-none";
    //  ConstructorFacturas constructorFacturas;

    TextView tv_NIT;
    TextView tv_VALOR;
    TextView tv_IMPUESTO;
    TextView tv_TAG;
    TextView tv_PORCENTAJE;
    TextView tv_NOMBRE;

    //
    String pathHead = "";
    String pathBody= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_imgprocessing);

        btnAction = (Button) findViewById(R.id.btnAction);
        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.bienvenido);

        debug = new Debugger(this.getBaseContext());
        debug.debugConsole(TAG, "create activity");
        //debug.debugConsole(TAG + ":", folder_path.getAbsolutePath());
        btnAction.setText("Empezar");

        //debug.addResultado(initModuleAnimation());
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        pathHead = savedInstanceState.getString("head");
        pathBody = savedInstanceState.getString("body");
        status_process = savedInstanceState.getString("status_process");
        ACTION = savedInstanceState.getString("action");


        Log.d("Restore head", pathHead);
        Log.d("Restore body", pathBody);
        Log.d("Restore status", status_process);
        Log.d("Restore action", ACTION);

        if(ACTION.equalsIgnoreCase("TOMAR_FOTO:body")){
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmpFacturaHead = BitmapFactory.decodeFile(Uri.fromFile(new File(pathHead)).getPath(), bmOptions);
            bmpFacturaHead = Bitmap.createScaledBitmap(bmpFacturaHead,
                    (int) (bmpFacturaHead.getWidth() * rateProportion),
                    (int) (bmpFacturaHead.getHeight() * rateProportion),
                    true);

            bmpFacturaBody = BitmapFactory.decodeFile(Uri.fromFile(new File(pathHead)).getPath(), bmOptions);
            bmpFacturaBody = Bitmap.createScaledBitmap(bmpFacturaBody,
                    (int) (bmpFacturaBody.getWidth() * rateProportion),
                    (int) (bmpFacturaBody.getHeight() * rateProportion),
                    true);
        }



        Log.d("Restore path", pathHead);
        Log.d("Restore path", pathBody);

        Log.d("Restore instance", " ");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString("head", pathHead);
        savedInstanceState.putString("body", pathBody);
        savedInstanceState.putString("status_process", status_process);
        savedInstanceState.putString("action", ACTION);
        Log.d("Save instance", " ");

    }

    @Override
    public void onResume() {
        debug.debugConsole(TAG, "Resume activity");
        super.onResume();  // Always call the superclass method first
    }


    @Override
    public void onPause() {
        debug.debugConsole(TAG, "Pause activivty");
        //  debug.debugConsole("onActivityResult", " ACTION:" + ACTION + " URI" + file.getPath() );
        super.onPause();  // Always call the superclass method first
    }

    @Override
    public void onDestroy() {
        debug.debugConsole(TAG, "Destroy activivty");
        super.onDestroy();
    }


    public String initModuleEditFactura() {
        setContentView(R.layout.activity_editfactura);
        tv_NOMBRE = (TextView) findViewById(R.id.edit_nombre);
        tv_NIT = (TextView) findViewById(R.id.edit_NIT);
        tv_VALOR = (TextView) findViewById(R.id.edit_Compra);
        tv_IMPUESTO = (TextView) findViewById(R.id.edit_Impuesto);
        tv_TAG = (TextView) findViewById(R.id.edit_Tag);
        tv_PORCENTAJE = (TextView) findViewById(R.id.edit_Porcentaje);

        try {
            tv_NOMBRE.setText("");
            tv_NIT.setText(factura.NIT);
            tv_VALOR.setText(factura.valor);
            tv_IMPUESTO.setText(factura.listaImpuestos.get(0).tipo);
            tv_PORCENTAJE.setText(factura.listaImpuestos.get(0).valorVirtual + "");
            tv_TAG.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return "";
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        debug.debugConsole("onActivityResult", " ACTION:" + ACTION + " " + requestCode + " " + resultCode);

        if (ACTION.contains("TOMAR_FOTO")) {
            if (requestCode == 100) {
                if (resultCode == RESULT_OK) {
                    //Se reduce el tamano de la imagen porque no es posible hacerle render
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    if (ACTION.contains("head")) {
                        file = Uri.fromFile(new File(pathHead));
                        bmpFacturaHead = BitmapFactory.decodeFile(file.getPath(), bmOptions);
                        bmpFacturaHead = Bitmap.createScaledBitmap(bmpFacturaHead,
                                (int) (bmpFacturaHead.getWidth() * rateProportion),
                                (int) (bmpFacturaHead.getHeight() * rateProportion),
                                true);

                        debug.addResultado("SizePicHead:" + bmpFacturaHead.getByteCount());
                        imageView.setImageBitmap(bmpFacturaHead);
                    } else if (ACTION.contains("body")) {
                        file = Uri.fromFile(new File(pathBody));
                        bmpFacturaBody = BitmapFactory.decodeFile(file.getPath(), bmOptions);
                        bmpFacturaBody = Bitmap.createScaledBitmap(bmpFacturaBody,
                                (int) (bmpFacturaBody.getWidth() * rateProportion),
                                (int) (bmpFacturaBody.getHeight() * rateProportion),
                                true);

                        debug.addResultado("SizePicBody:" + bmpFacturaBody.getByteCount());
                        imageView.setImageBitmap(bmpFacturaBody);
                    }

                    controladorProcesoFactura("NEXT");
                }
            }
        }
    }

    /**
     * Metodo que realiza la extracción del texto de un bitmap
     */
    public String procesarImagen(View view) throws Exception {
        controladorProcesoFactura("NEXT");
        btnAction.setVisibility(View.INVISIBLE);

        // Nueva async task
        AsyncTask task = new ProcessImage().execute();

        return " ";
    }


    /**
     * Metodo que registra la facturaVirtual
     */
    public void tomarFoto(String fotoParte) {
        Log.d("M tomarFoto: ", ACTION);
        File mediaStorageDir = new File(folder_path, this.getResources().getString(R.string.folder_app));

        if (modoPick == "bmp") {
            File head = new File(mediaStorageDir.getPath() + "/fct20160918_182041.jpg");
            File body = new File(mediaStorageDir.getPath() + "/fct20160918_182134.jpg");
            factura = new FacturaVirtual(" ", " ");
            mediaStorageDir = new File(head.getAbsolutePath());
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            file = Uri.fromFile(mediaStorageDir);
            bmpFacturaHead = BitmapFactory.decodeFile(file.getPath(), bmOptions);
            bmpFacturaHead = Bitmap.createScaledBitmap(
                    bmpFacturaHead,
                    (int) (bmpFacturaHead.getWidth() * rateProportion),
                    (int) (bmpFacturaHead.getHeight() * rateProportion),
                    true);

            mediaStorageDir = new File(body.getAbsolutePath());
            bmOptions = new BitmapFactory.Options();
            file = Uri.fromFile(mediaStorageDir);
            bmpFacturaBody = BitmapFactory.decodeFile(file.getPath(), bmOptions);
            bmpFacturaBody = Bitmap.createScaledBitmap(
                    bmpFacturaBody,
                    (int) (bmpFacturaBody.getWidth() * rateProportion),
                    (int) (bmpFacturaBody.getHeight() * rateProportion),
                    true);

            ImageView myImage = (ImageView) findViewById(R.id.imageView);
            myImage.setImageBitmap(bmpFacturaHead);

            controladorProcesoFactura("PROCESAR_FOTO");

        } else { //  MODO NORMAL EN EL QUE SE TOMA LAS DOS FOTOS
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            if (fotoParte.equalsIgnoreCase("head")) {
                // factura = new FacturaVirtual(mediaStorageDir.getPath(), this.getResources().getString(R.string.sufijo_factura) + timeStamp + ".jpg");
                factura = new FacturaVirtual(mediaStorageDir.getPath(), "");
                pathHead = mediaStorageDir.getPath() + "/" + this.getResources().getString(R.string.sufijo_factura_encabezado) + timeStamp + ".jpg";

                debug.addResultado("Head PicFolder:" + mediaStorageDir.getAbsolutePath());
                status_process = "head";
//                mediaStorageDir = new File(factura.getPath_head());
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(pathHead)));

            } else if (fotoParte.equalsIgnoreCase("body")) {
                factura = new FacturaVirtual(mediaStorageDir.getPath(), "");
                pathBody = mediaStorageDir.getPath() + "/" + this.getResources().getString(R.string.sufijo_factura_impuesto) + timeStamp + ".jpg";
                factura.setPath_body(pathBody);
                debug.addResultado("Body PicFolder:" + mediaStorageDir.getAbsolutePath());
       //         mediaStorageDir = new File(factura.getPath_body());
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(pathBody)));
            }
            // debug.debugConsole(TAG, "File PATH: " + mediaStorageDir.getPath());
            debug.showResultado();
            debug.debugConsole(TAG + ":Folder_media", mediaStorageDir.getAbsolutePath());
            this.startActivityForResult(intent, 100);
        }
    }

    /**
     * Metodo que sirve de controlador de cada paso de usuario al procesar una información
     *
     * @param estado
     */
    void controladorProcesoFactura(String estado) {
        // Cunado viene vacio hace next
        Log.d(TAG, "Estado: " + estado + " Action IN: " + ACTION);
        if (estado.equalsIgnoreCase("NEXT")) {
            if (ACTION.contains("INIT")) {
                ACTION = "TOMAR_FOTO:head";
                textView.setText("");
                btnAction.setText("Captura encabezado");

            } else if (ACTION.contains("TOMAR_FOTO")) {
                if (ACTION.contains("head")) {
                    ACTION = "TOMAR_FOTO:body";
                    btnAction.setText("Captura Impuesto");

                } else if (ACTION.contains("body")) {
                    ACTION = "PROCESAR_FOTO";
                    textView.setText("");
                    btnAction.setText("Procesar Factura");
                }
            } else if (ACTION.contains("PROCESAR_FOTO")) {
                ACTION = "EXTRACT_TEXTO";

            } else if (ACTION.contains("EXTRACT_TEXTO")) {
                //ACTION = "INIT";
                btnAction.setText("Edit");
                ACTION = "EDIT_CONFIRM";
                formFactura();
            } else if (ACTION.contains("EDIT_CONFIRM")) {
                ACTION = "TOMAR_FOTO:head";
                /*textView.setText("");
                btnAction.setText("Captura encabezado");*/
                initModuleEditFactura();
                ACTION = "CONFIRM";
            } else if (ACTION.contains("CONFIRM")) {
                ACTION = "TOMAR_FOTO:head";
                textView.setText("");
                btnAction.setText("Captura encabezado");
            }
        } else if (estado == "PROCESAR_FOTO") {
            ACTION = "TOMAR_FOTO:body";
            controladorProcesoFactura("NEXT");
        }
        Log.d(TAG, " Action OUT: " + ACTION);
    }

    /**
     * @return
     */
    public String formFactura() {
        processFactura("head");
        processFactura("body");

        imageView = null;
        textView.setText("FacturaVirtual: </br>" + "Nit: " + factura.NIT +
                "</br> Valor:" + factura.valor +
                "</br>" + " Impuesto: " +
                "</br>" + factura.getlistaImpuestosToString());

        return "";
    }

    /**
     * Es el metodo que le pone la logica a la facturaVirtual
     *
     * @param part
     */
    public void processFactura(String part) {
        Log.d(TAG, "Init search part:" + part);

        if (part.equalsIgnoreCase("head")) {
            String[] lines = extractedTextHead.split(System.getProperty("line.separator"));
            String nitPattern = "(NIT|nit|N.I.T|n.i.t|Nit)";
            String nitNumPattern = "(\\d{3}(\\s)*?(.|-|,|—|)(\\s)*\\d{3}(\\s)*?(.|-|,|)(\\s)*\\d{3}(\\s)*?(-|--|- -|—|»)(\\s)*\\d{1})";

            Pattern p1 = Pattern.compile(nitPattern);
            Pattern p2 = Pattern.compile(nitNumPattern);
            Matcher matcher;

            for (String line : lines) {
                Log.d("PRINT LINES HEAD", line);
                matcher = p2.matcher(line);
                if (matcher.find()) {
                    factura.setNITFactura(matcher.group(1));
                    break;
                }
            }
            Log.d(TAG, "End search part:" + "FACTURA:" + factura.NIT);

        } else if (part.equalsIgnoreCase("body")) {
            String[] lines = extractedTextBody.split(System.getProperty("line.separator"));
            String impuestoPattern = "(%|IVA|I.V.A|iva|i.v.a)";
            String pagoPattern = "(TOTAL|VENTA|PAGAR)";

            Impuesto impuesto = null;
            //impuesto = new Impuesto();

            Pattern p1 = Pattern.compile(impuestoPattern);
            Pattern p2 = Pattern.compile(pagoPattern);
            Matcher matcher1;
            Matcher matcher2;

            for (String line : lines) {
                Log.d("PRINT LINES BODY", line);

                matcher1 = p1.matcher(line);
                if (matcher1.find()) {
                    impuesto.setTipoImpuesto(line);
                }

                matcher2 = p2.matcher(line);
                if (matcher2.find()) {
                    factura.setValorFactura(line);
                }
            }

            factura.addImpuesto(impuesto);
            Log.d(TAG, "End search part:");
        }
    }


    /**
     * Metodo que convierte una foto a blanco y negro. para limipiar el ruido de la foto
     *
     * @param src
     * @return Bitman Que representa la facturaVirtual en blanco y negro
     */
    private Bitmap convertBW(Bitmap src) {

        Bitmap dest = Bitmap.createBitmap(src.getWidth(),
                src.getHeight(),
                src.getConfig());

        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                int pixelColor = src.getPixel(x, y);
                int pixelAlpha = Color.alpha(pixelColor);
                int pixelRed = Color.red(pixelColor);
                int pixelGreen = Color.green(pixelColor);
                int pixelBlue = Color.blue(pixelColor);

                int pixelBW = (pixelRed + pixelGreen + pixelBlue) / 3;
                int newPixel = Color.argb(pixelAlpha,
                        pixelBW,
                        pixelBW,
                        pixelBW);

                dest.setPixel(x, y, newPixel);
            }
        }

        return dest;
    }

    /**
     * Metodo que guarde un snap del bitmap que se tiene
     *
     * @param img
     * @param filename
     * @return
     */
    public String saveFacturaSnapshot(Bitmap img, String filename) {
        String resultado = "";
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(folder_path + "/" + this.getResources().getString(R.string.folder_app) + "/" + "snap_" + filename);
            img.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultado;
    }

    /**
     * Metodo que se ejecuta en la interfaz. Cambia el estado de
     *
     * @param view
     */
    public void action(View view) {
        Log.d("action", ACTION);
        if (ACTION.contains("INIT")) {
            controladorProcesoFactura("NEXT");

        } else if (ACTION.contains("TOMAR_FOTO:head")) {
            tomarFoto("head");

        } else if (ACTION.contains("TOMAR_FOTO:body")) {
            tomarFoto("body");

        } else if (ACTION.contains("PROCESAR_FOTO")) {
            try {
                procesarImagen(view);
                debug.showResultado();
            } catch (Exception e) {
                Log.d("Error procesando", e.getMessage());
            }
        } else if (ACTION.contains("EDIT_CONFIRM")) {
            controladorProcesoFactura("NEXT");
            debug.clearResultados();
        } else if (ACTION.contains("none")) {
            ACTION = "INIT";
            action(view);
        } else {
            debug.debugConsole("Not find" + ACTION);
        }


    }

    /**
     * Metodo que crea la factura en la base de datos
     *
     * @param view
     */
    public void goCreate(View view) {
        // Crea la factura en la base de datos

        //constructorFacturas = new ConstructorFacturas(getApplicationContext());

        double nit = Double.parseDouble(tv_NIT.getText() + "");
        float valor = Integer.parseInt(tv_VALOR.getText() + "") * (Float.parseFloat(tv_PORCENTAJE.getText() + "") / 100);
        Log.d("Valor impuemultip", valor + "");

        /*constructorFacturas.addFactura( tv_NOMBRE.getText()+"",
                                        Integer.parseInt(tv_VALOR.getText()+""), // VALOR DE FACTURA
                                        tv_IMPUESTO.getText()+"",                   // TIPO DE IMPUESTO
                                        valor+"",// VALOR DE IMPUESTO ?
                                        factura.path,                               // PATH
                                        nit  // NIT
                                        );
*/
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Factura registrada")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        setContentView(R.layout.activity_imgprocessing);
                        controladorProcesoFactura("NEXT");
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();


        //  constructorFacturas.addFactura("ICO","25000","RUTA", 1234567890);
    }

    /**
     * @param view
     */
    public void goInit(View view) {
        Log.d("go back", "algo");
    }


    public boolean onTouchEvent(MotionEvent event) {
        //canvas.restore();
        /*
        initModuleAnimation();
        canvas.save();
        int x = (int)event.getX();
        int y = (int)event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
        }
        canvas.restore();
        Log.d("Mousex ", x + " Mousey " + y);

        // Line
        Paint paint = new Paint();
        paint.setColor(Color.argb(40,255, 153, 51));
        paint.setStrokeWidth(10);
        int startx = 50;
        int starty = 90;
        int endx = 150;
        int endy = 360;
        canvas.drawRect(0, y, 300, 500, paint );

        */
        return false;
    }

    /**
     * Clase encargada de manejar de forma asincronica el procesamiento de facturas
     */
    // Async Task Class
    class ProcessImage extends AsyncTask<String, Integer, String> {

        boolean inicio;
        Paint paint;

        int increase = 5;
        private ProgressDialog dialog;


        public ProcessImage() {
            dialog = new ProgressDialog(ImgProcessingActivity.this);
        }

        @Override
        protected void onPreExecute() {
            inicio = true;
            this.dialog.setMessage("Inicio de procesamiento");
            this.dialog.show();

        }

        @Override
        protected String doInBackground(String... param) {
            if (inicio) {

                TessBaseAPI tessBaseApi = new TessBaseAPI();
                tessBaseApi.init(folder_path.getPath() + "/YoSumo/", "spa");

                //bmpFacturaHead = convertBW(bmpFacturaHead);

                tessBaseApi.setImage(bmpFacturaHead);
                tessBaseApi.setDebug(true);
                extractedTextHead = tessBaseApi.getUTF8Text();

                bmpFacturaBody = convertBW(bmpFacturaBody);
                tessBaseApi.setImage(bmpFacturaBody);
                extractedTextBody = tessBaseApi.getUTF8Text();

                tessBaseApi.end();
                inicio = false;
            }

            return "";
        }

        @Override
        protected void onProgressUpdate(Integer... params) {

        }

        @Override
        protected void onPostExecute(String params) {
            textView.setText(" Head: " + extractedTextHead + "<br/> Body: " + extractedTextBody);
            debug.addResultado("tess-extract:" + extractedTextHead + extractedTextBody);
            btnAction.setVisibility(View.VISIBLE);
            debug.showResultado();
            controladorProcesoFactura("NEXT");
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

}