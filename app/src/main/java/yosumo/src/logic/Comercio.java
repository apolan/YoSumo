package yosumo.src.logic;

/**
 * Clase que representa la entidad comercio
 * Created by a-pol_000 on 9/10/2016.
 * <20160917 Modified by DRM - agregados constructor, y llave entera, getters y setters.
 */
public class Comercio {

    private int id;
    private String nit;
    private String nombre_Legal;
    private String label_nombre;

    //No necesarios por ahora
    private double latitud;
    private double altitud;

    public Comercio()
    {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNIT() {
        return nit;
    }

    public void setNIT(String NIT) {
        this.nit = NIT;
    }

    public String getNombre_Legal() {
        return nombre_Legal;
    }

    public void setNombre_Legal(String nombre_Legal) {
        this.nombre_Legal = nombre_Legal;
    }

    public String getLabel_nombre() {
        return label_nombre;
    }

    public void setLabel_nombre(String label_nombre) {
        this.label_nombre = label_nombre;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getAltitud() {
        return altitud;
    }

    public void setAltitud(double altitud) {
        this.altitud = altitud;
    }

    public String toString()
    {
        return label_nombre +"-"+nit;
    }
}
