<%@page import="model.Livre"%>
<%@page import="model.Auteur"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Détails du Livre</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
            color: #333;
        }
        header {
            background-color: #005f73;
            padding: 20px;
            color: white;
            text-align: center;
        }
        header h1 {
            margin: 0;
        }
        .navbar {
            background-color: #333;
            overflow: hidden;
        }
        .navbar a {
            color: white;
            padding: 14px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
        }
        .navbar a:hover {
            background-color: #ddd;
            color: black;
        }
        .book-details {
            max-width: 800px;
            margin: 20px auto;
            padding: 20px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .book-details h2 {
            color: #333;
        }
        .book-details p {
            font-size: 16px;
            line-height: 1.6;
        }
        .book-details img {
            max-width: 300px;
            height: auto;
            border-radius: 8px;
            margin-top: 20px;
        }
        .authors-list {
            list-style-type: none;
            padding: 0;
        }
        .authors-list li {
            font-size: 16px;
            margin-bottom: 5px;
        }
        .buttons-section {
            margin-top: 20px;
            display: flex;
            gap: 10px;
            justify-content: center;
        }
        .buttons-section button {
            padding: 10px 20px;
            font-size: 16px;
            background-color: #005f73;
            color: white;
            border: none;
            cursor: pointer;
            border-radius: 5px;
        }
        .buttons-section button:hover {
            background-color: #004c60;
        }
        .back-link {
            display: block;
            text-align: center;
            margin-top: 20px;
            font-size: 16px;
        }
        footer {
            text-align: center;
            padding: 20px;
            background-color: #333;
            color: white;
        }
    </style>
</head>
<body>

    <!-- Conditionally include navbar based on user role -->
    <%
        String userRole = (String) session.getAttribute("userRole");
        if ("Admin".equals(userRole)) {
    %>
        <jsp:include page="navbarAdmin.jsp" />
    <% } else { %>
        <jsp:include page="navbar.jsp" />
    <% } %>

    <header>
        <h1>Détails du Livre</h1>
    </header>

    <div class="book-details">
        <%
            Livre livre = (Livre) request.getAttribute("livre");
            if (livre != null) {
        %>
            <h2>Titre: <%= livre.getTitre() %></h2>
            <p><strong>Résumé:</strong> <%= livre.getResume() %></p>
            <p><strong>Année de publication:</strong> <%= livre.getAnnee() %></p>
            <p><strong>Format:</strong> <%= livre.getFormat() %></p>

            <!-- Display the book image -->
            <% if (livre.getImageUrl() != null && !livre.getImageUrl().isEmpty()) { %>
                <img src="<%= livre.getImageUrl() %>" alt="Image de <%= livre.getTitre() %>" />
            <% } else { %>
                <p>Aucune image disponible pour ce livre.</p>
            <% } %>

            <h3>Auteurs:</h3>
            <ul class="authors-list">
                <%
                    for (Auteur auteur : livre.getAuteurs()) {
                %>
                    <li><%= auteur.getNom() %></li>
                <%
                    }
                %>
            </ul>

            <!-- Buttons Section -->
            <div class="buttons-section">
                <!-- Button to add the book to favorites -->
                <form action="ModifLivreServlet" method="get" style="display: inline;">
                    <input type="hidden" name="idLivre" value="<%= livre.getId() %>">
                    <button type="submit">Modifier</button>
                </form>
                
                <form action="SupprimerLivreServlet" method="post" style="display: inline;">
                    <input type="hidden" name="idLivre" value="<%= livre.getId() %>">
                    <button type="submit" onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce livre?');">Supprimer</button>
                </form>
            </div>

        <%
            } else {
        %>
            <p>Aucun détail disponible pour ce livre.</p>
        <%
            }
        %>
    </div>

    <a href="ChercherLivresServlet" class="back-link">Retour à la liste des livres</a>

    <footer>
        <p>&copy; 2024 Gestion des Livres. Tous droits réservés.</p>
    </footer>

</body>
</html>
