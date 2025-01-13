package servlet;

import dao.ReservationDAO;
import dao.RoomDAO;
import model.Reservation;
import model.Room;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@WebServlet("/user/reserve-rooms") // Corrected URL mapping
public class MakeReservationServlet extends HttpServlet {

    private RoomDAO roomDAO;
    private ReservationDAO reservationDAO;

    @Override
    public void init() throws ServletException {
        roomDAO = new RoomDAO();
        reservationDAO = new ReservationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null || action.equals("list")) {
            // Fetch all available rooms for reservation
            List<Room> roomList = roomDAO.getAvailableRooms();
            request.setAttribute("rooms", roomList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/user/make-reservation.jsp");
            dispatcher.forward(request, response);
        } else {
            // Handle other actions or return a default error response
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Invalid action for GET method");
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if the user is logged in
        Integer userId = (Integer) request.getSession().getAttribute("userId");

        if (userId == null) {
            // If not logged in, redirect to login page
            request.setAttribute("error", "Please log in to make a reservation.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Retrieve reservation details from the form
        int roomId = Integer.parseInt(request.getParameter("room"));
        LocalDate date = LocalDate.parse(request.getParameter("date"));
        LocalTime startTime = LocalTime.parse(request.getParameter("startTime"));
        LocalTime endTime = LocalTime.parse(request.getParameter("endTime"));

        // Verify room availability and save the reservation
        boolean isRoomAvailable = reservationDAO.isRoomAvailable(roomId, date, startTime, endTime);
        if (!isRoomAvailable) {
            request.setAttribute("error", "The selected room is not available.");
            doGet(request, response);
            return;
        }

        // Create and save the reservation with the logged-in user's ID
        Reservation reservation = new Reservation(date, startTime, endTime, "Pending", roomId, userId);
        reservationDAO.saveReservation(reservation);

        // Redirect with a success message
        request.getSession().setAttribute("success", "Reservation made successfully.");
        response.sendRedirect("reserve-rooms?action=list");
    }


}
