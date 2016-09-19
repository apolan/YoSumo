package yosumo.src.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.File;

import yosumo.src.R;
import yosumo.src.debug.Debugger;

/**
 * Created by a-pol_000 on 9/7/2016.
 * Clase encargada de manejar las funcionalidades del usuario
 * MOD 20160907 - AFP - Inicialización actividad
 * MOD 20160910 - AFP - Adicion borrar facturas-se crean muchos archivos en cada test
 * MOD 20160917 - DM - Adición del botón mostrar base de datos
 * MOD 20160918 - AFP - Adicion modificacion del contador de tus impuestos
 *                      Animación del contador
 */
public class HomeActivity extends AppCompatActivity {
    private TextView counter   = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.Bar_Black));

        counter = (TextView) findViewById(R.id.txtCounter);
        updateCounterImpuestos();
    }

    //  AFP - 20160918 - I : Animacion counter
    public void updateCounterImpuestos () {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 90);
        valueAnimator.setDuration(2000);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                counter.setText("$" + valueAnimator.getAnimatedValue().toString());
                }
            });
            valueAnimator.start();
    }
    //  AFP - 20160918 - F

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
     * Método que llama a la actividad MostrarFacturas. Carga unos valores a la base de datos para mostrarlos
     * @param v sds
     */
    public void mostrarUsuarios(View v){
        Intent intent = new Intent(this, MostrarFacturasActivity.class);
        startActivity(intent);
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
            //Debugger.getInstance().debugConsole("Deletefacturas",fileOrDirectory.getName());
        }
    }

}
