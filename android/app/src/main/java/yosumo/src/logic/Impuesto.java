package yosumo.src.logic;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by a-pol_000 on 9/7/2016.
 * DRM 20161709     agregados getters y seters
 * AFP 20160924     REORGAIZACION DE CODIGO Y LIMPIEZA.
 *                  Adicion nuevo constructor y atributtos  valor_iva|valor_ico
 */
public class Impuesto {

    public double valor;
    public int factura_id;
    public float porcent_iva;
    public float porcent_ico;
    public double valor_iva;
    public double valor_ico;
    public int id;

    public String tipo = "";

    public String valorVirtual = "";

    /**
     *
     * @param porcent_iva
     * @param porcent_ico
     * @param valor_iva
     * @param valor_ico
     * @param factura_id
     */
    public Impuesto(float porcent_iva, double valor_iva, float porcent_ico,  double valor_ico, int factura_id  ){
        this.porcent_iva = porcent_iva;
        this.porcent_ico = porcent_ico;
        this.valor_iva = valor_iva;
        this.valor_ico= valor_ico;
        this.factura_id = factura_id;
    }


    public Impuesto(int id, float porcent_iva, double valor_iva, float porcent_ico,  double valor_ico, int factura_id  ){
        this.id = id;
        this.porcent_iva = porcent_iva;
        this.porcent_ico = porcent_ico;
        this.valor_iva = valor_iva;
        this.valor_ico= valor_ico;
        this.factura_id = factura_id;
    }
    /**
     *
     * @param tipo
     */
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

    public void calcularIVA (double valor_total){
        valor_iva = valor_total*(porcent_iva/100);
    }


    public void calcularICO (double valor_total){
        valor_ico = valor_total*(porcent_ico/100);
    }

    public void setPorcent_iva(float porcent_iva) {
        this.porcent_iva = porcent_iva;
    }

    public void setPorcent_ico(float porcent_ico) {
        this.porcent_ico = porcent_ico;
    }

    public void setValor_iva(double valor_iva) {
        this.valor_iva = valor_iva;
    }

    public void setValor_ico(double valor_ico) {
        this.valor_ico = valor_ico;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
