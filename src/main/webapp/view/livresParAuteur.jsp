<%@page import="java.util.List"%>
<%@page import="model.Livre"%>
<%@page import="model.Auteur"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Livres de l'Auteur</title>
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
        .search-form select {
            padding: 10px;
            font-size: 16px;
            margin-right: 10px;
        }
        .search-form button {
            padding: 10px 20px;
            font-size: 16px;
            background-color: #005f73;
            color: white;
            border: none;
            cursor: pointer;
        }
        .search-form button:hover {
            background-color: #004c60;
        }
        h2 {
            text-align: center;
            margin-top: 30px;
        }
        table {
            width: 80%;
            margin: 0 auto;
            border-collapse: collapse;
            background-color: white;
            box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
        }
        th, td {
            padding: 10px;
            text-align: left;
            border: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
        }
        td img {
            width: 100px;
            height: 150px;
            object-fit: cover;
            border-radius: 8px;
        }
        footer {
            text-align: center;
            padding: 20px;
            background-color: #333;
            color: white;
            margin-top: 40px;
        }
    </style>
</head>
<body>

    <header>
        <h1>Livres écrits par <%= request.getAttribute("nomAuteur") %></h1>
    </header>

    <div class="search-form">
        <form action="LivresParAuteurServlet" method="GET">
            <label for="auteurId">Sélectionner un auteur :</label>
            <select name="auteurId" id="auteurId">
                <% 
                    List<Auteur> auteurs = (List<Auteur>) request.getAttribute("auteurs");
                    for (Auteur auteur : auteurs) { 
                %>
                <option value="<%= auteur.getId() %>"><%= auteur.getNom() %></option>
                <% } %>
            </select>
            <button type="submit">Afficher les livres</button>
        </form>
    </div>

    <h2>Liste des livres</h2>
    <table>
        <thead>
            <tr>
                <th>Image</th>
                <th>Titre</th>
                <th>Résumé</th>
                <th>Année</th>
                <th>Format</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<Livre> livres = (List<Livre>) request.getAttribute("livres");
                for (Livre livre : livres) { 
            %>
            <tr>
                <td><img src="<%= livre.getImageUrl() %>" alt="Image de <%= livre.getTitre() %>"></td>
                <td><%= livre.getTitre() %></td>
                <td><%= livre.getResume() %></td>
                <td><%= livre.getAnnee() %></td>
                <td><%= livre.getFormat() %></td>
            </tr>
            <% } %>
        </tbody>
    </table>

    <footer>
        <p>&copy; 2024 Gestion des Livres. Tous droits réservés.</p>
    </footer>

</body>
</html>
