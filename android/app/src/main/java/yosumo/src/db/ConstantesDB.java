package yosumo.src.db;

/**
 * Maneja los nombres de cada tabla y atributo de la base de datos como constates
 * Created by David Ricardo on 14/09/2016.
 * AFP 20160920     REORGAIZACION DE CODIGO Y LIMPIEZA.
 * MODIFICACION CONVENCIONES.
 * Adicion  queries para cada entidad
 * AFP 20161030     Adicion tabla denuncia.
 */
public final class ConstantesDB {


    public static final String DATABASE_NAME = "yosumo";
    public static final int DATABASE_VERSION = 1;

    //  - - - - - - - - - - - ATTRIBUTES factura - - - - - - - - - - - - - - -
    public static final String TABLE_FACTURA = "factura";
    public static final String TABLE_FACTURA_ID = "id";
    public static final String TABLE_FACTURA_NK_CONSECUTIVO = "nk_consecutivo";
    public static final String TABLE_FACTURA_FECHA_COMPRA = "fecha_compra";
    public static final String TABLE_FACTURA_FECHA_CAPTURA = "fecha_captura";
    public static final String TABLE_FACTURA_PATH = "path_factura";
    public static final String TABLE_FACTURA_FK_COMERCIO_NIT = "fk_comercio_nit";
    public static final String TABLE_FACTURA_NOMBRE = "nombre_lugar";
    public static final String TABLE_FACTURA_FK_USUARIO_USUARIO = "fk_usuario";
    public static final String TABLE_FACTURA_FK_IMPUESTO = "fk_impuesto_id";
    public static final String TABLE_FACTURA_VALOR_TOTAL = "valor_total";
    public static final String TABLE_FACTURA_TAG = "tag";

    //  - - - - - - - - - - - ATTRIBUTES usuario - - - - - - - - - - - - - - -
    public static final String TABLE_USUARIO = "usuario";
    public static final String TABLE_USUARIO_DOCUMENTO = "documento";
    public static final String TABLE_USUARIO_TIPODOC = "tipo_documento";
    public static final String TABLE_USUARIO_USUARIO = "usuario";
    public static final String TABLE_USUARIO_NOMBRE = "nombre";
    public static final String TABLE_USUARIO_ID = "id_usuario";
    public static final String TABLE_USUARIO_MAIL = "mail";
    public static final String TABLE_USUARIO_PASSWORD = "password";
    public static final String TABLE_USUARIO_PATH_PIC = "path_picture";


    //  - - - - - - - - - - - ATTRIBUTES COMERCIO - - - - - - - - - - - - - - -
    public static final String TABLE_COMERCIO = "comercio";
    public static final String TABLE_COMERCIO_NIT = "pk_nit";
    public static final String TABLE_COMERCIO_NOMBRE = "nombre";
    public static final String TABLE_COMERCIO_NOMBRE_LEGAL = "nombre_legal";
    public static final String TABLE_COMERCIO_DIRECCION = "direccion";
    public static final String TABLE_COMERCIO_REGIMEN = "regimen";
    public static final String TABLE_COMERCIO_ESTADO = "estado";


    //  - - - - - - - - - - - ATTRIBUTES IMPUESTO - - - - - - - - - - - - - - -
    public static final String TABLE_IMPUESTO = "impuesto";
    public static final String TABLE_IMPUESTO_ID = "id_impuesto";
    public static final String TABLE_IMPUESTO_TIPO = "tipo";
    public static final String TABLE_IMPUESTO_PORCEN_IVA = "porcentaje_iva";
    public static final String TABLE_IMPUESTO_PORCEN_ICO = "porcentaje_ico";
    public static final String TABLE_IMPUESTO_VALOR_IVA = "valor_iva";
    public static final String TABLE_IMPUESTO_VALOR_ICO = "valor_ico";
    public static final String TABLE_IMPUESTO_FK_FACTURA_ID = "fk_factura_id";
    public static final String TABLE_FK_COMERCIO_ID = "fk_comercio_nit";

