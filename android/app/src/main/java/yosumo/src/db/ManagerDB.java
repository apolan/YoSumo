package yosumo.src.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.Random;

import yosumo.src.debug.Debugger;
import yosumo.src.logic.Comercio;
import yosumo.src.logic.Denuncia;
import yosumo.src.logic.Factura;
import yosumo.src.logic.Impuesto;
import yosumo.src.logic.ManagerFormat;
import yosumo.src.logic.Usuario;

/**
 * Clase que representa la base de datos y maneja sus operaciones
 * Created by David Ricardo on 14/09/2016.
 * DRM 20160916     COMERCIO_ID es ahora el NIT, integer NK
 * AFP 20160923     REORGAIZACION DE CODIGO Y LIMPIEZA.
 *                  Re-asignacion de responsabildades clase ConstantesDB
 *                  Unificacion de nombres y eliminacion de raw queries
 *                  Cambio de nombres y revalidación de tipos
 *                  Cambio de Convenciones
 // AFP 20161030    Adicion de multas
 */
public class ManagerDB extends SQLiteOpenHelper {

    private final String TAG = "ManagerDB";
    private Context context;
    private Debugger debug;

    /**
     * Constructor
     * @param context
     */
    public ManagerDB(Context context) {
        super(context, ConstantesDB.DATABASE_NAME, null, ConstantesDB.DATABASE_VERSION);
        this.context = context;
        debug = new Debugger(context);
        //Insert impuesto
        Impuesto impuesto = new Impuesto(10,0,8,0,getFacturaMaxID());
        Factura factura = new Factura(impuesto,
                                        getComercioByNIT("1202200000"),
                                        "Apolan",
                                        (new Random().nextInt(99000 - 10000 + 1) + 10000),
                                        "20160915",
                                        "20160915",
                                        "path"
                                    );
        insertFactura(factura);
    }

    /**
     * Metodo constructor de las tablas en el momento inicial
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(ConstantesDB.CREATE_TABLE_USUARIO);
        db.execSQL(ConstantesDB.CREATE_TABLE_IMPUESTO);
        db.execSQL(ConstantesDB.CREATE_TABLE_COMERCIO);
        db.execSQL(ConstantesDB.CREATE_TABLE_FACTURA);
        // 20161030 AFP - I
        db.execSQL(ConstantesDB.CREATE_TABLE_DENUNCIA);
        // 20161030 AFP - F
        debug.debugConsole(TAG,"Created DB");
    }

    public void onUpgrade(SQLiteDatabase db, int a, int b){

    }

    /**
     * Metodo Que obtienen todas las facturas
     * @return
     */
    public ArrayList<Factura> getAllFacturas(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Factura> facturas = new ArrayList<Factura>();

        Cursor cursorFactura = db.query(
                                ConstantesDB.TABLE_FACTURA,                      /* table */
                                new String[] {
                                        ConstantesDB.TABLE_FACTURA_FK_IMPUESTO,
                                        ConstantesDB.TABLE_FACTURA_FK_COMERCIO_NIT,
                                        ConstantesDB.TABLE_FACTURA_FK_USUARIO_USUARIO,
                                        ConstantesDB.TABLE_FACTURA_VALOR_TOTAL,
                                        ConstantesDB.TABLE_FACTURA_FECHA_COMPRA,
                                        ConstantesDB.TABLE_FACTURA_FECHA_CAPTURA,
                                        ConstantesDB.TABLE_FACTURA_PATH,
                                        ConstantesDB.TABLE_FACTURA_ID
                                },
                                null/*ConstantesDB.TABLE_USUARIO_NAME + " = ?" /* where or selection */,
                                null/* selectionzArgs i.e. value to replace ? */,
                                null /* groupBy */,
                                null /* having */,
                                null /* orderBy */
                            );

        if (cursorFactura.moveToFirst()) {
            do {

                Factura factura = new Factura(getImpuestoByID(cursorFactura.getInt(0)),
                        getComercioByNIT(cursorFactura.getInt(1)+""),
                        cursorFactura.getString(2),
                        cursorFactura.getInt(3),
                        cursorFactura.getString(4),
                        cursorFactura.getString(5),
                        cursorFactura.getString(6),
                        cursorFactura.getInt(7)
                );

                facturas.add(factura);

            } while (cursorFactura.moveToNext());
        }

        db.close();
        return facturas;
    }


    /**
     *
     * @param xusuario
     * @return
     */
    public Usuario getUserByUser(String xusuario){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = getReadableDatabase().rawQuery(ConstantesDB.QUERY_GET_USER_BYUSUARIO, new String[] { xusuario });

            cursor.moveToFirst();
            debug.debugConsole(TAG, "Rows count: "+ cursor.getCount()+"");
            Usuario usuario = new Usuario(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));

