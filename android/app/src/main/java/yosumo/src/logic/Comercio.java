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

    public Comercio(String nit, String nombre_Label){
        this.nit = nit;
        this.nombre_Label = nombre_Label;
        estado = "activo";
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombre_Legal() {
        return nombre_Legal;
    }

    public void setNombre_Legal(String nombre_Legal) {
        this.nombre_Legal = nombre_Legal;
    }

    public String getNombre_Label() {
        return nombre_Label;
    }

    public void setNombre_Label(String nombre_Label) {
        this.nombre_Label = nombre_Label;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRegimen() {
        return regimen;
    }

    public void setRegimen(String regimen) {
        this.regimen = regimen;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
}
