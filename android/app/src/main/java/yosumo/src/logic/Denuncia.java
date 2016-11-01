package yosumo.src.logic;

import android.util.Log;

import java.util.Date;

import yosumo.src.commons.ManagerFormat;

/**
 * Created by a-pol_000 on 10/29/2016.
 */
public class Denuncia {
    int id;
   // int fk_usuario;
    String username;
    String nombre_comercio;
    String direccion_comercio;
    String comentario;
    double latitud;
    double longitud;
    public Date fechaDenuncia;
    public Date fechaCaptura;
    String estado;

    public Denuncia(String username, String nombre_comercio, String direccion_comercio, String comentario, double latitud, double longitud) {
        //this.fk_usuario = fk_usuario;
        this.username = username;
        this.nombre_comercio = nombre_comercio;
        this.direccion_comercio = direccion_comercio;
        this.comentario = comentario;
        this.fechaDenuncia = new Date();
        this.fechaCaptura = new Date();
        this.estado = "pendiente";
        this.latitud = latitud;
        this.longitud = longitud;
    }

    /**
     * La de la creacion nueva desde la aplicaicon
     * @param nombre_comercio
     * @param comentario
     * @param latitud
     * @param longitud
     */
    public Denuncia(String username, String nombre_comercio,  String comentario, double latitud, double longitud) {
        this.username = username;
        this.nombre_comercio = nombre_comercio;
        this.direccion_comercio = direccion_comercio;
        this.comentario = comentario;
        this.fechaDenuncia = new Date();
        this.fechaCaptura = new Date();
        this.estado = "pendiente";
        this.latitud = latitud;
        this.longitud = longitud;
    }

    /**
     * Es el metodo que llama la base de datos para represetntar una denunicai en el sisemte
     *
     * @param username
     * @param nombre_comercio
     * @param direccion_comercio
     * @param comentario
     * @param latitud
     * @param longitud
     * @param estado
     * @param fechaDenuncia
     * @param fechaCaptura
     */
    public Denuncia(String username, String nombre_comercio, String direccion_comercio, String comentario, double latitud, double longitud, String estado, Date fechaDenuncia, Date fechaCaptura) {
        this.username = username;
        this.nombre_comercio = nombre_comercio;
        this.direccion_comercio = direccion_comercio;
        this.comentario = comentario;
        this.fechaDenuncia = fechaDenuncia;
        this.fechaCaptura = fechaCaptura;
        this.estado = estado;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String toSocket() {
        String str = username+"|"+nombre_comercio+"|"+direccion_comercio+"|"+comentario+"|"+latitud+"|"+longitud+"|"+estado+"|"+ ManagerFormat.formatTimestamp(fechaDenuncia)+"|"+ManagerFormat.formatTimestamp(fechaCaptura);
        Log.d("to socket: ", str);
        return str;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }


    public String getNombre_comercio() {
        return nombre_comercio;
    }

    public void setNombre_comercio(String nombre_comercio) {
        this.nombre_comercio = nombre_comercio;
    }

    public String getDireccion_comercio() {
        return direccion_comercio;
    }

    public void setDireccion_comercio(String direccion_comercio) {
        this.direccion_comercio = direccion_comercio;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFechaDenuncia() {
        return fechaDenuncia;
    }

    public void setFechaDenunia(Date fechaDenuncia) {
        this.fechaDenuncia = fechaDenuncia;
    }

    public Date getFechaCaptura() {
        return fechaCaptura;
    }

    public void setFechaCaptura(Date fechaCaptura) {
        this.fechaCaptura = fechaCaptura;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setFechaDenuncia(Date fechaDenuncia) {
        this.fechaDenuncia = fechaDenuncia;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }


    public String toString(){
        return "Lugar: "+ nombre_comercio  + "  : " + ManagerFormat.formatDate(fechaDenuncia);
    }
}
