package controller;

import entity.Route;
import helper.Helper;
import repository.RouteDAO;

import java.util.ArrayList;

public class RouteController {
    private Helper helper=new Helper();
    private RouteDAO routeDAO=new RouteDAO();
    public void getInputEntity() {
        int distance,totalBusStop;
        do {
            distance= helper.getInt("Nhập độ dài quãng đường (Km) :");
            break;
        } while (true);

        do {
            totalBusStop = helper.getInt("Nhập số điểm dừng: ");
            break;

        } while (true);

        Route newRoute = new Route(distance,totalBusStop);
        if(routeDAO.save(newRoute)){
            System.out.println("Thêm tuyến đường mới thành công");
        }
        else{
            System.out.println("Đã xảy ra lỗi khi thêm tuyến đường");
        }
    }
    public  void getInputListEntity( ){
        int numberOfTeacher= helper.getInt("Nhập số lượng tuyến đường cần thêm: ");
        for (int i=0;i<=numberOfTeacher-1 ; i++){
            getInputEntity();
        }
    }
    public void printListData(){
        ArrayList<Route> routeArrayList= routeDAO.getAll();
        for(Route r:routeArrayList){
            System.out.println(r.toString());
        }
    }
}
