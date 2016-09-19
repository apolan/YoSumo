package yosumo.src.logic;

import java.util.List;

/**
 * Entidad que representa la información de un usuario
 * Created by a-pol_000 on 9/7/2016.
 * Edited <170916> agregados setter y getters
 * atributo id, integer key
 *</170916>
 */
public class Usuario {

    List<FacturaVirtual> listaFacturaVirtuals;
    List<Impuesto> listaImpuestos;

    int id;
    double contadorImpuestos;
    String nombre;

    public Usuario(String nombre)
    {
        this.nombre=nombre;
    }

    public Usuario()
    {

    }

    public void calcularImpuestos(){

    }

    /**
     *
     * @return
     */
    public boolean registrarFactura(FacturaVirtual facturaVirtual){
        boolean resultado=false;

        return resultado;
    }

    /**
     * Metodo que carga la informacion del usuario
     */
    public void cargarInformacionUsuario(){
        //todo En caso que el usurio no se ha registrado en usuario es el "invitado"

        //todo Debe cargar las facturas asociadas
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String toString()
    {
        return this.nombre;
    }

}
