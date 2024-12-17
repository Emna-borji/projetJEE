package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Livre;
import model.Auteur; // Import the Auteur class
import utils.ConnexionBD;

@WebServlet("/ChercherLivresServlet")
public class ChercherLivresServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ChercherLivresServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().write("Servlet is being invoked!");
        
        String query = request.getParameter("query");
        List<Livre> livres = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = ConnexionBD.connect()) {
            String sql = "";
            
            if (query == null || query.trim().isEmpty()) {
                // Default query: Fetch the last added books
                sql = "SELECT l.id, l.titre, l.resume, l.annee, l.format, l.categorie_id, l.image_url, "
                    + "a.id AS auteur_id, a.nom AS auteur_nom "
                    + "FROM gest_bib.livres l "
                    + "JOIN gest_bib.livres_auteurs la ON l.id = la.livre_id "
                    + "JOIN gest_bib.auteurs a ON la.auteur_id = a.id "
                    + "ORDER BY l.id DESC LIMIT 10"; // Adjust the LIMIT as needed
            } else {
                // Search query: Fetch books based on search criteria
                sql = "SELECT l.id, l.titre, l.resume, l.annee, l.format, l.categorie_id, l.image_url, "
                    + "a.id AS auteur_id, a.nom AS auteur_nom "
                    + "FROM gest_bib.livres l "
                    + "JOIN gest_bib.livres_auteurs la ON l.id = la.livre_id "
                    + "JOIN gest_bib.auteurs a ON la.auteur_id = a.id "
                    + "WHERE l.titre ILIKE ? "
                    + "OR a.nom ILIKE ? "
                    + "OR l.categorie_id IN (SELECT id FROM gest_bib.categories WHERE nom ILIKE ?)";
            }

            PreparedStatement statement = connection.prepareStatement(sql);
            if (query != null && !query.trim().isEmpty()) {
                statement.setString(1, "%" + query + "%");
                statement.setString(2, "%" + query + "%");
                statement.setString(3, "%" + query + "%");
            }

            ResultSet resultSet = statement.executeQuery();

            Map<Integer, Livre> livreMap = new HashMap<>(); 

            while (resultSet.next()) {
                int livreId = resultSet.getInt("id");

                Livre livre = livreMap.get(livreId);
                if (livre == null) {
                    livre = new Livre();
                    livre.setId(livreId);
                    livre.setTitre(resultSet.getString("titre"));
                    livre.setResume(resultSet.getString("resume"));
                    livre.setAnnee(resultSet.getInt("annee"));
                    livre.setFormat(resultSet.getString("format"));
                    livre.setCategorieId(resultSet.getInt("categorie_id"));
                    livre.setImageUrl(resultSet.getString("image_url")); 
                    livre.setAuteurs(new ArrayList<>());

                    livreMap.put(livreId, livre); 
                    livres.add(livre); 
                }

                int auteurId = resultSet.getInt("auteur_id");
                String auteurNom = resultSet.getString("auteur_nom");
                Auteur auteur = new Auteur(auteurId, auteurNom); 
                livre.getAuteurs().add(auteur); 
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (livres.isEmpty()) {
            System.out.println("No books found for query: " + query); 
        }

        request.setAttribute("livres", livres);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/view/livreListe.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
