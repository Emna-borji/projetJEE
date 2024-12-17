
<!-- navbar.jsp -->
<nav id="navbar" style="display: none; background-color: #005f73; position: fixed; top: 0; left: 0; width: 250px; height: 100vh; padding-top: 20px; z-index: 999; box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);">
    <ul style="list-style: none; padding-left: 0; margin: 0;">
        <li><a href="/GestionBib/index.jsp">Accueil</a></li>
                <li><a href="/GestionBib/GererAuteursServlet">Gérer Auteurs</a></li>
                <li><a href="/GestionBib/GererCategoriesServlet">Gérer Catégories</a></li>
                <li><a href="/GestionBib/EmpruntsActifsServlet">Emprunts Actifs</a></li>
                <li><a href="/GestionBib/StatistiquesServlet">Statistiques</a></li>
                <li><a href="/GestionBib/LivresParAuteurServlet">Livres Par Auteur</a></li>
                <li><a href="/GestionBib/UtilisateursActifsServlet">Utilisateurs Actifs</a></li>
                <li><a href="/GestionBib/view/mon-compte.jsp">Mon Compte</a></li>
                <% if (session.getAttribute("userId") != null) { %>
                    <li><a href="/GestionBib/DeconnexionServlet">Déconnexion</a></li>
                <% } else { %>
                    <li><a href="/GestionBib/login.jsp">Connexion</a></li>
                <% } %>
    </ul>
</nav>

<style>
    /* Ensure the body and html take up the full height */
    html, body {
        height: 100%;
        margin: 0;
    }

    nav ul li {
        margin: 15px 0;
    }

    nav ul li a {
        display: block;
        padding: 12px 20px;
        color: #ecf0f1;
        text-decoration: none;
        font-size: 18px;
        border-radius: 4px;
        transition: background-color 0.3s ease, padding-left 0.3s ease;
    }

    nav ul li a:hover {
        background-color: #004c60; /* Darker color for hover effect */
        padding-left: 30px;
    }

    /* Menu Toggle Button */
    #toggleNavbarBtn {
        background-color: #005f73; /* Matching button color */
        color: white;
        padding: 12px 25px;
        border: none;
        cursor: pointer;
        position: absolute;
        left: 20px;
        top: 20px;
        font-size: 18px;
        border-radius: 4px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        transition: background-color 0.3s ease;
    }

    #toggleNavbarBtn:hover {
        background-color: #004c60; /* Darker color for hover effect */
    }
</style>

