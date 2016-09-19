package yosumo.src.logic;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by a-pol_000 on 9/18/2016.
 * Entidad encargada de manejar la lógica de factura
 */
public class FacturaVirtual {

    public Date fechaCaptura;
    public Date fechaCompra;
    public String path;
    public String Nombre;
    public String NIT = "none";
    public String lugar;
    public String valor;

    public Bitmap encabezado;
    public Bitmap cuerpo;

    private List<Impuesto> listaImpuestos;

    public FacturaVirtual(String path, String filename){
        this.path = path+"/"+filename;
        listaImpuestos = new ArrayList<Impuesto> ();
    }

    // AFP - 20160918  - I
    public void addImpuesto(Impuesto impuesto){
        listaImpuestos.add(impuesto);
    }


    public String getlistaImpuestosToString(){
        String resultado =  "";

        for(Impuesto impuesto : listaImpuestos){
            resultado = "valor: " + impuesto.valorVirtual + " tipo: " + impuesto.tipo;
        }

        return resultado;
    }

    /**
     * Metodo que es llamado al momento de limpiar y hacer set del NIT
     * @param nit
     */
     public void setNITFactura(String nit){
        // Se hace el clean de la fatura
         Log.d("se encontro: ",nit);

         this.NIT = nit.replaceAll("(\\.|,|-|»|\\s|—)","");
     }

    public void setValorFactura(String valor){
        // Se hace el clean de v fatura
        Log.d("setValorFactura: ",valor);
        Pattern p = Pattern.compile("(([1-9]\\d{0,2}(,\\d{3})*)|(([1-9]\\d*)?\\d))(\\.\\d{2,3})");
        Matcher matcher = p.matcher(valor);
        if(matcher.find()){
            this.valor = matcher.group();
            Log.d(valor, matcher.toString());
        }else {
            Log.d("setValo.No se encontro:",valor);
        }
        Log.d("setValorFactura: ",this.valor);
    }




    // AFP - 20160918  - F
}
