package yosumo.src.activity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import yosumo.src.db.ManagerDB;
import yosumo.src.logic.Usuario;


public class Client extends AsyncTask<Void, Void, Void> {

    Usuario usuario;
    ManagerDB db;

    String dstAddress;
    int dstPort;
    String tag;
    String response = "";
//    String post = "";
    //   TextView textResponse;

    BufferedReader input;
    PrintWriter out;
    Socket socket = null;


    @Override
    protected void onPreExecute() {

    }

    Client(String addr, int port, String tag, Usuario usuario, ManagerDB db) {
        dstAddress = addr;
        dstPort = port;
        this.tag = tag;

        this.usuario = usuario;
        this.db = db;

    }

    @Override
    protected Void doInBackground(Void... arg0) {
        try {
            String resultado = "";
            socket = new Socket(dstAddress, dstPort);
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));


            if (tag.equalsIgnoreCase("update:comercio")
                    || tag.equalsIgnoreCase("update:denuncia")
                    || tag.equalsIgnoreCase("update:factura")
                    || tag.equalsIgnoreCase("insert:denuncia")) {

                out.println("INIT:" + usuario.getUsuario());
            } else {
                Log.d("Not find tag socket" , tag);
                return null; // Para el socket
            }

            boolean rta = false;
            while (true) {
                try {
                    response = input.readLine();
                    Log.d("Response", response);

                    if (response.equalsIgnoreCase("INIT:SERVER") && !rta) { // Estado server:OK esta bien y continuo
                        if (tag.equalsIgnoreCase("update:denuncia")) {
                            out.println("UPDATE:DENUNCIA");

                        } else if (tag.equalsIgnoreCase("update:comercio")) {
                            out.println("UPDATE:COMERCIO");

                        } else if (tag.equalsIgnoreCase("update:factura")) {
                            out.println("UPDATE:FACTURA");

                        } else if (tag.equalsIgnoreCase("publicar:denuncia")) {
                            out.println("TOUPDATE:" + db.getDenunciasPendientesToString());
                            out.println("TOUPDATE:END");
                            break;
                        }
                        rta = true;
                    }

                    if (response.split(":")[0].equalsIgnoreCase("TOUPDATE") && rta) {
                        if (tag.equalsIgnoreCase("update:denuncia")) {
                            // db.insertDenunciaBulk(response);

                        } else if (tag.equalsIgnoreCase("update:comercio")) {
                            db.insertComercioBulk(response);

                        } else if (tag.equalsIgnoreCase("update:factura")) {
                            //out.println("UPDATE:FACTURA");

                        } else {
                            System.out.println("Not find tag to udpate socket");
                        }
                    }

                } catch (Exception a) {
                    System.out.println("Error: " + a.getMessage());
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        Log.d("Response", response);
        //textResponse.setText(textResponse.getText() + response);
        super.onPostExecute(result);
    }

}