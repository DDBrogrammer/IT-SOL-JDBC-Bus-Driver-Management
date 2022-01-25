package entity;

import java.io.Serializable;
import java.util.Map;

public class Roster implements Serializable {
     private Driver driver;
     private Map<Route,Integer> routeList;

    public Roster() {
    }

    public Roster(Driver driver, Map<Route, Integer> routeList) {
        this.driver = driver;
        this.routeList = routeList;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Map<Route, Integer> getRouteList() {
        return routeList;
    }

    public void setRouteList(Map<Route, Integer> routeList) {
        this.routeList = routeList;
    }

    @Override
    public String toString() {
        return "Roster{" +
                "driver=" + driver +
                ", routeList=" + routeList +
                '}';
    }
}
