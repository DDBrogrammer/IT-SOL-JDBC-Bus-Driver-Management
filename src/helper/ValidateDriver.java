package helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateDriver {
    private Helper helper=new Helper();
    private  String PHONE_NUMBER_REGEX="(84|0[3|5|7|8|9])+([0-9]{8})\\b";
    public Boolean validateName( String name){
        if(name.length() == 0){
            System.out.println("Bạn chưa nhập tên học sinh");
            return false;
        }else if(
                name.length()>=100
        )  {
            System.out.println("Tên học sinh quá dài");
            return  false;
        } else if(
                !helper.checkContainLetterOnly(name)
        ){
            System.out.println("Tên học sinh không phù hợp quy tắc chung");
            return false;
        } else {
            return true;}
    }

    public Boolean validatePhone(String phone) {
        Pattern p = Pattern.compile(PHONE_NUMBER_REGEX);
        Matcher m = p.matcher(phone);
        boolean b = m.matches();
        if(phone.length() == 0){
            System.out.println("Bạn chưa nhập số điện thoại của học sinh");
            return false;
        }else if(
                phone.length()>11
        )  {
            System.out.println("Số điện thoại quá dài");
            return  false;
        } else if( !b){
            System.out.println("Số điện thoại không hợp lệ");
            return false;}
        else{
            return true;

        }    }

    public Boolean validateAddress( String address){
        if(address.length() == 0){
            System.out.println("Bạn chưa nhập địa chỉ ");
            return false;
        }else if(
                address.length()>=250
        )  {
            System.out.println("Địa chỉ quá dài ");
            return  false;
        } else {
            return true;}
    }

    public Boolean validateSkillLevel( String type){
        if(type.length() == 1 && helper.checkContainLetterOnly(type)
        ){
            return true;
        }   else {
            System.out.println("Chỉ được nhập các mã trình độ là chữ cái đơn từ a đến f");
            return false;}
    }

}
