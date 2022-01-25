package repository;

import entity.Driver;
import entity.Roster;
import entity.Route;

import java.io.*;
import java.util.*;

public class RosterDAO implements DataAccessible<Roster,Integer> {
    private File ROSTER_DATA_FILE = new File("RosterData.txt");

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
            } finally {
                return checkSave;
            }

        }
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

    public ArrayList<Roster> getAll() {
        ArrayList<Roster> rosterArrayList = new ArrayList();
        if(ROSTER_DATA_FILE.length()!=0){
            try {
                FileInputStream fi = new FileInputStream(ROSTER_DATA_FILE);
                ObjectInputStream oi = new ObjectInputStream(fi);
                // Read objects
                ArrayList<Roster> fileRosterArrayList = (ArrayList<Roster>) oi.readObject();
                for (Roster roster : fileRosterArrayList) {
                    rosterArrayList.add(roster);
                }
                oi.close();
                fi.close();
            }
            catch (FileNotFoundException e) {
                System.out.println("File not found");
            } catch (EOFException e) {
            } catch (IOException e) {
                System.out.println("Error initializing stream");
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                return rosterArrayList;
            }

        }else {return rosterArrayList;}
    }

    public Roster findById(Integer id) {
        ArrayList<Roster> rosterArrayList = getAll();
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
