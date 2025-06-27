package com.ssoserver.model;

import com.ssoserver.factory.UserFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DB {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PWD = "123456";

    private static String tableName = "user";


    // 连接数据库
    public static void connect() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't register driver!", e);
        }
        System.out.println("Mysql驱动已加载！");
    }
    // 断开数据库
    public static void disconnect() throws SQLException {
        System.out.println("数据库已断开！");
    }
    // 获取数据库连接
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PWD);
    }

//    static {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException("Can't register driver!", e);
//        }
//    }
//
//    // 获取数据库连接
//    private static Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PWD);
//    }

    // 创建新用户
    public static void createUser(int id,String name, String password, String email, int isManager, int isForbid) {
        String sql = "INSERT INTO " + tableName + " (id,name, password, email, isManager, isForbid) VALUES (?,?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, Integer.toString(id));
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, email);
            preparedStatement.setInt(5, isManager);
            preparedStatement.setInt(6, isForbid);

            int affectedRows = preparedStatement.executeUpdate();
            System.out.println("Created User:  id:" +id + ", Name: " + name + ", Affected Rows: " + affectedRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 查询用户
    public static ArrayList<User> queryUser(int id,String email) {
        ArrayList<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName + " WHERE id = ?";
        if(id==0)sql = "SELECT * FROM " + tableName + " WHERE email = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            if(id==0)preparedStatement.setString(1, email);
            else preparedStatement.setString(1, Integer.toString(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            UserFactory userFactory = new UserFactory();
            while (resultSet.next()) {
                userList.add(userFactory.produce(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("password"), resultSet.getString("email"), resultSet.getInt("isManager"), resultSet.getInt("isForbid")));
                System.out.println("ID: " + resultSet.getInt("id")
                        + ", Name: " + resultSet.getString("name")
                        + ", Email: " + resultSet.getString("email")
                        + ", isManager: " + resultSet.getInt("isManager")
                        + ", isForbid: " + resultSet.getInt("isForbid"));
            }
            if(userList.size()!=0)System.out.println("DB ID:"+userList.get(0).getId());
            else System.out.println("DB ID not found!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    // 查询非管理员
    public static ArrayList<User> queryNoManager() {
        ArrayList<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName + " WHERE isManager = 0";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            UserFactory userFactory = new UserFactory();
            while (resultSet.next()) {
                userList.add(userFactory.produce(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("password"), resultSet.getString("email"), resultSet.getInt("isManager"), resultSet.getInt("isForbid")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    // 更新用户信息
    public static void updateUser(int id, String newName, String newPassword, String newEmail, int newIsManager, int newIsForbid) {
        String sql = "UPDATE " + tableName + " SET password = ?, email = ?, isManager = ?, isForbid = ?, name = ?  WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, newEmail);
            preparedStatement.setInt(3, newIsManager);
            preparedStatement.setInt(4, newIsForbid);
            preparedStatement.setString(5, newName);
            preparedStatement.setString(6, Integer.toString(id));

            int affectedRows = preparedStatement.executeUpdate();
            System.out.println("Updated User:  id:" + id + ", Name: " + newName + ", Affected Rows: " + affectedRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 禁用用户
    public static void updateForbid(int id, int isForbid) {
        String sql = "UPDATE " + tableName + " SET  isForbid = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, Integer.toString(isForbid));
            preparedStatement.setString(2, Integer.toString(id));
            int affectedRows = preparedStatement.executeUpdate();
            System.out.println("禁用/启用:  id:" + id +  ", Affected Rows: " + affectedRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除用户
    public static void deleteUser(int id) {
        String sql = "DELETE FROM " + tableName + " WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, Integer.toString(id));
            int affectedRows = preparedStatement.executeUpdate();
            System.out.println("Deleted User id: " + id + ", Affected Rows: " + affectedRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 测试方法
    public static void main(String[] args) {
        // 查询用户
        ArrayList<User> userList = DB.queryUser(0,"yb3179174149@163.com");
        for (User user : userList) {
            System.out.println(user.getId());
        }

    }

//    public static void setTableName(String tableName) {
//        DB.tableName = tableName;
//    }
}
