package yosumo.src.logic;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by a-pol_000 on 9/7/2016.
 */
public class Impuesto {

    public String tipo = "";
    public String valor = "";

    public Impuesto(){

    }


    // AFP 20160918 - I
    public void setTipoImpuesto(String tipo){
        Log.d("setTipoImpuesto: ",tipo);
        Pattern p = Pattern.compile("\\d{1,2}%");
        Matcher matcher = p.matcher(tipo);
        if(matcher.find()){
            this.valor =  matcher.group().replace("%","");
            if(this.valor.equalsIgnoreCase("16")){
                this.tipo="IVA";
            }

        }else {
            Log.d("setTipoImpuesto. No :",this.tipo);
        }
        Log.d("setTipoImpuesto: ",this.valor + " - "+ this.tipo);
    }

    public void setValorImpuesto(String valor){

    }

    // AFP 20160918 - F
}