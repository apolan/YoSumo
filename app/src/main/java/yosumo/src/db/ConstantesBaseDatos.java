package yosumo.src.db;

/**
 * Maneja los nombres de cada tabla y atributo de la base de datos como constates
 * Created by David Ricardo on 14/09/2016.
 */
public final class ConstantesBaseDatos {


    public static final String DATABASE_NAME = "facturas";
    public static final int DATABASE_VERSION = 1;


    public static final String TABLE_FACTURAS = "factura";
    public static final String TABLE_FACTURAS_ID = "id";
    public static final String TABLE_FACTURAS_FECHA_COMPRA = "fechaCompra";
    public static final String TABLE_FACTURAS_FECHA_CAPTURA = "fechaCaptura";
    public static final String TABLE_FACTURAS_PATH = "path";
    public static final String TABLE_FACTURAS_NAME = "name";
    public static final String TABLE_FACTURAS_NIT = "nit";
    //public static final String TABLE_FACTURAS_COMERCIO_ID = "comercio"; //FK
    public static final String TABLE_FACTURAS_USUARIO_ID = "usuario"; //FK
    public static final String TABLE_FACTURAS_IMPUESTO_TIPO = "impuestoTipo";
    public static final String TABLE_FACTURAS_IMPUESTO_VALOR = "impuestoValor";
    public static final String TABLE_FACTURAS_VALOR = "valor";

    public static final String TABLE_USUARIO = "usuario";
    public static final String TABLE_USUARIO_NAME = "name";
    public static final String TABLE_USUARIO_ID = "id_usuario";
    public static final String TABLE_USUARIO_MAIL = "mail";
    public static final String TABLE_USUARIO_PASSWORD = "password";


    public static final String TABLE_COMERCIOS = "comercio";
    //public static final String TABLE_COMERCIOS_ID = "id_comercio";
    public static final String TABLE_COMERCIOS_NIT = "nit";
    public static final String TABLE_COMERCIOS_NOMBRE = "nombre";
    public static final String TABLE_COMERCIOS_NOMBRE_LEGAL = "nombre_legal";
    public static final String TABLE_COMERCIOS_LOCATION = "location";

    // TODO: 14/09/2016 TABLE REGISTROS_USUARIOS



    
}
