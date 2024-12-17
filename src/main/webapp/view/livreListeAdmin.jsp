<%@page import="java.util.List"%>
<%@page import="model.Livre"%>
<%@page import="model.Auteur"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rechercher des Livres</title>
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
        .search-form {
            display: flex;
            justify-content: center;
            margin: 20px 0;
        }
        .search-form input[type="text"] {
            padding: 10px;
            font-size: 16px;
            width: 300px;
            margin-right: 10px;
        }
        .search-form input[type="submit"] {
            padding: 10px 20px;
            font-size: 16px;
            background-color: #005f73;
            color: white;
            border: none;
            cursor: pointer;
        }
        .search-form input[type="submit"]:hover {
            background-color: #004c60;
        }
        .book-container {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            gap: 20px;
            margin: 20px;
        }
        .book-card {
            background-color: white;
            border: 1px solid #ddd;
            border-radius: 8px;
            width: 200px;
            padding: 15px;
            text-align: center;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: space-between;
            height: 350px;  /* Fixe la hauteur des cartes pour que toutes soient de la même taille */
        }
        .book-card img {
            width: 170px;   /* Définir une largeur fixe pour l'image */
            height: 250px;  /* Définir une hauteur fixe pour l'image */
            object-fit: cover; /* S'assurer que l'image couvre la zone sans déformer l'image */
            border-radius: 8px;
        }
        .book-card h3 {
            font-size: 16px;
            margin-top: 10px;
            color: #333;
            margin-bottom: 15px;  /* Espacement entre le titre et le bouton */
        }
        .book-card a {
            display: block;
            background-color: #005f73;
            color: white;
            padding: 8px;
            text-decoration: none;
            border-radius: 5px;
            margin-top: auto;  /* Push le bouton vers le bas de la carte */
        }
        .book-card a:hover {
            background-color: #004c60;
        }
        .btn-add {
            display: inline-block;
            padding: 10px 20px;
            background-color: #005f73;
            color: white;
            text-decoration: none;
            font-size: 16px;
            border-radius: 5px;
            margin: 20px 0;
        }
        .btn-add:hover {
            background-color: #004c60;
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
    <jsp:include page="navbarAdmin.jsp" />

    <header>
    
        <h1>Rechercher des Livres</h1>
    </header>

    <div class="search-form">
        <form action="ChercherLivresServlet" method="get">
            <label for="query">Rechercher :</label>
            <input type="text" id="query" name="query" placeholder="Titre, Auteur, Catégorie" value="${param.query}" />
            <input type="submit" value="Rechercher" />
        </form>
    </div>

    <h2 style="text-align:center;">Résultats de la recherche</h2>
    
    <div class="book-container">
        <!-- Loop over livres using EL -->
        <%
            List<Livre> livres = (List<Livre>) request.getAttribute("livres");
            if (livres != null) {
                for (Livre livre : livres) {
        %>
            <div class="book-card">
                <!-- Display book image -->
                <% if (livre.getImageUrl() != null && !livre.getImageUrl().isEmpty()) { %>
                    <img src="<%= livre.getImageUrl() %>" alt="Image de <%= livre.getTitre() %>" />
                <% } else { %>
                    <span>Pas d'image disponible</span>
                <% } %>
                <h3><%= livre.getTitre() %></h3>
                <a href="LivreDetailsAdminServlet?id=<%= livre.getId() %>">Voir les détails</a>
            </div>
        <%
                }
            }
        %>
    </div>
	
	<% 
        String userRole = (String) session.getAttribute("userRole");
        if ("Admin".equals(userRole)) {
    %>
    	<div style="text-align:center;">
        <a href="DonneesAjoutServlet" class="btn-add">Ajouter un Livre</a>
    </div>
    <% } %>
    

    <footer>
        <p>&copy; 2024 Gestion des Livres. Tous droits réservés.</p>
    </footer>

</body>
</html>
