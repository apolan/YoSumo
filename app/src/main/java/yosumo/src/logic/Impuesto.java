package yosumo.src.logic;

/**
 * Created by a-pol_000 on 9/7/2016.
 * 20161709 - Modified by DRM - agregados getters y seters
 */
public class Impuesto {

    private double valor;
    private String tipo;

    public Impuesto(){

    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
