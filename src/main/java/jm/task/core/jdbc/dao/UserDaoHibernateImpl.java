package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;


public class UserDaoHibernateImpl implements UserDao {

    private String table = "user_tabl";

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS " + table + "(\n" +
                    "  id BIGINT NOT NULL AUTO_INCREMENT,\n" +
                    "  name VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NULL,\n" +
                    "  last_name VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NULL,\n" +
                    "  age TINYINT NULL,\n" +
                    "  PRIMARY KEY (id));\n").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (getSessionFactory().getCurrentSession().getTransaction().isActive()) {
                getSessionFactory().getCurrentSession().getTransaction().rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Ошибка создания базы данных", e);
        }
    }


    @Override
    public void dropUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS " + table).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (getSessionFactory().getCurrentSession().getTransaction().isActive()) {
                getSessionFactory().getCurrentSession().getTransaction().rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Ошибка удаления базы данных", e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            User newUser = new User(name, lastName, (byte) age);
            session.save(newUser);
            session.getTransaction().commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (Exception e) {
            if (getSessionFactory().getCurrentSession().getTransaction().isActive()) {
                getSessionFactory().getCurrentSession().getTransaction().rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Ошибка сохранения пользователя", e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            if (user == null) {
                System.out.println("Запись с ID " + id + " не найдена в БД");
            } else {
                session.delete(user);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            if (getSessionFactory().getCurrentSession().getTransaction().isActive()) {
                getSessionFactory().getCurrentSession().getTransaction().rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Ошибка удаления пользователя по ID", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            userList = session.createQuery("from User").getResultList();
            session.getTransaction().commit();
            System.out.println(userList);
        } catch (Exception e) {
            if (getSessionFactory().getCurrentSession().getTransaction().isActive()) {
                getSessionFactory().getCurrentSession().getTransaction().rollback();
            }
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DELETE FROM " + table).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (getSessionFactory().getCurrentSession().getTransaction().isActive()) {
                getSessionFactory().getCurrentSession().getTransaction().rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Ошибка очистки таблицы", e);
        }
    }
}
