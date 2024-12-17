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
import java.sql.SQLException;

/**
 * Servlet implementation class SupprimerLivreServlet
 */
@WebServlet("/SupprimerLivreServlet")
public class SupprimerLivreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupprimerLivreServlet() {
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
        String userRole = (String) session.getAttribute("userRole");
        if (userRole == null || !userRole.equals("Admin")) {
            response.sendRedirect("/GestionBib/view/login.jsp");
            return;
        }
		int idLivre = Integer.parseInt(request.getParameter("idLivre"));
		String requete = "DELETE FROM gest_bib.livres WHERE id = ?";

        try (Connection connection = ConnexionBD.connect();
             PreparedStatement stmt = connection.prepareStatement(requete)) {

            stmt.setInt(1, idLivre);

            int lignesAffectees = stmt.executeUpdate();

            if (lignesAffectees > 0) {
                response.sendRedirect("ChercherLivresServlet");
            } else {
                response.getWriter().write("Erreur lors de la suppression du livre.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Erreur de connexion à la base de données.");
        }
	}

}
