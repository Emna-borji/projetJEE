<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.Utilisateur" %>
<%@ page import="utils.ConnexionBD" %>
<%@ page import="java.sql.*" %>

<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mon Compte</title>
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
        .form-container {
            background-color: white;
            padding: 30px;
            margin: 20px auto;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 80%;
            max-width: 500px;
        }
        .form-container label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
        }
        .form-container input {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 16px;
        }
        .form-container button {
            width: 100%;
            padding: 10px;
            background-color: #005f73;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
        }
        .form-container button:hover {
            background-color: #004c60;
        }
        .message {
            text-align: center;
            font-size: 18px;
            color: #d9534f;
        }
        .message a {
            color: #005f73;
            text-decoration: none;
        }
        .message a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <header>
        <h1>Mon Compte</h1>
    </header>

    <div class="form-container">
        <% if (session.getAttribute("userId") != null) { %>
        <form action="/GestionBib/ModifProfilServlet" method="post">
            <label for="nom">Nom:</label>
            <input type="text" id="nom" name="nom" value="<%= session.getAttribute("userName") %>" required><br>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="<%= session.getAttribute("userEmail") %>" required><br>

            <label for="motDePasse">Mot de passe:</label>
            <input type="password" id="motDePasse" name="motDePasse"><br>

            <button type="submit">Mettre à jour</button>
        </form>
        <% } else { %>
        <div class="message">
            <p>Veuillez vous connecter pour gérer votre profil.</p>
            <a href="login.jsp">Se connecter</a>
        </div>
        <% } %>
    </div>
</body>
</html>
