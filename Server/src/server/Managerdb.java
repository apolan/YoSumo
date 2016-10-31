/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Date;
import server.entity.Denuncia;
import server.entity.ManagerFormat;

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
     *
     * @return
     */
    public String getComercios() {
        try {
            CallableStatement cStmt = null;
            cStmt = getConnectionJDBC().prepareCall(" select "
                    + " REPLACE(GROUP_CONCAT( "
                    + " cm.nit,'|',cm.nombre,'|',cm.nombre_legal,'|',cm.regimen,'|',cm.direccion,'|',cm.ciudad,'|',cm.estado,'|',cm.dt_creacion,'$'  "
                    + " ),'$,','$') "
                    + " from yosumo.comercio cm "
            );
            // cStmt.setString(1, value);

            ResultSet rs = cStmt.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
            getConnectionJDBC().close();
        } catch (Exception w) {
            System.out.println("Error Asociando " + w.getMessage());
        }
        return "";
    }

    /**
     * Retorna en un string todos los comercios
     *
     * @return
     */
    public String getDenuncias(String username) {
        try {
            CallableStatement cStmt = null;
            cStmt = getConnectionJDBC().prepareCall(" select "
                    + " REPLACE(GROUP_CONCAT( "
                    + " dn.username,'|',dn.nombre_comercio,'|',dn.direccion_comercio,'|',dn.comentario,'|',dn.latitud,'|',dn.longitud,'|',dn.dt_denuncia,'|', dn.dt_creacion,'|',dn.estado,'|','$'  "
                    + " ),'$,','$') "
                    + " from yosumo.denuncia dn "
                    + " where dn.username = ? ); " //???
            );
            cStmt.setString(1, username);
            System.out.println("cStmt: " + cStmt);

            ResultSet rs = cStmt.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
            getConnectionJDBC().close();
        } catch (Exception w) {
            System.out.println("Error Asociando " + w.getMessage());
        }
        return "";
    }

    /**
     * Retorna la lista de amigos a partir de un usuario
     *
     * @param idAmigo
     * @return
     */
    public String getAmigos(int idAmigo) {
        String comercios = "";
        return comercios;
    }

    /**
     * Retorna la lista de amigos a partir de un usuario
     *
     * @param idAmigo
     * @return
     */
    public void registrarDenuncia(String denuncia) {
        System.out.println("denuncia 1: " + denuncia); 
        try {
            CallableStatement stmt = null;
            stmt = getConnectionJDBC().prepareCall(
                    "INSERT INTO yosumo.denuncia "
                    + "(`username`, `nombre_comercio`, `direccion_comercio`, `comentario`, `latitud`, `longitud`, `estado`, `dt_denuncia`, `dt_creacion`  )"
                    + " VALUES "
                    + "( ?, ?, ?, ?, ?, ?, ?, ?,? )"
            //                    + "( 116, 'Carulla', 'cll 116 47a - 06', 'No medieron factura  en el momento de compra', 54.0221620, 54.01620, ('2016-10-25'),'enviado' )"
            );
            stmt.setString(1, denuncia.split("\\|")[0]);
            stmt.setString(2, denuncia.split("\\|")[1]);
            stmt.setString(3, denuncia.split("\\|")[2]);
            stmt.setString(4, denuncia.split("\\|")[3]);
            stmt.setFloat(5, Float.parseFloat(denuncia.split("\\|")[4]));
            stmt.setFloat(6, Float.parseFloat(denuncia.split("\\|")[5]));
            //stmt.setString(7, denuncia.split("\\|")[6]);
            stmt.setString(7, "enviado");
            stmt.setDate(8, new java.sql.Date(ManagerFormat.formatDate(denuncia.split("\\|")[7]).getTime()));
            stmt.setDate(9, new java.sql.Date(ManagerFormat.formatDate(denuncia.split("\\|")[8]).getTime()));

            /*
            System.out.println(""+ Integer.parseInt(denuncia.split("\\|")[0]));
            System.out.println(""+ denuncia.split("\\|")[1]);
            System.out.println(""+ denuncia.split("\\|")[2]);
            System.out.println(""+ denuncia.split("\\|")[3]);
            System.out.println(""+ Float.parseFloat(denuncia.split("\\|")[4]));
            System.out.println(""+ Float.parseFloat(denuncia.split("\\|")[5]));
            System.out.println(""+ denuncia.split("\\|")[6]);
            System.out.println(""+ new java.sql.Date(ManagerFormat.formatDate(denuncia.split("\\|")[7].replaceAll("-", "")).getTime()));
            System.out.println(""+ new java.sql.Date(ManagerFormat.formatDate(denuncia.split("\\|")[8]).getTime()));
            */
            stmt.execute();
        } catch (Exception a) {
            System.out.println("Error: " + a.getMessage() + a.toString());
        } finally {
        }
    }

    public Connection getConnectionJDBC() {
        return conn;
    }

}
