package dao;

import model.User;
import util.HibernateUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class UserDAO {

    // Save a new user to the database
    public void saveUser(User user) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Retrieve all users from the database
    public List<User> getAllUsers() {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        List<User> users = null;
        try {
            users = em.createQuery("from User", User.class).getResultList();
        } finally {
            em.close();
        }
        return users;
    }

    public User getUserById(int id) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        User user = null;
        try {
            user = em.find(User.class, id);
        } finally {
            em.close();
        }
        return user;
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        User user = null;
        try {
            System.out.println("Attempting to authenticate with username: " + username + " and password: " + password);
            user = em.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class)
                    .setParameter("username", username.trim()) // Trim whitespace
                    .setParameter("password", password.trim()) // Trim whitespace
                    .getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            System.err.println("Invalid credentials. No matching user found.");
            user = null;
        } catch (Exception e) {
            System.err.println("Error during authentication: " + e.getMessage());
        } finally {
            em.close();
        }
        return user;
    }



}