    //  - - - - - - - - - - - ATTRIBUTES DENUNCIA  - - - - - - - - - - - - - - -
    public static final String TABLE_DENUNCIA = "denuncia";
    public static final String TABLE_DENUNCIA_ID = "id";
    public static final String TABLE_DENUNCIA_FK_USUARIO = "fk_usuario";
    public static final String TABLE_DENUNCIA_NOMBRECOMERCIO = "nombre_comercio";
    public static final String TABLE_DENUNCIA_DIRECCIONCOMERCIO = "direccion_comercio";
    public static final String TABLE_DENUNCIA_COMENTARIO = "comentario";
    public static final String TABLE_DENUNCIA_DTDENUNCIA = "dt_denuncia";
    public static final String TABLE_DENUNCIA_DTCREACION = "dt_creacion";
    public static final String TABLE_DENUNCIA_ESTADO = "estado";


    // - - - - - - - - - - - TABLE FACTURA - - - - - - - - - - - - - - -
    public static final String CREATE_TABLE_FACTURA = "CREATE TABLE IF NOT EXISTS " + TABLE_FACTURA + "( " +
            TABLE_FACTURA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TABLE_FACTURA_NK_CONSECUTIVO + " TEXT," +
            TABLE_FACTURA_FECHA_COMPRA + " TEXT  NOT NULL," +
            TABLE_FACTURA_FECHA_CAPTURA + " TEXT ," +
            TABLE_FACTURA_FK_USUARIO_USUARIO + " TEXT NOT NULL," +
            TABLE_FACTURA_FK_COMERCIO_NIT + " INTEGER  NOT NULL," +
            TABLE_FACTURA_NOMBRE + " TEXT," +
            TABLE_FACTURA_FK_IMPUESTO + " INTEGER NOT NULL," +
            TABLE_FACTURA_VALOR_TOTAL + " INTEGER NOT NULL," +
            TABLE_FACTURA_PATH + " BLOB NOT NULL," +
            TABLE_FACTURA_TAG + " TEXT" +
            ")";

    public static final String DROP_TABLE_FACTURA = "DROP TABLE " + TABLE_FACTURA;

    public static final String QUERY_FACTURA_MAX_ID = "SELECT MAX(" + TABLE_FACTURA_ID + ") FROM " + TABLE_FACTURA;


    // - - - - - - - - - - - TABLE USUARIO - - - - - - - - - - - - - - -
    public static final String CREATE_TABLE_USUARIO = "CREATE TABLE IF NOT EXISTS " + TABLE_USUARIO + "( " +
            TABLE_USUARIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TABLE_USUARIO_DOCUMENTO + " TEXT," +
            TABLE_USUARIO_TIPODOC + " INTEGER," +
            TABLE_USUARIO_USUARIO + " TEXT NOT NULL," +
            TABLE_USUARIO_MAIL + " TEXT NOT NULL," +
            TABLE_USUARIO_PASSWORD + " TEXT NOT NULL," +
            TABLE_USUARIO_NOMBRE + " TEXT NOT NULL," +
            TABLE_USUARIO_PATH_PIC + " TEXT " +
            ")";

    public static final String DROP_TABLE_USUARIO = "DROP TABLE " + TABLE_USUARIO;

    public static final String QUERY_GET_USER_BYUSUARIO = "SELECT " + TABLE_USUARIO_USUARIO + ","
            + TABLE_USUARIO_NOMBRE + ","
            + TABLE_USUARIO_MAIL + ","
            + TABLE_USUARIO_PASSWORD
            + " FROM " + TABLE_USUARIO +
            " WHERE " + TABLE_USUARIO_USUARIO + " = ?";

    public static final String QUERY_GET_USER_BYID = "SELECT * FROM " + TABLE_USUARIO + " WHERE " + TABLE_USUARIO_USUARIO + " = ?";

