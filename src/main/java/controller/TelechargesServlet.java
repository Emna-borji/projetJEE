package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.LivresTelecharges;
import utils.ConnexionBD;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/TelechargesServlet")
public class TelechargesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public TelechargesServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer idUtilisateur = (Integer) session.getAttribute("userId");

        if (idUtilisateur == null) {
            response.sendRedirect("/GestionBib/view/login.jsp");
            return;
        }

        List<LivresTelecharges> telechargesList = new ArrayList<>();

        String sql = "SELECT lt.id, lt.utilisateur_id, lt.livre_id, l.titre, l.image_url, lt.date_telechargement "
                   + "FROM gest_bib.livres_telecharges lt "
                   + "JOIN gest_bib.livres l ON lt.livre_id = l.id "
                   + "WHERE lt.utilisateur_id = ?";

        try (Connection connexion = ConnexionBD.connect()) {
            PreparedStatement stmt = connexion.prepareStatement(sql);
            stmt.setInt(1, idUtilisateur);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int utilisateurId = rs.getInt("utilisateur_id");
                int livreId = rs.getInt("livre_id");
                String titre = rs.getString("titre");
                String imageUrl = rs.getString("image_url");
                Date dateTelechargement = rs.getDate("date_telechargement");

                LivresTelecharges telecharge = new LivresTelecharges(id, utilisateurId, livreId, titre, imageUrl, dateTelechargement);
                telechargesList.add(telecharge);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Erreur lors de la récupération des livres téléchargés.");
        }

        request.setAttribute("telechargesList", telechargesList);
        request.getRequestDispatcher("/view/telecharges.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
