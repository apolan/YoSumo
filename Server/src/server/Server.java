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
public class Server {

    public static void main(String[] args) throws Exception {
        ServerSocket m_ServerSocket = new ServerSocket(12111);
        int id = 0;
        
        Managerdb mn = new Managerdb();
                
        while (true) {
            Socket clientSocket = m_ServerSocket.accept();
            ClientServiceThread cliThread = new ClientServiceThread(clientSocket, id++);
            cliThread.start();
        }
    }
    
}
