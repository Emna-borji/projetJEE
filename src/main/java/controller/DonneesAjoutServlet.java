package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Auteur;
import model.Categorie;
import utils.ConnexionBD;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class DonneesAjoutServlet
 */
@WebServlet("/DonneesAjoutServlet")
public class DonneesAjoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DonneesAjoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Auteur> auteurs = new ArrayList<>();
        List<Categorie> categories = new ArrayList<>();

        try (Connection connection = ConnexionBD.connect()) {
            // 1️⃣ Query for authors (auteurs)
            String auteurQuery = "SELECT id, nom FROM gest_bib.auteurs";
            try (PreparedStatement stmt = connection.prepareStatement(auteurQuery);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nom = rs.getString("nom");
                    auteurs.add(new Auteur(id, nom));
                }
            }

            // 2️⃣ Query for categories (categories)
            String categorieQuery = "SELECT id, nom FROM gest_bib.categories";
            try (PreparedStatement stmt = connection.prepareStatement(categorieQuery);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nom = rs.getString("nom");
                    categories.add(new Categorie(id, nom));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Nombre de catégories récupérées : " + categories.size());

        // Add the lists to the request scope
        request.setAttribute("auteurs", auteurs);
        request.setAttribute("categories", categories);

        // Forward to the JSP page
        request.getRequestDispatcher("/view/ajouterLivre.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
