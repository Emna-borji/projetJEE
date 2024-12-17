<%@page import="model.LivresEmpruntes"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Mes Livres Empruntés</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f9;
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
        table img {
            max-width: 100px;
            border-radius: 8px;
        }
        table tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        table tr:hover {
            background-color: #dbe9e8;
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
            position: fixed;
            bottom: 0;
            width: 100%;
        }
    </style>
</head>
<body>

    <header>
        <h1>Mes Livres Empruntés</h1>
    </header>

    <table>
        <thead>
            <tr>
                <th>Titre</th>
                <th>Image</th>
                <th>Date d'emprunt</th>
                <th>Date de retour</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<LivresEmpruntes> empruntsList = (List<LivresEmpruntes>) request.getAttribute("empruntsList");
                if (empruntsList != null && !empruntsList.isEmpty()) {
                    for (LivresEmpruntes livre : empruntsList) {
            %>
                        <tr>
                            <td><%= livre.getTitre() %></td>
                            <td><img src="<%= livre.getImage() %>" alt="Book Image"></td>
                            <td><%= livre.getDateEmprunt() %></td>
                            <td><%= livre.getDateRetour() %></td>
                        </tr>
            <%
                    }
                } else {
            %>
                <tr>
                    <td colspan="4" class="no-books">Aucun livre emprunté</td>
                </tr>
            <%
                }
            %>
        </tbody>
    </table>

    <footer>
        <p>&copy; 2024 Tous droits réservés</p>
    </footer>

</body>
</html>
