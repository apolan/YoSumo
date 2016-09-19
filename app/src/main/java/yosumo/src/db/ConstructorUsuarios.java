package yosumo.src.db;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

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
        //insertarTresUsuarios(bd);
        return bd.obtenerTodosLosUsuarios();
    }


    public void insertarTresUsuarios(BaseDatos bd)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_USUARIO_NAME,"David Hernandez");
        contentValues.put(ConstantesBaseDatos.TABLE_USUARIO_MAIL,"droS@g.com");
        contentValues.put(ConstantesBaseDatos.TABLE_USUARIO_PASSWORD,"hrez");

        bd.insertarUsuario(contentValues);

        contentValues= new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_USUARIO_NAME,"Daniel Alvarez");
        contentValues.put(ConstantesBaseDatos.TABLE_USUARIO_MAIL,"droaS@g.com");
        contentValues.put(ConstantesBaseDatos.TABLE_USUARIO_PASSWORD,"dgeahez");
        bd.insertarUsuario(contentValues);

        contentValues= new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_USUARIO_NAME,"Pedro Sanchez");
        contentValues.put(ConstantesBaseDatos.TABLE_USUARIO_MAIL,"PedroS@g.com");
        contentValues.put(ConstantesBaseDatos.TABLE_USUARIO_PASSWORD,"Peahez");
        bd.insertarUsuario(contentValues);
    }


    // AFP - 20160919 - I

    /**
     *
     */
    public void registrarUsuario(String nombre, String mail, String pass ){
        ContentValues contentValues = new ContentValues();
        BaseDatos db = new BaseDatos(context);
        contentValues= new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_USUARIO_NAME, nombre);
        contentValues.put(ConstantesBaseDatos.TABLE_USUARIO_MAIL, mail );
        contentValues.put(ConstantesBaseDatos.TABLE_USUARIO_PASSWORD,pass);
        db.registrarUsuario(contentValues);
        Log.d("se agrego db","");
    }
    // AFP - 20160919 - F
}
