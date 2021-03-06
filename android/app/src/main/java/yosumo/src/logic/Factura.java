package yosumo.src.logic;
import android.graphics.Bitmap;

import java.util.Date;

import yosumo.src.commons.Dummy;


/**
 * Clase que representa la base de datos y maneja sus operaciones
 * Created by David Ricardo on 14/09/2016.
 * DRM 20160916     COMERCIO_ID es ahora el NIT, integer NK
 *                  agregados getters and setters
 *                  insertado el formato de las fechas
 *                  agegados atributos tipo_impuesto y valor_impuesto
 * AFP 20160923     REORGAIZACION DE CODIGO Y LIMPIEZA.
 *                  Cambio de constructores y de atributos
 */

public class Factura {
    public Bitmap encabezado;
    public Bitmap cuerpo;

    public Date fechaCaptura;
    public Date fechaCompra;
    public String path;

    public Comercio comercio;
    public String numero_consecutivo;
    public String usuario;
    public Impuesto impuesto;
    public double valor_total;
    public int id;
    public String tag;

    public Factura(  Comercio comercio, String usuario, double valor_total, String fechaCaptura, String fechaCompra, String path, int id ){
        this.comercio = comercio;
        this.usuario = usuario;
        this.path = path;
        this.valor_total = valor_total;
        this.fechaCompra = Dummy.formatTimestamp(fechaCompra);
        this.fechaCaptura = Dummy.formatTimestamp(fechaCaptura);
        this.id = id;
    }
    /**
     *
     * @param impuesto
     * @param comercio
     * @param usuario
     * @param valor_total
     * @param fechaCaptura
     * @param fechaCompra
     * @param path
     */
    public Factura( Impuesto impuesto, Comercio comercio, String usuario, double valor_total, String fechaCaptura, String fechaCompra, String path, int id ){
        this.impuesto = impuesto;
        this.comercio = comercio;
        this.usuario = usuario;
        this.path = path;
        this.valor_total = valor_total;
        this.fechaCompra = Dummy.formatDate(fechaCompra);
        this.fechaCaptura = Dummy.formatDate(fechaCaptura);
        this.id = id;
    }

    public Factura( Impuesto impuesto, Comercio comercio, String usuario, double valor_total, String fechaCaptura, String fechaCompra, String path){
        this.impuesto = impuesto;
        this.comercio = comercio;
        this.usuario = usuario;
        this.path = path;
        this.valor_total = valor_total;
        this.fechaCompra = Dummy.formatDate(fechaCompra);
        this.fechaCaptura = Dummy.formatDate(fechaCaptura);
    }

    public Impuesto getImpuesto() {
        return impuesto;
    }

    public Comercio getComercio() {
        return comercio;
    }

   // AFP - 20160919 - I
    /**
     * Metodo llamado por las diferntes listas Este es importatne!!
     * @return
     */
    public String toString(){
        return "Lugar: "+ comercio.getNombre_Label() +" Valor: "+ Dummy.formatMoneyK(this.valor_total ,0)  + "  Fecha: " + Dummy.formatDate(fechaCompra);
    }
    // AFP - 20160919 - F
}
