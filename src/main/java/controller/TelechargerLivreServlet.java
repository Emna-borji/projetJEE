package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.ConnexionBD;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class TelechargerLivreServlet
 */
@WebServlet("/TelechargerLivreServlet")
public class TelechargerLivreServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public TelechargerLivreServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer idUtilisateur = (Integer) session.getAttribute("userId");

        if (idUtilisateur == null || !"Client".equals(session.getAttribute("userRole"))) {
            response.sendRedirect("/GestionBib/view/login.jsp");
            return;
        }

        // Get the book ID from the request
        String idLivre = request.getParameter("idLivre");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connexion = ConnexionBD.connect()) {
            // Check if the user has already downloaded this book
            String verifierTelechargementSql = "SELECT COUNT(*) FROM gest_bib.livres_telecharges WHERE utilisateur_id = ? AND livre_id = ?";
            PreparedStatement verifierTelechargementStmt = connexion.prepareStatement(verifierTelechargementSql);
            verifierTelechargementStmt.setInt(1, idUtilisateur);
            verifierTelechargementStmt.setInt(2, Integer.parseInt(idLivre));

            ResultSet rs = verifierTelechargementStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                request.setAttribute("message", "Vous avez déjà téléchargé ce livre.");
            } else {
                String insererTelechargementSql = "INSERT INTO gest_bib.livres_telecharges (utilisateur_id, livre_id) VALUES (?, ?)";
                PreparedStatement insererTelechargementStmt = connexion.prepareStatement(insererTelechargementSql);
                insererTelechargementStmt.setInt(1, idUtilisateur);
                insererTelechargementStmt.setInt(2, Integer.parseInt(idLivre));

                insererTelechargementStmt.executeUpdate();
                request.setAttribute("message", "Livre téléchargé");

                String sql = "SELECT fichier_url FROM gest_bib.livres WHERE id = ?";
                PreparedStatement stmt = connexion.prepareStatement(sql);
                stmt.setInt(1, Integer.parseInt(idLivre));
                ResultSet rs2 = stmt.executeQuery();

                if (rs2.next()) {
                    String fichierUrl = rs2.getString("fichier_url");

                    if (fichierUrl.startsWith("http://") || fichierUrl.startsWith("https://")) {
                        try {
                            // Créer une URL à partir du fichier distant
                            URL url = new URL(fichierUrl);
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("GET");
                            connection.setDoOutput(true);

                            // Récupérer le type de contenu à partir de la réponse (par exemple, "application/pdf")
                            String contentType = connection.getContentType();
                            response.setContentType(contentType); // Définir le type de contenu approprié
                            response.setHeader("Content-Disposition", "attachment; filename=" + new File(url.getFile()).getName());

                            // Lire le fichier distant et le transmettre au client
                            try (InputStream inputStream = connection.getInputStream();
                                 OutputStream outputStream = response.getOutputStream()) {

                                byte[] buffer = new byte[1024];
                                int bytesRead;
                                while ((bytesRead = inputStream.read(buffer)) != -1) {
                                    outputStream.write(buffer, 0, bytesRead);
                                }
                            }
                        } catch (IOException e) {
                            response.getWriter().write("Erreur lors du téléchargement du fichier.");
                            e.printStackTrace();
                        }
                    } else {
                        // Si l'URL n'est pas valide (non HTTP/HTTPS)
                        response.getWriter().write("URL de fichier invalide.");
                    }
                } else {
                    response.getWriter().write("Livre non trouvé.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Erreur de base de données lors de la vérification ou de l'insertion du téléchargement.");
        }

        // Forward the request to the book details page with the appropriate message
        request.getRequestDispatcher("/LivreDetailsServlet?id=" + idLivre).forward(request, response);
    }
}
