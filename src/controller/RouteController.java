package controller;

import entity.Route;
import main.MainRun;
import utils.Helper;
import repository.RouteDAO;

import java.util.ArrayList;

public class RouteController {
    private RouteDAO routeDAO=new RouteDAO();
    public void getInputEntity() {
        int totalBusStop;
        Double distance;
        do {
            distance= MainRun.helper.getDouble("Nhập độ dài quãng đường (Km) :");
            break;
        } while (true);

        do {
            totalBusStop = MainRun.helper.getInt("Nhập số điểm dừng: ");
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
        int numberOfTeacher= MainRun.helper.getInt("Nhập số lượng tuyến đường cần thêm: ");
        for (int i=0;i<=numberOfTeacher-1 ; i++){
            getInputEntity();
        }
    }
    public void printListData(){
        ArrayList<Route> routeArrayList= routeDAO.findAll();
        for(Route r:routeArrayList){
            System.out.println(r.toString());
        }
    }
    public  boolean routerManage() {
        int chose_2;
        boolean run=false;
        do {
            chose_2 = MainRun.helper.getInt("Nhập lựa chọn:\n"
                    + "[1] Thêm tuyến đường.\n"
                    + "[2] Xem danh sách tuyến đường.\n"
                    + "[3] Quay lại.\n"
            );
            if(chose_2>=1 && chose_2<=3){
                break;
            }
            System.out.println("Bạn phải nhập số nguyên từ 1 đến 3");
        } while(true);
        if(chose_2==1){
            getInputListEntity();
            run=true;
        }else if(chose_2==2){
            printListData();
            run=true;
        }
        return run;
    }
}
