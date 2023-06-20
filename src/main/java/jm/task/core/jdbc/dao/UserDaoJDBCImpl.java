package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    String tableName = "user";

    public UserDaoJDBCImpl() {
    }


    public void createUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS users." + tableName + " (\n" +
                    "  id BIGINT NOT NULL AUTO_INCREMENT,\n" +
                    "  name VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NULL,\n" +
                    "  last_name VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NULL,\n" +
                    "  age TINYINT NULL,\n" +
                    "  PRIMARY KEY (id));\n");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS " + tableName);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String qery = "INSERT INTO users." + tableName + "(name, last_name, age) VALUES (?, ?, ?)";
        try (Connection connection = Util.getConnection();
             PreparedStatement rs = connection.prepareStatement(qery)) {
            rs.setString(1, name);
            rs.setString(2, lastName);
            rs.setByte(3, age);
            rs.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");

        } catch (SQLException e) {

        }

    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM " + tableName + " WHERE id=" + id);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);
            while (resultSet.next()) {
                User user = new User(resultSet.getString(2), resultSet.getString(3), resultSet.getByte(4));
                userList.add(user);
            }

        } catch (SQLException e) {

        }
        System.out.println(userList);
        return userList;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM " + tableName);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
