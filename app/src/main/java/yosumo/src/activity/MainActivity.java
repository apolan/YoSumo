package yosumo.src.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import yosumo.src.R;
import yosumo.src.db.BaseDatos;
import yosumo.src.db.ConstructorFacturas;
import yosumo.src.debug.Debugger;
import yosumo.src.logic.Usuario;

/**
 * Created by a-pol_000 on 9/7/2016.
 * Clase main de la aplicación
 * MOD 20160907 - AFP - Adición principales funcionalidades
 * MOD 20160909 - AFP - Adición lógica de manejo de errores problemas
 *                      Adición openCV_libs en el proyecto - FALTA IMPLEMENTAR TEXT_TRACKING
 *                      Init Lógica base de datos
 * MOD 20160910 - AFP - Adición módulo tess
 *                      Checker de archivos y carpetas
 * MOD 20160918 - AFP - Adición revision base de datos
 *
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Usuario usario;
    private Debugger debugger;
    private BaseDatos db;
    ConstructorFacturas constructorFacturas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.Bar_Black));

        setContentView(R.layout.activity_main);
        List<String> resultados = new ArrayList<String>();;

        try {
            resultados.add(launchDebbuger());
            //resultados.add(checkPermissions());
            resultados.add(checkFilesAndFolder());
            resultados.add(initdb());

            }catch(Exception e){

            }
        debugger.debugConsole(TAG,resultados);
    }

    /**
     * Metodo que hace un check de las carpetas y archivos necsarios
     * @return
     * @post Se hace check de la carpeta tessdata
     */
    public String checkFilesAndFolder(){
        String resultado = "";
        // Path donde se guardaran las facturas (fotos)
        String path_base= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath();
        String directory_app=this.getResources().getString(R.string.folder_app);
        String directory_tessdata=this.getResources().getString(R.string.folder_tessdata);
        String file_tessdata=this.getResources().getString(R.string.file_tessdata);

        resultado += " - " + check(path_base+directory_app, "folder");
        resultado += " - " + check(path_base+directory_app+directory_tessdata, "folder");

        copyAsset( file_tessdata, path_base+directory_app+directory_tessdata+"/");

        return resultado;
    }

    /**
     * Metodo que revisa un archivo o folder determinado a partir de su path. Lo crea
     * en caso que no exista
     * @param path String del directorio que a revisar
     * @return resultado de la operación
     */
    String check(String path, String xType){
        String resultado="";
        File dir = new File(path);
        if(xType.equalsIgnoreCase("folder")){
            if (!dir.exists()) {
                resultado = "Directorio se ha creado";
                if (!dir.mkdirs()) {
                    resultado = "Directorio no creado";
                    Log.e(TAG, "ERROR: Creation of directory " + path + " failed, check does Android Manifest have permission to write to external storage.");
                }
            } else {
                Log.i(TAG, "Created directory " + path);
                resultado = "Directorio ya creado";;
            }

        }
        debugger.debugConsole(TAG,resultado);

        return resultado+ " "+path;
    }

    /**
     *
     * @param fileName
     * @param pathOut
     */
    public void copyAsset(String fileName, String pathOut){
        try {
            File file = new File(pathOut + fileName);
            if(file.exists()){
                debugger.debugConsole(TAG+" ",":: Existe tessData "+pathOut + fileName);
            }else{
                debugger.debugConsole(TAG+" ",":: No Existe tessData copiando a"+pathOut + fileName);

                InputStream myInput = this.getAssets().open(fileName);
                String outFileName = pathOut+ fileName;
                OutputStream myOutput = new FileOutputStream(outFileName);

                // transfer bytes from the inputfile to the outputfile
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                // Close the streams
                myOutput.flush();
                myOutput.close();
                myInput.close();
                debugger.debugConsole(TAG+" ",":: Copiado tessData");
            }
        } catch (IOException e) {
            e.printStackTrace();
            debugger.debugConsole(TAG+" ",":: Error tessData");
        }
    }



    /**
     * Metodo que verifica al inicio de la aplicacion los permisos del sistema.
     */
    public String checkPermissions(){

        String resultado;
        try{
            final String[] permissions = new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.SEND_SMS
                    , Manifest.permission.READ_SMS
                    , Manifest.permission.CAMERA
            };

            for (String permision : permissions){
                if(ActivityCompat.checkSelfPermission(this,permision) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.shouldShowRequestPermissionRationale(this, permision );
                }
            }
            //todo: modificar para que solo se soliciten los que no se encontraron .. // Solicitud de permisos al usuario
            ActivityCompat.requestPermissions(this, permissions,0 );

            resultado  =  this.getResources().getString(R.string.OK_CODE_PERMISSON_100);
        } catch(Exception e){
            resultado  =  this.getResources().getString(R.string.ERROR_CODE_LIB_100);
        }
        return resultado;
    }

    /**
     * Método que inicia el launcher del debugger
     */
    public String launchDebbuger(){
        String resultado;
        try {
            debugger = new Debugger(this.getApplicationContext());
            debugger .debugConsole("Main", "Inicio Debug");
            resultado = this.getResources().getString(R.string.OK_CODE_DEBUG_100);
      }catch(Exception a){
            resultado = this.getResources().getString(R.string.ERROR_CODE_DEBUG_100);
      }
        return resultado;
    }

    /**
     *
     * @return
     */
    public String initdb(){
        String resultado;
        try{
            // AFP 20160918 - I
            db  = new BaseDatos(getBaseContext());
            constructorFacturas.insertarTresFacturas(db);
            // AFP 20160918 - F

            resultado  =  this.getResources().getString(R.string.OK_CODE_DB_100);
        }catch(Exception e){
            resultado  =  this.getResources().getString(R.string.ERROR_DB_100);
        }
        return resultado;
    }


    /**
     * Método que llama a la actividad home, donde están todas las funcionalidades
     * @param v sds
     */
    public void goUserHome(View v){
        Intent intent = new Intent(this, HomeActivity.class);
        //intent.set
        startActivity(intent);
    }


    /**
     * Método que llama a la actividad home, para registrar los datos del usuario
     * @param v sds
     */
    public void goUserNew(View v){
        Intent intent = new Intent(this, UserActivity.class);
        //intent.set
        startActivity(intent);
    }
}
