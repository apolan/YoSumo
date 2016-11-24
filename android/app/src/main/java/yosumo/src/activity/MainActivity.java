package yosumo.src.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import yosumo.src.R;
import yosumo.src.db.ManagerDB;
import yosumo.src.commons.Debugger;
import yosumo.src.logic.Usuario;


import com.facebook.FacebookSdk;

import com.facebook.appevents.AppEventsLogger;

/**
 * Created by a-pol_000 on 9/7/2016.
 * Clase main de la aplicación
 * MOD 20160907 - AFP - Adición principales funcionalidades
 * MOD 20160909 - AFP - Adición lógica de manejo de errores problemas
 * Adición openCV_libs en el proyecto - FALTA IMPLEMENTAR TEXT_TRACKING
 * Init Lógica base de datos
 * MOD 20160910 - AFP - Adición módulo tess
 * Checker de archivos y carpetas
 * MOD 20160924 - AFP - Adición revision base de datos
 * Check user adicion de usuario
 * MOD 20161022 - AFP - Arreglo de la conexion con el modulo img processing
 * MOD 20161029 - AFP - Adicion  tab debuug
 * MOD 20161029 - DRM - facebook login
 */


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Usuario usario;
    private Debugger debugger;
    private ManagerDB db;

    /**
     * Variable que hace check de login de un usuario
     */
    boolean checkLogin = false;

    /**
     * Variables del layout
     */
    TextView tv_Password;
    TextView tv_user;


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
        List<String> resultados = new ArrayList<String>();


        try {
            resultados.add(launchDebbuger());
            resultados.add(checkPermissions());
            resultados.add(checkFilesAndFolder());
            resultados.add(checkdb());
            // AFP -  20161029 -  I | TASK: facebook login check
            resultados.add(checkFacebook());
            // AFP -  20161029 -  F

        } catch (Exception e) {
            Log.d("Error init ", e.getMessage());
        }
        debugger.debugConsole(TAG, resultados);

    }


    /**
     * Método que inicia el launcher del debugger
     */
    public String launchDebbuger() {
        String resultado;
        try {
            debugger = new Debugger(this.getApplicationContext());
            debugger.debugConsole("Main", "Inicio Debug");
            resultado = this.getResources().getString(R.string.OK_CODE_DEBUG_100);
        } catch (Exception a) {
            resultado = this.getResources().getString(R.string.ERROR_CODE_DEBUG_100);
        }
        return resultado;
    }

    /**
     * Metodo que hace un check de las carpetas y archivos necsarios
     *
     * @return
     * @post Se hace check de la carpeta tessdata
     */
    public String checkFilesAndFolder() {
        String resultado = "";
        // Path donde se guardaran las facturas (fotos)
        String path_base = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath();
        String directory_app = this.getResources().getString(R.string.folder_app);
        String directory_tessdata = this.getResources().getString(R.string.folder_tessdata);
        String file_tessdata = this.getResources().getString(R.string.file_tessdata);

        resultado += " - " + checkFolder(path_base + directory_app, "folder");
        resultado += " - " + checkFolder(path_base + directory_app + directory_tessdata, "folder");

        copyAsset(file_tessdata, path_base + directory_app + directory_tessdata + "/");

        return resultado;
    }

    /**
     * Metodo que revisa un archivo o folder determinado a partir de su path. Lo crea
     * en caso que no exista
     *
     * @param path String del directorio que a revisar
     * @return resultado de la operación
     */
    String checkFolder(String path, String xType) {
        String resultado = "";
        File dir = new File(path);
        if (xType.equalsIgnoreCase("folder")) {
            if (!dir.exists()) {
                resultado = "Directorio se ha creado";
                if (!dir.mkdirs()) {
                    resultado = "Directorio no creado";
                    Log.e(TAG, "ERROR: Creation of directory " + path + " failed, check does Android Manifest have permission to write to external storage.");
                }
            } else {
                Log.i(TAG, "Created directory " + path);
                resultado = "Directorio ya creado";
                ;
            }
        }
        debugger.debugConsole(TAG, resultado);

        return resultado + " " + path;
    }


    /**
     * Metodo que verifica al inicio de la aplicacion los permisos del sistema.
     */
    public String checkPermissions() {

        String resultado;
        try {
            final String[] permissions = new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.SEND_SMS
                    , Manifest.permission.READ_SMS
                    , Manifest.permission.CAMERA
                    , Manifest.permission.ACCESS_FINE_LOCATION
                    , Manifest.permission.ACCESS_COARSE_LOCATION
            };

            List<String> permissionsTo = new ArrayList();

            for (String permision : permissions) {
                if (ActivityCompat.checkSelfPermission(this, permision) != PackageManager.PERMISSION_GRANTED) {
                    permissionsTo.add(permision);
                }
            }

            ActivityCompat.requestPermissions(this, permissionsTo.toArray(new String[0]), 0);
            resultado = this.getResources().getString(R.string.OK_CODE_PERMISSON_100);
        } catch (Exception e) {
            resultado = this.getResources().getString(R.string.ERROR_CODE_LIB_100);
        }
        return resultado;
    }


    /**
     * Metodo qu realiza la validacion
     *
     * @return
     */
    public String checkdb() {
        String resultado;
        try {
            db = new ManagerDB(getBaseContext());
            resultado = this.getResources().getString(R.string.OK_CODE_DB_100);
        } catch (Exception e) {
            resultado = this.getResources().getString(R.string.ERROR_DB_100) + e.getMessage();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(resultado)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            findViewById(R.id.editTextPassword).setEnabled(false);
                            findViewById(R.id.editTextUsuario).setEnabled(false);
                            findViewById(R.id.btnIngresar).setEnabled(false);
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        return resultado;
    }


    // DMR -  20161101 -  I | TASK: facebook login

    /**
     * Metodo qu realiza la validacion de facebook
     *
     * @return
     */
    public String checkFacebook() {
        String resultado = "";
        try {
            FacebookSdk.sdkInitialize(getApplicationContext());
            AppEventsLogger.activateApp(this);
            //initFacebookLogin();
            resultado = this.getResources().getString(R.string.OK_CODE_FB_100);
        } catch (Exception e) {
            Log.d("--- Error fb", " ");
        }
        return resultado;
    }
    // DMR -  20161101 -  F


    /**
     * @param fileName
     * @param pathOut
     */
    public void copyAsset(String fileName, String pathOut) {
        try {
            File file = new File(pathOut + fileName);
            if (file.exists()) {
                debugger.debugConsole(TAG + " ", ":: Existe tessData " + pathOut + fileName);
            } else {
                debugger.debugConsole(TAG + " ", ":: No Existe tessData copiando a" + pathOut + fileName);

                InputStream myInput = this.getAssets().open(fileName);
                String outFileName = pathOut + fileName;
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
                debugger.debugConsole(TAG + " ", ":: Copiado tessData");
            }
        } catch (IOException e) {
            e.printStackTrace();
            debugger.debugConsole(TAG + " ", ":: Error tessData");
        }
    }


    /**
     * Método que llama a la actividad home, donde están todas las funcionalidades
     *
     * @param v sds
     */
    public void goUserHome(View v) {
        tv_user = (TextView) findViewById(R.id.editTextUsuario);
        tv_Password = (TextView) findViewById(R.id.editTextPassword);

        if (!checkLogin) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("nombre", "none");
            intent.putExtra("id", "2");
            startActivity(intent);
        } else {
            if (tv_user.getText().length() == 0 || tv_Password.length() == 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Por favor ingresa tus datos. Si eres nuevo registrate.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                Usuario usuario = db.getUserByUser(tv_user.getText().toString());

                if (usuario != null) {
                    debugger.debugConsole(TAG, usuario.toString());
                    if (tv_user.getText().toString().equalsIgnoreCase(usuario.getUsuario()) && tv_Password.getText().toString().equalsIgnoreCase(usuario.getPassword())) {
                        Intent intent = new Intent(this, HomeActivity.class);
                        intent.putExtra("usuario", usuario.getUsuario());
                        startActivity(intent);
                        return;
                    }
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Información incorrecta por favor registrate")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }

    /**
     * Método que llama a la actividad home, para registrar los datos del usuario
     *
     * @param v sds
     */
    public void goUserNew(View v) {
        String nombre = "";
        try {
            if (!nombre.isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("No es posible registrar más de un usuario.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                Intent intent = new Intent(this, UserActivity.class);
                startActivity(intent);
            }

        } catch (Exception e) {
            Intent intent = new Intent(this, UserActivity.class);
            startActivity(intent);
        }
    }

    // DMR -  20161101 -  I | TASK: facebook login
    /**
     *
     * @param v
     */
    public void goFacebook(View v) {
        Intent intent = new Intent(this, FacebookLoginActivity.class);
        startActivity(intent);
    }
    // DMR -  20161101 -  F

}
