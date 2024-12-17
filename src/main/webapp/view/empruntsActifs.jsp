<%@page import="model.LivresEmpruntes"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Emprunts Actifs</title>
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

        h2 {
            text-align: center;
            color: #005f73;
            margin-top: 20px;
        }

        table {
            width: 90%;
            margin: 30px auto;
            border-collapse: collapse;
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 6px 15px rgba(0, 0, 0, 0.1);
        }

        th, td {
            padding: 12px;
            text-align: left;
            border: 1px solid #ddd;
            font-size: 16px;
        }

        th {
            background-color: #005f73;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        tr:hover {
            background-color: #e0f7fa;
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
        <h1>Gestion des Emprunts</h1>
    </header>

    <h2>Emprunts Actifs</h2>

    <table>
        <thead>
            <tr>
                <th>Utilisateur</th>
                <th>Livre</th>
                <th>Date Emprunt</th>
                <th>Date Retour</th>
            </tr>
        </thead>
        <tbody>
            <%
    // Supposons que "empruntsActifs" soit une liste d'objets dans ton modèle
    List<LivresEmpruntes> empruntsActifs = (List<LivresEmpruntes>) request.getAttribute("empruntsActifs");
    for (int i = 0; i < empruntsActifs.size(); i++) {
    	LivresEmpruntes emprunt = empruntsActifs.get(i);
%>
            <tr>
                <td><%= emprunt.getUtilisateur() %></td>
                <td><%= emprunt.getLivre() %></td>
                <td><%= emprunt.getDateEmprunt() %></td>
                <td><%= emprunt.getDateRetour() %></td>
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
