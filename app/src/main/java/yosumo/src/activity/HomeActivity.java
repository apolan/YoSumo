package yosumo.src.activity;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;

import yosumo.src.R;
import yosumo.src.debug.Debugger;

/**
 * Created by a-pol_000 on 9/7/2016.
 * Clase encargada de manejar las funcionalidades del usuario
 * MOD 20160907 - AFP - Inicialización actividad
 * MOD 20160910 - AFP - Adicion borrar facturas-se crean muchos archivos en cada test
 */
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    /**
     * Método que llama a la actividad ImgProcessingActivity. Procesamiento de imagenes
     * @param v sds
     */
    public void goRegistrarFactura(View v){
        Intent intent = new Intent(this, ImgProcessingActivity.class);
        startActivity(intent);
    }

    /**
     * Método que llama a la actividad ImgProcessingActivity. Procesamiento de imagenes
     * @param v sds
     */
    public void borrarFacturas(View v){
        String path_base= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath();
        String directory_app=this.getResources().getString(R.string.folder_app);
        deleteRecursive(new File(path_base+directory_app));
    }

    /**
     *
     * @param fileOrDirectory
     */
    public static void deleteRecursive(File fileOrDirectory)    {
        if (fileOrDirectory.isDirectory()){
            for (File child : fileOrDirectory.listFiles()){
                if(!child.isDirectory()){
                    deleteRecursive(child);
                }
            }
        }else{
            fileOrDirectory.delete();
            Debugger.getInstance().debugConsole("Deletefacturas",fileOrDirectory.getName());
        }
    }

}
