package yosumo.src.logic;

import java.util.List;

/**
 * Entidad que representa la información de un usuario
 * Created by a-pol_000 on 9/7/2016.
 */
public class Usuario {

    List<Factura> listaFacturas;
    List<Impuesto> listaImpuestos;

    double contadorImpuestos;
    String nombre;

    public Usuario(String nombre){
        this.nombre=nombre;
    }

    public void calcularImpuestos(){

    }

    /**
     *
     * @return
     */
    public boolean registrarFactura(Factura factura){
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

}
