package yosumo.src.logic;

import java.util.List;

/**
 * Entidad que representa la información de un usuario
 * Created by a-pol_000 on 9/7/2016.
 * <170916> agregados setter y getters
 * atributo id, integer key
 *<180916 - DM> Agregados atributos mail y contraseña
 *
 */
public class Usuario {

    List<FacturaVirtual> listaFacturaVirtuals;
    List<Impuesto> listaImpuestos;

    int id;
    String usuario;
    String nombre;
    String mail;
    String password;


    public Usuario(String usuario, String nombre, String mail, String password) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.mail = mail;
        this.password = password;
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

    public String getUsuario() {
        return usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String toString(){
        return "Nombre: "+nombre+ " Usuario: "+usuario + " Pass: "+password;
    }
}
