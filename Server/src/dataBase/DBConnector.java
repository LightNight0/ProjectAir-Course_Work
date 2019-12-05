package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    private final String USERNAME = "root";
    private final String PASSWORD = "1234";
    private final String CONNECTION_URL = "jdbc:mysql://localhost:3306/Project_Air";

    private static volatile DBConnector instance;
    private Connection connection;

    private DBConnector() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try{
            connection = DriverManager.getConnection(CONNECTION_URL,USERNAME,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DBConnector getInstance() {
        if (instance == null) {
            synchronized (DBConnector.class) {
                if (instance == null) {
                    instance = new DBConnector();
                }
            }
        }
        return instance;
    }

    public Connection getConnection(){
        return connection;
    }

    public void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