    // - - - - - - - - - - - TABLE COMERCIO - - - - - - - - - - - - - - -
    public static final String CREATE_TABLE_COMERCIO = "CREATE TABLE IF NOT EXISTS " + TABLE_COMERCIO + "( " +
            TABLE_COMERCIO_NIT + " INTEGER NOT NULL," +
            TABLE_COMERCIO_NOMBRE + " TEXT," +
            TABLE_COMERCIO_NOMBRE_LEGAL + " TEXT," +
            TABLE_COMERCIO_REGIMEN + " TEXT," +
            TABLE_COMERCIO_DIRECCION + " TEXT," +
            TABLE_COMERCIO_ESTADO + " TEXT" +
            ")";

    public static final String DROP_TABLE_COMERCIO = "DROP TABLE " + TABLE_COMERCIO;

    public static final String QUERY_GET_ALL_COMERCIOS = "SELECT * FROM " + TABLE_COMERCIO;

    public static final String QUERY_GET_COMERCIO_BYNIT = "SELECT " +
            TABLE_COMERCIO_NIT + "," +
            TABLE_COMERCIO_NOMBRE +
            " FROM " + TABLE_COMERCIO +
            " WHERE " + TABLE_COMERCIO_NIT + " = ? AND " +
            TABLE_COMERCIO_ESTADO + " = ?";

    // - - - - - - - - - - - TABLE IMPUESTO - - - - - - - - - - - - - - -
    public static final String CREATE_TABLE_IMPUESTO = "CREATE TABLE IF NOT EXISTS " + TABLE_IMPUESTO + "( " +
            TABLE_IMPUESTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TABLE_IMPUESTO_PORCEN_IVA + " INTEGER," +
            TABLE_IMPUESTO_VALOR_IVA + " INTEGER," +
            TABLE_IMPUESTO_PORCEN_ICO + " INTEGER," +
            TABLE_IMPUESTO_VALOR_ICO + " INTEGER," +
            TABLE_IMPUESTO_FK_FACTURA_ID + " INTEGER NOT NULL" +
            ")";

    public static final String DROP_TABLE_IMPUESTO = "DROP TABLE " + TABLE_IMPUESTO;

    public static final String QUERY_GET_ALL_IMPUESTOS = "SELECT SUM(" + TABLE_IMPUESTO_VALOR_IVA + "+" + TABLE_IMPUESTO_VALOR_IVA + ") " +
            " FROM " + TABLE_IMPUESTO;

    public static final String QUERY_GET_IMPUESTOS_BYTYPE = "SELECT SUM(" + 0 + ") AS IVA FROM " + TABLE_IMPUESTO;

    public static final String QUERY_GET_IMPUESTO_BYTID_FACTURA = "SELECT " + TABLE_IMPUESTO_ID + "," +
            TABLE_IMPUESTO_PORCEN_IVA + "," +
            TABLE_IMPUESTO_VALOR_IVA + "," +
            TABLE_IMPUESTO_PORCEN_ICO + "," +
            TABLE_IMPUESTO_VALOR_ICO + "," +
            TABLE_IMPUESTO_FK_FACTURA_ID +
            " FROM " + TABLE_IMPUESTO +
            " WHERE " + TABLE_IMPUESTO_FK_FACTURA_ID + " = ?";

    public static final String LAST_INSERT = "select last_insert_rowid()";


    // - - - - - - - - - - - TABLE DENUNCIA - - - - - - - - - - - - - - -
    public static final String CREATE_TABLE_DENUNCIA = "CREATE TABLE IF NOT EXISTS " + TABLE_DENUNCIA + "( " +
            TABLE_DENUNCIA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TABLE_DENUNCIA_FK_USUARIO + " TEXT," +
            TABLE_DENUNCIA_NOMBRECOMERCIO + " TEXT," +
            TABLE_DENUNCIA_DIRECCIONCOMERCIO + " TEXT," +
            TABLE_DENUNCIA_COMENTARIO + " TEXT," +
            TABLE_DENUNCIA_DTDENUNCIA + " TEXT," +
            TABLE_DENUNCIA_DTCREACION + " TEXT," +
            TABLE_DENUNCIA_ESTADO + " TEXT" +
            ")";

    public static final String DROP_TABLE_DENUNCIA = "DROP TABLE " + TABLE_DENUNCIA;


}
