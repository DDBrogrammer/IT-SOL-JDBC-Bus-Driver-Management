package repository;



import entity.Driver;

import java.io.*;
import java.util.ArrayList;

public class DriverDAO implements DataAccessible<Driver,Integer> {
    private  File DRIVER_DATA_FILE = new File("DriverData.txt");
    public boolean save(Driver driver) {

        boolean ok = false;
        ArrayList<Driver> newDriverArrayList = new ArrayList();
        if( DRIVER_DATA_FILE.length()!=0 ) {
            try {
                FileInputStream fi = new FileInputStream(DRIVER_DATA_FILE);
                ObjectInputStream oi = new ObjectInputStream(fi);
                // Read objects
                ArrayList<Driver> oldDriverArrayList = (ArrayList<Driver>) oi.readObject();
                for (Driver oldDriver : oldDriverArrayList) {
                    newDriverArrayList.add(oldDriver);
                }
                oi.close();
                fi.close();
                deleteAll();
                FileOutputStream f = new FileOutputStream(DRIVER_DATA_FILE);
                ObjectOutputStream o = new ObjectOutputStream(f);
                driver.setId(newDriverArrayList.size()+10000);
                newDriverArrayList.add(driver);
                o.writeObject(newDriverArrayList);
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
                FileOutputStream f = new FileOutputStream(DRIVER_DATA_FILE);
                ObjectOutputStream o = new ObjectOutputStream(f);
                driver.setId(10000);
                newDriverArrayList.add(driver);
                o.writeObject(newDriverArrayList);
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
            new FileOutputStream(DRIVER_DATA_FILE).close();
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

    public ArrayList<Driver> getAll() {
        ArrayList<Driver> driverArrayList = new ArrayList();
        if(DRIVER_DATA_FILE.length()!=0){
            try {
                FileInputStream fi = new FileInputStream(DRIVER_DATA_FILE);
                ObjectInputStream oi = new ObjectInputStream(fi);
                // Read objects
                ArrayList<Driver> fileDriverArrayList = (ArrayList<Driver>) oi.readObject();
                for (Driver driver : fileDriverArrayList) {
                    driverArrayList.add(driver);
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
                return driverArrayList;
            }

        }else {return driverArrayList;}
    }

    public Driver findById(Integer id) {
        ArrayList<Driver> driverArrayList = getAll();
        Driver driver = new Driver();
        for (Driver d : driverArrayList) {
            if (d.getId()==id) {
                driver = d;
                break;

            }
        }
        return driver;

    }




}
