package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Utilisateur;
import utils.ConnexionBD;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class UtilisateursActifsServlet
 */
@WebServlet("/UtilisateursActifsServlet")
public class UtilisateursActifsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UtilisateursActifsServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer idUtilisateur = (Integer) session.getAttribute("userId");

        if (idUtilisateur == null || !"Admin".equals(session.getAttribute("userRole"))) {
            response.sendRedirect("/GestionBib/view/login.jsp");
            return;
        }

        List<Utilisateur> utilisateursParTelechargements = obtenirUtilisateursLesPlusActifsParTelechargements();
        List<Utilisateur> utilisateursParEmprunts = obtenirUtilisateursLesPlusActifsParEmprunts();
        List<Utilisateur> utilisateursParFavoris = obtenirUtilisateursLesPlusActifsParFavoris();
        
        request.setAttribute("utilisateursParTelechargements", utilisateursParTelechargements);
        request.setAttribute("utilisateursParEmprunts", utilisateursParEmprunts);
        request.setAttribute("utilisateursParFavoris", utilisateursParFavoris);
        
        request.getRequestDispatcher("/view/utilisateursActifs.jsp").forward(request, response);
    }

    private List<Utilisateur> obtenirUtilisateursLesPlusActifsParTelechargements() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String requete = 
            "SELECT u.id, u.nom, u.email, COUNT(lt.id) AS nb_telechargements " +
            "FROM gest_bib.utilisateurs u " +
            "JOIN gest_bib.livres_telecharges lt ON u.id = lt.utilisateur_id " +
            "GROUP BY u.id, u.nom, u.email " +
            "ORDER BY nb_telechargements DESC " +
            "LIMIT 10";
        
        try (Connection connection = ConnexionBD.connect();
             PreparedStatement stmt = connection.prepareStatement(requete)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Utilisateur utilisateur = new Utilisateur(
                    rs.getInt("id"), 
                    rs.getString("nom"), 
                    rs.getString("email"), 
                    rs.getInt("nb_telechargements")
                );
                utilisateurs.add(utilisateur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateurs;
    }

    private List<Utilisateur> obtenirUtilisateursLesPlusActifsParEmprunts() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String requete = 
            "SELECT u.id, u.nom, u.email, COUNT(le.id) AS nb_emprunts " +
            "FROM gest_bib.utilisateurs u " +
            "JOIN gest_bib.livres_empruntes le ON u.id = le.user_id " +
            "GROUP BY u.id, u.nom, u.email " +
            "ORDER BY nb_emprunts DESC " +
            "LIMIT 10";
        
        try (Connection connection = ConnexionBD.connect();
             PreparedStatement stmt = connection.prepareStatement(requete)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Utilisateur utilisateur = new Utilisateur(
                    rs.getInt("id"), 
                    rs.getString("nom"), 
                    rs.getString("email"), 
                    rs.getInt("nb_emprunts")
                );
                utilisateurs.add(utilisateur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateurs;
    }

    private List<Utilisateur> obtenirUtilisateursLesPlusActifsParFavoris() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String requete = 
            "SELECT u.id, u.nom, u.email, COUNT(f.id) AS nb_favoris " +
            "FROM gest_bib.utilisateurs u " +
            "JOIN gest_bib.favoris f ON u.id = f.utilisateur_id " +
            "GROUP BY u.id, u.nom, u.email " +
            "ORDER BY nb_favoris DESC " +
            "LIMIT 10";
        
        try (Connection connection = ConnexionBD.connect();
             PreparedStatement stmt = connection.prepareStatement(requete)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Utilisateur utilisateur = new Utilisateur(
                    rs.getInt("id"), 
                    rs.getString("nom"), 
                    rs.getString("email"), 
                    rs.getInt("nb_favoris")
                );
                utilisateurs.add(utilisateur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateurs;
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
