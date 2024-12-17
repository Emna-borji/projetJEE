package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Auteur;
import model.Livre;
import utils.ConnexionBD;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Servlet implementation class LivreDetailsAdminServlet
 */
@WebServlet("/LivreDetailsAdminServlet")
public class LivreDetailsAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LivreDetailsAdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String idParam = request.getParameter("id");
        Livre livre = null;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = ConnexionBD.connect()) {
        	String sql = "SELECT l.id, l.titre, l.resume, l.annee, l.format, l.categorie_id, "
                    + "l.image_url, "
                    + "a.id AS auteur_id, a.nom AS auteur_nom "
                    + "FROM gest_bib.livres l "
                    + "JOIN gest_bib.livres_auteurs la ON l.id = la.livre_id "
                    + "JOIN gest_bib.auteurs a ON la.auteur_id = a.id "
                    + "WHERE l.id = ?";


            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(idParam));

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                livre = new Livre();
                livre.setId(resultSet.getInt("id"));
                livre.setTitre(resultSet.getString("titre"));
                livre.setResume(resultSet.getString("resume"));
                livre.setAnnee(resultSet.getInt("annee"));
                livre.setFormat(resultSet.getString("format"));
                livre.setCategorieId(resultSet.getInt("categorie_id"));
                livre.setImageUrl(resultSet.getString("image_url")); // Add image URL
                livre.setAuteurs(new ArrayList<>());
                
                do {
                    Auteur auteur = new Auteur(resultSet.getInt("auteur_id"), resultSet.getString("auteur_nom"));
                    livre.getAuteurs().add(auteur);
                } while (resultSet.next());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("livre", livre);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/view/livreDetailsAdmin.jsp");
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
