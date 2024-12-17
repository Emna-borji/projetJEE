package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import utils.ConnexionBD;

/**
 * Servlet implementation class EmprunterLivreServlet
 */
@WebServlet("/EmprunterLivreServlet")
public class EmprunterLivreServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmprunterLivreServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
        Integer idUtilisateur = (Integer) session.getAttribute("userId");

        if (idUtilisateur == null || !"Client".equals(session.getAttribute("userRole"))) {
            response.sendRedirect("/GestionBib/view/login.jsp");
            return;
        }
        Integer userId = idUtilisateur;

        // Get the book ID from the form
        String livreId = request.getParameter("livreId");

        try (Connection connection = ConnexionBD.connect()) {
            String checkSql = "SELECT COUNT(*) FROM gest_bib.livres_empruntes WHERE user_id = ? AND book_id = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkSql);
            checkStmt.setInt(1, userId);
            checkStmt.setInt(2, Integer.parseInt(livreId));

            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                request.setAttribute("errorMessage", "Vous avez déjà emprunté ce livre.");
                request.getRequestDispatcher("/LivreDetailsServlet?id=" + livreId).forward(request, response);
                return;
            }

            Date dateEmprunt = new Date(System.currentTimeMillis());

            // Set the return date (14 days later)
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateEmprunt);
            calendar.add(Calendar.DAY_OF_MONTH, 14);
            Date dateRetour = new Date(calendar.getTimeInMillis());

            // Insert the borrowed book into the database
            String sql = "INSERT INTO gest_bib.livres_empruntes (user_id, book_id, date_emprunt, date_retour) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, Integer.parseInt(livreId));
            statement.setDate(3, dateEmprunt);
            statement.setDate(4, dateRetour);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error while borrowing the book.");
        }

        response.sendRedirect("LivreDetailsServlet?id=" + livreId);
    }
}
