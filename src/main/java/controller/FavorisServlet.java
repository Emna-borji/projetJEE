package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Favoris;
import utils.ConnexionBD;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class FavorisServlet
 */
@WebServlet("/FavorisServlet")
public class FavorisServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FavorisServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer idUtilisateur = (Integer) session.getAttribute("userId");

        if (idUtilisateur == null) {
            response.sendRedirect("/GestionBib/view/login.jsp");
            return;
        }

        List<Favoris> favorisList = new ArrayList<>();

        String sql = "SELECT f.id, f.utilisateur_id, f.livre_id, l.titre, l.image_url, f.date_ajout "
                   + "FROM gest_bib.favoris f "
                   + "JOIN gest_bib.livres l ON f.livre_id = l.id "
                   + "WHERE f.utilisateur_id = ?";

        try (Connection connexion = ConnexionBD.connect()) {
            PreparedStatement stmt = connexion.prepareStatement(sql);
            stmt.setInt(1, idUtilisateur);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int userId = rs.getInt("utilisateur_id");
                int bookId = rs.getInt("livre_id");
                String titre = rs.getString("titre");
                String imageUrl = rs.getString("image_url");  // Get image_url from DB
                Date dateAjout = rs.getDate("date_ajout");

                Favoris favori = new Favoris(id, userId, bookId, titre, imageUrl, dateAjout);
                favorisList.add(favori);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Erreur lors de la récupération des favoris.");
        }

        request.setAttribute("favorisList", favorisList);
        request.getRequestDispatcher("/view/favoris.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
