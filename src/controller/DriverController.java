package controller;

import entity.Driver;
import main.MainRun;
import utils.Helper;
import utils.validate.ValidateDriver;
import repository.DriverDAO;

import java.util.ArrayList;
import java.util.Locale;

public class DriverController {
        private Helper helper=new Helper();
        private ValidateDriver validateDriver=new ValidateDriver();
        private DriverDAO driverDAO=new DriverDAO();
    public void getInputEntity() {
        String name, address,phone, skillLevel ;
        do {
            name = helper.getString("Nhập tên lái xe:");
        } while (!validateDriver.validateName(name));

        do {
            address = helper.getString("Nhập địa chỉ lái xe: ");
        } while (!validateDriver.validateAddress(address));

        do {
            phone = helper.getString("Nhập số điện thoại lái xe: ");
        } while (!validateDriver.validatePhone(phone));
        do {
            skillLevel = helper.getString("Nhập trình độ lái xe:");
        } while (!validateDriver.validateSkillLevel(skillLevel));
        Driver newStudent = new Driver(helper.getNormalizedString(name),helper.getNormalizedString(address),phone,skillLevel.toUpperCase(Locale.ROOT));
        if(driverDAO.save(newStudent)){
            System.out.println("Thêm lái xe mới thành công");
        }
        else{
            System.out.println("Đã xảy ra lỗi khi thêm lái xe");
        }
    }
    public  void getInputListEntity( ){
        int numberOfTeacher= helper.getInt("Nhập số lượng lái xe cần thêm: ");
        for (int i=0;i<=numberOfTeacher-1 ; i++){
            getInputEntity();
        }
    }
    public void printListData(){
        ArrayList<Driver> driverArrayList= driverDAO.findAll();
        for(Driver d:driverArrayList){
            System.out.println(d.toString());
        }
    }
    public boolean driverManage() {
        int chose_1;
        boolean run=false;
        do {
            chose_1 = MainRun.helper.getInt("Nhập lựa chọn:\n"
                    + "[1] Thêm người lái.\n"
                    + "[2] Xem danh sách người lái.\n"
                    + "[3] Quay lại.\n"
            );
            if(chose_1>=1 && chose_1<=3){
                break;
            }
            System.out.println("Bạn phải nhập số nguyên từ 1 đến 3");
        } while(true);
        if(chose_1==1){
            getInputListEntity();
            run= true;
        }else if(chose_1==2){
            printListData();
            run=true;
        }
        return run;
    }
}
