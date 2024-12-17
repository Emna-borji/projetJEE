package controller;

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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class EmpruntsServlet
 */
@WebServlet("/EmpruntsServlet")
public class EmpruntsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmpruntsServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer idUtilisateur = (Integer) session.getAttribute("userId");

        if (idUtilisateur == null) {
            response.sendRedirect("/GestionBib/view/login.jsp");
            return;
        }

        List<LivresEmpruntes> empruntsList = new ArrayList<>();

        String sql = "SELECT le.id, le.user_id, le.book_id, l.titre, l.image_url, le.date_emprunt, le.date_retour "
                   + "FROM gest_bib.livres_empruntes le "
                   + "JOIN gest_bib.livres l ON le.book_id = l.id "
                   + "WHERE le.user_id = ?";

        try (Connection connexion = ConnexionBD.connect()) {
            PreparedStatement stmt = connexion.prepareStatement(sql);
            stmt.setInt(1, idUtilisateur);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int userId = rs.getInt("user_id");
                int bookId = rs.getInt("book_id");
                String titre = rs.getString("titre");
                String image = rs.getString("image_url"); 
                Date dateEmprunt = rs.getDate("date_emprunt");
                Date dateRetour = rs.getDate("date_retour");

                LivresEmpruntes emprunt = new LivresEmpruntes(id, userId, bookId, titre, image, dateEmprunt, dateRetour);
                empruntsList.add(emprunt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Erreur lors de la récupération des livres empruntés.");
        }

        request.setAttribute("empruntsList", empruntsList);
        request.getRequestDispatcher("/view/emprunts.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
