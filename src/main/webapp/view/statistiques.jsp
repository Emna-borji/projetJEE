<%@page import="model.StatistiqueMensuelle"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Statistiques Mensuelles</title>
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

        h3 {
            color: #00796b;
            margin-top: 30px;
            text-align: center;
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
        <h1>Gestion des Statistiques</h1>
    </header>

    <h2>Statistiques Mensuelles</h2>

    <h3>Emprunts</h3>
    <table>
        <thead>
            <tr>
                <th>Année</th>
                <th>Mois</th>
                <th>Emprunts</th>
            </tr>
        </thead>
        <tbody>
            <%
    List<StatistiqueMensuelle> empruntsStats = (List<StatistiqueMensuelle>) request.getAttribute("empruntsStats");
    for (StatistiqueMensuelle stat : empruntsStats) {
%>
            <tr>
                <td><%= stat.getAnnee() %></td>
                <td><%= stat.getMois() %></td>
                <td><%= stat.getTotalEmprunts() %></td>
            </tr>
<%
    }
%>
        </tbody>
    </table>

    <h3>Téléchargements</h3>
    <table>
        <thead>
            <tr>
                <th>Année</th>
                <th>Mois</th>
                <th>Téléchargements</th>
            </tr>
        </thead>
        <tbody>
            <%
    List<StatistiqueMensuelle> telechargementsStats = (List<StatistiqueMensuelle>) request.getAttribute("telechargementsStats");
    for (StatistiqueMensuelle stat : telechargementsStats) {
%>
            <tr>
                <td><%= stat.getAnnee() %></td>
                <td><%= stat.getMois() %></td>
                <td><%= stat.getTotalTelechargements() %></td>
            </tr>
<%
    }
%>
        </tbody>
    </table>

    <h3>Ajouts de Livres</h3>
    <table>
        <thead>
            <tr>
                <th>Année</th>
                <th>Mois</th>
                <th>Ajouts</th>
            </tr>
        </thead>
        <tbody>
            <%
    List<StatistiqueMensuelle> ajoutsStats = (List<StatistiqueMensuelle>) request.getAttribute("ajoutsStats");
    for (StatistiqueMensuelle stat : ajoutsStats) {
%>
            <tr>
                <td><%= stat.getAnnee() %></td>
                <td><%= stat.getMois() %></td>
                <td><%= stat.getTotalAjouts() %></td>
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
