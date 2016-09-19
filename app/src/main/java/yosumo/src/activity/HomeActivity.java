package yosumo.src.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.File;
import java.text.NumberFormat;

import yosumo.src.R;
import yosumo.src.db.ConstructorFacturas;
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
    ConstructorFacturas constructorFacturas;
    int sizeCounter;
    boolean IVA = true;
    boolean ICO = true;

    private static char[] c = new char[]{'k', 'm', 'b', 't'};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.Bar_Black));

        counter = (TextView) findViewById(R.id.txtCounter);
        sizeCounter = (int)counter.getTextSize();
        constructorFacturas = new ConstructorFacturas(getApplicationContext());

        updateCounter();
    }

    public void updateCounter(){
        if (IVA && ICO){
            updateCounterImpuestos(constructorFacturas.obtenerTotalImpuestos("ALL"));
        } else if (IVA && !ICO){
            updateCounterImpuestos(constructorFacturas.obtenerTotalImpuestos("IVA"));
        } else if (!IVA && ICO){
            updateCounterImpuestos(constructorFacturas.obtenerTotalImpuestos("ICO"));
        } else if (!IVA && !ICO){
            updateCounterImpuestos(constructorFacturas.obtenerTotalImpuestos("NONE"));
        }
    }


    //  AFP - 20160918 - I : Animacion counter
    public void updateCounterImpuestos ( double max) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, (int)max);
        valueAnimator.setDuration(2000);
        if(max > 1000){
            counter.setTextSize((int)(counter.getTextSize()*0.5));
        }else if(max > 10000){
            counter.setTextSize((int)(counter.getTextSize()*0.5)); // depende
        }
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            NumberFormat format = NumberFormat.getCurrencyInstance();

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                //counter.setText(format.format(valueAnimator.getAnimatedValue()).toString());
                counter.setText(coolFormat((int)valueAnimator.getAnimatedValue(), (int) 0));

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

    // AFP - 20160919 - I
    /**
     * Recursive implementation, invokes itself for each factor of a thousand, increasing the class on each invokation.
     * @param n the number to format
     * @param iteration in fact this is the class from the array c
     * @return a String representing the number n formatted in a cool looking way.
     */
    private static String coolFormat(double n, int iteration) {
        double d = ((long) n / 100) / 10.0;
        boolean isRound = (d * 10) %10 == 0;//true if the decimal part is equal to 0 (then it's trimmed anyway)
        return (d < 1000? //this determines the class, i.e. 'k', 'm' etc
                ((d > 99.9 || isRound || (!isRound && d > 9.99)? //this decides whether to trim the decimals
                        (int) d * 10 / 10 : d + "" // (int) d * 10 / 10 drops the decimal
                ) + "" + c[iteration])
                : coolFormat(d, iteration+1));

    }
    /**
     * Metodo que llama el checkBoc ICO
     * @param view
     */
    public void setICO(View view) {
        counter.setTextSize(sizeCounter);
        Log.d("Click ICO. OUT", ICO +"");
        if(ICO){
            ICO = false;
        } else {
            ICO = true;
        }
        updateCounter();
        Log.d("Click ICO. OUT", ICO +"");
    }

    /**
     * Metodo que llama el checkBoc IVA
     * @param view
     */
    public void setIVA(View view) {
        counter.setTextSize(sizeCounter);
        Log.d("Click IVA. IN", IVA +"");
        if(IVA){
            IVA = false;
        } else {
            IVA = true;
        }
        updateCounter();
        Log.d("Click IVA. OUT", IVA +"");
    }
    // AFP - 20160919 - F
}
