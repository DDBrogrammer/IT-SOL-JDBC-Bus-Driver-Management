package repository;
import entity.Route;

import java.io.*;
import java.util.ArrayList;

public class RouteDAO implements DataAccessible<Route,Integer>{

    private File ROUTE_DATA_FILE = new File("RouteData.txt");
    public boolean save(Route route) {
        boolean ok = false;
        ArrayList<Route> newRouteArrayList = new ArrayList();

        if( ROUTE_DATA_FILE.length()!=0 ) {
            try {
                FileInputStream fi = new FileInputStream(ROUTE_DATA_FILE);
                ObjectInputStream oi = new ObjectInputStream(fi);
                // Read objects
                ArrayList<Route> oldRouteArrayList = (ArrayList<Route>) oi.readObject();
                for (Route oldRoute : oldRouteArrayList) {
                    newRouteArrayList.add(oldRoute);
                }
                oi.close();
                fi.close();
                deleteAll();
                FileOutputStream f = new FileOutputStream(ROUTE_DATA_FILE);
                ObjectOutputStream o = new ObjectOutputStream(f);
                route.setId(newRouteArrayList.size()+100);
                newRouteArrayList.add(route);
                o.writeObject(newRouteArrayList);
                o.flush();
                o.close();
                ok = true;
            } catch (EOFException eof) {
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

        }else {
            try {
                FileOutputStream f = new FileOutputStream(ROUTE_DATA_FILE);
                ObjectOutputStream o = new ObjectOutputStream(f);
                route.setId(100);
                newRouteArrayList.add(route);
                o.writeObject(newRouteArrayList);
                o.flush();
                o.close();
                ok = true;

            } catch (EOFException eof) {
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

    }

    public boolean deleteAll() {
        boolean ok = false;
        try {
            new FileOutputStream(ROUTE_DATA_FILE).close();
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

    public ArrayList<Route> getAll() {
        ArrayList<Route> routeArrayList = new ArrayList();
        if(ROUTE_DATA_FILE.length()!=0){
            try {
                FileInputStream fi = new FileInputStream(ROUTE_DATA_FILE);
                ObjectInputStream oi = new ObjectInputStream(fi);
                // Read objects
                ArrayList<Route> fileDriverArrayList = (ArrayList<Route>) oi.readObject();
                for (Route driver : fileDriverArrayList) {
                    routeArrayList.add(driver);
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
                return routeArrayList;
            }

        }else {return routeArrayList;}
    }

    public Route findById(Integer id) {
        ArrayList<Route> routeArrayList = getAll();
        Route driver = new Route();
        for (Route d : routeArrayList) {
            if (d.getId()==id) {
                driver = d;
                break;
            }
        }
        return driver;

    }

}
