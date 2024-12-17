<%@page import="model.Categorie"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Gérer les Catégories</title>
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
        <h1>Gestion des Catégories</h1>
    </header>

    <table>
        <thead>
            <tr>
                <th>Nom</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<Categorie> categories = (List<Categorie>) request.getAttribute("categories");
                if (categories != null && !categories.isEmpty()) {
                    for (Categorie categorie : categories) {
            %>
                        <tr>
                            <td><%= categorie.getNom() %></td>
                            <td class="actions">
                                <a href="/GestionBib/view/modifierCategorie.jsp?id=<%= categorie.getId() %>">Modifier</a>
                                <a href="/GestionBib/SupprimerCategorieServlet?id=<%= categorie.getId() %>">Supprimer</a>
                            </td>
                        </tr>
            <% 
                    } 
                } else { 
            %>
                    <tr>
                        <td colspan="2" class="no-books">Aucune catégorie disponible</td>
                    </tr>
            <% 
                } 
            %>
        </tbody>
    </table>

    <div class="button-container">
        <a href="/GestionBib/view/ajouterCategorie.jsp" class="button">Ajouter une Catégorie</a>
    </div>

    <footer>
        <p>&copy; 2024 Tous droits réservés</p>
    </footer>
</body>
</html>
