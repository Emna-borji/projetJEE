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
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class ToggleFavorisServlet
 */
@WebServlet("/ToggleFavorisServlet")
public class ToggleFavorisServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ToggleFavorisServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
        Integer idUtilisateur = (Integer) session.getAttribute("userId");

        if (idUtilisateur == null || !"Client".equals(session.getAttribute("userRole"))) {
            response.sendRedirect("/GestionBib/view/login.jsp");
            return;
        }
		
        String idLivre = request.getParameter("idLivre");

        try (Connection connexion = ConnexionBD.connect()) {
            // Check if the book is already in the user's favorites
            String verifierFavoriSql = "SELECT COUNT(*) FROM gest_bib.favoris WHERE utilisateur_id = ? AND livre_id = ?";
            PreparedStatement verifierFavoriStmt = connexion.prepareStatement(verifierFavoriSql);
            verifierFavoriStmt.setInt(1, idUtilisateur);
            verifierFavoriStmt.setInt(2, Integer.parseInt(idLivre));

            ResultSet rs = verifierFavoriStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                // If the book is already in the favorites table, remove it
                String supprimerFavoriSql = "DELETE FROM gest_bib.favoris WHERE utilisateur_id = ? AND livre_id = ?";
                PreparedStatement supprimerFavoriStmt = connexion.prepareStatement(supprimerFavoriSql);
                supprimerFavoriStmt.setInt(1, idUtilisateur);
                supprimerFavoriStmt.setInt(2, Integer.parseInt(idLivre));

                supprimerFavoriStmt.executeUpdate();
                request.setAttribute("message", "Livre retiré de vos favoris.");
            } else {
                // If the book is not in the favorites table, add it
                String ajouterFavoriSql = "INSERT INTO gest_bib.favoris (utilisateur_id, livre_id) VALUES (?, ?)";
                PreparedStatement ajouterFavoriStmt = connexion.prepareStatement(ajouterFavoriSql);
                ajouterFavoriStmt.setInt(1, idUtilisateur);
                ajouterFavoriStmt.setInt(2, Integer.parseInt(idLivre));

                ajouterFavoriStmt.executeUpdate();
                request.setAttribute("message", "Livre ajouté à vos favoris.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Erreur de base de données lors de l'ajout ou de la suppression du favori.");
        }

        // Forward the request to the book details page with the appropriate message
        request.getRequestDispatcher("/LivreDetailsServlet?id=" + idLivre).forward(request, response);
	}

}
