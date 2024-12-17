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

/**
 * Servlet implementation class ModifProfilServlet
 */
@WebServlet("/ModifProfilServlet")
public class ModifProfilServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ModifProfilServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        String userName = (String) session.getAttribute("userName"); 
        String email = (String) session.getAttribute("userEmail"); 
        String motDePasse = request.getParameter("motDePasse");

        if (userId == null) {
            response.sendRedirect("/GestionBib/view/login.jsp");
            return;
        }

        String nom = request.getParameter("nom"); // Get the updated name from the form
        if (nom == null || nom.isEmpty()) {
            nom = userName; // Use the current name if the form field is empty
        }

        try (Connection con = ConnexionBD.connect()) {
            String sql = "UPDATE gest_bib.utilisateurs SET nom = ?, email = ?";
            if (motDePasse != null && !motDePasse.isEmpty()) {
                sql += ", mot_de_passe = ?";
            }
            sql += " WHERE id = ?";

            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, nom);
                pst.setString(2, email);

                if (motDePasse != null && !motDePasse.isEmpty()) {
                    pst.setString(3, motDePasse);
                    pst.setInt(4, userId);
                } else {
                    pst.setInt(3, userId);
                }

                int rowsUpdated = pst.executeUpdate();
                if (rowsUpdated > 0) {
                    session.setAttribute("userName", nom);
                    session.setAttribute("userEmail", email);
                    response.sendRedirect("/GestionBib/view/mon-compte.jsp?success=true");
                } else {
                    response.sendRedirect("/GestionBib/view/mon-compte.jsp?error=true");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("/GestionBib/view/mon-compte.jsp?error=true");
        }
    }
}
