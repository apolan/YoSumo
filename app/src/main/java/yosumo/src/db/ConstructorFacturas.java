package yosumo.src.db;

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;

import yosumo.src.db.BaseDatos;
import yosumo.src.db.ConstantesBaseDatos;
import yosumo.src.logic.Factura;

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
        insertarTresFacturas(bd);
        return bd.obtenerTodasLasFacturas();
    }


    public void insertarTresFacturas(BaseDatos bd)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_NAME,"Comida");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_IMPUESTO_TIPO,"IVA");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_IMPUESTO_VALOR,"4100");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_PATH, "data/facturas/factura1.png");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_FECHA_COMPRA,"12:01 AM 17-12-15");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_FECHA_CAPTURA,"12:13 AM 17-12-15");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_NIT,"860.001335-4");

        bd.insertarFactura(contentValues);

        contentValues= new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_NAME,"Factura_Rumba");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_IMPUESTO_TIPO,"IVA");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_IMPUESTO_VALOR,"1300");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_PATH, "data/facturas/factura3.png");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_FECHA_COMPRA,"6:01 PM 16-12-15");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_FECHA_CAPTURA,"7:13 PM 16-12-15");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_NIT,"1020.79367-5");
        bd.insertarFactura(contentValues);

        contentValues= new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_NAME,"Bebida");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_IMPUESTO_TIPO,"Consumo");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_IMPUESTO_VALOR,"650");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_PATH, "data/facturas/factura3.png");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_FECHA_COMPRA,"8:01 PM 18-12-15");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_FECHA_CAPTURA,"11:13 PM 18-12-15");
        contentValues.put(ConstantesBaseDatos.TABLE_FACTURAS_NIT,"1020.79367-5");

        bd.insertarFactura(contentValues);
    }

}
