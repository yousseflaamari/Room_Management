package dao;

import model.Room;
import util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class RoomDAO {

    // Save a new room
    public void addRoom(Room room) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(room);
            tx.commit();
            System.out.println("Room added successfully!");
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // Update an existing room
    public void updateRoom(Room room) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.merge(room);
            tx.commit();
            System.out.println("Room updated successfully!");
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // Fetch a room by ID
    public Room getRoomById(int id) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        Room room = null;
        try {
            room = em.find(Room.class, id);
        } finally {
            em.close();
        }
        return room;
    }

    // Fetch all rooms (available and not available)
    public List<Room> getAllRooms() {
        EntityManager entityManager = null;
        List<Room> rooms = null;

        try {
            entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
            String query = "SELECT r FROM Room r"; // Fetch all rooms
            rooms = entityManager.createQuery(query, Room.class).getResultList();
            System.out.println("Fetched rooms: " + rooms.size()); // Debug log
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return rooms;
    }

    public List<Room> getAvailableRooms() {
        EntityManager entityManager = null;
        List<Room> rooms = null;

        try {
            entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
            String query = "SELECT r FROM Room r WHERE r.availability = true"; // Fetch only available rooms
            rooms = entityManager.createQuery(query, Room.class).getResultList();

            // Enhanced Debugging: Print room details
            if (rooms != null && !rooms.isEmpty()) {
                System.out.println("Available Rooms:");
                for (Room room : rooms) {
                    System.out.println("ID: " + room.getId() +
                            ", Name: " + room.getName() +
                            ", Capacity: " + room.getCapacity() +
                            ", Equipment: " + room.getEquipment() +
                            ", Availability: " + room.isAvailability());
                }
            } else {
                System.out.println("No available rooms found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return rooms;
    }

    // Delete a room by ID
    public void deleteRoom(int id) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            Room room = em.find(Room.class, id);
            if (room != null) {
                em.remove(room);
                System.out.println("Room deleted successfully!");
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
