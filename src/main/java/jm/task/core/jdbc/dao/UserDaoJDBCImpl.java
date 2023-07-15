package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnecion;

public class UserDaoJDBCImpl  implements UserDao {
    Connection conn = getConnecion();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS user " +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), " +
                    "lastName VARCHAR(255), age INT)");
            System.out.println("Table created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS user");
            System.out.println("Table dropped");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age)  {
        try (PreparedStatement preparedStatement = conn.prepareStatement(
                     "INSERT INTO user (name, lastName, age) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User saved");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void removeUserById(long id)  {
        try (PreparedStatement preparedStatement = conn.prepareStatement(
                     "DELETE FROM user WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("User deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (ResultSet resultSet = conn.createStatement().executeQuery("SELECT * FROM user")) {
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");
                User user = new User(id, name, lastName, age);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE user");
            System.out.println("Table cleaned");
        } catch (SQLException e) {
            e.printStackTrace();


        }


    }
}