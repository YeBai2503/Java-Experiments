package com.example.tool;

import com.example.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DB2 {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PWD = "123456";

    private static String tableName = "historyLogin";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't register driver!", e);
        }
    }

    // 获取数据库连接
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PWD);
    }

    // 创建新用户
    public static void createUser(int id,String name, String email, String time, String web) {
        String sql = "INSERT INTO " + tableName + " (id,name,  email, time, web) VALUES (?,?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, Integer.toString(id));
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, time);
            preparedStatement.setString(5, web);

            int affectedRows = preparedStatement.executeUpdate();
            System.out.println("Created User:  id:" +id + ", Name: " + name +", web: "+web+", time: "+time+ ", Affected Rows: " + affectedRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 查询用户(单变量)
    public static ArrayList<User> queryUser(int id, String web) {
        ArrayList<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName + " WHERE id = ?";
        if(id==0)sql = "SELECT * FROM " + tableName + " WHERE web = ?";
        if(id==-1)sql = "SELECT * FROM " + tableName ;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            if(id==0)preparedStatement.setString(1, web);
            else if(id==-1){}
            else preparedStatement.setString(1, Integer.toString(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("DB2 查询 User:");
            while (resultSet.next()) {
                userList.add(new User(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("email"), resultSet.getString("time"), resultSet.getString("web")));
                System.out.println("ID: " + resultSet.getInt("id")
                        + ", Name: " + resultSet.getString("name")
                        + ", Email: " + resultSet.getString("email")
                        + ", Time: " + resultSet.getString("time")
                        + ", Web: " + resultSet.getString("web"));
            }
            if(userList.size()!=0)System.out.println("DB2 查询ID总数:"+userList.size());
            else System.out.println("DB2 ID not found!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    //查询用户(双变量)
    public static ArrayList<User> queryUser2(int id,String web) {
        ArrayList<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName + " WHERE id = ? AND web = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id); // 设置 id 参数
            preparedStatement.setString(2, web); // 设置 web 参数
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("DB2 查询 Online:");
            while (resultSet.next()) {
                userList.add(new User(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("email"), resultSet.getString("time"), resultSet.getString("web")));
                System.out.println("ID: " + resultSet.getInt("id")
                        + ", Name: " + resultSet.getString("name")
                        + ", Email: " + resultSet.getString("email")
                        + ", Time: " + resultSet.getString("time")
                        + ", Web: " + resultSet.getString("web"));
            }
            if(userList.size()!=0)System.out.println("DB2 查询ID总数:"+userList.size());
            else System.out.println("DB2 ID not found!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    // 删除用户
    public static void deleteUser(int id,String web) {
        String sql = "DELETE FROM " + tableName + " WHERE id = ? AND web = ?" ;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, Integer.toString(id));
            preparedStatement.setString(2, web); // 设置 web 参数
            int affectedRows = preparedStatement.executeUpdate();
            System.out.println(tableName +"  Deleted User id: " + id + ", Affected Rows: " + affectedRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 更新用户
    public static void updateUser(int id,String name, String email, String time, String web) {
        String sql = "UPDATE " + tableName + " SET name = ?,email = ?, time = ?  WHERE web = ? AND id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, time);
            preparedStatement.setString(4, web);
            preparedStatement.setString(5, Integer.toString(id));


            int affectedRows = preparedStatement.executeUpdate();
            System.out.println("Updated User:  id:" +id + ", Name: " + name +", web: "+web+", time: "+time+ ", Affected Rows: " + affectedRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // 测试方法
    public static void main(String[] args) {
//        DB2.createUser(3,"ssosr","<EMAIL>", GetTime.get(),"1");
//        DB2.createUser(2,"ssoserver","<EMAIL>",GetTime.get(),"1");
//        DB2.createUser(2,"ssoserver","<EMAIL>",GetTime.get(),"2");
//        // 查询用户
//        deleteUser(1);


//        setTableName("onlineUser");
//        ArrayList<User> userList = DB2.queryUser2(2,"Web2");
//        for (User user : userList) {
//            System.out.println(user.getName()+","+user.getWeb());
//        }

    }

    public static void setTableName(String tableName) {
        DB2.tableName = tableName;
    }
}
