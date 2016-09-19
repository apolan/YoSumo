package yosumo.src.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import yosumo.src.db.BaseDatos;
import yosumo.src.db.ConstantesBaseDatos;
import yosumo.src.logic.Comercio;
import yosumo.src.logic.Factura;
import yosumo.src.logic.Usuario;

/**
 * Created by David Ricardo on 17/09/2016.
 * Clase de que actua como interactor trayendo los las facturas y lo presenta en el context
 */
public class ConstructorFacturas {


    private Context context;
    public ConstructorFacturas(Context context) {
        this.context = context;
    }


    public ArrayList<Factura> obtenerDatos()
    {
        BaseDatos bd = new BaseDatos(context);
        //insertarTresFacturas(bd);
        return bd.obtenerTodasLasFacturas();
    }


    public void insertarTresFacturas(BaseDatos bd)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_NAME,"Comida1");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_IMPUESTO_TIPO,"IVA");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_IMPUESTO_VALOR,"4100");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_PATH, "data/facturas/factura1.png");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_FECHA_COMPRA,"12:01 AM 17-12-15");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_FECHA_CAPTURA,"12:13 AM 17-12-15");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_NIT,1005212422); //puedo agregar una FK que no exista en comercio?

        bd.insertarFactura(contentValues);

        contentValues= new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_NAME,"Factura_Rumba");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_IMPUESTO_TIPO,"IVA");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_IMPUESTO_VALOR,"1300");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_PATH, "data/facturas/factura3.png");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_FECHA_COMPRA,"6:01 PM 16-12-15");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_FECHA_CAPTURA,"7:13 PM 16-12-15");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_NIT,1020793657);
        bd.insertarFactura(contentValues);

        contentValues= new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_NAME,"Bebida");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_IMPUESTO_TIPO,"Consumo");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_IMPUESTO_VALOR,"650");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_PATH, "data/facturas/factura3.png");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_FECHA_COMPRA,"8:01 PM 18-12-15");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_FECHA_CAPTURA,"11:13 PM 18-12-15");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_NIT,1991246122);

        bd.insertarFactura(contentValues);
    }


    /**
     * Inserta una Factura cuyo NIT y usuario ya estan definidos en la clase Factura.
     * @param factura
     * exception, que el NIT o el usuario existan
     */
    public void addFactura(Factura factura)//params. nit?
    {
        BaseDatos bd = new BaseDatos(context);
        ContentValues contentValues = new ContentValues();

        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_NAME,factura.getNombre());
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_IMPUESTO_TIPO,factura.getTipoImpuesto());
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_IMPUESTO_VALOR,factura.getValorImpuesto());
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_PATH, factura.getPath());
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_FECHA_COMPRA,factura.getStringFechaCompra());
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_FECHA_CAPTURA,factura.getStringFechaCaptura());
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_NIT,factura.getNit());
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_USUARIO_ID,factura.getContribuyente().getId());
        bd.insertarFactura(contentValues);
    }


    public void addFactura(Factura factura, Comercio comercio, Usuario contribuyente)//params. nit?
    {
        BaseDatos bd = new BaseDatos(context);
        ContentValues contentValues = new ContentValues();

        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_NAME,factura.getNombre());
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_IMPUESTO_TIPO,factura.getTipoImpuesto());
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_IMPUESTO_VALOR,factura.getValorImpuesto());
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_PATH, factura.getPath());
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_FECHA_COMPRA,factura.getStringFechaCompra());
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_FECHA_CAPTURA,factura.getStringFechaCaptura());
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_NIT,comercio.getNIT());
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_USUARIO_ID,contribuyente.getId());
        bd.insertarFactura(contentValues);
    }

    // AFP - 20160919 - I Adicion del valor de la factura
    /**
     *
     * @param impuestoTipo
     * @param impuestoValor
     * @param ruta
     * @param nit
     */
    public void addFactura(int valorFactura ,String impuestoTipo, String impuestoValor, String ruta, int nit)
    {
        BaseDatos bd = new BaseDatos(context);
        ContentValues contentValues = new ContentValues();

        SimpleDateFormat dateFormat = new SimpleDateFormat(Factura.DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        String fechaActual= dateFormat.format(cal.getTime());

        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_VALOR, valorFactura);
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_NAME, "");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_IMPUESTO_TIPO,impuestoTipo);
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_IMPUESTO_VALOR,impuestoValor);
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_PATH, ruta);
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_FECHA_COMPRA, fechaActual);
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_FECHA_CAPTURA, fechaActual);
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_NIT, nit);
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_USUARIO_ID, 123);
        bd.insertarFactura(contentValues);

    }
    // AFP - 20160919 - F

    public double obtenerTotalImpuestosPorTipo(String tipo)
    {
        BaseDatos db = new BaseDatos(context);
        return db.obtenerTotalImpuestosPorTipo(tipo);

    }

    // AFP - 20160919 - I

    /**
     * Metodo multi-proposito
     * @param tipo
     * @return
     */
    public double obtenerTotalImpuestos(String tipo) {
        BaseDatos db = new BaseDatos(context);
        double resultado = 0;
        if(tipo.equalsIgnoreCase("ALL")){
            resultado = db.obtenerTotalImpuestosPorTipo(tipo);
        } else if(tipo.equalsIgnoreCase("IVA")){
            resultado = db.obtenerTotalImpuestosPorTipo(tipo);
        } else if(tipo.equalsIgnoreCase("ICO")){
            resultado = db.obtenerTotalImpuestosPorTipo(tipo);
        } else if(tipo.equalsIgnoreCase("NONE")){
            resultado = db.obtenerTotalImpuestosPorTipo(tipo);
        }

        return resultado;
    }
    // AFP - 20160919 - F

    public double obtenerTotalImpuestosPorTipo(Usuario user)
    {
        BaseDatos db = new BaseDatos(context);
        return db.obtenerTotalImpuestos(user);

    }

    public void deleteAll(){
        BaseDatos db = new BaseDatos(context);
        db.delete();
        Log.d("se borro db","");
    }


}
