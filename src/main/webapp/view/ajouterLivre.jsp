<%@page import="model.Auteur"%>
<%@page import="model.Categorie"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Ajouter un Livre</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f9;
            color: #333;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
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

        .form-container {
            width: 90%;
            max-width: 500px;
            margin: 30px auto;
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 6px 15px rgba(0, 0, 0, 0.1);
            padding: 30px;
        }

        h2 {
            text-align: center;
            color: #005f73;
            margin-bottom: 20px;
        }

        form label {
            display: block;
            font-size: 16px;
            margin-bottom: 8px;
        }

        form input[type="text"], 
        form input[type="number"], 
        form input[type="url"], 
        form textarea, 
        form select {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
        }

        form input[type="submit"] {
            display: inline-block;
            width: 100%;
            padding: 15px 0;
            background-color: #005f73;
            color: white;
            font-size: 18px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        form input[type="submit"]:hover {
            background-color: #004c60;
        }

        .button-container {
            margin-top: auto;
            display: flex;
            justify-content: center;
            padding: 20px 0;
        }

        a.button {
            display: inline-block;
            padding: 15px 30px;
            background-color: #005f73;
            color: white;
            text-decoration: none;
            border-radius: 8px;
            font-size: 18px;
            text-align: center;
            transition: background-color 0.3s ease;
        }

        a.button:hover {
            background-color: #004c60;
        }

        footer {
            text-align: center;
            padding: 20px;
            background-color: #333;
            color: white;
            margin-top: auto;
            width: 100%;
        }
    </style>
</head>
<body>
    <header>
        <h1>Ajouter un Livre</h1>
    </header>

    <div class="form-container">
        <h2>Ajouter un Nouveau Livre</h2>
        <form action="AjouterLivreServlet" method="post">
            <label for="titre">Titre :</label>
            <input type="text" id="titre" name="titre" required>

            <label for="resume">Résumé :</label>
            <textarea id="resume" name="resume" required></textarea>

            <label for="annee">Année :</label>
            <input type="number" id="annee" name="annee" required>

            <label for="format">Format :</label>
            <select id="format" name="format" required>
                <option value="PDF">PDF</option>
                <option value="EPUB">EPUB</option>
            </select>

            <label for="categorie">Catégorie :</label>
            <select id="categorie" name="categorie" required>
                <% 
                    List<Categorie> categories = (List<Categorie>) request.getAttribute("categories");
                    for (Categorie categorie : categories) {
                %>
                    <option value="<%= categorie.getId() %>"><%= categorie.getNom() %></option>
                <% } %>
            </select>

            <label for="auteurs">Auteurs :</label>
            <select id="auteurs" name="auteurs" multiple required>
                <% 
                    List<Auteur> auteurs = (List<Auteur>) request.getAttribute("auteurs");
                    for (Auteur auteur : auteurs) {
                %>
                    <option value="<%= auteur.getId() %>"><%= auteur.getNom() %></option>
                <% } %>
                <option value="new" onclick="toggleAuthorForm()">Ajouter un nouvel auteur</option>
            </select>

            <label for="image_url">URL de l'image du Livre :</label>
            <input type="url" id="image_url" name="image_url" placeholder="https://example.com/image.jpg" required>

            <label for="fichier_url">URL du Livre :</label>
            <input type="url" id="fichier_url" name="fichier_url" placeholder="https://example.com/fichier.jpg" required>

            <input type="submit" value="Ajouter le Livre">
        </form>
    </div>

    <div class="button-container">
        <a href="/GestionBib/view/gererLivres.jsp" class="button">Retour à la liste des Livres</a>
    </div>

    <footer>
        <p>&copy; 2024 Tous droits réservés</p>
    </footer>
</body>
</html>
