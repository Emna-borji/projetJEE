<!DOCTYPE html>
<%@page import="model.Utilisateur"%>
<%@page import="java.util.List"%>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Utilisateurs les plus actifs</title>
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
        table {
            width: 80%;
            margin: 20px auto;
            border-collapse: collapse;
            background-color: white;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        table th, table td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        table th {
            background-color: #005f73;
            color: white;
        }
        table tr:hover {
            background-color: #f1f1f1;
        }
        .back-link {
            display: block;
            text-align: center;
            margin: 20px;
            font-size: 16px;
            text-decoration: none;
            color: #005f73;
        }
        .back-link:hover {
            color: #004c60;
        }
    </style>
</head>
<body>
    <header>
        <h1>Liste des utilisateurs les plus actifs</h1>
    </header>

    <!-- Section des utilisateurs les plus actifs par téléchargements -->
    <h2 style="text-align: center;">Par Téléchargements</h2>
    <table>
        <tr>
            <th>Nom</th>
            <th>Email</th>
            <th>Nombre de téléchargements</th>
        </tr>
        <% 
            List<Utilisateur> utilisateursTelechargements = (List<Utilisateur>) request.getAttribute("utilisateursParTelechargements");
            for (Utilisateur utilisateur : utilisateursTelechargements) { 
        %>
        <tr>
            <td><%= utilisateur.getNom() %></td>
            <td><%= utilisateur.getEmail() %></td>
            <td><%= utilisateur.getNbActions() %></td>
        </tr>
        <% } %>
    </table>

    <!-- Section des utilisateurs les plus actifs par emprunts -->
    <h2 style="text-align: center;">Par Emprunts</h2>
    <table>
        <tr>
            <th>Nom</th>
            <th>Email</th>
            <th>Nombre d'emprunts</th>
        </tr>
        <% 
            List<Utilisateur> utilisateursEmprunts = (List<Utilisateur>) request.getAttribute("utilisateursParEmprunts");
            for (Utilisateur utilisateur : utilisateursEmprunts) { 
        %>
        <tr>
            <td><%= utilisateur.getNom() %></td>
            <td><%= utilisateur.getEmail() %></td>
            <td><%= utilisateur.getNbActions() %></td>
        </tr>
        <% } %>
    </table>

    <!-- Section des utilisateurs les plus actifs par favoris -->
    <h2 style="text-align: center;">Par Favoris</h2>
    <table>
        <tr>
            <th>Nom</th>
            <th>Email</th>
            <th>Nombre d'ajouts en favoris</th>
        </tr>
        <% 
            List<Utilisateur> utilisateursFavoris = (List<Utilisateur>) request.getAttribute("utilisateursParFavoris");
            for (Utilisateur utilisateur : utilisateursFavoris) { 
        %>
        <tr>
            <td><%= utilisateur.getNom() %></td>
            <td><%= utilisateur.getEmail() %></td>
            <td><%= utilisateur.getNbActions() %></td>
        </tr>
        <% } %>
    </table>

    <a href="index.jsp" class="back-link">Retour à l'accueil</a>
</body>
</html>
