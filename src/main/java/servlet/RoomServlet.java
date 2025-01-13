package servlet;

import dao.RoomDAO;
import model.Room;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/rooms")
public class RoomServlet extends HttpServlet {

    private RoomDAO roomDAO;

    @Override
    public void init() {
        roomDAO = new RoomDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null || action.equals("list")) {
            // Fetch all rooms for room management
            List<Room> roomList = roomDAO.getAllRooms();
            System.out.println("Rooms fetched for admin list: " + roomList.size()); // Debug
            request.setAttribute("rooms", roomList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/room-management.jsp");
            dispatcher.forward(request, response);
        } else if (action.equals("delete")) {
            // Delete a room
            int id = Integer.parseInt(request.getParameter("id"));
            roomDAO.deleteRoom(id);
            response.sendRedirect("rooms?action=list");
        } else if (action.equals("edit")) {
            // Fetch room details for editing
            int id = Integer.parseInt(request.getParameter("id"));
            Room room = roomDAO.getRoomById(id);
            System.out.println("Editing Room ID: " + id); // Debug
            request.setAttribute("room", room);
            request.setAttribute("rooms", roomDAO.getAllRooms());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/room-management.jsp");
            dispatcher.forward(request, response);
        } else if (action.equals("make-reservation")) {
            // Fetch available rooms for user reservation
            List<Room> availableRooms = roomDAO.getAllRooms();
            System.out.println("Rooms fetched for user reservation: " + availableRooms.size()); // Debug
            request.setAttribute("availableRooms", availableRooms);
            RequestDispatcher dispatcher = request.getRequestDispatcher("user/make-reservation.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equals("add")) {
            // Add a new room
            String name = request.getParameter("name");
            int capacity = Integer.parseInt(request.getParameter("capacity"));
            String equipment = request.getParameter("equipment");
            boolean availability = request.getParameter("availability") != null;

            Room room = new Room(name, capacity, equipment, availability);
            roomDAO.addRoom(room);
            System.out.println("Room added: " + room.getName()); // Debug
        } else if (action.equals("update")) {
            // Update existing room
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            int capacity = Integer.parseInt(request.getParameter("capacity"));
            String equipment = request.getParameter("equipment");
            boolean availability = request.getParameter("availability") != null;

            Room room = new Room(id, name, capacity, equipment, availability);
            roomDAO.updateRoom(room);
            System.out.println("Room updated: " + room.getName()); // Debug
        } else if (action.equals("delete")) {
            // Delete a room
            int id = Integer.parseInt(request.getParameter("id"));
            roomDAO.deleteRoom(id);
            System.out.println("Room deleted with ID: " + id); // Debug
        }

        response.sendRedirect("rooms?action=list");
    }
}
