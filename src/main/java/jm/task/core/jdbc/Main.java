package jm.task.core.jdbc;
import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import java.sql.SQLException;


public class Main {

    public static void main(String[] args) throws SQLException {

        UserDao userDao = new UserDaoJDBCImpl();

        User user1 = new User("Ivan", "Petrov", (byte) 30);
        User user2 = new User("Petr", "Ivanov", (byte) 31);
        User user3 = new User("Kirill", "Sidorov", (byte) 33);
        User user4 = new User("Sidr", "Kirillov", (byte) 29);


        userDao.createUsersTable();
        userDao.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        userDao.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        userDao.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        userDao.saveUser(user4.getName(), user4.getLastName(), user4.getAge());


        userDao.removeUserById(1);
        userDao.getAllUsers();
        userDao.cleanUsersTable();
        userDao.dropUsersTable();

    }

}
