<%@page import="model.Auteur"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Gérer Auteurs</title>
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

        table {
            width: 90%;
            margin: 30px auto;
            border-collapse: collapse;
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 6px 15px rgba(0, 0, 0, 0.1);
        }

        table th, table td {
            padding: 15px;
            text-align: center;
            border: 1px solid #ddd;
        }

        table th {
            background-color: #005f73;
            color: white;
            font-size: 18px;
        }

        table td {
            font-size: 16px;
        }

        table tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        table tr:hover {
            background-color: #dbe9e8;
        }

        .button-container {
            margin-top: auto; /* Prend l'espace disponible pour pousser l'élément au bas */
            display: flex;
            justify-content: center;
            padding: 20px 0; /* Espace au-dessus et au-dessous du bouton */
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

        .actions a {
            display: inline-block;
            margin: 5px;
            padding: 8px 12px;
            background-color: #005f73;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-size: 14px;
        }

        .actions a:hover {
            background-color: #0a9396;
        }

        .no-books {
            text-align: center;
            font-size: 18px;
            color: #555;
        }

        footer {
            text-align: center;
            padding: 20px;
            background-color: #333;
            color: white;
            margin-top: auto; /* Fixe le footer en bas */
            width: 100%;
        }
    </style>
</head>
<body>
    <header>
        <h1>Gestion des Auteurs</h1>
    </header>

    <table>
        <thead>
            <tr>
                <th>Nom</th>
                <th>Date de naissance</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<Auteur> auteurs = (List<Auteur>) request.getAttribute("auteurs");
                if (auteurs != null && !auteurs.isEmpty()) {
                    for (Auteur auteur : auteurs) {
            %>
                        <tr>
                            <td><%= auteur.getNom() %></td>
                            <td><%= auteur.getDateDeNaissance() %></td>
                            <td class="actions">
                                <a href="/GestionBib/view/modifierAuteur.jsp?id=<%= auteur.getId() %>">Modifier</a>
                                <a href="/GestionBib/SupprimerAuteurServlet?id=<%= auteur.getId() %>">Supprimer</a>
                            </td>
                        </tr>
            <% 
                    } 
                } else { 
            %>
                    <tr>
                        <td colspan="3" class="no-books">Aucun auteur disponible</td>
                    </tr>
            <% 
                } 
            %>
        </tbody>
    </table>

    <div class="button-container">
        <a href="/GestionBib/view/ajouterAuteur.jsp" class="button">Ajouter un Auteur</a>
    </div>

    <footer>
        <p>&copy; 2024 Tous droits réservés</p>
    </footer>
</body>
</html>
