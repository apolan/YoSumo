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
import java.net.ServerSocket;
import java.net.Socket;
/**
 *
 * @author af.polania212 <af.polania212@uniandes.edu.co>
 */
class ClientServiceThread extends Thread {

    Socket clientSocket;
    int clientID = -1;
    boolean running = true;

    ClientServiceThread(Socket s, int i) {
        clientSocket = s;
        clientID = i;
    }

    public void run() {
        System.out.println("Accepted Client : ID - " + clientID + " : Address - " + clientSocket.getInetAddress().getHostName());
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            while (running) {
                String clientCommand = in.readLine();
                System.out.println("Client Says :" + clientCommand);
                if (clientCommand.equalsIgnoreCase("hola..")) {
                    System.out.println("entra"); 
                    out.println("q mas");
                    
                }

                if (clientCommand.equalsIgnoreCase("quit")) {
                    running = false;
                    System.out.print("Stopping client thread for client : " + clientID);
                } else {
                    out.println(clientCommand);
                    out.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}