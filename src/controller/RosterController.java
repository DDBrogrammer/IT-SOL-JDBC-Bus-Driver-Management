package controller;

import entity.Driver;
import entity.Roster;
import entity.Route;
import main.MainRun;

import utils.validate.ValidateRoster;
import repository.DriverDAO;
import repository.RosterDAO;
import repository.RouteDAO;

import java.util.*;

public class RosterController {
    private RosterDAO rosterDAO=new RosterDAO();
    private DriverDAO driverDAO=new DriverDAO();
    private ValidateRoster validateRoster=new ValidateRoster();
    private RouteDAO routeDao=new RouteDAO();
   public void getInputEntity(){
       MainRun.driverController.printListData();
       System.out.println("Chọn lái xe từ danh sách đã cho");
       Driver driver= new Driver("","","",0,"");
       do{int driverId=MainRun.helper.getInt("Nhập Id lái xe từ danh sách:");
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
      MainRun.routeController.printListData();
       int numberOfRoute;
       do{
           numberOfRoute=MainRun.helper.getInt("Nhập số lượng tuyến đường của lái xe");
           if(numberOfRoute>routeDao.findAll().size()){
               System.out.println("Vượt quá số lượng tuyếm đường hiện có ("+routeDao.findAll().size()+")");
           }else{
               break;
           }
       }while(true);
       Map<Route,Integer> routeList=new HashMap<>();
       int routeId,totalRoute;
       for(int i=0;i<numberOfRoute;i++){
           do{
               routeId=MainRun.helper.getInt("Nhập id tuyến đường trong danh sách");
               if(!validateRoster.validateChosenSubject(routeList,routeId) &&
                       routeDao.findById(routeId).getId()!=0){
                   System.out.println("Đã chọn tuyến đường: " + routeDao.findById(routeId));

                   break;
               }else{
                   System.out.println("Tuyến đường vừa chọn đã được lưu cho người lái hoặc không có trong danh sách");
               }
           }while(true);
           do{
               totalRoute=MainRun.helper.getInt("Nhập tổng số lượt:");
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
        ArrayList<Roster> rosterArrayList= rosterDAO.findAll();
       printInputListData(rosterArrayList);
    }

    public void printInputListData(ArrayList<Roster> rosterArrayList){
       if(!rosterArrayList.isEmpty()){
        for(Roster ros:rosterArrayList){
            System.out.println("                            ");
            System.out.println("Người lái: "+ ros.getDriver().toString());
            for (Map.Entry<Route,Integer> entry : ros.getRouteList().entrySet())
                System.out.println("Tuyến đường: " + entry.getKey() +
                        ", Tổng số lượt: " + entry.getValue());
            System.out.println("                            ");
        }
    }}

    public void printListSortByDriverName(){
        ArrayList<Roster> rosterArrayList= rosterDAO.findAll();
        rosterArrayList.sort(new DriverNameComparator());
        printInputListData(rosterArrayList);
    }

    public void printListSortByTotalRoute(){
        ArrayList<Roster> rosterArrayList= rosterDAO.findAll();
        rosterArrayList.sort(new NumberOfRouteComparator());
        printInputListData(rosterArrayList);
    }

    public void printListTotalRange(){
        ArrayList<Roster> rosterArrayList= rosterDAO.findAll();
        for(Roster ros:rosterArrayList){
            System.out.println("                            ");
            System.out.println("Người lái: "+ ros.getDriver().toString());
            System.out.println("Tổng độ dài quãng đường: "+ getRange(ros.getRouteList())+ " (Km)" );

        }

    }

    public double getRange(Map<Route,Integer> rosterList ){
       double total=0;
        for (Map.Entry<Route,Integer> entry :rosterList.entrySet()){
            total=total+entry.getValue()*entry.getKey().getDistance();
        }
        return total;
    }

    public boolean sortRosterManage(){
        int chose_4;
        boolean run=false;
        do {
            chose_4 =MainRun.helper.getInt("Nhập lựa chọn:\n"
                    + "[1] sắp xếp danh sách theo tên lái xe.\n"
                    + "[2] sắp xếp danh sách theo số lượng tuyến trong ngày.\n"
                    + "[3] Quay lại.\n"
            );
            if(chose_4>=1 && chose_4<=3){
                break;
            }
            System.out.println("Bạn phải nhập số nguyên từ 1 đến 3");
        } while(true);
        if(chose_4==1){
            printListSortByDriverName();
            run=true;
        }else if(chose_4==2){
            printListSortByTotalRoute();
            run=true;
        }
        return run;
    }

    public boolean rosterManage() {
        int chose_3;
        boolean run=false;
        do {
            chose_3 = MainRun.helper.getInt("Nhập lựa chọn:\n"
                    + "[1] Thêm bản ghi phân công.\n"
                    + "[2] Xem danh sách phân công.\n"
                    + "[3] Quay lại.\n"
            );
            if(chose_3>=1 && chose_3<=3){
                break;
            }
            System.out.println("Bạn phải nhập số nguyên từ 1 đến 3");
        } while(true);
        if(chose_3==1){
            getInputEntity();
            run=true;
        }else if(chose_3==2){
            printListData();
            run=true;
        }
        return run;
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
        return Integer.compare(r2.getRouteList().size(), r1.getRouteList().size());
    }
}
