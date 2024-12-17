<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Modifier une Catégorie</title>
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

        form input[type="text"] {
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
        <h1>Modifier une Catégorie</h1>
    </header>

    <div class="form-container">
        <h2>Modifier une Catégorie</h2>
        <form action="/GestionBib/ModifierCategorieServlet" method="post">
            <input type="hidden" name="id" value="${param.id}">
            <label for="nom">Nom de la Catégorie:</label>
            <input type="text" id="nom" name="nom" value="${param.nom}" required>

            <input type="submit" value="Modifier">
        </form>
    </div>

    <div class="button-container">
        <a href="/GestionBib/view/gererCategories.jsp" class="button">Retour à la liste des Catégories</a>
    </div>

    <footer>
        <p>&copy; 2024 Tous droits réservés</p>
    </footer>
</body>
</html>
