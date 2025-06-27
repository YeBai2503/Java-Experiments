package com.example.tool;

import com.example.model.User;

import java.sql.*;
import java.util.ArrayList;

public class DB {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PWD = "123456";

    private static String tableName = "user";

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
    public static ArrayList<User> queryUser(int id) {
        ArrayList<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName + " WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {


            preparedStatement.setString(1, Integer.toString(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userList.add(new User(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("password"), resultSet.getString("email"), resultSet.getInt("isManager"), resultSet.getInt("isForbid")));
                System.out.println("ID: " + resultSet.getInt("id")
                        + ", Name: " + resultSet.getString("name")
                        + ", Email: " + resultSet.getString("email")
                        + ", isManager: " + resultSet.getInt("isManager")
                        + ", isForbid: " + resultSet.getInt("isForbid"));
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
        ArrayList<User> userList = DB.queryUser(1);
        for (User user : userList) {
            System.out.println(user.getId());
        }

    }

    public static void setTableName(String tableName) {
        DB.tableName = tableName;
    }
}
