package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.ConnexionBD;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");
        
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection con = ConnexionBD.connect()) {
            String query = "SELECT * FROM gest_bib.utilisateurs WHERE email = ?";
            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setString(1, email);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    int userId = rs.getInt("id");  // Fetch the user's ID
                    String storedPassword = rs.getString("mot_de_passe");
                    String storedRole = rs.getString("role");
                    String storedNom = rs.getString("nom"); // Fetch the user's name

                    // Compare the provided password with the stored one
                    if (motDePasse.equals(storedPassword)) {
                        // Set the session attributes
                        HttpSession session = request.getSession();
                        session.setAttribute("userId", userId);       // Store the user ID
                        session.setAttribute("userEmail", email);     // Store the user email
                        session.setAttribute("userRole", storedRole); // Store the user role
                        session.setAttribute("userName", storedNom);  // Store the user name

                        // Redirect based on the user's role
                        if ("Client".equals(storedRole)) {
                            response.sendRedirect("ChercherLivresServlet");
                        } else {
                            response.sendRedirect("ChercherLivresServlet");
                        }
                    } else {
                        request.setAttribute("message", "Mot de passe incorrect.");
                        request.getRequestDispatcher("/view/login.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("message", "Email non trouvé.");
                    request.getRequestDispatcher("/view/login.jsp").forward(request, response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error during login.");
        }
    }
}
