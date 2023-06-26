package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;


public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery("CREATE TABLE IF NOT EXISTS users.user_table (\n" +
                "  id BIGINT NOT NULL AUTO_INCREMENT,\n" +
                "  name VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NULL,\n" +
                "  last_name VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NULL,\n" +
                "  age TINYINT NULL,\n" +
                "  PRIMARY KEY (id));\n").executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void dropUsersTable() {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS user_table").executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User newUser = new User(name, lastName, (byte) age);
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        session.save(newUser);
        session.getTransaction().commit();
        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }

    @Override
    public void removeUserById(long id) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        User user = session.get(User.class, id);
        if (user == null) {
            System.out.println("Запись с ID " + id + " не найдена в БД");
        } else {
            session.delete(user);
        }
        session.getTransaction().commit();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        userList = session.createQuery("from User").getResultList();
        session.getTransaction().commit();
        System.out.println(userList);
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery("DELETE FROM user_table").executeUpdate();
        session.getTransaction().commit();
    }
}
