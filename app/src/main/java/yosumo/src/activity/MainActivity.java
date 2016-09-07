package yosumo.src.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.security.Permission;

import yosumo.src.R;
import yosumo.src.debug.Debugger;

/**
 * Created by a-pol_000 on 9/7/2016.
 * Clase main de la aplicación
 * MOD 20160907 - AFP - Adición principales funcionalidades
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);


        launchDebbuger();
        checkPermissions();


    }

    /**
     * Metodo que verifica al inicio de la aplicacion los permisos del sistema.
     */
    public void checkPermissions(){
        //Solicitud de permisos al usuario
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

    }


    /**
     * Método que inicia el launcher del debugger
     */
    public void launchDebbuger(){
        Debugger debug = new Debugger(this.getApplicationContext());
        debug.debugConsole("Main","Inicio Debug");
    }


    /**
     * Método que llama a la actividad home, donde están todas las funcionalidades
     * @param v sds
     */
    public void goUserHome(View v){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
