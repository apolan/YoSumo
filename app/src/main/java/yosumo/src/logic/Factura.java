package yosumo.src.logic;
import java.util.Date;
import java.util.List;

/**
 * Created by a-pol_000 on 9/7/2016.
 * Entidad encargada de manejar la lógica de factura
 */
public class Factura {

    private Date fechaCaptura;
    private Date fechaCompra;
    private String path ;
    private String name;
    private int NIT;
    private String lugar;

    private List<Impuesto> listaImpuestos;

    public Factura (){
    }
}
