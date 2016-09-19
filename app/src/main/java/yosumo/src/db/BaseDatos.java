package yosumo.src.db;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import yosumo.src.logic.Comercio;
import yosumo.src.logic.Factura;
import yosumo.src.logic.Usuario;

/**
 * Clase que representa la base de datos y maneja sus operaciones
 * Created by David Ricardo on 14/09/2016.
 * <180916 Mod DRM> CCOMERCIO_ID es ahora el NIT, integer NK
 */
public class BaseDatos extends SQLiteOpenHelper {

    private Context context;

    public BaseDatos(Context context) {
        super(context, ConstantesBaseDatos.DATABASE_NAME, null, ConstantesBaseDatos.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCrearTablaFacturas = "CREATE TABLE IF NOT EXISTS" + ConstantesBaseDatos.TABLE_FACTURAS + " ( "+
                ConstantesBaseDatos.TABLE_FACTURAS_ID               +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                ConstantesBaseDatos.TABLE_FACTURAS_NAME             +" TEXT,"+
                ConstantesBaseDatos.TABLE_FACTURAS_FECHA_CAPTURA    +" TEXT," +
                ConstantesBaseDatos.TABLE_FACTURAS_FECHA_COMPRA     +" TEXT," +
                ConstantesBaseDatos.TABLE_FACTURAS_PATH             +" TEXT," +
                ConstantesBaseDatos.TABLE_FACTURAS_NIT              +" INTEGER," +
                ConstantesBaseDatos.TABLE_FACTURAS_IMPUESTO_TIPO    +" TEXT,"+
                ConstantesBaseDatos.TABLE_FACTURAS_IMPUESTO_VALOR   +" FLOAT,"+
                //ConstantesBaseDatos.TABLE_FACTURAS_COMERCIO_ID      +" INTEGER," +
                ConstantesBaseDatos.TABLE_FACTURAS_USUARIO_ID       +" INTEGER, " +
                "FOREIGN KEY ( "+ConstantesBaseDatos.TABLE_FACTURAS_USUARIO_ID+ " ) "+
                "REFERENCES "+ConstantesBaseDatos.TABLE_USUARIO + " ( "+ConstantesBaseDatos.TABLE_USUARIO_ID+" ), " +
                "FOREIGN KEY ( "+ConstantesBaseDatos.TABLE_FACTURAS_NIT+ " ) "+
                "REFERENCES "+ConstantesBaseDatos.TABLE_COMERCIOS + " ( "+ConstantesBaseDatos.TABLE_COMERCIOS_NIT+" )"+
                ")";

        String queryCrearTablaUsuario = "CREATE TABLE "+ConstantesBaseDatos.TABLE_USUARIO + " ( "+
                ConstantesBaseDatos.TABLE_USUARIO_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ConstantesBaseDatos.TABLE_USUARIO_NAME  + " TEXT "+
                ConstantesBaseDatos.TABLE_USUARIO_MAIL  + " TEXT "+
                ConstantesBaseDatos.TABLE_USUARIO_PASSWORD  + " TEXT "+
                ")";

        String queryCrearTablaComercios = "CREATE TABLE "+ConstantesBaseDatos.TABLE_COMERCIOS + "("+
                //ConstantesBaseDatos.TABLE_COMERCIOS_ID    + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ConstantesBaseDatos.TABLE_COMERCIOS_NIT  + " INTEGER PRIMARY KEY, "+
                ConstantesBaseDatos.TABLE_COMERCIOS_NOMBRE  + " TEXT )" ;
                //ConstantesBaseDatos.TABLE_COMERCIOS_LOCATION  + " TEXT )";


        db.execSQL(queryCrearTablaUsuario);
        db.execSQL(queryCrearTablaComercios);
        db.execSQL(queryCrearTablaFacturas);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXIST"+ConstantesBaseDatos.TABLE_USUARIO);
        db.execSQL("DROP TABLE IF EXIST"+ConstantesBaseDatos.TABLE_FACTURAS);
        onCreate(db);
    }

    public ArrayList<Factura> obtenerTodasLasFacturas(){

        ArrayList<Factura> facturas = new ArrayList<Factura>();

        String query =  "SELECT * FROM " + ConstantesBaseDatos.TABLE_FACTURAS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor registros = db.rawQuery(query,null);

        while(registros.moveToNext())
        {
            Factura facturaActual = new Factura();
            facturaActual.setId(registros.getInt(0));
            facturaActual.setNombre(registros.getString(1));
            facturaActual.setFechaCompra(registros.getString(3));
            facturaActual.setFechaCaptura(registros.getString(2));
            facturaActual.setNit(registros.getInt(5));
            facturaActual.setPath(registros.getString(4));
            facturaActual.setTipoImpuesto(registros.getString(5));
            facturaActual.setValorImpuesto(registros.getFloat(6));
            //todo obtener el nombre del comercio por medio de la FK

            facturas.add(facturaActual);
        }

        db.close();

        return facturas;

    }

    public ArrayList<Usuario> obtenerTodosLosUsuarios()
    {
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

        String query =  "SELECT * FROM " +ConstantesBaseDatos.TABLE_USUARIO;
        SQLiteDatabase db = this.getWritableDatabase(); //no sería readable?
        Cursor registros = db.rawQuery(query,null);

        while(registros.moveToNext()){
            Usuario usuarioActual = new Usuario();
            usuarioActual.setId(registros.getInt(0));
            usuarioActual.setNombre(registros.getString(1));
            usuarioActual.setMail(registros.getString(2));
            usuarioActual.setPassword(registros.getString(3));

            usuarios.add(usuarioActual);
        }

        db.close();

        return usuarios;

    }


    public ArrayList<Comercio> obtenerTodosLosComercios()
    {
        ArrayList<Comercio> comercios = new ArrayList<Comercio>();

        String query =  "SELECT * FROM " +ConstantesBaseDatos.TABLE_COMERCIOS;
        SQLiteDatabase db = this.getWritableDatabase(); //no sería readable?
        Cursor registros = db.rawQuery(query,null);

        while(registros.moveToNext())
        {
            Comercio comercioActual = new Comercio();
            comercioActual.setNIT(registros.getInt(1));
            comercioActual.setLabel_nombre(registros.getString(2));

            comercios.add(comercioActual);
        }

        db.close();

        return comercios;

    }

    public void insertarUsuario(ContentValues contentValues)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ConstantesBaseDatos.TABLE_USUARIO,null,contentValues);
        db.close();
    }

    public void insertarFactura(ContentValues contentValues)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ConstantesBaseDatos.TABLE_FACTURAS,null,contentValues);
        db.close();
    }

    public void insertarComercio(ContentValues contentValues)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ConstantesBaseDatos.TABLE_COMERCIOS,null,contentValues);
        db.close();
    }


    public double obtenerTotalImpuestosPorTipo(String tipoImpuesto)
    {

        double monto = 0;
        String query = "SELECT SUM ( "+ConstantesBaseDatos.TABLE_FACTURAS_IMPUESTO_VALOR+" )"+
                " FROM "+ConstantesBaseDatos.TABLE_FACTURAS+
                " WHERE "+ConstantesBaseDatos.TABLE_FACTURAS_IMPUESTO_TIPO+"="+tipoImpuesto;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);

        while(registros.moveToNext())
        {
            monto= registros.getDouble(0); //Si retornará double?
        }

        db.close();

        return monto;
    }


    public double obtenerTotalImpuestos(Usuario user)
    {

        double monto = 0;
        String query = "SELECT SUM ( "+ConstantesBaseDatos.TABLE_FACTURAS_IMPUESTO_VALOR+" )"+
                " FROM "+ConstantesBaseDatos.TABLE_FACTURAS;
                //+" WHERE "+ConstantesBaseDatos.TABLE_FACTURAS_USUARIO_ID+"="+user.getId();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);

        while(registros.moveToNext())
        {
            monto= registros.getDouble(0); //Si retornará double?
        }

        db.close();

        return monto;
    }
}