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
 * Servlet implementation class UpdateLivreServlet
 */
@WebServlet("/UpdateLivreServlet")
public class UpdateLivreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateLivreServlet() {
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

        if (userRole == null || !"Admin".equals(userRole)) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Get the form data
        int idLivre = Integer.parseInt(request.getParameter("idLivre"));
        String titre = request.getParameter("titre");
        String resume = request.getParameter("resume");
        int annee = Integer.parseInt(request.getParameter("annee"));
        String format = request.getParameter("format");
        String imageUrl = request.getParameter("imageUrl");

        try (Connection connection = ConnexionBD.connect()) {
            String query = "UPDATE gest_bib.livres SET titre = ?, resume = ?, annee = ?, format = ?, image_url = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, titre);
                stmt.setString(2, resume);
                stmt.setInt(3, annee);
                stmt.setString(4, format);
                stmt.setString(5, imageUrl);
                stmt.setInt(6, idLivre);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    response.sendRedirect("ChercherLivresAdminServlet");
                } else {
                    response.getWriter().write("Erreur lors de la mise à jour du livre.");
                }
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
	}

}
