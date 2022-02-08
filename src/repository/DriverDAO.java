package repository;
import entity.Driver;
import utils.database_connection.OracleDBConnection;


import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class DriverDAO implements DataAccessible<Driver,Integer> {
    private final String TABLE_NAME="driver";
    @Override
    public boolean save(Driver driver) {
        String saveSql = "INSERT INTO " + TABLE_NAME + " ( name, address,phone,skill_level) VALUES ( ?, ?,?,?)";
        String updateSql = "UPDATE " + TABLE_NAME + " SET name=?, address=?,phone=?,skill_level=?  WHERE id=?";
        boolean checkOk=false;
        boolean checkExit=false;
        ArrayList<Driver> listDepartment= findAll();
        for(Driver d:listDepartment) {
            if (d.getId()==driver.getId()) {
                checkExit=true;
            }
        }
        if(checkExit) {
            try { Connection conn= OracleDBConnection.getConnection();
                PreparedStatement statement;
                statement = conn.prepareStatement(updateSql);
                statement.setString(1, driver.getName());
                statement.setString(2, driver.getAddress());
                statement.setString(3, driver.getPhone());
                statement.setString(4, driver.getSkillLevel());
                statement.setInt(5, driver.getId());
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    checkOk=true;
                }
                conn.close();

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally{
                OracleDBConnection.closeConnection();
            }
        }else {
            try {Connection conn= OracleDBConnection.getConnection();
                PreparedStatement statement;
                statement = conn.prepareStatement(saveSql);
                statement.setString(1, driver.getName());
                statement.setString(2, driver.getAddress());
                statement.setString(3, driver.getPhone());
                statement.setString(4, driver.getSkillLevel());
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    checkOk=true;
                }
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally{
                OracleDBConnection.closeConnection();
            }
        }

        return checkOk;
    }

    @Override
    public boolean deleteAll() {
        return false;
    }

    @Override
    public ArrayList<Driver> findAll() {
        Connection conn= OracleDBConnection.getConnection();
        ArrayList<Driver> listDepartment=new ArrayList<>();
        String sql = "SELECT * FROM "+TABLE_NAME ;
        try {
            Statement statement;
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()){
                int id=result.getInt(1);
                String name = result.getString(2);
                String address = result.getString(3);
                String phone = result.getString(4);
                String skillLevel = result.getString(5);
                Driver driver=new Driver(name,address,phone,id,skillLevel);
                listDepartment.add(driver);
            }
            conn.close();
        } catch ( SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
             OracleDBConnection.closeConnection();
        }

        return listDepartment;
    }

    @Override
    public Driver findById(Integer id) {
        Connection conn= OracleDBConnection.getConnection();
        String sql = "SELECT * FROM "+TABLE_NAME +" WHERE id=?";
        Driver driver=new Driver("","","",0,"");
        try {
            PreparedStatement prepStatement= conn.prepareStatement(sql);
            prepStatement.setInt(1,id);
            ResultSet resultList=prepStatement.executeQuery();
            while (resultList.next()){
                int driverId=resultList.getInt(1);
                String name = resultList.getString(2);
                String address = resultList.getString(3);
                String phone =  resultList.getString(4);
                String skillLevel =  resultList.getString(5);
                driver=new Driver(name,address,phone,driverId,skillLevel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            OracleDBConnection.closeConnection();
        }
        return driver;
    }
}
