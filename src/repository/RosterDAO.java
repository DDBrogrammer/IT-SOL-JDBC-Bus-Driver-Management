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
        boolean checkSave = false;
        ArrayList<Roster> newRosterArrayList = new ArrayList();
        int driverId=roster.getDriver().getId();
        if( ROSTER_DATA_FILE.length()!=0 ) {
            try {
                FileInputStream fi = new FileInputStream(ROSTER_DATA_FILE);
                ObjectInputStream oi = new ObjectInputStream(fi);
                // Read objects
                ArrayList<Roster> fileRosterArrayList = (ArrayList<Roster>) oi.readObject();
                /*System.out.println("run");*/
                if(findById(driverId).getDriver().getId()==roster.getDriver().getId()){
                   /* System.out.println("Run-2");*/
                    for(Roster ros:fileRosterArrayList){
                        if(ros.getDriver().getId()==roster.getDriver().getId()){
                            ros.setRouteList(roster.getRouteList());
                            ros.setDriver(roster.getDriver());
                        }
                    }
                }else {
                   /* System.out.println("Run-3");*/
                    fileRosterArrayList.add(roster);
                }
                oi.close();
                fi.close();
                deleteAll();
                FileOutputStream f = new FileOutputStream(ROSTER_DATA_FILE);
                ObjectOutputStream o = new ObjectOutputStream(f);
                o.writeObject(fileRosterArrayList);
                o.flush();
                o.close();
                checkSave = true;
            } catch (EOFException eof) {
                // end of file reached, do nothing
            } catch (FileNotFoundException e) {
                checkSave = false;
                System.out.println("File not found");
            } catch (IOException e) {
                checkSave = false;
                System.out.println(e);
                System.out.println("Error initializing stream");
            } finally {
                return checkSave;
            }

        }else {
            try {
                FileOutputStream f = new FileOutputStream(ROSTER_DATA_FILE);
                ObjectOutputStream o = new ObjectOutputStream(f);
                newRosterArrayList.add(roster);
                o.writeObject(newRosterArrayList);
                o.flush();
                o.close();
                checkSave= true;
            } catch (EOFException eof) {
                // end of file reached, do nothing
            } catch (FileNotFoundException e) {
                checkSave = false;
                System.out.println("File not found");
            } catch (IOException e) {
                checkSave = false;
                System.out.println(e);
                System.out.println("Error initializing stream");
            }
        }
        return checkSave;
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
        int tempId=-1,totalRoute=0;boolean checkFist=true;
        Route route=new Route(0,0.0,0);
        ArrayList<Driver> driverArrayList=getDriverList();
        ArrayList<Roster> rosterArrayList=new ArrayList<Roster>();
        Connection conn= OracleDBConnection.getConnection();
        String sql = "SELECT * FROM "+TABLE_NAME+" where driver_id= ?" ;

        try {
            for(Driver d:driverArrayList){
                Map<Route,Integer> routeList=new HashMap<Route,Integer>();
                PreparedStatement prepStatement= conn.prepareStatement(sql);
                prepStatement.setInt(1,d.getId());
                ResultSet result=prepStatement.executeQuery();
                int driverId=0,routeId=0;
                while (result.next() ){
                    driverId=result.getInt(1);
                    routeId=result.getInt(2);
                    totalRoute=result.getInt(3);
                    route.setId(routeId);
                    routeList.put(route,totalRoute);
                    Roster roster=new Roster(d,routeList);
                    rosterArrayList.add(roster);
                }
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

