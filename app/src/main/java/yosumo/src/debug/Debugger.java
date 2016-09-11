package yosumo.src.debug;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import yosumo.src.R;

/**
 * Created by a-pol_000 on 9/7/2016.
 * Clase encargada de manejar ciertos debbugers de la aplicación simplificando su llamado
 * MOD 20160909 - AFP - Adición funcionalidad resultados.
 */
public  class Debugger {

    private Context context;
    private static Debugger instance;
    List<String> resultados = new ArrayList<String>();

    /**
     *
     */
    public Debugger (){
        instance = this;
    }

    public Debugger (Context context){
        this.context=context;
    }

    public  void debugConsole(String x){
        if(context.getResources().getBoolean(R.bool.debugger)){
            System.out.println("debuger: "+"["+""+"]"+ " " + x );
        }
    }

    public  void debugConsole(String where,List<String> estados){
        if(context.getResources().getBoolean(R.bool.debugger)){
            Log.d(where, estados.toString());
        }
    }

    public void debugConsole(String where, String message){
        if(context.getResources().getBoolean(R.bool.debugger)){
            System.out.println("debuger: "+"["+where+"]"+ ": " + message );
        }
    }

    public void addResultado(String resultado){
        resultados.add(resultados.size()+"_"+resultado+" ");
    }

    public void showResultado(){
        if(context.getResources().getBoolean(R.bool.debugger)) {
            System.out.println("debuger_Resultados: " + resultados.toString());
        }
    }
    /**
     *
     * @return
     */
    public Context getContext() {
        return context;
    }

    /**
     *
     * @return
     */
    public static Debugger getInstance() {
        return instance;
    }

    /**
     *
     * @return
     */
    public List<String> getResultados() {
        return resultados;
    }

    /**
     *
     */
    public void clearResultados() {
        this.resultados.clear();
    }
}
