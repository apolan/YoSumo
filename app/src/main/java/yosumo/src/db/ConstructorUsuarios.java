package yosumo.src.db;

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;

import yosumo.src.db.BaseDatos;
import yosumo.src.db.ConstantesBaseDatos;
import yosumo.src.logic.Usuario;

/**
 * Created by David Ricardo on 17/09/2016.
 * Clase de que actua como interactor trayendo los datos de usuarios de la base de datos y lo presenta en el context
 */
public class ConstructorUsuarios {


    private Context context;
    public ConstructorUsuarios(Context context) {
        this.context = context;
    }


    public ArrayList<Usuario> obtenerDatos()
    {

        BaseDatos bd = new BaseDatos(context);
        insertarTresUsuarios(bd);
        return bd.obtenerTodosLosUsuarios();
    }


    public void insertarTresUsuarios(BaseDatos bd)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_USUARIO_NAME,"David Hernandez");

        bd.insertarUsuario(contentValues);

        contentValues= new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_USUARIO_NAME,"Daniel Alvarez");
        bd.insertarUsuario(contentValues);

        contentValues= new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_USUARIO_NAME,"Pedro Sanchez");
        bd.insertarUsuario(contentValues);
    }

}
