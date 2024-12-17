package controller;

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
import java.util.List;

/**
 * Servlet implementation class LivresParAuteurServlet
 */
@WebServlet("/LivresParAuteurServlet")
public class LivresParAuteurServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LivresParAuteurServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
        Integer idUtilisateur = (Integer) session.getAttribute("userId");

        if (idUtilisateur == null || !"Admin".equals(session.getAttribute("userRole"))) {
            response.sendRedirect("/GestionBib/view/login.jsp");
            return;
        }
		List<Auteur> auteurs = obtenirTousLesAuteurs();
        request.setAttribute("auteurs", auteurs);

        String idAuteurParam = request.getParameter("auteurId");
        List<Livre> livres = new ArrayList<>();
        
        if (idAuteurParam != null && !idAuteurParam.isEmpty()) {
            int idAuteur = Integer.parseInt(idAuteurParam);
            livres = obtenirLivresParAuteurId(idAuteur);
            String nomAuteur = obtenirNomAuteurParId(idAuteur);
            request.setAttribute("nomAuteur", nomAuteur);
        }

        request.setAttribute("livres", livres);
        request.getRequestDispatcher("/view/livresParAuteur.jsp").forward(request, response);
    
	}
	private List<Livre> obtenirLivresParAuteurId(int idAuteur) {
        List<Livre> livres = new ArrayList<>();
        String requete = "SELECT l.id, l.titre, l.resume, l.annee, l.format, l.image_url " +
                         "FROM gest_bib.livres l " +
                         "JOIN gest_bib.livres_auteurs la ON l.id = la.livre_id " +
                         "WHERE la.auteur_id = ?";

        try (Connection connection = ConnexionBD.connect();
             PreparedStatement stmt = connection.prepareStatement(requete)) {
            stmt.setInt(1, idAuteur);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Livre livre = new Livre(
                    rs.getInt("id"), 
                    rs.getString("titre"), 
                    rs.getString("resume"), 
                    rs.getInt("annee"), 
                    rs.getString("format"),
                    rs.getString("image_url")
                );
                livres.add(livre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livres;
    }

    private String obtenirNomAuteurParId(int idAuteur) {
        String nomAuteur = "";
        String requete = "SELECT nom FROM gest_bib.auteurs WHERE id = ?";

        try (Connection connection = ConnexionBD.connect();
             PreparedStatement stmt = connection.prepareStatement(requete)) {
            stmt.setInt(1, idAuteur);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nomAuteur = rs.getString("nom");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nomAuteur;
    }

    private List<Auteur> obtenirTousLesAuteurs() {
        List<Auteur> auteurs = new ArrayList<>();
        String requete = "SELECT id, nom FROM gest_bib.auteurs";

        try (Connection connection = ConnexionBD.connect();
             PreparedStatement stmt = connection.prepareStatement(requete)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Auteur auteur = new Auteur(rs.getInt("id"), rs.getString("nom"));
                auteurs.add(auteur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return auteurs;
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
