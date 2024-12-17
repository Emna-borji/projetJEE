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
 * Servlet implementation class AjouterLivreServlet
 */
@WebServlet("/AjouterLivreServlet")
public class AjouterLivreServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjouterLivreServlet() {
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

        if (idUtilisateur == null) {
            response.sendRedirect("/GestionBib/view/login.jsp");
            return;
        }
        
        String titre = request.getParameter("titre");
        String resume = request.getParameter("resume");
        int annee = Integer.parseInt(request.getParameter("annee"));
        String format = request.getParameter("format");
        System.out.println(format);
        String image = gererUploadImage(request);  // URL de l'image
        String fichier = request.getParameter("fichier_url");
        String categorieId = request.getParameter("categorie");
        String[] auteurIds = request.getParameterValues("auteurs");
        if (auteurIds == null) {
            System.out.println("No auteurs selected.");
        } else {
            System.out.println("Selected auteurs:");
            for (String auteurId : auteurIds) {
                System.out.println(auteurId);
            }
        }
        // Sauvegarder le livre avec les auteurs et la catégorie sélectionnés
        ajouterLivre(titre, resume, annee, format, categorieId, image, fichier, auteurIds);

        response.sendRedirect("/GestionBib/ChercherLivresServlet");
    }

    /**
     * Gérer le chargement de l'URL de l'image du livre.
     * Cette méthode vérifie si une URL d'image a été fournie dans le formulaire.
     * @param request La requête HTTP contenant les paramètres du formulaire.
     * @return L'URL de l'image si elle existe, sinon null.
     */
    private String gererUploadImage(HttpServletRequest request) {
        String image = request.getParameter("image_url");
        return (image != null && !image.isEmpty()) ? image : null;
    }
    
    /**
     * Ajouter un livre dans la base de données avec les informations fournies.
     * @param titre Le titre du livre.
     * @param resume Le résumé du livre.
     * @param annee L'année de publication.
     * @param format Le format du livre.
     * @param categorieId L'ID de la catégorie du livre.
     * @param image L'URL de l'image du livre.
     * @param auteurIds Les IDs des auteurs associés au livre.
     */
    private void ajouterLivre(String titre, String resume, int annee, String format, String categorieId, String image, String fichier , String[] auteurIds) {
        try (Connection connection = ConnexionBD.connect()) {
            String query = "INSERT INTO gest_bib.livres (titre, resume, annee, format, categorie_id, image_url, fichier_url) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, titre);
                stmt.setString(2, resume);
                stmt.setInt(3, annee);
                stmt.setString(4, format);
                stmt.setInt(5, Integer.parseInt(categorieId));  // L'ID de la catégorie doit être un entier
                stmt.setString(6, image);
                stmt.setString(7, fichier);
                stmt.executeUpdate();

                // Récupérer l'ID du livre généré
                int bookId = -1;
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        bookId = rs.getInt(1);
                    }
                }

                // Lier les auteurs au livre
                if (bookId != -1 && auteurIds != null) {
                    for (String authorId : auteurIds) {
                        lierAuteurAuLivre(bookId, authorId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    /**
     * Lier un auteur à un livre dans la table livres_auteurs.
     * @param bookId L'ID du livre.
     * @param authorId L'ID de l'auteur.
     */
    private void lierAuteurAuLivre(int bookId, String authorId) {
        try (Connection connection = ConnexionBD.connect()) {
            String query = "INSERT INTO gest_bib.livres_auteurs (livre_id, auteur_id) VALUES (?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, bookId);
                stmt.setInt(2, Integer.parseInt(authorId));
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
