package br.dev.rafaelnoleto.survivors.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author rafaelnoleto
 */
public class Utils {

    public static Connection getConnection() {
        try {
            String url = "jdbc:postgresql://localhost:5432/cev";
            String username = "postgres";
            String password = "cev2020";
            
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            return null;
        }
    }
    
}
