package yosumo.src.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import yosumo.src.R;

/**
 * Created by a-pol_000 on 9/7/2016.
 * Clase encargada de manejar las funcionalidades del usuario
 * MOD 20160907 - AFP - Inicialización actividad
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

}
