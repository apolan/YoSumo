package yosumo.src.db;

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;

import yosumo.src.logic.Comercio;
import yosumo.src.logic.Usuario;

/**
 * Created by David Ricardo on 17/09/2016.
 * Clase de que actua como interactor trayendo los datos de usuarios de la base de datos y lo presenta en el context
 */
public class ConstructorComercios {


    private Context context;
    public ConstructorComercios(Context context) {
        this.context = context;
    }


    public ArrayList<Comercio> obtenerDatos()
    {

        BaseDatos bd = new BaseDatos(context);
        insertarDosComercios(bd);
        return bd.obtenerTodosLosComercios();
    }


    public void insertarDosComercios(BaseDatos bd)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_COMERCIOS_NOMBRE,"Hotel V");
        contentValues.put(ConstantesBaseDatos.TABLE_COMERCIOS_NIT,"800.212.4224");
        //contentValues.put(ConstantesBaseDatos.TABLE_COMERCIOS_LOCATION,"4.645218, -74.063961");

        bd.insertarComercio(contentValues);

        contentValues= new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_COMERCIOS_NOMBRE,"Cascabel");
        contentValues.put(ConstantesBaseDatos.TABLE_COMERCIOS_NIT,"901.212.4224");
        //contentValues.put(ConstantesBaseDatos.TABLE_COMERCIOS_LOCATION,"4.602659, -74.064833");
        bd.insertarComercio(contentValues);


    }

}
