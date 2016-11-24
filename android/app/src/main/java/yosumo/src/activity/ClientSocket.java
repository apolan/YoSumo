package yosumo.src.activity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import android.os.AsyncTask;
import android.util.Log;
import yosumo.src.db.ManagerDB;
import yosumo.src.logic.Usuario;

/**
 * Clase encargada de manejar la conexion con el servidor a traves de sockets
 * MOD 20161030 - AFP - INIT asincronica para abrir socket
 */
public class ClientSocket extends AsyncTask<Void, Void, Void> {

    Usuario usuario;
    ManagerDB db;

    String denuncia;

    String dstAddress;
    int dstPort;
    String tag;
    String response = "";

    BufferedReader input;
    PrintWriter out;
    Socket socket = null;

    void setDenuncia(String denuncia){
        this.denuncia = denuncia;
    }

    @Override
    protected void onPreExecute() {

    }

    ClientSocket(String addr, int port, String tag, Usuario usuario, ManagerDB db) {
        dstAddress = addr;
        dstPort = port;
        this.tag = tag;

        this.usuario = usuario;
        this.db = db;
        //Log.d("Listening", " ");
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        try {
           // Log.d("Listening 2", " ");
            String resultado = "";
            socket = new Socket(dstAddress, dstPort);
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));


            if (tag.equalsIgnoreCase("update:comercio")
                    || tag.equalsIgnoreCase("update:denuncia")
                    || tag.equalsIgnoreCase("update:factura")
                    || tag.equalsIgnoreCase("update:impuesto")
                    || tag.equalsIgnoreCase("public:denuncia")) {

                out.println("INIT_:_" + usuario.getUsuario());  // INICIO DE CONVERSACION
            } else {
                Log.d("Not find tag socket", tag);
                return null; // Para el socket
            }

            boolean rta = false;
            while (true) {
                try {
                    //Log.d("Listening 3", " ");
                    response = input.readLine();
                    Log.d("Response", response);

                    if (response.equalsIgnoreCase("INIT_:_SERVER") && !rta) { // Estado server:OK  Respuesta servidor
                        if (tag.equalsIgnoreCase("public:denuncia")) {
                            out.println("PUBLIC_:_DENUNCIA_:_" + denuncia);
                            out.println("PUBLIC_:_END");
                            break;

                        }else if (tag.equalsIgnoreCase("update:comercio")) {
                            out.println("UPDATE_:_COMERCIO"); // Solicita comercios al servidor
                        }else if (tag.equalsIgnoreCase("update:denuncia")) {
                            out.println("UPDATE_:_DENUNCIA"); // Solicita denuncias al servidor
                        }else if (tag.equalsIgnoreCase("update:factura")) {
                            out.println("UPDATE_:_FACTURA"); // Solicita facuras al servidor
                        }else if (tag.equalsIgnoreCase("update:impuesto")) {
                            out.println("UPDATE_:_IMPUESTO"); // Solicita facuras al servidor
                        }
                        rta = true;
                    }

                    if (response.split("_:_")[0].equalsIgnoreCase("TOUPDATE") && rta) {
                        if (tag.equalsIgnoreCase("update:denuncia")) {
                            db.insertDenunciaBulk(response);
                            break;

                        } else if (tag.equalsIgnoreCase("update:comercio")) {
                            db.insertComercioBulk(response);
                            break;

                        } else if (tag.equalsIgnoreCase("update:factura")) {
                            db.insertFacturaBulk(response);
                            break;
                        } else if (tag.equalsIgnoreCase("update:impuesto")) {
                            db.insertImpuestoBulk(response);
                            break;

                        } else {
                            System.out.println("Not find tag to udpate socket");
                        }
                    }

                } catch (Exception a) {
                    System.out.println("Error: " + a.getMessage());
                    break;
                } finally {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                    Log.d("Cierr del socket ", " ");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        //textResponse.setText(textResponse.getText() + response);
        super.onPostExecute(result);
    }

}