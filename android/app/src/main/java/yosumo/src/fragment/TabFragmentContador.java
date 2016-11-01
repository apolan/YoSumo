package yosumo.src.fragment;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.NumberFormat;

import yosumo.src.R;
import yosumo.src.animation.Circle;
import yosumo.src.animation.CircleAngleAnimation;
import yosumo.src.db.ManagerDB;
import yosumo.src.commons.ManagerFormat;

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
    private TextView counterImp   = null;
    CheckBox box ;
    ManagerDB db = null;
    int imp_valor;
    String imp_tag;
    int sizeCounter;
    Circle circle;
    CircleAngleAnimation animation;
    double valortotal;
    double valorimp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_fragment_contador, container, false);

        counter = (TextView) rootView.findViewById(R.id.txt_contador);
        counterImp = (TextView) rootView.findViewById(R.id.txt_impuesto);

        Spinner spinner = (Spinner) rootView.findViewById(R.id.impuestos);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(rootView.getContext(), R.array.impuestos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (pos == 0){
                    valortotal = db.getAllImpuestos();
                    valorimp = db.getImpuestosByType("valor_iva");
                    circle.setColor("blue");
                } else if (pos == 1){
                    valortotal = db.getAllImpuestos();
                    valorimp = db.getImpuestosByType("valor_ico");
                    circle.setColor("red");
                }
                //counterImp.setText("$"+ManagerFormat.formatMoneyK( (int)(valorimp), (int) 0));
                updateCounterImpuestos(counterImp,valorimp);
                animation = new CircleAngleAnimation(circle, (int)ManagerFormat.reglaTres(360,valortotal,valorimp));
                animation.setDuration(1000);
                circle.startAnimation(animation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        db = new ManagerDB(rootView.getContext());
        sizeCounter = (int)(counter.getTextSize()*0.5);
        counter.setTextSize(sizeCounter);
        valortotal = db.getAllImpuestos();
        valorimp = db.getImpuestosByType("valor_iva");
        counterImp.setText("$"+ManagerFormat.formatMoneyK( (int)(valorimp), (int) 2));

        updateCounter(valortotal);

        // Animacion del circulo

        circle = (Circle)rootView.findViewById(R.id.circle);
        animation = new CircleAngleAnimation(circle, (int)ManagerFormat.reglaTres(360,valortotal,valorimp));
        animation.setDuration(1000);
        circle.startAnimation(animation);

        return rootView;
    }

    /**
     *
     */
    public void updateCounter(double value){
        updateCounterImpuestos(counter, valortotal);
    }


    /**
     *
     * @param max
     */
    public void updateCounterImpuestos ( TextView counter, double max) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, (int)max);

        int value=0;
        if(max < 10000){
            value = 700;
        }else if(max < 60000){
            value = 1200;
        }else{
            value = 2000;
        }
        final TextView counter1 = counter;
        valueAnimator.setDuration(value);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            NumberFormat format = NumberFormat.getCurrencyInstance();

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                counter1.setText("$"+ManagerFormat.formatMoneyK( (int)(valueAnimator.getAnimatedValue()), (int) 0));
            }
        });
        valueAnimator.start();
    }

}