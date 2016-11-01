/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

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
        Managerdb db = new Managerdb();
       
        //db.testQuery("all");
                
        while (true) {
            Socket clientSocket = m_ServerSocket.accept();
            ClientServiceThread cliThread = new ClientServiceThread(clientSocket, id++);
            cliThread.setDb(db);
            cliThread.start();
        }
    }
}
