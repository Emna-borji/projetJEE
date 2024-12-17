package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ConnexionBD;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet implementation class AjouterAuteurServlet
 */
@WebServlet("/AjouterAuteurServlet")
public class AjouterAuteurServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjouterAuteurServlet() {
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
		String nom = request.getParameter("nom");
        Date dateDeNaissance = Date.valueOf(request.getParameter("date_de_naissance"));

        try (Connection connection = ConnexionBD.connect()) {
            String query = "INSERT INTO gest_bib.auteurs (nom, date_de_naissance) VALUES (?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, nom);
                stmt.setDate(2, dateDeNaissance);
                stmt.executeUpdate();
            }
            response.sendRedirect("/GestionBib/GererAuteursServlet");
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

}
