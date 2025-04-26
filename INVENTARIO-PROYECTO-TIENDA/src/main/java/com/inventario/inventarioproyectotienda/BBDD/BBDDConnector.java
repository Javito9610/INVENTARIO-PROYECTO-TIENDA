package com.inventario.inventarioproyectotienda.BBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class BBDDConnector {
    private static BBDDConnector instance=null;
    private static final String JDBC_URL="jdbc:mysql://localhost:3306/scriptTienda?autoReconnect=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "Javitolb1996";

    public BBDDConnector(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public static BBDDConnector getInstance(){
        if(instance==null){
            instance = new BBDDConnector();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        Properties prop = new Properties();
        prop.put("user", USER);
        prop.put("password", PASSWORD);
        return DriverManager.getConnection(JDBC_URL, prop);
    }
}