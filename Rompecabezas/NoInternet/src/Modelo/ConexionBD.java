/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author demon
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    
    /*
    Connection es la que se encarga de solo y unicamente conectarse 
    al servidor de la BD
    Statement es el que se encarga de realizar las sentencias de sql, 
    LDD, y LMD
    ResultSet este objeto es unico y esclusivamente para poder realizar
    las consultas a las tablas
    
    */
    
    private static final String URL = "jdbc:mysql://localhost:3306/rompecabezas?serverTimezone=America/Mexico_City";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "Iv4nP4tl4n";
    
    public static Connection getConexion() throws Exception{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            
        }catch(ClassNotFoundException e){
            throw new SQLException("Driver no encontrado");
        }
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }   
    
    public static boolean probarConexion() throws Exception{
        try{
            Connection con = getConexion();
            System.out.println("Si se conecto");
            return con != null && !con.isClosed();
            
        }catch(SQLException e){
            System.out.println("Error de conexion : "+ e.getMessage());
            return false;
        }
    }
}
