package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Auteur;
import utils.ConnexionBD;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class GererAuteursServlet
 */
@WebServlet("/GererAuteursServlet")
public class GererAuteursServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GererAuteursServlet() {
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

        try (Connection connection = ConnexionBD.connect()) {
            String query = "SELECT * FROM gest_bib.auteurs";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();
                List<Auteur> auteurs = new ArrayList<>();
                while (rs.next()) {
                    Auteur auteur = new Auteur(rs.getInt("id"), rs.getString("nom"), rs.getDate("date_de_naissance"));
                    auteurs.add(auteur);
                }
                request.setAttribute("auteurs", auteurs);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/view/gererAuteurs.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
