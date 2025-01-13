package util;

import dao.ReservationDAO;
import model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationTestData {
    public static void main(String[] args) {
        // Create a ReservationDAO instance
        ReservationDAO reservationDAO = new ReservationDAO();

        // Insert test data
        try {
            // Existing confirmed reservation (Conflict Test)
            Reservation confirmedReservation = new Reservation();
            confirmedReservation.setDate(LocalDate.of(2024, 12, 18)); // Fixed date for testing
            confirmedReservation.setStartTime(LocalTime.of(10, 0));   // 10:00 AM
            confirmedReservation.setEndTime(LocalTime.of(12, 0));     // 12:00 PM
            confirmedReservation.setStatus("Confirmed");
            confirmedReservation.setRoomId(1);  // Existing Room ID
            confirmedReservation.setUserId(1);  // Existing User ID

            // New pending reservation with overlapping time
            Reservation pendingReservation = new Reservation();
            pendingReservation.setDate(LocalDate.of(2024, 12, 18)); // Same day as the confirmed reservation
            pendingReservation.setStartTime(LocalTime.of(11, 0));   // Overlaps with 10:00 - 12:00
            pendingReservation.setEndTime(LocalTime.of(13, 0));     // 1:00 PM
            pendingReservation.setStatus("Pending");
            pendingReservation.setRoomId(1);  // Same Room ID (conflict test)
            pendingReservation.setUserId(2);  // Different User ID

            // Save reservations
            reservationDAO.saveReservation(confirmedReservation);
            reservationDAO.saveReservation(pendingReservation);

            System.out.println("Test data inserted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
