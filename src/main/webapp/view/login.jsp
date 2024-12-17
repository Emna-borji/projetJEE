<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Connexion</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            color: #333;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .container {
            background-color: #fff;
            width: 100%;
            max-width: 400px;
            padding: 40px 20px;
            border-radius: 10px;
            box-shadow: 0 10px 15px rgba(0, 0, 0, 0.1);
            text-align: center;
        }

        h1 {
            margin-bottom: 20px;
            font-size: 24px;
            color: #333;
        }

        form {
            display: flex;
            flex-direction: column;
        }

        label {
            text-align: left;
            font-weight: bold;
            margin-bottom: 5px;
            font-size: 14px;
        }

        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 10px 15px;
            font-size: 14px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            transition: all 0.3s ease;
        }

        input[type="email"]:focus,
        input[type="password"]:focus {
            border-color: #6667ab;
            outline: none;
        }

        button {
            width: 100%;
            padding: 10px 0;
            font-size: 16px;
            color: #fff;
            background-color: #005f73;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        button:hover {
            background-color: #004c60;
        }

        .error-message {
            color: red;
            margin-top: 10px;
        }

        a {
            display: block;
            margin-top: 20px;
            text-decoration: none;
            color: #6667ab;
        }

        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

    <div class="container">
        <h1>Connexion</h1>

        <form action="/GestionBib/LoginServlet" method="post">
            <label for="email">Email</label>
            <input type="email" id="email" name="email" placeholder="Entrez votre email" required>

            <label for="motDePasse">Mot de Passe</label>
            <input type="password" id="motDePasse" name="motDePasse" placeholder="Entrez votre mot de passe" required>

            <button type="submit">Se connecter</button>
        </form>

        <!-- Message d'erreur (affiché uniquement s'il y a une erreur) -->
        <% if (request.getAttribute("message") != null) { %>
    <p class="error-message"><%= request.getAttribute("message") %></p>
<% } %>

        <a href="inscription.jsp">Pas encore inscrit ? Créez un compte ici.</a>
    </div>

</body>
</html>
