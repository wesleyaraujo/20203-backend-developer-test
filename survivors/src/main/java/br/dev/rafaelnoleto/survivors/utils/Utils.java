package br.dev.rafaelnoleto.survivors.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.LinkedHashMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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

    public static Response responseError(List<String> errors) {
        LinkedHashMap<String, Object> responseError = new LinkedHashMap();
        responseError.put("errors", errors);

        return Utils.response(responseError, Status.BAD_REQUEST);
    }

    public static Response response(LinkedHashMap<String, Object> data) {
        return Utils.response(data, Status.OK);
    }
    
    public static Response response(List<LinkedHashMap<String, Object>> data) {
        return Utils.response(data, Status.OK);
    }
    
    public static Response response(LinkedHashMap<String, Object> data, Status status) {
        LinkedHashMap<String, Object> response = new LinkedHashMap<>();
        response.put("data", data);

        return Response.status(status).entity(response).build();
    }
    
    public static Response response(List<LinkedHashMap<String, Object>> data, Status status) {
        LinkedHashMap<String, Object> response = new LinkedHashMap();
        response.put("data", data);

        return Response.status(status).entity(response).build();
    }

    public static LinkedHashMap<String, Object> parseIdResponse(Integer id) {
        return new LinkedHashMap() {
            {
                put("id", id);
            }
        };
    }

    public static Integer parseInt(Object value) {
        Integer x;

        try {
            x = Integer.parseInt(value.toString());
        } catch (Exception e) {
            x = null;
        }

        return x;
    }
    
    public static Double parseDouble(Object value) {
        Double x;

        try {
            x = Double.parseDouble(value.toString());
        } catch (Exception e) {
            x = null;
        }

        return x;
    }

}
