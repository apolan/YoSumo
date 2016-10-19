package yosumo.src.logic;
import android.graphics.Bitmap;

import java.util.Date;


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

    public Factura (){
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
        this.fechaCompra = ManagerFormat.formatDate(fechaCompra);
        this.fechaCaptura = ManagerFormat.formatDate(fechaCaptura);
        this.id = id;
    }

    public Factura( Impuesto impuesto, Comercio comercio, String usuario, double valor_total, String fechaCaptura, String fechaCompra, String path){
        this.impuesto = impuesto;
        this.comercio = comercio;
        this.usuario = usuario;
        this.path = path;
        this.valor_total = valor_total;
        this.fechaCompra = ManagerFormat.formatDate(fechaCompra);
        this.fechaCaptura = ManagerFormat.formatDate(fechaCaptura);
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
        return "Factura. Valor: "+ ManagerFormat.formatMoneyK(this.valor_total ,0) + "  Nit: " + ManagerFormat.formatNIT( this.comercio.nit) + "  Fecha: " + ManagerFormat.formatDate(fechaCompra);
    }
    // AFP - 20160919 - F
}
