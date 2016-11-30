package yosumo.src.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import yosumo.src.R;
import yosumo.src.commons.Debugger;
import yosumo.src.commons.Dummy;
import yosumo.src.logic.Comercio;
import yosumo.src.logic.Denuncia;
import yosumo.src.logic.Factura;
import yosumo.src.logic.Impuesto;
import yosumo.src.logic.Usuario;

/**
 * Clase que representa la base de datos y maneja sus operaciones
 * Created by David Ricardo on 14/09/2016.
 * DRM 20160916     COMERCIO_ID es ahora el NIT, integer NK
 * AFP 20160923     REORGAIZACION DE CODIGO Y LIMPIEZA.
 * Re-asignacion de responsabildades clase ConstantesDB
 * Unificacion de nombres y eliminacion de raw queries
 * Cambio de nombres y revalidación de tipos
 * Cambio de Convenciones
 * // AFP 20161030    Adicion de multas
 * // DM 201161127 insertar factura desde QR
 */
public class ManagerDB extends SQLiteOpenHelper {

    private final String TAG = "ManagerDB";
    private Context context;
    private Debugger debug;

    /**
     * Constructor
     *
     * @param context
     */
    public ManagerDB(Context context) {
        super(context, ConstantesDB.DATABASE_NAME, null, ConstantesDB.DATABASE_VERSION);
        this.context = context;
        debug = new Debugger(context);
        //Insert impuesto

    }

    /**
     * Metodo constructor de las tablas en el momento inicial
     *
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
        debug.debugConsole(TAG, "Created DB");
    }

    public void onUpgrade(SQLiteDatabase db, int a, int b) {

    }

    /**
     * Metodo Que obtienen todas las facturas
     *
     * @return
     */
    public ArrayList<Factura> getAllFacturas() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Factura> facturas = new ArrayList<Factura>();