            return usuario;
        } catch (Exception e) {
            debug.debugConsole(TAG, e.getMessage());
            return null;
        }
    }


    /**
     *
     * @return
     */
    public ArrayList<Comercio> getAllComercios() {
        ArrayList<Comercio> comercios = new ArrayList<Comercio>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = getReadableDatabase().rawQuery(ConstantesDB.QUERY_GET_ALL_COMERCIOS, null);

        while(cursor.moveToNext()){
            Comercio comercioActual = new Comercio(cursor.getInt(0));
            comercios.add(comercioActual);
        }

        db.close();

        return comercios;
    }

    /**
     *
     * @return
     */
    public double getAllImpuestos() {
        double monto = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = getReadableDatabase().rawQuery(ConstantesDB.QUERY_GET_ALL_IMPUESTOS, null);

        cursor.moveToFirst();
        monto = cursor.getDouble(0);
        debug.debugConsole(monto+" ");
        db.close();

        return monto;
    }

    /**
     *
     * @return
     */
    public int getFacturaMaxID(){
        int id = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = getReadableDatabase().rawQuery(ConstantesDB.QUERY_FACTURA_MAX_ID, null);

        cursor.moveToFirst();
        id = cursor.getInt(0);
        db.close();

        return id+1;
    }

    /**
     * Metodo que es llamado por cliente socket
     * @param
     * @return
     */
    public String getDenunciasPendientesToString(){

        String denuncias = "";


        return denuncias;
    }


    /***
     *
     * @param tipoImpuesto
     * @return
     */
    public double getImpuestosByType(String tipoImpuesto){

        double monto = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = getReadableDatabase().rawQuery(ConstantesDB.QUERY_GET_IMPUESTOS_BYTYPE, new String[] { tipoImpuesto });

        cursor.moveToFirst();
        monto = cursor.getDouble(0);
        db.close();

        return monto;
    }

    /**
     *
     * @param id
     * @return
     */
    public Impuesto getImpuestoByID(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = getReadableDatabase().rawQuery(ConstantesDB.QUERY_GET_IMPUESTO_BYTID_FACTURA, new String[] { id+"" });

        cursor.moveToFirst();
        Impuesto impuesto = new Impuesto(cursor.getInt(0),
                                        cursor.getFloat(1),
                                        cursor.getDouble(2),
                                        cursor.getFloat(3),
                                        cursor.getDouble(4),
                                        cursor.getInt(5)
                                        );

        db.close();

        return impuesto;
    }


    public Comercio getComercioByNIT(String nit){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = getReadableDatabase().rawQuery(ConstantesDB.QUERY_GET_COMERCIO_BYNIT, new String[] { nit, "activo"  });

        if(cursor.getCount()== 0){
            insertComercio(new Comercio(Double.parseDouble(nit)));
            db.close();
            return getComercioByNIT(nit);
        }

        cursor.moveToFirst();
        Comercio comercio = new Comercio(cursor.getDouble(0));
        db.close();

        return comercio;
    }

    /**
     * Inserta una nueva denuncia
     * @param denuncia
     * @return
     */
    public int insertDenuncia(Denuncia denuncia){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesDB.TABLE_DENUNCIA_FK_USUARIO, denuncia.getFk_usuario());
        contentValues.put(ConstantesDB.TABLE_DENUNCIA_NOMBRECOMERCIO, denuncia.getNombre_comercio());
        contentValues.put(ConstantesDB.TABLE_DENUNCIA_DIRECCIONCOMERCIO, denuncia.getDireccion_comercio());
        contentValues.put(ConstantesDB.TABLE_DENUNCIA_DTDENUNCIA, ManagerFormat.formatDate(denuncia.getFechaDenuncia()));
        contentValues.put(ConstantesDB.TABLE_DENUNCIA_DTCREACION, ManagerFormat.formatDate(denuncia.getFechaCaptura()));
        contentValues.put(ConstantesDB.TABLE_DENUNCIA_ESTADO, denuncia.getEstado());

        db.insert(ConstantesDB.TABLE_DENUNCIA, null, contentValues);
        db.close();
        return 0;
    }


    /**
     *
     * @param factura
     */
    public void insertFactura(Factura factura) {
        SQLiteDatabase db = this.getWritableDatabase();
        factura.getImpuesto().calcularIVA(factura.valor_total);
        factura.getImpuesto().calcularICO(factura.valor_total);

        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesDB.TABLE_FACTURA_FECHA_COMPRA, ManagerFormat.formatDate(factura.fechaCompra));
        contentValues.put(ConstantesDB.TABLE_FACTURA_FECHA_CAPTURA, ManagerFormat.formatDate(factura.fechaCaptura));
        contentValues.put(ConstantesDB.TABLE_FACTURA_PATH, factura.path);
        contentValues.put(ConstantesDB.TABLE_FACTURA_FK_USUARIO_USUARIO, factura.usuario);
        contentValues.put(ConstantesDB.TABLE_FACTURA_FK_COMERCIO_NIT, factura.getComercio().nit);
        contentValues.put(ConstantesDB.TABLE_FACTURA_FK_IMPUESTO, insertImpuesto(factura.getImpuesto()));
        contentValues.put(ConstantesDB.TABLE_FACTURA_VALOR_TOTAL, factura.valor_total);

        if(!db.isOpen()){
            db = this.getWritableDatabase();
        }

        db.insert(ConstantesDB.TABLE_FACTURA, null, contentValues);
        debug.debugConsole("created factura");
        db.close();
    }

    /**
     *
     * @param usuario
     * @return
     */
    public int insertUsuario(Usuario usuario){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesDB.TABLE_USUARIO_USUARIO, usuario.getUsuario());
        contentValues.put(ConstantesDB.TABLE_USUARIO_NOMBRE, usuario.getNombre());
        contentValues.put(ConstantesDB.TABLE_USUARIO_MAIL, usuario.getMail());
        contentValues.put(ConstantesDB.TABLE_USUARIO_PASSWORD, usuario.getPassword());

        db.insert(ConstantesDB.TABLE_USUARIO, null, contentValues);
        db.close();
        return 0;
    }

    /**
     *
     * @param comercio
     * @return
     */
    public int insertComercio(Comercio comercio){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesDB.TABLE_COMERCIO_NIT, comercio.nit);
        contentValues.put(ConstantesDB.TABLE_COMERCIO_DIRECCION, comercio.direccion);
        contentValues.put(ConstantesDB.TABLE_COMERCIO_REGIMEN, comercio.regimen);
        contentValues.put(ConstantesDB.TABLE_COMERCIO_ESTADO, comercio.estado);

        db.insert(ConstantesDB.TABLE_COMERCIO, null, contentValues);
        db.close();
        return 0;
    }

    /**
     * Es lo que el servidor manda para que el cliente actualice
     * @param
     * @return
     */
    public int insertComercioBulk(String comercios){
        SQLiteDatabase db = this.getWritableDatabase();

/*        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesDB.TABLE_COMERCIO_NIT, comercio.nit);
        contentValues.put(ConstantesDB.TABLE_COMERCIO_DIRECCION, comercio.direccion);
        contentValues.put(ConstantesDB.TABLE_COMERCIO_REGIMEN, comercio.regimen);
        contentValues.put(ConstantesDB.TABLE_COMERCIO_ESTADO, comercio.estado);

        db.insert(ConstantesDB.TABLE_COMERCIO, null, contentValues);
        db.close();*/
        return 0;
    }

    /***
     *
     * @param impuesto
     * @return
     */
    public int insertImpuesto(Impuesto impuesto){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesDB.TABLE_IMPUESTO_PORCEN_IVA, impuesto.porcent_iva);
        contentValues.put(ConstantesDB.TABLE_IMPUESTO_VALOR_IVA, impuesto.valor_iva);
        contentValues.put(ConstantesDB.TABLE_IMPUESTO_PORCEN_ICO, impuesto.porcent_iva);
        contentValues.put(ConstantesDB.TABLE_IMPUESTO_VALOR_ICO, impuesto.valor_ico);
        contentValues.put(ConstantesDB.TABLE_IMPUESTO_FK_FACTURA_ID, impuesto.factura_id);

        db.insert(ConstantesDB.TABLE_IMPUESTO, null, contentValues);
        db.close();
        return impuesto.factura_id;
    }

    /**
     * Metodo que hace el drop de una tabla definida
     * @param table Nombre de la tabla
     */
    public void dropTable(String table){
        SQLiteDatabase db = this.getWritableDatabase();
        String query =  "";

        if(table.equalsIgnoreCase(ConstantesDB.TABLE_FACTURA)){
            query = ConstantesDB.DROP_TABLE_FACTURA;
        }else if(table.equalsIgnoreCase(ConstantesDB.TABLE_COMERCIO)){
            query = ConstantesDB.DROP_TABLE_COMERCIO;
        }else if(table.equalsIgnoreCase(ConstantesDB.TABLE_USUARIO)){
            query = ConstantesDB.DROP_TABLE_USUARIO;
        }else if(table.equalsIgnoreCase(ConstantesDB.TABLE_IMPUESTO)){
            query = ConstantesDB.DROP_TABLE_IMPUESTO;
        }

        db.execSQL(query);
        db.close();
    }

}
