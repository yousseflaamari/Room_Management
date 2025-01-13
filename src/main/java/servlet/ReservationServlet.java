package servlet;

import dao.ReservationDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@WebServlet("/view-reservations")
public class ReservationServlet extends HttpServlet {
    private ReservationDAO reservationDAO;

    @Override
    public void init() throws ServletException {
        reservationDAO = new ReservationDAO();
    }

    // Handle GET request to fetch all reservations
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Fetch reservations with details
            List<Object[]> reservations = reservationDAO.getReservationsWithDetails();
            request.setAttribute("reservations", reservations);
            request.getRequestDispatcher("admin/reservations.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to load reservations.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Retrieve and validate parameters
            String idParam = request.getParameter("id");
            String status = request.getParameter("status");

            if (idParam == null || status == null) {
                throw new IllegalArgumentException("Missing required parameters: id or status.");
            }

            int id = Integer.parseInt(idParam);

            // Update reservation status
            reservationDAO.updateReservationStatusById(id, status);

            // Fetch updated reservations list
            List<Object[]> reservations = reservationDAO.getReservationsWithDetails();

            // Set success message and updated reservations in request scope
            request.setAttribute("success", "Reservation updated successfully.");
            request.setAttribute("reservations", reservations);

            // Forward the request back to the JSP
            request.getRequestDispatcher("admin/reservations.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Invalid numeric input data.");
            request.getRequestDispatcher("admin/reservations.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("admin/reservations.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to process the request: " + e.getMessage());
            request.getRequestDispatcher("admin/reservations.jsp").forward(request, response);
        }
    }

}
