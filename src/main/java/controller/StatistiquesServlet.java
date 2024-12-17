package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.StatistiqueMensuelle;
import utils.ConnexionBD;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class StatistiquesServlet
 */
@WebServlet("/StatistiquesServlet")
public class StatistiquesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StatistiquesServlet() {
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

            // Récupérer les statistiques sur les livres empruntés
            String empruntsQuery = "SELECT EXTRACT(YEAR FROM date_emprunt) AS annee, EXTRACT(MONTH FROM date_emprunt) AS mois, COUNT(*) AS total_emprunts " +
                                   "FROM gest_bib.livres_empruntes " +
                                   "GROUP BY EXTRACT(YEAR FROM date_emprunt), EXTRACT(MONTH FROM date_emprunt) " +
                                   "ORDER BY annee DESC, mois DESC";

            try (PreparedStatement stmt = connection.prepareStatement(empruntsQuery)) {
                ResultSet rsEmprunts = stmt.executeQuery();
                List<StatistiqueMensuelle> empruntsStats = new ArrayList<>();
                while (rsEmprunts.next()) {
                    StatistiqueMensuelle stat = new StatistiqueMensuelle(
                        rsEmprunts.getInt("annee"),
                        rsEmprunts.getInt("mois"),
                        rsEmprunts.getInt("total_emprunts"),
                        0, 0 
                    );
                    empruntsStats.add(stat);
                }
                request.setAttribute("empruntsStats", empruntsStats);
            }

            // Récupérer les statistiques sur les livres téléchargés
            String telechargementsQuery = "SELECT EXTRACT(YEAR FROM date_telechargement) AS annee, EXTRACT(MONTH FROM date_telechargement) AS mois, COUNT(*) AS total_telechargements " +
                                          "FROM gest_bib.livres_telecharges " +
                                          "GROUP BY EXTRACT(YEAR FROM date_telechargement), EXTRACT(MONTH FROM date_telechargement) " +
                                          "ORDER BY annee DESC, mois DESC";

            try (PreparedStatement stmt = connection.prepareStatement(telechargementsQuery)) {
                ResultSet rsTelechargements = stmt.executeQuery();
                List<StatistiqueMensuelle> telechargementsStats = new ArrayList<>();
                while (rsTelechargements.next()) {
                    StatistiqueMensuelle stat = new StatistiqueMensuelle(
                        rsTelechargements.getInt("annee"),
                        rsTelechargements.getInt("mois"),
                        0,  
                        rsTelechargements.getInt("total_telechargements"),
                        0
                    );
                    telechargementsStats.add(stat);
                }
                request.setAttribute("telechargementsStats", telechargementsStats);
            }

            // Récupérer les statistiques sur les livres ajoutés
            String ajoutsQuery = "SELECT EXTRACT(YEAR FROM date_ajout) AS annee, EXTRACT(MONTH FROM date_ajout) AS mois, COUNT(*) AS total_ajouts " +
                                 "FROM gest_bib.favoris " +
                                 "GROUP BY EXTRACT(YEAR FROM date_ajout), EXTRACT(MONTH FROM date_ajout) " +
                                 "ORDER BY annee DESC, mois DESC";

            try (PreparedStatement stmt = connection.prepareStatement(ajoutsQuery)) {
                ResultSet rsAjouts = stmt.executeQuery();
                List<StatistiqueMensuelle> ajoutsStats = new ArrayList<>();
                while (rsAjouts.next()) {
                    StatistiqueMensuelle stat = new StatistiqueMensuelle(
                        rsAjouts.getInt("annee"),
                        rsAjouts.getInt("mois"),
                        0, 0, 
                        rsAjouts.getInt("total_ajouts")
                    );
                    ajoutsStats.add(stat);
                }
                request.setAttribute("ajoutsStats", ajoutsStats);
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("/view/statistiques.jsp");
            dispatcher.forward(request, response);

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
