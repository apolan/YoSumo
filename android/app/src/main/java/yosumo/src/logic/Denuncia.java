package yosumo.src.logic;

import java.util.Date;

/**
 * Created by a-pol_000 on 10/29/2016.
 */
public class Denuncia {
    int id;
    int fk_usuario;
    String nombre_comercio;
    String direccion_comercio;
    String comentario;
    public Date fechaDenuncia;
    public Date fechaCaptura;
    String estado;

    public Denuncia(int fk_usuario, String nombre_comercio, String direccion_comercio, String comentario) {
        this.fk_usuario = fk_usuario;
        this.nombre_comercio = nombre_comercio;
        this.direccion_comercio = direccion_comercio;
        this.comentario = comentario;
        this.fechaDenuncia = new Date();
        this.fechaCaptura = new Date();
        this.estado = "pendiente";
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFk_usuario() {
        return fk_usuario;
    }

    public void setFk_usuario(int fk_usuario) {
        this.fk_usuario = fk_usuario;
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
}
