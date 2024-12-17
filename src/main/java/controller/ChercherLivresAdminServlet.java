package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Auteur;
import model.Livre;
import utils.ConnexionBD;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet implementation class ChercherLivresAdminServlet
 */
@WebServlet("/ChercherLivresAdminServlet")
public class ChercherLivresAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChercherLivresAdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
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

            Map<Integer, Livre> livreMap = new HashMap<>(); // Map to store books by their ID

            while (resultSet.next()) {
                int livreId = resultSet.getInt("id");

                // Check if the book already exists in the map
                Livre livre = livreMap.get(livreId);
                if (livre == null) {
                    // Create a new Livre object if it's the first time we encounter this book
                    livre = new Livre();
                    livre.setId(livreId);
                    livre.setTitre(resultSet.getString("titre"));
                    livre.setResume(resultSet.getString("resume"));
                    livre.setAnnee(resultSet.getInt("annee"));
                    livre.setFormat(resultSet.getString("format"));
                    livre.setCategorieId(resultSet.getInt("categorie_id"));
                    livre.setImageUrl(resultSet.getString("image_url")); // Set the image URL
                    livre.setAuteurs(new ArrayList<>()); // Initialize the authors list

                    livreMap.put(livreId, livre); // Store the book in the map
                    livres.add(livre); // Add the book to the list of books
                }

                // Add the author to the book's list of authors
                int auteurId = resultSet.getInt("auteur_id");
                String auteurNom = resultSet.getString("auteur_nom");
                Auteur auteur = new Auteur(auteurId, auteurNom); // Authors don't need a list of books for now
                livre.getAuteurs().add(auteur); // Add the author to the book's authors list
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (livres.isEmpty()) {
            System.out.println("No books found for query: " + query); // Debugging log
        }

        // Pass the list of livres to the JSP for rendering
        request.setAttribute("livres", livres);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/view/livreListeAdmin.jsp");
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
