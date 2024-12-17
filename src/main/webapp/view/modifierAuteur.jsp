<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Modifier un Auteur</title>
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

        form input[type="text"], form input[type="date"] {
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
        <h1>Modifier un Auteur</h1>
    </header>

    <div class="form-container">
        <h2>Modifier un Auteur</h2>
        <form action="/GestionBib/ModifierAuteurServlet" method="post">
            <input type="hidden" name="id" value="${param.id}">

            <label for="nom">Nom de l'Auteur:</label>
            <input type="text" id="nom" name="nom" value="${param.nom}" required>

            <label for="date_de_naissance">Date de naissance:</label>
            <input type="date" id="date_de_naissance" name="date_de_naissance" value="${param.date_de_naissance}" required>

            <input type="submit" value="Modifier">
        </form>
    </div>

    <div class="button-container">
        <a href="/GestionBib/view/gererAuteurs.jsp" class="button">Retour à la liste des Auteurs</a>
    </div>

    <footer>
        <p>&copy; 2024 Tous droits réservés</p>
    </footer>
</body>
</html>
