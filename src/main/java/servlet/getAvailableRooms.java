package servlet;

import dao.RoomDAO;
import model.Room;

import java.util.List;

public class getAvailableRooms {
    public static void main(String[] args) {
        // Instantiate the DAO
        RoomDAO roomDAO = new RoomDAO();

        // Test getAvailableRooms
        try {
            List<Room> availableRooms = roomDAO.getAvailableRooms();

            // Print the results
            if (availableRooms != null && !availableRooms.isEmpty()) {
                System.out.println("Available Rooms:");
                for (Room room : availableRooms) {
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
        }
    }
}