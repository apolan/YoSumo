package yosumo.src.debug;

import android.content.Context;
import android.content.res.Resources;

import yosumo.src.R;

/**
 * Created by a-pol_000 on 9/7/2016.
 * Clase encargada de manejar ciertos debbugers de la aplicación simplificando su llamado
 */
public class Debugger {

    private Context context;
    private Debugger instance;

    /**
     *
     */
    public Debugger (){
        instance = this;
    }

    public Debugger (Context context){
        this.context=context;
    }

    public void debugConsole(String x){
        if(context.getResources().getBoolean(R.bool.debugger)){
            System.out.println("debuger: "+"["+""+"]"+ " " + x );
        }
    }

    public void debugConsole(String where, String message){
        if(context.getResources().getBoolean(R.bool.debugger)){
            System.out.println("debuger: "+"["+where+"]"+ ": " + message );
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
    public Debugger getInstance() {
        return instance;
    }
}
