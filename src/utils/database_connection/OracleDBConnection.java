package utils.database_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleDBConnection {
    private static Connection con= null;
    private static final String URL="jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER_NAME="driver";
    private static final String PASSWORD="dai24032001";
    public static Connection getConnection() {
        try {
            if(con==null|| con.isClosed())
                openConnection();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } return con;
    }
    private static synchronized void openConnection() {
        try {
            con= DriverManager.getConnection(URL, USER_NAME, PASSWORD);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        if(con!=null) {
            try {
                con.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }



}
