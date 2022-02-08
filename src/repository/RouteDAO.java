package repository;
import entity.Route;
import entity.Route;
import utils.database_connection.OracleDBConnection;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class RouteDAO implements DataAccessible<Route,Integer>{

    private final String TABLE_NAME="route";
    @Override
    public boolean save(Route route) {
        String saveSql = "INSERT INTO " + TABLE_NAME + " ( distance,total_bus_stop) VALUES ( ?, ?)";
        String updateSql = "UPDATE " + TABLE_NAME + " SET distance=?, total_bus_stop=?  WHERE id=?";
        boolean checkOk=false;
        boolean checkExit=false;
        ArrayList<Route> listDepartment= findAll();
        for(Route d:listDepartment) {
            if (d.getId()==route.getId()) {
                checkExit=true;
            }
        }
        if(checkExit) {
            try { Connection conn= OracleDBConnection.getConnection();
                PreparedStatement statement;
                statement = conn.prepareStatement(updateSql);
                statement.setDouble(1, route.getDistance());
                statement.setInt(2, route.getTotalBusStop());
                statement.setInt(3, route.getId());
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    checkOk=true;
                }
                conn.close();

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally{
                OracleDBConnection.closeConnection();
            }
        }else {
            try {Connection conn= OracleDBConnection.getConnection();
                PreparedStatement statement;
                statement = conn.prepareStatement(saveSql);
                statement.setDouble(1, route.getDistance());
                statement.setInt(2, route.getTotalBusStop());
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    checkOk=true;
                }
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally{
                OracleDBConnection.closeConnection();
            }
        }

        return checkOk;
    }

    @Override
    public boolean deleteAll() {
        return false;
    }

    @Override
    public ArrayList<Route> findAll() {
        Connection conn= OracleDBConnection.getConnection();
        ArrayList<Route> listRoute=new ArrayList<>();
        String sql = "SELECT * FROM "+TABLE_NAME ;
        try {
            Statement statement;
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()){
                int id=result.getInt(1);
                double distance = result.getDouble(2);
                int totalBusStop = result.getInt(3);
                Route route=new Route(id,distance,totalBusStop);
                listRoute.add(route);
            }
            conn.close();
        } catch ( SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            OracleDBConnection.closeConnection();
        }
        return listRoute;
    }

    @Override
    public Route findById(Integer id) {
        Connection conn= OracleDBConnection.getConnection();
        String sql = "SELECT * FROM "+TABLE_NAME +" WHERE id=?";
        Route route=new Route(0,0,0);
        try {
            PreparedStatement prepStatement= conn.prepareStatement(sql);
            prepStatement.setInt(1,id);
            ResultSet resultList=prepStatement.executeQuery();
            while (resultList.next()){
                int driverId=resultList.getInt(1);
                Double distance = resultList.getDouble(2);
                int totalBusStop = resultList.getInt(3);
                route=new Route(driverId,distance,totalBusStop);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            OracleDBConnection.closeConnection();
        }
        return route;
    }

}
