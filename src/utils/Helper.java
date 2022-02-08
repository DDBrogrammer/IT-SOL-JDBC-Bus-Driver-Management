package utils;

import main.MainRun;

import java.util.Scanner;

public class Helper {

    private static final String SPECIAL_CHAR_AND_NUMBER_REGEX="[$&+,:;=?@#|'<>.-^*()%!0123456789]";
    private static final String SPECIAL_CHAR_REGEX="[$&+,:;=?@#|'<>.-^*()%!]";

    public static String getNormalizedString(String str) {
        str = normalize(str);
        String temp[] = str.split(" ");
        str = ""; // ? ^-^
        for (int i = 0; i < temp.length; i++) {
            str += String.valueOf(temp[i].charAt(0)).toUpperCase() + temp[i].substring(1);
            if (i < temp.length - 1) // ? ^-^
                str += " ";
        }
        return str;
    }
    public static String normalize(String str) {
        str = str.trim();
        str = str.replaceAll("\\s+", " ");
        return str;
    }
    public boolean checkContainLetterOnly(String str){
        return !str.equals("") && !str.matches(SPECIAL_CHAR_AND_NUMBER_REGEX);
    }

    public boolean checkContainLetterAndNumberOnly(String str){
        return !str.equals("") && !str.matches(SPECIAL_CHAR_REGEX);
    }
    static String getConvertString(String str){
        boolean check=true;
        char[] ch = new char[str.length()];
        for (int i = 0; i < str.length(); i++) {
            if(check){
                ch[i] =Character.toUpperCase(str.charAt(i));
                check=false;
            }else{
                ch[i] =Character.toLowerCase(str.charAt(i));
                check=true;
            }
        }
        String res= String.valueOf(ch);
        return res;
    }

    public  void printMenu() {
        System.out.println("Nhập lựa chọn:\n"
                + "[1] Quản lý lái xe.\n"
                + "[2] Quản lý tuyến đường.\n"
                + "[3] Quản lý bảng phân công.\n"
                + "[4] Sắp xếp danh sách phân công.\n"
                + "[5] Xem bảng thống kê khoảng cách chạy xe mỗi ngày. \n"
                + "[6] Thoát chương  trình.");
    }

    public  int getInt(String ask) {
        boolean run=true;
        int a=0;
        while(run==true) {try {
            Scanner sc = new Scanner(System.in);
            System.out.println(ask);
            a = sc.nextInt();
            run=false;

        }catch(Exception e ) {
            System.out.println("Bạn phải nhập một số nguyên");
        }

        }

        return (int)a;
    }

    public  String getString(String ask) {
        boolean run=true;
        String s="";
        while(run==true) {try {
            Scanner sc = new Scanner(System.in);
            System.out.println(ask);
            s = sc.nextLine();
            run=false;
        }
        catch(Exception e ) {
            System.out.println("Bạn phải nhập một đoạn văn bản");
        }

        }
        return s;
    }
    public void askContinue() {
        if (!askYesNo()) {
            MainRun.run = false;
        }
    }
    public  Double getDouble(String ask) {
        boolean run=true;
        Double d=0.0;
        while(run) {try {
            Scanner sc = new Scanner(System.in);
            System.out.println(ask);
            d = sc.nextDouble();
            run=false;
        }
        catch(Exception e ) {
            System.out.println("Bạn phải nhập một chữ số thập phân");
        }

        }
        return (Double)d;
    }

    public  boolean askYesNo() {
        boolean ok=false;
        boolean runAgain=true;
        String ans="";
        while(runAgain) {
            try {
                ans = getString("Bạn có muốn dùng tiếp chức năng khác không \n" +
                        "[C]  có\n" +
                        "[K]  không\n" +
                        "Chọn một trong 2 lựa chọn: "); }catch (Exception e) {
                // TODO: handle exceptions
                ans = getString("Bạn phải nhập [C] hoặc [K].");
            }
            if(ans.toUpperCase().equals("C")) {
                ok=true;
                runAgain=false;
            }else if(ans.toUpperCase().equals("K")) {
                ok=false;
                runAgain=false;
            }else {
                runAgain=true;
            }


        }
        return ok;

    }
}
