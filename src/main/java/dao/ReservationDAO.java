package dao;

import model.Reservation;
import util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReservationDAO {

    private EntityManagerFactory entityManagerFactory;

    public ReservationDAO() {
        this.entityManagerFactory = HibernateUtil.getEntityManagerFactory();
    }

    // Save Reservation
    public void saveReservation(Reservation reservation) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(reservation);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    // Get All Reservations
    public List<Reservation> getAllReservations() {
        EntityManager entityManager = null;
        List<Reservation> reservations = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            TypedQuery<Reservation> query = entityManager.createQuery("SELECT r FROM Reservation r", Reservation.class);
            reservations = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return reservations;
    }


    public List<Object[]> getReservationsWithDetails() {
        EntityManager entityManager = null;
        List<Object[]> results = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            String query = "SELECT r.id, r.date, r.startTime, r.endTime, r.status, u.username, rm.name " +
                    "FROM Reservation r " +
                    "JOIN User u ON r.userId = u.id " +
                    "JOIN Room rm ON r.roomId = rm.id";
            TypedQuery<Object[]> typedQuery = entityManager.createQuery(query, Object[].class);
            results = typedQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return results;
    }

    public boolean updateStatus(int reservationId, String status) {
        EntityManager entityManager = null;
        boolean updated = false;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            // Find the reservation
            Reservation reservation = entityManager.find(Reservation.class, reservationId);

            if (reservation != null) {
                // Only check for availability if updating to 'Confirmed'
                if ("Confirmed".equals(status)) {
                    boolean available = isRoomAvailable(
                            reservation.getRoomId(),
                            reservation.getDate(),
                            reservation.getStartTime(),
                            reservation.getEndTime()
                    );

                    if (!available) {
                        // Room not available
                        System.out.println("Room is not available during the selected time.");
                        return false;
                    }
                }

                // Update the status if no conflicts
                reservation.setStatus(status);
                entityManager.getTransaction().commit();
                updated = true;
            }
        } catch (Exception e) {
            if (entityManager != null) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return updated;
    }


    public boolean isRoomAvailable(int roomId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();

            // Query to check overlapping reservations
            String query = "SELECT COUNT(r) FROM Reservation r " +
                    "WHERE r.roomId = :roomId " +
                    "AND r.date = :date " +
                    "AND r.status = 'Confirmed' " +
                    "AND ((r.startTime < :endTime AND r.endTime > :startTime))";

            Long count = entityManager.createQuery(query, Long.class)
                    .setParameter("roomId", roomId)
                    .setParameter("date", date)
                    .setParameter("startTime", startTime)
                    .setParameter("endTime", endTime)
                    .getSingleResult();

            return count == 0; // Room is available if no conflicts
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public boolean isDuplicateReservation(int userId, int roomId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();

            String query = "SELECT COUNT(r) FROM Reservation r " +
                    "WHERE r.userId = :userId AND r.roomId = :roomId " +
                    "AND r.date = :date " +
                    "AND r.startTime = :startTime AND r.endTime = :endTime";

            Long count = entityManager.createQuery(query, Long.class)
                    .setParameter("userId", userId)
                    .setParameter("roomId", roomId)
                    .setParameter("date", date)
                    .setParameter("startTime", startTime)
                    .setParameter("endTime", endTime)
                    .getSingleResult();

            return count > 0; // True if duplicate exists
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public boolean isUserOverlappingReservation(int userId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();

            String query = "SELECT COUNT(r) FROM Reservation r " +
                    "WHERE r.userId = :userId " +
                    "AND r.date = :date " +
                    "AND ((r.startTime < :endTime AND r.endTime > :startTime))";

            Long count = entityManager.createQuery(query, Long.class)
                    .setParameter("userId", userId)
                    .setParameter("date", date)
                    .setParameter("startTime", startTime)
                    .setParameter("endTime", endTime)
                    .getSingleResult();

            return count > 0; // True if overlap exists
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public List<Reservation> getUserReservations(int userId) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            String query = "SELECT r FROM Reservation r WHERE r.userId = :userId";
            return entityManager.createQuery(query, Reservation.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public void deleteReservation(int reservationId) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            Reservation reservation = entityManager.find(Reservation.class, reservationId);
            if (reservation != null) {
                entityManager.remove(reservation);
            }

            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }
    public Reservation getReservation(int reservationId) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            return entityManager.find(Reservation.class, reservationId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public List<Reservation> getReservationsByUserId(int userId) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        List<Reservation> reservations = null;
        try {
            reservations = em.createQuery("SELECT r FROM Reservation r WHERE r.userId = :userId", Reservation.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } finally {
            em.close();
        }
        return reservations;
    }

    public void updateReservationStatusById(int id, String status) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();

            // Find the reservation by ID
            Reservation reservation = em.find(Reservation.class, id);

            if (reservation != null) {
                reservation.setStatus(status);
                em.merge(reservation);
            } else {
                throw new IllegalArgumentException("Reservation not found for ID: " + id);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to update reservation status", e);
        } finally {
            em.close();
        }
    }

    public int getTotalReservations() {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            Long count = em.createQuery("SELECT COUNT(r) FROM Reservation r", Long.class).getSingleResult();
            return count.intValue();
        } finally {
            em.close();
        }
    }

    public List<Object[]> getMostBookedRooms() {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            List<Object[]> results = em.createQuery("SELECT r.roomId, COUNT(r) FROM Reservation r GROUP BY r.roomId ORDER BY COUNT(r) DESC", Object[].class)
                    .setMaxResults(5) // Limit to top 5 most booked rooms
                    .getResultList();
            System.out.println("Most Booked Rooms: " + results);
            return results;
        } finally {
            em.close();
        }
    }


    public List<Object[]> getReservationsPerDay() {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT r.date, COUNT(r) FROM Reservation r GROUP BY r.date ORDER BY r.date ASC", Object[].class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Object[]> getReservationsByUser() {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT r.userId, COUNT(r) FROM Reservation r GROUP BY r.userId ORDER BY COUNT(r) DESC", Object[].class)
                    .getResultList();
        } finally {
            em.close();
        }
    }













}
