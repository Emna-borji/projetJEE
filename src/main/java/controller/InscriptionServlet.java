package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ConnexionBD;
import utils.HashUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/InscriptionServlet")
public class InscriptionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public InscriptionServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form parameters
    	String nom = request.getParameter("nom");
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");
        String role = request.getParameter("role");

        // If role is not provided, default to "Client"
        if (role == null || role.isEmpty()) {
            role = "Client";
        }

        try (Connection con = ConnexionBD.connect()) {
            String query = "INSERT INTO gest_bib.utilisateurs (nom, email, mot_de_passe, role) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setString(1, nom);
                pst.setString(2, email);
                pst.setString(3, motDePasse); // Save plain text password
                pst.setString(4, role);

                int result = pst.executeUpdate();
                if (result > 0) {
                    response.sendRedirect("/GestionBib/view/login.jsp"); // Redirect to login page
                } else {
                    request.setAttribute("message", "Erreur lors de l'inscription, essayez à nouveau.");
                    request.getRequestDispatcher("/inscription.jsp").forward(request, response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error while processing the registration.");
        }
    }
}
