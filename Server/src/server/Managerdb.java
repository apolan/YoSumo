/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.sql.*;
import server.entity.Denuncia;

/**
 *
 * @author af.polania212 <af.polania212@uniandes.edu.co>
 */
public class Managerdb {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String USER = "root";
    static final String PASS = "";

    Connection conn = null;

    /**
     *
     */
    public Managerdb() {
        connectDB();
    }

    /**
     *
     */
    public void connectDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/yosumo", USER, PASS);
            System.out.println("Se establecio la conexion successfully...");
        } catch (Exception e) {
            System.out.println("Error en la base de datos conexion server");
        }
    }

    /**
     * Retorna en un string todos los comercios
     * @return 
     */
    public String getComercios() {
        String comercios = "";
        return comercios;
    }

    /**
     * Retorna la lista de amigos a partir de un usuario
     * @param idAmigo
     * @return 
     */
    public String getAmigos(int idAmigo) {
        String comercios = "";
        return comercios;
    }
    
        /**
     * Retorna la lista de amigos a partir de un usuario
     * @param idAmigo
     * @return 
     */
    public void registrarDenuncia (Denuncia denuncia) {
        String comercios = ""; 
    }
   
}
