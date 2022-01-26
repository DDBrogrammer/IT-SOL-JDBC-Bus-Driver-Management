package entity;

import java.io.Serializable;

public class Route implements Serializable {
    private int id;
    private double distance;
    private int totalBusStop;

    public Route() {
    }

    public Route(int id, double distance, int totalBusStop) {
        this.id = id;
        this.distance = distance;
        this.totalBusStop = totalBusStop;
    }
    public Route( double distance, int totalBusStop) {
        this.distance = distance;
        this.totalBusStop = totalBusStop;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public int getTotalBusStop() {
        return totalBusStop;
    }

    public void setTotalBusStop(int totalBusStop) {
        this.totalBusStop = totalBusStop;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", distance=" + distance +
                ", totalBusStop=" + totalBusStop +
                '}';
    }
}
