<%@page import="model.Livre"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Modifier le Livre</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f9;
            color: #333;
            padding: 20px;
        }

        header {
            background-color: #005f73;
            color: white;
            text-align: center;
            padding: 20px 0;
            margin-bottom: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        header h1 {
            font-size: 28px;
            margin: 0;
        }

        form {
            width: 90%;
            max-width: 600px;
            margin: 0 auto;
            background-color: #ffffff;
            padding: 20px 40px;
            border-radius: 10px;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
        }

        form label {
            font-weight: bold;
            display: block;
            margin-bottom: 10px;
            font-size: 16px;
            color: #555;
        }

        form input[type="text"], 
        form input[type="number"], 
        form textarea, 
        form select {
            width: 100%;
            padding: 10px;
            font-size: 16px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            background-color: #f9f9f9;
            box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.05);
        }

        form input[type="text"]:focus, 
        form input[type="number"]:focus, 
        form textarea:focus, 
        form select:focus {
            border-color: #005f73;
            outline: none;
        }

        form button[type="submit"] {
            width: 100%;
            padding: 15px;
            font-size: 18px;
            font-weight: bold;
            background-color: #005f73;
            color: white;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        form button[type="submit"]:hover {
            background-color: #004c60;
        }

        .message {
            text-align: center;
            font-size: 18px;
            padding: 20px;
            color: #ff0000;
        }

    </style>
</head>
<body>

    <%
        // Contrôle de l'accès par rôle
        String userRole = (String) session.getAttribute("userRole");
        if (userRole == null || !"Admin".equals(userRole)) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Récupération de l'objet Livre à partir de la requête
        Livre livre = (Livre) request.getAttribute("livre");
        if (livre != null) {
    %>

    <header>
        <h1>Modifier le livre</h1>
    </header>

    <form action="/GestionBib/UpdateLivreServlet" method="post">
        <input type="hidden" name="idLivre" value="<%= livre.getId() %>">
        
        <!-- Champ Titre -->
        <label for="titre">Titre:</label>
        <input type="text" id="titre" name="titre" placeholder="Entrez le titre du livre" value="<%= livre.getTitre() %>" required>

        <!-- Champ Résumé -->
        <label for="resume">Résumé:</label>
        <textarea id="resume" name="resume" rows="5" placeholder="Entrez un résumé du livre" required><%= livre.getResume() %></textarea>

        <!-- Champ Année -->
        <label for="annee">Année de publication:</label>
        <input type="number" id="annee" name="annee" placeholder="Entrez l'année de publication" value="<%= livre.getAnnee() %>" min="1000" max="9999" required>

        <!-- Champ Format -->
        <label for="format">Format du livre:</label>
        <select id="format" name="format" required>
            <option value="PDF" <%= "PDF".equals(livre.getFormat()) ? "selected" : "" %>>PDF</option>
            <option value="EPUB" <%= "EPUB".equals(livre.getFormat()) ? "selected" : "" %>>EPUB</option>
        </select>

        <!-- Champ URL de l'image -->
        <label for="imageUrl">URL de l'image:</label>
        <input type="text" id="imageUrl" name="imageUrl" placeholder="Entrez l'URL de l'image du livre" value="<%= livre.getImageUrl() %>" required>

        <!-- Bouton de soumission -->
        <button type="submit">Mettre à jour</button>
    </form>

    <% 
        } else { 
    %>
        <div class="message">
            <p>Le livre n'existe pas ou une erreur est survenue.</p>
        </div>
    <% 
        } 
    %>

</body>
</html>
