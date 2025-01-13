package servlet;

import dao.ReservationDAO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/statistics")
public class AdminStatisticsServlet extends HttpServlet {

    private ReservationDAO reservationDAO;

    @Override
    public void init() throws ServletException {
        reservationDAO = new ReservationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle the "action" parameter
        String action = request.getParameter("action");

        if (action == null || action.equals("list")) { // Use "list" for fetching statistics
            try {
                // Fetch statistics
                int totalReservations = reservationDAO.getTotalReservations();
                List<Object[]> mostBookedRooms = reservationDAO.getMostBookedRooms();
                List<Object[]> reservationsPerDay = reservationDAO.getReservationsPerDay();
                List<Object[]> reservationsByUser = reservationDAO.getReservationsByUser();

                // Debugging logs
                System.out.println("=== Admin Statistics Debugging ===");
                System.out.println("Total Reservations: " + totalReservations);
                System.out.println("Most Booked Rooms: " + (mostBookedRooms != null ? mostBookedRooms.size() : "No Data"));
                System.out.println("Reservations Per Day: " + (reservationsPerDay != null ? reservationsPerDay.size() : "No Data"));
                System.out.println("Reservations by User: " + (reservationsByUser != null ? reservationsByUser.size() : "No Data"));

                // Verify content of the lists
                if (mostBookedRooms != null) {
                    for (Object[] room : mostBookedRooms) {
                        System.out.println("Room ID: " + room[0] + ", Reservations: " + room[1]);
                    }
                }

                if (reservationsPerDay != null) {
                    for (Object[] day : reservationsPerDay) {
                        System.out.println("Date: " + day[0] + ", Reservations: " + day[1]);
                    }
                }

                if (reservationsByUser != null) {
                    for (Object[] user : reservationsByUser) {
                        System.out.println("User ID: " + user[0] + ", Reservations: " + user[1]);
                    }
                }

                // Add statistics data to request attributes
                request.setAttribute("totalReservations", totalReservations);
                request.setAttribute("mostBookedRooms", mostBookedRooms);
                request.setAttribute("reservationsPerDay", reservationsPerDay);
                request.setAttribute("reservationsByUser", reservationsByUser);

                // Forward to the statistics JSP
                RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/statistics.jsp");
                dispatcher.forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching statistics.");
            }
        } else {
            // Invalid action, return an error
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirect to GET for processing the request
        response.sendRedirect(request.getContextPath() + "/admin/statistics?action=list");
    }
}