        Cursor cursorFactura = db.query(
                ConstantesDB.TABLE_FACTURA,                      /* table */
                new String[]{
            //            ConstantesDB.TABLE_FACTURA_FK_IMPUESTO,
            //            ConstantesDB.TABLE_FACTURA_FK_COMERCIO_NIT,
                        ConstantesDB.TABLE_FACTURA_FK_COMERCIO_ID,
                        ConstantesDB.TABLE_FACTURA_FK_USUARIO,
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

                Factura factura = new Factura(
                        //getImpuestoByFacturaID(cursorFactura.getInt(0)),
                        getComercioByID(cursorFactura.getInt(0)),
                        cursorFactura.getString(1),
                        cursorFactura.getDouble(2),
                        cursorFactura.getString(3),
                        cursorFactura.getString(4),
                        cursorFactura.getString(5),
                        cursorFactura.getInt(6)
                );

                //  Comercio comercio, String usuario, double valor_total, String fechaCaptura, String fechaCompra, String path, int id ){
                facturas.add(factura);

            } while (cursorFactura.moveToNext());
        }
        db.close();
        return facturas;
    }


    /**
     * Metodo Que obtienen todas las facturas
     *
     * @return
     */
    public ArrayList<Denuncia> getAllDenuncias() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Denuncia> denuncias = new ArrayList<Denuncia>();

        Cursor cursorDenuncia = db.query(
                ConstantesDB.TABLE_DENUNCIA,                      /* table */
                new String[]{
                        ConstantesDB.TABLE_DENUNCIA_FK_USUARIO,
                        ConstantesDB.TABLE_DENUNCIA_NOMBRECOMERCIO,
                        ConstantesDB.TABLE_DENUNCIA_DIRECCIONCOMERCIO,
                        ConstantesDB.TABLE_DENUNCIA_COMENTARIO,
                        ConstantesDB.TABLE_DENUNCIA_LATITUD,
                        ConstantesDB.TABLE_DENUNCIA_LONGITUD,
                        ConstantesDB.TABLE_DENUNCIA_ESTADO,
                        ConstantesDB.TABLE_DENUNCIA_DTDENUNCIA,
                        ConstantesDB.TABLE_DENUNCIA_DTCREACION
                },
                null/*ConstantesDB.TABLE_USUARIO_NAME + " = ?" /* where or selection */,
                null/* selectionzArgs i.e. value to replace ? */,
                null /* groupBy */,
                null /* having */,
                null /* orderBy */
        );

        if (cursorDenuncia.moveToFirst()) {
            do {

               // Log.d("date1", cursorDenuncia.getString(7));
                // Log.d("date2", cursorDenuncia.getString(8));

                Denuncia denuncia = new Denuncia(
                        cursorDenuncia.getString(0), // int fk_usuario
                        cursorDenuncia.getString(1), // String nombre_comercio
                        cursorDenuncia.getString(2), // String direccion_comercio
                        cursorDenuncia.getString(3), // String comentario
                        cursorDenuncia.getDouble(4),  // float latitud
                        cursorDenuncia.getDouble(5), // float longitud
                        cursorDenuncia.getString(6), //String estado
                        Dummy.formatTimestamp(cursorDenuncia.getString(7)), // Date fechaDenuncia
                        Dummy.formatTimestamp(cursorDenuncia.getString(8)) // Date fechaCaptura
                );

                denuncias.add(denuncia);

            } while (cursorDenuncia.moveToNext());
        }

        db.close();
        return denuncias;
    }


    /**
     * @param xusuario
     * @return
     */
    public Usuario getUserByUser(String xusuario) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = getReadableDatabase().rawQuery(ConstantesDB.QUERY_GET_USER_BYUSUARIO, new String[]{xusuario});

            cursor.moveToFirst();
            debug.debugConsole(TAG, "Rows count: " + cursor.getCount() + "");
            Usuario usuario = new Usuario(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));

            return usuario;
        } catch (Exception e) {
            debug.debugConsole(TAG, e.getMessage());
            return null;
        }
    }

    /**
     * @param id
     * @return
     */
    public Usuario getUserById(String id) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = getReadableDatabase().rawQuery(ConstantesDB.QUERY_GET_USER_BYID, new String[]{id});

            cursor.moveToFirst();
            debug.debugConsole(TAG, "Rows count: " + cursor.getCount() + "");
            Usuario usuario = new Usuario(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));

            return usuario;
        } catch (Exception e) {
            debug.debugConsole(TAG, e.getMessage());
            return null;
        }
    }


    /**
     * @return
     */
    public ArrayList<Comercio> getAllComercios() {
        ArrayList<Comercio> comercios = new ArrayList<Comercio>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = getReadableDatabase().rawQuery(ConstantesDB.QUERY_GET_ALL_COMERCIOS, null);

        while (cursor.moveToNext()) {
            Comercio comercioActual = new Comercio(cursor.getString(0),cursor.getString(1));
            comercios.add(comercioActual);
        }

        db.close();

        return comercios;
    }

    /**
     * @return
     */
    public double getAllImpuestos() {
        double monto = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = getReadableDatabase().rawQuery(ConstantesDB.QUERY_GET_ALL_IMPUESTOS, null);

        cursor.moveToFirst();
        monto = cursor.getDouble(0);
        debug.debugConsole(monto + " ");
        db.close();

        return monto;
    }


    /**
     * @return
     */
    public int getFacturaMaxID() {
        int id = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = getReadableDatabase().rawQuery(ConstantesDB.QUERY_FACTURA_MAX_ID, null);

        cursor.moveToFirst();
        id = cursor.getInt(0);
        db.close();

        return id + 1;
    }



    /***
     * @param tipoImpuesto
     * @return
     */
    public double getImpuestosByType(String tipoImpuesto) {
        double monto = 0;
        debug.debugConsole("qry" + " :" + ConstantesDB.QUERY_GET_IMPUESTOS_BYTYPE);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = getReadableDatabase().rawQuery(ConstantesDB.QUERY_GET_IMPUESTOS_BYTYPE, null);

        cursor.moveToFirst();
        if(tipoImpuesto.equalsIgnoreCase("valor_iva")){
            monto = cursor.getDouble(0);
        }else {
            monto = cursor.getDouble(1);
        }
        debug.debugConsole(monto + " ");
        db.close();

        return monto;
    }

    /**
     * @param id
     * @return
     */
    public Impuesto getImpuestoByFacturaID(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = getReadableDatabase().rawQuery(ConstantesDB.QUERY_GET_IMPUESTO_BYTID_FACTURA, new String[]{id + ""});

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





    public Comercio getComercioByID(int id) {
        //Log.d("getComercioByID: ", id+"" );

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = getReadableDatabase().rawQuery(ConstantesDB.QUERY_GET_COMERCIO_BY_ID, new String[]{id+"", "activo"});

        if (cursor.getCount() == 0) {
           // insertComercio(new Comercio(id));
            db.close();
           // return getComercioByID(id);
        }

        cursor.moveToFirst();
        Comercio comercio = new Comercio(cursor.getString(0),cursor.getString(2));
        db.close();

        return comercio;
    }

    /**
     * Inserta una nueva denuncia
     *
     * @param denuncia
     * @return
     */
    public int insertDenuncia(Denuncia denuncia) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesDB.TABLE_DENUNCIA_FK_USUARIO, denuncia.getUsername());
        contentValues.put(ConstantesDB.TABLE_DENUNCIA_NOMBRECOMERCIO, denuncia.getNombre_comercio());
        contentValues.put(ConstantesDB.TABLE_DENUNCIA_DIRECCIONCOMERCIO, denuncia.getDireccion_comercio());
        contentValues.put(ConstantesDB.TABLE_DENUNCIA_COMENTARIO, denuncia.getComentario());
        contentValues.put(ConstantesDB.TABLE_DENUNCIA_DTDENUNCIA, Dummy.formatDate(denuncia.getFechaDenuncia()));
        contentValues.put(ConstantesDB.TABLE_DENUNCIA_DTCREACION, Dummy.formatDate(denuncia.getFechaCaptura()));
        contentValues.put(ConstantesDB.TABLE_DENUNCIA_LATITUD, denuncia.getLatitud());
        contentValues.put(ConstantesDB.TABLE_DENUNCIA_LONGITUD, denuncia.getLongitud());
        contentValues.put(ConstantesDB.TABLE_DENUNCIA_ESTADO, denuncia.getEstado());

        db.insert(ConstantesDB.TABLE_DENUNCIA, null, contentValues);
        db.close();
        return 0;
    }


    /**
     * @param factura
     */
    public void insertFactura(Factura factura) {
        SQLiteDatabase db = this.getWritableDatabase();
        factura.getImpuesto().calcularIVA(factura.valor_total);
        factura.getImpuesto().calcularICO(factura.valor_total);

        ContentValues contentValues = new ContentValues();
        //contentValues.put(ConstantesDB.TABLE_FACTURA_NK_CONSECUTIVO, nk); // todo de donde saco este numero?
        contentValues.put(ConstantesDB.TABLE_FACTURA_FECHA_COMPRA, Dummy.formatDate(factura.fechaCompra));
        contentValues.put(ConstantesDB.TABLE_FACTURA_FECHA_CAPTURA, Dummy.formatDate(factura.fechaCaptura));
        contentValues.put(ConstantesDB.TABLE_FACTURA_PATH, factura.path); //no hay path
        contentValues.put(ConstantesDB.TABLE_FACTURA_FK_COMERCIO_ID, factura.comercio.getNit());
        contentValues.put(ConstantesDB.TABLE_FACTURA_FK_USUARIO, factura.usuario); //todo como saca el usuario actual?
      //  contentValues.put(ConstantesDB.TABLE_FACTURA_FK_COMERCIO_NIT, factura.getComercio().nit);
      //  contentValues.put(ConstantesDB.TABLE_FACTURA_FK_IMPUESTO, insertImpuesto(factura.getImpuesto()));
        contentValues.put(ConstantesDB.TABLE_FACTURA_VALOR_TOTAL, factura.valor_total);
        contentValues.put(ConstantesDB.TABLE_FACTURA_TAG, factura.tag);



        if (!db.isOpen()) {
            db = this.getWritableDatabase();
        }

        db.insert(ConstantesDB.TABLE_FACTURA, null, contentValues);
        debug.debugConsole("created factura");
        db.close();
    }

    /**
     * @param usuario
     * @return
     */
    public int insertUsuario(Usuario usuario) {
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
     * @param comercio
     * @return
     */
    public int insertComercio(Comercio comercio) {
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
     *
     * @param
     * @return
     */
    public int insertComercioBulk(String comercios) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            comercios = comercios.split("_:_")[1];
            // Log.d("Line comercios 0 : ", comercios);

            for (String comercio : comercios.split("\\$")) {
        /*    Log.d("Line comercios: ", comercio);
            Log.d("Comercio details: 1 : ", comercio.split("\\|")[0]);
            Log.d("Comercio details: 2 : ", comercio.split("\\|")[1]);
            Log.d("Comercio details: 3 : ", comercio.split("\\|")[2]);
            Log.d("Comercio details: 4 : ", comercio.split("\\|")[3]);
            Log.d("Comercio details: 5 : ", comercio.split("\\|")[4]);
            Log.d("Comercio details: 6 : ", comercio.split("\\|")[6]);*/

                ContentValues contentValues = new ContentValues();
                contentValues.put(ConstantesDB.TABLE_COMERCIO_ID, comercio.split("\\|")[0]);
                contentValues.put(ConstantesDB.TABLE_COMERCIO_NIT, comercio.split("\\|")[1]);
                contentValues.put(ConstantesDB.TABLE_COMERCIO_NOMBRE, comercio.split("\\|")[2]);
                contentValues.put(ConstantesDB.TABLE_COMERCIO_NOMBRE_LEGAL, comercio.split("\\|")[3]);
                contentValues.put(ConstantesDB.TABLE_COMERCIO_REGIMEN, comercio.split("\\|")[4]);
                contentValues.put(ConstantesDB.TABLE_COMERCIO_DIRECCION, comercio.split("\\|")[5]);
                contentValues.put(ConstantesDB.TABLE_COMERCIO_ESTADO, comercio.split("\\|")[7]);

                //db.insert(ConstantesDB.TABLE_COMERCIO, null, contentValues);
                db.insertWithOnConflict(ConstantesDB.TABLE_COMERCIO, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);

            }

            db.close();
        } catch (Exception a) {
            System.out.println("Error: " + a.getMessage());
        } finally {
        }

        return 0;
    }


    /**
     * Es lo que el servidor manda para que el cliente actualice
     *
     * @param
     * @return
     */
    public int insertFacturaBulk(String facturas) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            facturas = facturas.split("_:_")[1];
            // Log.d("Line comercios 0 : ", comercios);

            for (String factura : facturas.split("\\$")) {
            //Log.d("Line factura: ", factura);

                ContentValues contentValues = new ContentValues();
                contentValues.put(ConstantesDB.TABLE_FACTURA_NK_CONSECUTIVO, factura.split("\\|")[0]);
                contentValues.put(ConstantesDB.TABLE_FACTURA_FECHA_COMPRA, factura.split("\\|")[1]);
                contentValues.put(ConstantesDB.TABLE_FACTURA_FECHA_CAPTURA, factura.split("\\|")[2]);
                contentValues.put(ConstantesDB.TABLE_FACTURA_PATH, factura.split("\\|")[3]);
                contentValues.put(ConstantesDB.TABLE_FACTURA_FK_COMERCIO_ID, factura.split("\\|")[4]);
                contentValues.put(ConstantesDB.TABLE_FACTURA_FK_USUARIO, factura.split("\\|")[5]);
                contentValues.put(ConstantesDB.TABLE_FACTURA_VALOR_TOTAL, factura.split("\\|")[6]);
                contentValues.put(ConstantesDB.TABLE_FACTURA_TAG, factura.split("\\|")[7]);

                db.insertWithOnConflict(ConstantesDB.TABLE_FACTURA, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
            }
            db.close();
        } catch (Exception a) {
            System.out.println("Error insertFacturaBulk: " + a.getMessage());
        } finally {
        }

        return 0;
    }


    /**
     * Es lo que el servidor manda para que el cliente actualice
     *
     * @param
     * @return
     */
    public int insertImpuestoBulk(String impuestos) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            impuestos = impuestos.split("_:_")[1];
            // Log.d("Line comercios 0 : ", comercios);

            for (String impuesto : impuestos.split("\\$")) {
                Log.d("Line impuesto: ", impuesto);
         /*   Log.d("Comercio details: 1 : ", comercio.split("\\|")[0]);
            Log.d("Comercio details: 2 : ", comercio.split("\\|")[1]);
            Log.d("Comercio details: 3 : ", comercio.split("\\|")[2]);
            Log.d("Comercio details: 4 : ", comercio.split("\\|")[3]);
            Log.d("Comercio details: 5 : ", comercio.split("\\|")[4]);
            Log.d("Comercio details: 6 : ", comercio.split("\\|")[6]);*/

                ContentValues contentValues = new ContentValues();

                contentValues.put(ConstantesDB.TABLE_IMPUESTO_ID, impuesto.split("\\|")[0]);
                contentValues.put(ConstantesDB.TABLE_IMPUESTO_PORCEN_IVA, impuesto.split("\\|")[1]);
                contentValues.put(ConstantesDB.TABLE_IMPUESTO_VALOR_IVA, impuesto.split("\\|")[2]);
                contentValues.put(ConstantesDB.TABLE_IMPUESTO_PORCEN_ICO, impuesto.split("\\|")[3]);
                contentValues.put(ConstantesDB.TABLE_IMPUESTO_VALOR_ICO, impuesto.split("\\|")[4]);
                contentValues.put(ConstantesDB.TABLE_IMPUESTO_VALOR_TOTAL, impuesto.split("\\|")[5]);
                contentValues.put(ConstantesDB.TABLE_IMPUESTO_FK_FACTURA_ID, impuesto.split("\\|")[6]);

                db.insertWithOnConflict(ConstantesDB.TABLE_IMPUESTO, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
            }
            db.close();
        } catch (Exception a) {
            System.out.println("Error: " + a.getMessage());
        } finally {
        }

        return 0;
    }

    /**
     * Es lo que el servidor manda para que el cliente actualice
     *
     * @param
     * @return
     */
    public int insertDenunciaBulk(String denuncias) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            denuncias = denuncias.split("_:_")[1];
            // Log.d("Line denuncias 0 : ", denuncias);

            for (String denuncia : denuncias.split("\\$")) {
                //      Log.d("Line denuncias: ", denuncia);
        /*    Log.d("Comercio details: 1 : ", comercio.split("\\|")[0]);
            Log.d("Comercio details: 2 : ", comercio.split("\\|")[1]);
            Log.d("Comercio details: 3 : ", comercio.split("\\|")[2]);
            Log.d("Comercio details: 4 : ", comercio.split("\\|")[3]);
            Log.d("Comercio details: 5 : ", comercio.split("\\|")[4]);
            Log.d("Comercio details: 6 : ", comercio.split("\\|")[6]);*/

                ContentValues contentValues = new ContentValues();
                contentValues.put(ConstantesDB.TABLE_DENUNCIA_FK_USUARIO, denuncia.split("\\|")[0]);
                contentValues.put(ConstantesDB.TABLE_DENUNCIA_NOMBRECOMERCIO, denuncia.split("\\|")[1]);
                contentValues.put(ConstantesDB.TABLE_DENUNCIA_DIRECCIONCOMERCIO, denuncia.split("\\|")[2]);
                contentValues.put(ConstantesDB.TABLE_DENUNCIA_COMENTARIO, denuncia.split("\\|")[3]);
                contentValues.put(ConstantesDB.TABLE_DENUNCIA_LATITUD, denuncia.split("\\|")[4]);
                contentValues.put(ConstantesDB.TABLE_DENUNCIA_LONGITUD, denuncia.split("\\|")[5]);
                contentValues.put(ConstantesDB.TABLE_DENUNCIA_DTDENUNCIA, denuncia.split("\\|")[6]);
                contentValues.put(ConstantesDB.TABLE_DENUNCIA_DTCREACION, denuncia.split("\\|")[7]);
                contentValues.put(ConstantesDB.TABLE_DENUNCIA_ESTADO, denuncia.split("\\|")[8]);


                //db.insert(ConstantesDB.TABLE_COMERCIO, null, contentValues);
                db.insertWithOnConflict(ConstantesDB.TABLE_DENUNCIA, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);

            }

            db.close();
        } catch (Exception a) {
            System.out.println("Error: " + a.getMessage());
        } finally {
        }

        return 0;
    }

    /***
     * @param impuesto
     * @return
     */
    public int insertImpuesto(Impuesto impuesto) {
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

    public int updateDenuncia() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            //String str = "UPDATE "+ ConstantesDB.TABLE_DENUNCIA+" SET "+ ConstantesDB.TABLE_DENUNCIA_ESTADO +" = 'enviado' WHERE estado = 'pendiente' ";
            //Log.d("QUERY update ",  str);
            //db.execSQL(str);

            ContentValues contentValues = new ContentValues();
            contentValues.put(ConstantesDB.TABLE_DENUNCIA_ESTADO, "enviado");

            db.update(ConstantesDB.TABLE_DENUNCIA,
                    contentValues,
                    null,
                    null
            );
            Log.d("update denuncias", " ");
            db.close();
        } catch (Exception a) {
            System.out.println("Error: " + a.getMessage());
        } finally {
        }

        return 0;
    }


    /**
     * Metodo que hace el drop de una tabla definida
     *
     * @param table Nombre de la tabla
     */
    public void dropTable(String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "";

        if (table.equalsIgnoreCase(ConstantesDB.TABLE_FACTURA)) {
            query = ConstantesDB.DROP_TABLE_FACTURA;
        } else if (table.equalsIgnoreCase(ConstantesDB.TABLE_COMERCIO)) {
            query = ConstantesDB.DROP_TABLE_COMERCIO;
        } else if (table.equalsIgnoreCase(ConstantesDB.TABLE_USUARIO)) {
            query = ConstantesDB.DROP_TABLE_USUARIO;
        } else if (table.equalsIgnoreCase(ConstantesDB.TABLE_IMPUESTO)) {
            query = ConstantesDB.DROP_TABLE_IMPUESTO;
        } else if (table.equalsIgnoreCase(ConstantesDB.TABLE_DENUNCIA)) {
            query = ConstantesDB.DROP_TABLE_DENUNCIA;
        }

        db.execSQL(query);
        db.close();
    }


    /**
     * Metodo que hace el drop de una tabla definida
     *
     * @param table Nombre de la tabla
     */
    public void deleteTable(String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "";

        if (table.equalsIgnoreCase(ConstantesDB.TABLE_FACTURA)) {
            query = ConstantesDB.DELETE_TABLE_FACTURA;
        } else if (table.equalsIgnoreCase(ConstantesDB.TABLE_COMERCIO)) {
            //query = ConstantesDB.DELETE_TABLE_COMERCIO;
        } else if (table.equalsIgnoreCase(ConstantesDB.TABLE_USUARIO)) {
            //query = ConstantesDB.DELETE_TABLE_USUARIO;
        } else if (table.equalsIgnoreCase(ConstantesDB.TABLE_IMPUESTO)) {
            query = ConstantesDB.DELETE_TABLE_IMPUESTO;
        } else if (table.equalsIgnoreCase(ConstantesDB.TABLE_DENUNCIA)) {
            query = ConstantesDB.DELETE_TABLE_DENUNCIA;
        }

        db.execSQL(query);
        db.close();
    }


    /**
     *
     * @param tag
     */
    public void generarEntity(String tag) {
        String resultado ="";
        if(tag.equalsIgnoreCase("factura")){

            Impuesto impuesto = new Impuesto(10, 0, 8, 0, getFacturaMaxID());
            /*Factura factura = new Factura(impuesto,
                    getComercioByNIT("1202200000"),
                    "Apolan",
                    (new Random().nextInt(99000 - 10000 + 1) + 10000),
                    "20160915",
                    "20160915",
                    "path"
            );*/
           // insertFactura(factura);

        }else if(tag.equalsIgnoreCase("")){
            resultado ="";
        }else{
            resultado ="Not find "+ tag;
        }
        Log.d("Tag not find", tag);
    }


    /**
     * @param
     */
    public void insertFacturaQR(String comercio, String nit, String fecha, int valorTotal, String impuesto,
                                int porcentaje, double valorImp, String tag ) {
        SQLiteDatabase db = this.getWritableDatabase();



        ContentValues contentValues = new ContentValues();

        String idFactura = "";

        //contentValues.put(ConstantesDB.TABLE_FACTURA_NK_CONSECUTIVO, idFactura); // todo de donde saco este numero?
        contentValues.put(ConstantesDB.TABLE_FACTURA_FECHA_COMPRA, fecha);
        contentValues.put(ConstantesDB.TABLE_FACTURA_FECHA_CAPTURA, fecha);
        //contentValues.put(ConstantesDB.TABLE_FACTURA_PATH, factura.path); //no hay path
        contentValues.put(ConstantesDB.TABLE_FACTURA_FK_COMERCIO_ID, nit);
        //contentValues.put(ConstantesDB.TABLE_FACTURA_FK_USUARIO, factura.usuario); //todo como saca el usuario actual?
        contentValues.put(ConstantesDB.TABLE_FACTURA_VALOR_TOTAL, valorTotal);
        contentValues.put(ConstantesDB.TABLE_FACTURA_TAG, tag);

        if (!db.isOpen()) {
            db = this.getWritableDatabase();
        }

        db.insert(ConstantesDB.TABLE_FACTURA, null, contentValues);
        debug.debugConsole("created factura");

        //impuesto
        contentValues = new ContentValues();

        String id = "";

        contentValues.put(ConstantesDB.TABLE_IMPUESTO_ID, id);  // todo de donde sale ese id, actualmente nulo

        if(impuesto.equalsIgnoreCase("iva"))
        {
            contentValues.put(ConstantesDB.TABLE_IMPUESTO_PORCEN_IVA, porcentaje);
            contentValues.put(ConstantesDB.TABLE_IMPUESTO_VALOR_IVA, valorImp);
            contentValues.put(ConstantesDB.TABLE_IMPUESTO_PORCEN_ICO, 0);
            contentValues.put(ConstantesDB.TABLE_IMPUESTO_VALOR_ICO, 0);

        }
        else if(impuesto.equalsIgnoreCase("ico"))
        {
            contentValues.put(ConstantesDB.TABLE_IMPUESTO_PORCEN_IVA, 0);
            contentValues.put(ConstantesDB.TABLE_IMPUESTO_VALOR_IVA, 0 );
            contentValues.put(ConstantesDB.TABLE_IMPUESTO_PORCEN_ICO, porcentaje);
            contentValues.put(ConstantesDB.TABLE_IMPUESTO_VALOR_ICO, valorImp);
            contentValues.put(ConstantesDB.TABLE_IMPUESTO_VALOR_TOTAL, valorImp);
        }

        contentValues.put(ConstantesDB.TABLE_IMPUESTO_VALOR_TOTAL, valorImp);
        contentValues.put(ConstantesDB.TABLE_IMPUESTO_FK_FACTURA_ID, idFactura);

        db.insertWithOnConflict(ConstantesDB.TABLE_IMPUESTO, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);


        db.close();
    }

}
