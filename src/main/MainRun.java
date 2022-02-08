package main;

import controller.DriverController;
import controller.RosterController;
import controller.RouteController;
import repository.RosterDAO;
import utils.Helper;
public class MainRun {
    public static Helper helper=new Helper();
    public static DriverController driverController=new DriverController();
    public static RouteController routeController=new RouteController();
    public static RosterController rosterController=new RosterController();
    private static RosterDAO rosterDAO=new RosterDAO();
    public static boolean run=true;
    public static void main(String[] args) {
        rosterDAO.findAll();
         mainRun();
    }

    private static void  mainRun(){

        do{
            helper.printMenu();
            int chose= helper.getInt(" ");
            switch (chose) {
                case 1 :
                    if (!driverController.driverManage()) {
                        break;
                    }
                    helper.askContinue();
                    break;
                case 2 :
                    if (!routeController.routerManage()) {
                        break;
                    }
                    helper.askContinue();
                    break;
                case 3 :
                    if (!rosterController.rosterManage()) {
                        break;
                    }
                    helper.askContinue();
                    break;
                case 4 :
                    if (!rosterController.sortRosterManage()) {
                        break;
                    }
                    helper.askContinue();
                    break;

              case 5 :
                    rosterController.printListTotalRange();
                    helper.askContinue();
                    break;

                case 6 : run = false;
                break;
                default : System.out.println("Bạn phải nhập số nguyên trong khoảng từ 1 đến 5");
            }
        }while(run);
        System.out.println("Đã thoát chương trình");
    }
}
