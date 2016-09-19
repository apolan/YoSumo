package yosumo.src.logic;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by a-pol_000 on 9/7/2016.
 * 20161709 - Modified by DRM - agregados getters y seters
 */
public class Impuesto {

    public double valor;

    public String tipo = "";
    // AFP 20160918 - I
    public String valorVirtual = "";
    // AFP 20160918 - F
    public Impuesto(){

    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    // AFP 20160918 - I
    public void setTipoImpuesto(String tipo){
        Log.d("setTipoImpuesto: ",tipo);
        Pattern p = Pattern.compile("\\d{1,2}%");
        Matcher matcher = p.matcher(tipo);
        if(matcher.find()){
            this.valorVirtual =  matcher.group().replace("%","");
            if(this.valorVirtual.equalsIgnoreCase("16")){
                this.tipo="IVA";
            }

        }else {
            Log.d("setTipoImpuesto. No :",this.tipo);
        }
        Log.d("setTipoImpuesto: ",this.valorVirtual + " - "+ this.tipo);
    }

    public void setValorImpuesto(String valor){

    }

    // AFP 20160918 - F
}
