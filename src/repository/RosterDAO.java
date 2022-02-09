package repository;

import com.sun.javafx.collections.MappingChange;
import entity.Driver;
import entity.Roster;
import entity.Route;
import main.MainRun;
import utils.database_connection.OracleDBConnection;

import java.io.*;
import java.sql.*;
import java.util.*;

public class RosterDAO implements DataAccessible<Roster,Integer> {
    private File ROSTER_DATA_FILE = new File("RosterData.txt");
    private final String TABLE_NAME= "roster";
    private DriverDAO driverDAO=new DriverDAO();
    private RouteDAO routeDAO=new RouteDAO();
    @Override
    public boolean save(Roster roster) {
        String saveSql = "INSERT INTO " + TABLE_NAME + " ( driver_id,route_id,total_route) VALUES ( ?, ?,?)";
        String updateSql = "UPDATE " + TABLE_NAME + " SET total_route  WHERE driver_id=? and route_id=?";
        boolean checkOk=false;
        boolean checkExit=false;
        int rowsInserted=0;
        ArrayList<Roster> listRoster= findAll();
        for(Roster ros:listRoster) {
            if (ros.getDriver().getId()==roster.getDriver().getId()) {
                checkExit=true;
            }
        }
        
        if(checkExit) {
            try { 
                Connection conn= OracleDBConnection.getConnection();
                PreparedStatement statement;
                statement = conn.prepareStatement(updateSql);
                for (Map.Entry<Route,Integer> entry : roster.getRouteList().entrySet()){
                    statement.setInt(1, entry.getValue());
                    statement.setInt(2, entry.getKey().getId());
                    statement.setInt(3,roster.getDriver().getId());
                     rowsInserted = statement.executeUpdate();
                }
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
            try {
                Connection conn= OracleDBConnection.getConnection();
                PreparedStatement statement;
                statement = conn.prepareStatement(saveSql);
                for (Map.Entry<Route,Integer> entry : roster.getRouteList().entrySet()){
                    statement.setInt(1, roster.getDriver().getId());
                    statement.setInt(2, entry.getKey().getId());
                    statement.setInt(3, entry.getValue());
                    rowsInserted = statement.executeUpdate();
                }
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

    public boolean deleteAll() {
        boolean ok = false;
        try {
            new FileOutputStream(ROSTER_DATA_FILE).close();
            ok=true;}
        catch (EOFException eof) {
            // end of file reached, do nothing
        } catch (FileNotFoundException e) {
            ok = false;
            System.out.println("File not found");
        } catch (IOException e) {
            ok = false;
            System.out.println(e);
            System.out.println("Error initializing stream");
        } finally {
            return ok;
        }
    }

    public ArrayList<Roster> findAll() {

        ArrayList<Driver> driverArrayList=getDriverList();
        ArrayList<Roster> rosterArrayList=new ArrayList<Roster>();
        Connection conn= OracleDBConnection.getConnection();
        String sql = "SELECT * FROM "+TABLE_NAME+" where driver_id= ?" ;
        int driverId=0,routeId=0;
        try {

            for(Driver d:driverArrayList){
                Map<Route,Integer> routeList=new HashMap<Route,Integer>();
                PreparedStatement prepStatement= conn.prepareStatement(sql);
                prepStatement.setInt(1,d.getId());
                ResultSet result=prepStatement.executeQuery();
                while (result.next() ){
                    routeId=result.getInt(2);
                    int totalRoute=result.getInt(3);
                    Route route=new Route(routeId,0.0,0);
                    routeList.put(route,totalRoute);
                    System.out.println("so phan tu: "+routeList.size());
                }
                Roster roster=new Roster(d,routeList);
                rosterArrayList.add(roster);
            }
        } catch ( SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            OracleDBConnection.closeConnection();
        }
        for(Roster ros:rosterArrayList){
             for(Route r:ros.getRouteList().keySet()){
                 r.setDistance( routeDAO.findById(r.getId()).getDistance());
                 r.setTotalBusStop(routeDAO.findById(r.getId()).getTotalBusStop());
             }
        }
     return rosterArrayList ;
    }

     public ArrayList<Driver> getDriverList(){
      Connection conn= OracleDBConnection.getConnection();
      ArrayList<Driver> driverArrayList=new ArrayList<Driver>();
      ArrayList<Integer> driverIdList=new ArrayList<Integer>();
      String sql = "SELECT DISTINCT DRIVER_ID FROM "+TABLE_NAME ;
      try {
          Statement statement;
          statement = conn.createStatement();
          ResultSet result = statement.executeQuery(sql);
          while (result.next()){
              int id=result.getInt(1);
              driverIdList.add(id);
          }
          conn.close();
      } catch ( SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }finally{
          OracleDBConnection.closeConnection();
      }
      for(int i:driverIdList){
          driverArrayList.add(driverDAO.findById(i));
      }
      return driverArrayList;
    }

    public Roster findById(Integer id) {
        ArrayList<Roster> rosterArrayList = findAll();
        Driver driver = new Driver("","","",0,"");
        Route route =new Route(0,0,0);
        int totalRoute=0;
        Map<Route,Integer> routeList= new HashMap<Route,Integer>();
        routeList.put(route,totalRoute);
        Roster roster = new Roster(driver,routeList);
        for (Roster d : rosterArrayList) {
            if (d.getDriver().getId()==id) {
                roster = d;
                break;
            }
        }
        return roster;
    }

}

