package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.ConnexionBD;
import model.Livre; // Assuming you have a Livre class in the "model" package

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class ModifLivreServlet
 */
@WebServlet("/ModifLivreServlet")
public class ModifLivreServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifLivreServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userRole = (String) session.getAttribute("userRole");
        if (userRole == null || !userRole.equals("Admin")) {
            response.sendRedirect("/GestionBib/view/login.jsp");
            return;
        }

        String idLivre = request.getParameter("idLivre");
        if (idLivre == null || idLivre.trim().isEmpty()) {
            response.sendRedirect("/GestionBib/view/listeLivres.jsp"); 
            return;
        }

        try (Connection connection = ConnexionBD.connect()) {
            String sql = "SELECT id, titre, resume, annee, format, categorie_id, image_url FROM gest_bib.livres WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, Integer.parseInt(idLivre));

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        Livre livre = new Livre();
                        livre.setId(resultSet.getInt("id"));
                        livre.setTitre(resultSet.getString("titre"));
                        livre.setResume(resultSet.getString("resume"));
                        livre.setAnnee(resultSet.getInt("annee"));
                        livre.setFormat(resultSet.getString("format"));
                        livre.setCategorieId(resultSet.getInt("categorie_id"));
                        livre.setImageUrl(resultSet.getString("image_url"));

                        request.setAttribute("livre", livre);
                        
                        request.getRequestDispatcher("/view/modifierLivre.jsp").forward(request, response);
                    } else {
                        response.sendRedirect("/GestionBib/view/listeLivres.jsp"); 
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Database error: " + e.getMessage(), e);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Implementation for POST if needed
    }
}
