package yosumo.src.logic;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by a-pol_000 on 9/7/2016.
 * Entidad encargada de manejar la lógica de factura
 * <170916 Editado por DM>
 *     agregados getters and setters
 *     insertado el formato de las fechas
 *     agegados atributos tipo_impuesto y valor_impuesto
 * </170916>
 *
 */
public class Factura {


    public static final String DATE_FORMAT = "h:mm a dd-MM-yy";

    private int id;
    private Date fechaCaptura;
    private Date fechaCompra;
    private String path ;
    private String nombre;

    private String nit;

    //<20120914> David M: cambia de Sring a Comercio, el cual tiene NIT
    private Comercio lugar;
    private Usuario contribuyente;

    private String tipoImpuesto;
    private double valorImpuesto;

    public Factura (){
    }

    public Date getFechaCaptura() {
        return fechaCaptura;
    }

    public String getStringFechaCaptura() {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        return df.format(fechaCaptura);
    }


    public void setFechaCaptura(String sFechaCaptura) {
        SimpleDateFormat dateformat = new SimpleDateFormat(DATE_FORMAT);
        try {
            Date dateFechaCaptura = dateformat.parse(sFechaCaptura);
            this.fechaCaptura = dateFechaCaptura;
        } catch (ParseException e) {
            System.out.println("Error del Date Parser en setFechaCaptura");
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public String getStringFechaCompra() {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        return df.format(fechaCompra);
    }

    public void setFechaCompra(String sFechaCompra) {
        SimpleDateFormat dateformat = new SimpleDateFormat(DATE_FORMAT);
        try {
            Date dateFechaCompra = dateformat.parse(sFechaCompra);
            this.fechaCompra = dateFechaCompra;
        } catch (ParseException e) {
            System.out.println("Error del Date Parser en setFechaCompra");
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String name) {
        this.nombre = name;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public Comercio getLugar() {
        return lugar;
    }

    public void setLugar(Comercio lugar) {
        this.lugar = lugar;
    }

    public Usuario getContribuyente() {
        return contribuyente;
    }

    public void setContribuyente(Usuario contribuyente) {
        this.contribuyente = contribuyente;
    }

    public String getTipoImpuesto() {
        return tipoImpuesto;
    }

    public void setTipoImpuesto(String tipoImpuesto) {
        this.tipoImpuesto = tipoImpuesto;
    }

    public double getValorImpuesto() {
        return valorImpuesto;
    }

    public void setValorImpuesto(double valorImpuesto) {
        this.valorImpuesto = valorImpuesto;
    }

    public String toString()
    {
        return nombre+"-"+getStringFechaCompra();
    }
}
