<%@page import="model.Livre"%>
<%@page import="model.Auteur"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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
        max-width: 900px;
        margin: 20px auto;
        padding: 20px;
        background-color: white;
        border-radius: 12px;
        box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
        display: flex;
        flex-wrap: wrap;
        gap: 20px;
    }
    .book-info {
        flex: 1;
        min-width: 300px;
    }
    .book-info h3 {
        color: #333;
        font-size: 24px;
        margin-bottom: 10px;
    }
    .book-info p {
        font-size: 16px;
        line-height: 1.6;
    }
    .book-image-container {
        flex: 0 0 300px;
        text-align: center;
    }
    .book-details img {
        max-width: 100%;
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
        width: 100%;
        display: flex;
        flex-wrap: wrap;
        gap: 15px;
        justify-content: center;
        margin-top: 20px;
    }
    .buttons-section form {
        margin: 0;
    }
    .buttons-section button {
        padding: 10px 20px;
        font-size: 16px;
        background-color: #005f73;
        color: white;
        border: none;
        cursor: pointer;
        border-radius: 8px;
        min-width: 180px;
        margin-bottom: 10px;
    }
    .buttons-section button:hover {
        background-color: #004c60;
    }
    .back-link {
        display: block;
        text-align: center;
        margin-top: 30px;
        font-size: 16px;
        color: #005f73;
        text-decoration: none;
    }
    .back-link:hover {
        text-decoration: underline;
    }
    footer {
        text-align: center;
        padding: 20px;
        background-color: #333;
        color: white;
    }
    #messageBox {
        position: fixed;
        top: 10px;
        right: 10px;
        background-color: #4CAF50;
        color: white;
        padding: 10px;
        border-radius: 5px;
        display: none;
        z-index: 1000;
    }
</style>
<script>
    function showMessage(message) {
        var messageBox = document.getElementById('messageBox');
        messageBox.innerText = message;
        messageBox.style.display = 'block';

        // Hide the message after 2 seconds
        setTimeout(function() {
            messageBox.style.display = 'none';
        }, 2000);
    }
</script>
</head>
<body>
    <%
        String userRole = (String) session.getAttribute("userRole");
        if ("Admin".equals(userRole)) {
    %>
        <jsp:include page="navbarAdmin.jsp" />
    <% } else { %>
        <jsp:include page="navbar.jsp" />
    <% } %>

    <div id="messageBox"></div>

    <%
        String message = (String) request.getAttribute("message");
        if (message != null) {
    %>
        <script>
            showMessage("<%= message %>");
        </script>
    <%
        }
    %>

    <header>
        <h1>Détails du Livre</h1>
    </header>

    <!-- Book Details Section -->
    <div class="book-details">
        <%
            Livre livre = (Livre) request.getAttribute("livre");
            if (livre != null) {
        %>
            <div class="book-info">
                <h3>Titre: <%= livre.getTitre() %></h3>
                <p><strong>Résumé:</strong> <%= livre.getResume() %></p>
                <p><strong>Année de publication:</strong> <%= livre.getAnnee() %></p>
                <p><strong>Format:</strong> <%= livre.getFormat() %></p>

                <!-- Authors Section -->
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
            </div>

            <div class="book-image-container">
                <% if (livre.getImageUrl() != null && !livre.getImageUrl().isEmpty()) { %>
                    <img src="<%= livre.getImageUrl() %>" alt="Image de <%= livre.getTitre() %>" />
                <% } else { %>
                    <p>Aucune image disponible pour ce livre.</p>
                <% } %>
            </div>

            <div class="buttons-section">
                <% if ("Admin".equals(userRole)) { %>
                    <!-- Admin Actions -->
                    <form action="ModifLivreServlet" method="get">
                        <input type="hidden" name="idLivre" value="<%= livre.getId() %>">
                        <button type="submit">Modifier</button>
                    </form>

                    <form action="SupprimerLivreServlet" method="post">
                        <input type="hidden" name="idLivre" value="<%= livre.getId() %>">
                        <button type="submit" onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce livre?');">Supprimer</button>
                    </form>
                <% } else { %>
                    <!-- User Actions -->
                    <form action="ToggleFavorisServlet" method="post">
                        <input type="hidden" name="idLivre" value="<%= livre.getId() %>">
                        <button type="submit">Ajouter aux Favoris</button>
                    </form>

                    <form action="EmprunterLivreServlet" method="post">
                        <input type="hidden" name="livreId" value="<%= livre.getId() %>">
                        <button type="submit">Emprunter le Livre</button>
                    </form>

                    <form action="TelechargerLivreServlet" method="post">
                        <input type="hidden" name="idLivre" value="<%= livre.getId() %>">
                        <button type="submit">Télécharger le Livre</button>
                    </form>
                <% } %>
            </div>

        <% } else { %>
            <p>Aucun détail disponible pour ce livre.</p>
        <% } %>

        <a href="ChercherLivresServlet" class="back-link">Retour à la liste des livres</a>
    </div>

    <!-- Footer Section -->
    <footer>
        <p>&copy; 2024 Tous droits réservés</p>
    </footer>
</body>
</html>
