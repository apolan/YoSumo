/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.BufferedWriter;

/**
 *
 * @author af.polania212 <af.polania212@uniandes.edu.co>
 */
class ClientServiceThread extends Thread {

    Socket clientSocket;
    int clientID = -1;
    boolean running = true;
    Managerdb db;

    ClientServiceThread(Socket s, int i) {
        clientSocket = s;
        clientID = i;
    }

    void setDb(Managerdb db) {
        this.db = db;
    }

    /**
     *
     */
    public void run() {
        System.out.println("Accepted Client : ID - " + clientID + " : Address - " + clientSocket.getInetAddress().getHostName());
        String username = "";
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);
            while (running) {
                String clientCommand = in.readLine();
                System.out.println("Client Says " + clientCommand);
                if (clientCommand.split("_:_")[0].contains("INIT")) { // Inicia convesacion
                    username = clientCommand.split("_:_")[1];
                    System.out.println(username);
                    out.println("INIT_:_SERVER");
                }

                if (clientCommand.split("_:_")[0].contains("UPDATE")) { // Inicia la accion solicitada
                    String type = clientCommand.split("_:_")[1];
                    System.out.println(type);

                    if (type.equalsIgnoreCase("COMERCIO")) {
                        // Manda los comercios de todos los usarios que estan registrados den el sistema
                        out.println("TOUPDATE_:_" + db.getComercios());
                        clientSocket.close();
                        break;
                    } else if (type.equalsIgnoreCase("DENUNCIA")) {
                        // Manda las denuncias de un usuario
                        out.println("TOUPDATE_:_" + db.getDenuncias(username));
                        clientSocket.close();
                        break;
                    } else if (type.equalsIgnoreCase("FACTURA")) {
                        // Manda las facturas de un usuario
                        out.println("TOUPDATE_:_" + db.getFacturas(username));
                        clientSocket.close();
                        break;
                    } else if (type.equalsIgnoreCase("IMPUESTO")) {
                        // Manda las facturas de un usuario
                        out.println("TOUPDATE_:_" + db.getImpuestos(username));
                        clientSocket.close();
                        break;
                    } else {
                        // out.println("TOUPDATE_:_" + db.getImpuestos(username));
                        System.out.println("Not find question from client: " + type);
                        clientSocket.close();
                        break;
                    }
                }

                if (clientCommand.split("_:_")[0].contains("PUBLIC")) { // Inicia accion de publiar en el servidor
                    String type = clientCommand.split("_:_")[1];
                    System.out.println(type);

                    if (type.equalsIgnoreCase("DENUNCIA")) {
                        // Manda las denuncias de un usuario
                        String denuncia = clientCommand.split("_:_")[2];
                        db.registrarDenuncia(denuncia);
                        clientSocket.close();
                        break;
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("Erro thread:" + username + " " + e.getMessage());
        }
        System.out.println("End socket");
    }
}
