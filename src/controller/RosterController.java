package controller;

import entity.Driver;
import entity.Roster;
import entity.Route;
import helper.Helper;
import helper.ValidateRoster;
import repository.DriverDAO;
import repository.RosterDAO;
import repository.RouteDAO;

import java.util.*;

public class RosterController {
    private RosterDAO rosterDAO=new RosterDAO();
    private Helper helper=new Helper();
    private DriverController driverController=new DriverController();
    private RouteController routeController=new RouteController();
    private DriverDAO driverDAO=new DriverDAO();
    private ValidateRoster validateRoster=new ValidateRoster();
    private RouteDAO routeDao=new RouteDAO();
   public void getInputEntity(){
       driverController.printListData();
       System.out.println("Chọn lái xe từ danh sách đã cho");
       Driver driver= new Driver("","","",0,"");
       do{int driverId=helper.getInt("Nhập Id lái xe từ danh sách:");
           if(
                   driverDAO.findById(driverId).getId()==0
           ){
               System.out.println("Không tìm thấy lái xe vừa chọn");
           }else{
               driver=driverDAO.findById(driverId);
               System.out.println("Đã chọn lái xe: "+ driver.toString());
               break;
           }
       }while(true);
       routeController.printListData();
       int numberOfRoute;
       do{
           numberOfRoute=helper.getInt("Nhập số lượng tuyến đường của lái xe");
           if(numberOfRoute>routeDao.getAll().size()){
               System.out.println("Vượt quá số lượng tuyếm đường hiện có ("+routeDao.getAll().size()+")");
           }else{
               break;
           }
       }while(true);
       Map<Route,Integer> routeList=new HashMap<>();
       int routeId,totalRoute;
       for(int i=0;i<numberOfRoute;i++){
           do{
               routeId=helper.getInt("Nhập id tuyến đường trong danh sách");
               if(!validateRoster.validateChosenSubject(routeList,routeId) &&
                       routeDao.findById(routeId).getId()!=0){
                   System.out.println("Đã chọn tuyến đường: " + routeDao.findById(routeId));

                   break;
               }else{
                   System.out.println("Tuyến đường vừa chọn đã được lưu cho người lái hoặc không có trong danh sách");
               }
           }while(true);
           do{
               totalRoute=helper.getInt("Nhập tổng số lượt:");
               if(totalRoute<=15 ){
                    routeList.put(routeDao.findById(routeId),totalRoute);
                   break;
               }else{
                   System.out.println("Số lượt phải nhỏ hơn 16");
               }
           }while(true);
       }
       Roster roster=new Roster(driver,routeList);
       rosterDAO.save(roster);
   }
    public void printListData(){
        ArrayList<Roster> rosterArrayList= rosterDAO.getAll();
        for(Roster ros:rosterArrayList){
            System.out.println("                            ");
            System.out.println("Người lái: "+ ros.getDriver().toString());
            for (Map.Entry<Route,Integer> entry : ros.getRouteList().entrySet())
                System.out.println("Tuyến đường: " + entry.getKey() +
                        ", Tổng số lượt: " + entry.getValue());
            System.out.println("                            ");
        }
    }

    public void printListData(ArrayList<Roster> rosterArrayList){
        for(Roster ros:rosterArrayList){
            System.out.println("                            ");
            System.out.println("Người lái: "+ ros.getDriver().toString());
            for (Map.Entry<Route,Integer> entry : ros.getRouteList().entrySet())
                System.out.println("Tuyến đường: " + entry.getKey() +
                        ", Tổng số lượt: " + entry.getValue());
            System.out.println("                            ");
        }
    }

    public void printListSortByDriverName(){
        ArrayList<Roster> rosterArrayList= rosterDAO.getAll();
        Collections.sort(rosterArrayList,new DriverNameComparator());
        printListData(rosterArrayList);
    }

    public void printListSortByTotalRoute(){
        ArrayList<Roster> rosterArrayList= rosterDAO.getAll();
        Collections.sort(rosterArrayList,new NumberOfRouteComparator());
        printListData(rosterArrayList);
    }
    public void printListTotalRange(){
        ArrayList<Roster> rosterArrayList= rosterDAO.getAll();
        for(Roster ros:rosterArrayList){
            System.out.println("                            ");
            System.out.println("Người lái: "+ ros.getDriver().toString());
            System.out.println("Tổng độ dài quãng đường: "+ getRange(ros.getRouteList())+ " (Km)" );

        }

    }


    public int getRange(Map<Route,Integer> rosterList ){
       int total=0;
        for (Map.Entry<Route,Integer> entry :rosterList.entrySet()){
            total=total+entry.getValue()*entry.getKey().getDistance();
        }
        return total;
    }
}
class DriverNameComparator implements Comparator<Roster> {

    // override the compare() method
    public int compare(Roster r1, Roster r2)
    {
        if (r1.getDriver().getName().equals(r2.getDriver().getName()))
            return 0;
        else if (r1.getDriver().getName().compareTo(r2.getDriver().getName())>0)
            return 1;
        else
            return -1;
    }
}

class NumberOfRouteComparator implements Comparator<Roster> {

    // override the compare() method
    public int compare(Roster r1, Roster r2)
    {
        if (r1.getRouteList().size()==r2.getRouteList().size())
            return 0;
        else if (r1.getRouteList().size()<r2.getRouteList().size())
            return 1;
        else
            return -1;
    }
}
