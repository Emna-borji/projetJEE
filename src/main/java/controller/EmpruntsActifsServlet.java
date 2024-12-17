package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.LivresEmpruntes;
import utils.ConnexionBD;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class EmpruntsActifsServlet
 */
@WebServlet("/EmpruntsActifsServlet")
public class EmpruntsActifsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmpruntsActifsServlet() {
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
        	String query = "SELECT e.id, u.nom AS utilisateur, b.titre AS livre, e.date_emprunt, e.date_retour " +
                    "FROM gest_bib.livres_empruntes e " +
                    "JOIN gest_bib.utilisateurs u ON e.user_id = u.id " +
                    "JOIN gest_bib.livres b ON e.book_id = b.id " +
                    "WHERE e.date_retour > CURRENT_DATE";  // Vérifie si la date de retour est dans le futur

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
            	
                ResultSet rs = stmt.executeQuery();
                List<LivresEmpruntes> empruntsActifs = new ArrayList<>();
                while (rs.next()) {
                    LivresEmpruntes emprunt = new LivresEmpruntes(
                        rs.getInt("id"),
                        rs.getString("utilisateur"),
                        rs.getString("livre"),
                        rs.getDate("date_emprunt"),
                        rs.getDate("date_retour")
                    );
                    empruntsActifs.add(emprunt);
                }
                System.out.println(empruntsActifs);
                request.setAttribute("empruntsActifs", empruntsActifs);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/view/empruntsActifs.jsp");
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
