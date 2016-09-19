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
    double contadorImpuestos;
    String nombre;

    String mail;


    String password;

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


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public double getContadorImpuestos() {
        return contadorImpuestos;
    }

    public void setContadorImpuestos(double contadorImpuestos) {
        this.contadorImpuestos = contadorImpuestos;
    }

}
