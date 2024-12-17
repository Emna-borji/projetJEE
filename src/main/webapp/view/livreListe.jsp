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
            position: relative;
        }
        header h1 {
            margin: 0;
        }
        header button {
            background-color: #005f73;
            color: white;
            padding: 10px 20px;
            border: none;
            cursor: pointer;
            position: absolute;
            left: 20px;
            top: 20px;
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
            height: 350px;
        }
        .book-card img {
            width: 170px;
            height: 250px;
            object-fit: cover;
            border-radius: 8px;
        }
        .book-card h3 {
            font-size: 16px;
            margin-top: 10px;
            color: #333;
            margin-bottom: 15px;
        }
        .book-card a {
            display: block;
            background-color: #005f73;
            color: white;
            padding: 8px;
            text-decoration: none;
            border-radius: 5px;
            margin-top: auto;
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
    <% 
        String userRole = (String) session.getAttribute("userRole");
        if ("Admin".equals(userRole)) {
    %>
        <jsp:include page="navbarAdmin.jsp" />
    <% } else { %>
        <jsp:include page="navbar.jsp" />
    <% } %>

    <header>
        <button id="toggleNavbarBtn">Menu</button>
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
        <% 
            List<Livre> livres = (List<Livre>) request.getAttribute("livres");
            if (livres != null) {
                for (Livre livre : livres) {
        %>
            <div class="book-card">
                <% if (livre.getImageUrl() != null && !livre.getImageUrl().isEmpty()) { %>
                    <img src="<%= livre.getImageUrl() %>" alt="Image de <%= livre.getTitre() %>" />
                <% } else { %>
                    <span>Pas d'image disponible</span>
                <% } %>
                <h3><%= livre.getTitre() %></h3>
                <a href="LivreDetailsServlet?id=<%= livre.getId() %>">Voir les détails</a>
            </div>
        <% 
                }
            }
        %>
    </div>
    
    <% 
        if ("Admin".equals(userRole)) {
    %>
    	<div style="text-align:center;">
        <a href="DonneesAjoutServlet" class="btn-add">Ajouter un Livre</a>
    </div>
    <% } %>

    <footer>
        <p>&copy; 2024 Gestion des Livres. Tous droits réservés.</p>
    </footer>

    <script>
        // Get references to the navbar, toggle button, and the body
        const navbar = document.getElementById('navbar');
        const toggleNavbarBtn = document.getElementById('toggleNavbarBtn');
        const body = document.body;

        // Add an event listener to toggle the navbar visibility when the button is clicked
        toggleNavbarBtn.addEventListener('click', function(event) {
            event.stopPropagation();  // Prevent the click event from propagating to the body
            if (navbar.style.display === 'none' || navbar.style.display === '') {
                navbar.style.display = 'block'; // Show the navbar
            } else {
                navbar.style.display = 'none'; // Hide the navbar
            }
        });

        // Add an event listener to the body to hide the navbar if clicked outside
        body.addEventListener('click', function(event) {
            if (!navbar.contains(event.target) && event.target !== toggleNavbarBtn) {
                navbar.style.display = 'none'; // Hide the navbar if clicked outside
            }
        });
    </script>
</body>
</html>
