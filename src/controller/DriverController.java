package controller;

import entity.Driver;
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
            name= helper.getString("Nhập tên lái xe:");
            if (validateDriver.validateName(name)) {
                break;
            }
        } while (true);

        do {
            address = helper.getString("Nhập địa chỉ lái xe: ");
            if (validateDriver.validateAddress(address)) {
                break;
            }
        } while (true);

        do{ phone= helper.getString("Nhập số điện thoại lái xe: ");
            if(validateDriver.validatePhone(phone)){
                break;
            }
        }
        while(true);
        do{ skillLevel= helper.getString("Nhập trình độ lái xe:");
            if(validateDriver.validateSkillLevel(skillLevel)){
                break;
            }
        }
        while(true);
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
}
