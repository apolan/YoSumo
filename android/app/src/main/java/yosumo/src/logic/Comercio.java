package yosumo.src.logic;

/**
 * Clase que representa la entidad comercio
 * Created by a-pol_000 on 9/10/2016.
 * <20160917 Mod DRM - agregados constructor, y llave entera, getters y setters.
 * <20160918 mod DRM -  NIT cambiado a Integer y usado como la PK Natural Key
 */
public class Comercio {

    public String nit;
    public String nombre_Legal;
    public String nombre_Label;
    public String direccion;
    public String regimen;
    public String estado;
    //No necesarios por ahora
    private double latitud;
    private double altitud;

    public Comercio(){
    }

    public Comercio(String nit){
        this.nit = nit;
        estado = "activo";
    }
}
