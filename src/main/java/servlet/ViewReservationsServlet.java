package servlet;

import dao.ReservationDAO;
import model.Reservation;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/user/view-reservations")
public class ViewReservationsServlet extends HttpServlet {

    private ReservationDAO reservationDAO;

    @Override
    public void init() throws ServletException {
        reservationDAO = new ReservationDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle the "action" parameter
        String action = request.getParameter("action");

        if (action == null || action.equals("list")) {
            // Check if the user is logged in
            Integer userId = (Integer) request.getSession().getAttribute("userId");

            if (userId == null) {
                // Redirect to login page if not logged in
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            // Fetch reservations for the logged-in user
            List<Reservation> reservations = reservationDAO.getReservationsByUserId(userId);

            // Add reservations to request attributes
            request.setAttribute("reservations", reservations);

            // Forward to JSP for displaying reservations
            RequestDispatcher dispatcher = request.getRequestDispatcher("/user/view-reservations.jsp");
            dispatcher.forward(request, response);
        } else {
            // Invalid action, return an error
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("view-reservations?action=list");


    }
}
