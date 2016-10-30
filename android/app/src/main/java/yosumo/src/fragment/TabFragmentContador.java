package yosumo.src.fragment;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;

import yosumo.src.R;
import yosumo.src.animation.Circle;
import yosumo.src.animation.CircleAngleAnimation;
import yosumo.src.db.ManagerDB;
import yosumo.src.logic.ManagerFormat;

/**
 * Created by a-pol_000 on 9/7/2016.
 * Clase encargada de manejar las funcionalidades del usuario
 * MOD 20160907 - AFP - Inicialización actividad
 * MOD 20160910 - AFP - Adicion borrar facturas-se crean muchos archivos en cada test
 * MOD 20160917 - DM - Adición del botón mostrar base de datos
 * MOD 20160925 - AFP - Adicion modificacion del contador de tus impuestos
 *                      Animación del contador
 */
public class TabFragmentContador extends Fragment {

    private TextView counter   = null;
    ManagerDB db = null;
    int sizeCounter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_fragment_contador, container, false);

        counter = (TextView) rootView.findViewById(R.id.txt_contador);
        sizeCounter = (int)(counter.getTextSize()*0.5);

        db = new ManagerDB(rootView.getContext());
        updateCounter();

        Circle circle = (Circle)rootView.findViewById(R.id.circle);
        CircleAngleAnimation animation = new CircleAngleAnimation(circle, 300);
        animation.setDuration(1000);
        circle.startAnimation(animation);

        return rootView;
    }

    /**
     *
     */
    public void updateCounter(){
        updateCounterImpuestos(db.getAllImpuestos());
    }


    /**
     *
     * @param max
     */
    public void updateCounterImpuestos ( double max) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, (int)max);
        int value=0;
        if(max < 10000){
            value = 700;
        }else if(max < 60000){
            value = 1200;
        }else{
            value = 2000;
        }

        valueAnimator.setDuration(value);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            NumberFormat format = NumberFormat.getCurrencyInstance();

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                counter.setText(ManagerFormat.formatMoneyK( (int)(valueAnimator.getAnimatedValue()), (int) 0));

            }
        });
        valueAnimator.start();
    }
}