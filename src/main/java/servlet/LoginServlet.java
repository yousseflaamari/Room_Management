package servlet;

import dao.UserDAO;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println("Username received: " + username); // Debugging log
        System.out.println("Password received: " + password); // Debugging log

        if (username == null || password == null) {
            System.out.println("Form data is not being received correctly.");
            request.setAttribute("error", "Username or password is missing.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        User user = userDAO.getUserByUsernameAndPassword(username, password);

        if (user != null) {
            // Store user details in the session
            request.getSession().setAttribute("userId", user.getId());
            request.getSession().setAttribute("userName", user.getUsername());
            request.getSession().setAttribute("userRole", user.getRole());

            // Redirect based on role
            if ("admin".equalsIgnoreCase(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/admin-dashboard.jsp");
            } else if ("user".equalsIgnoreCase(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/user/user-dashboard.jsp");
            } else {
                // If role is unrecognized, redirect to a default page or show an error
                request.setAttribute("error", "Unrecognized role: " + user.getRole());
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } else {
            // Invalid credentials
            request.setAttribute("error", "Invalid username or password.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
