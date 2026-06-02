# 1-1 For which reason is it better to run the container with a flag -e to give the environment variables rather than put them directly in the Dockerfile?
-e permet de sécuriser les clés API, mot de passe et utilisateur

# 1-2 Why do we need a volume to be attached to our postgres container?
Sans volume, les données sont stockées dans le conteneur, si on le supprime (docker rm), tout est perdu.

# 1-3 Document your database container essentials: commands and Dockerfile. 
Dockerfile : 
FROM postgres:17.2-alpine

ENV POSTGRES_DB=db \
    POSTGRES_USER=usr \
    POSTGRES_PASSWORD=pwd

Commands : 
docker build -t jzhang_database ./database

docker network create app-network

docker run -d \
  --name my-postgres \
  --network app-network \
  -e POSTGRES_DB=db \
  -e POSTGRES_USER=usr \
  -e POSTGRES_PASSWORD=pwd \
  -v /my/own/datadir:/var/lib/postgresql/data \
  my-database

  # 1-4 Why do we need a multistage build? And explain each step of this dockerfile.
  Un multistage build permet de séparer l’environnement de construction (build) et l’environnement d’exécution (runtime).
  Dans une première étape, on utilise une image contenant un JDK et des outils comme Maven afin de compiler l’application Spring Boot et générer un fichier .jar. Dans une deuxième étape, on utilise une image le JRE, suffisant pour exécuter l’application.

  # 1-5 Why do we need a reverse proxy?
  Un reverse proxy sert d’intermédiaire entre le client et les services backend. Dans notre cas, Apache reçoit les requêtes sur le port 80 et les redirige vers le backend Spring Boot sur le port 8080. Cela permet de ne pas exposer directement le backend à l’extérieur, améliorant ainsi la sécurité.

  # 1-6 Why is docker-compose so important?
  Docker Compose permet de définir et gérer plusieurs conteneurs dans un seul fichier. Au lieu de lancer plusieurs commandes docker run, on décrit toute l’application (services, réseaux, volumes) et on la démarre avec une seule commande. Cela simplifie le déploiement et garantit que tous les services fonctionnent ensemble correctement.

  # 1-7 Document docker-compose most important commands.
  docker compose up -d : Démarre tous les services en arrière-plan
  docker compose down : Arrête et supprime les conteneurs
  docker compose down -v : Arrête et supprime les conteneurs et supprime les volumes
  docker compose build : Rebuild toutes les images
  docker compose ps : Liste les services et leur état
  docker compose logs -f : Suit les logs en temps réel
  docker compose restart : Redémarre tous les services

  # 1-8 Document your docker-compose file.
  # 1-9 Document your publication commands and published images in dockerhub.
  Connexion à Docker Hub :
  docker login

  Tag des images avec le username et la version :
  docker tag tp-backend jzhang04/tp-backend:1.0
  docker tag tp-database jzhang04/tp-database:1.0
  docker tag tp-httpd jzhang04/tp-httpd:1.0

  Push vers Docker Hub : 
  docker push jzhang04/tp-backend:1.0
  docker push jzhang04/tp-database:1.0
  docker push jzhang04/tp-httpd:1.0

  Backend: jzhang04/tp-backend:1.0
  Database: jzhang04/tp-database:1.0
  HTTP server: jzhang04/tp-httpd:1.0

  # 1-10 Why do we put our images into an online repo?
  Publier ses images sur Docker Hub permet de les partager avec d'autres membres de l'équipe sans avoir à rebuilder sur chaque machine. N'importe qui peut récupérer l'image avec un docker pull et la lancer immédiatement. C'est aussi essentiel pour les pipelines CI/CD qui déploient automatiquement les nouvelles versions sur des serveurs distants. Cela permet de versionner les images avec des tags et de revenir facilement à une version antérieure en cas de problème.

  # 2-1
  Testcontainers est une bibliothèque Java permettant de lancer automatiquement des conteneurs Docker pendant les tests. Cela permet de tester l'application avec de vrais services (PostgreSQL, Redis, etc.) sans avoir à les installer localement.

  # 2-2
  Les variables sécurisées permettent de stocker des informations sensibles (mots de passe, tokens, clés API) sans les exposer dans le code source ou dans le dépôt GitHub.

  # 2-3 
  Pour ne pas construire/pousser une image si les tests échouent.

  # 2-4 
  On pousse les images Docker pour stocker et partager une version packagée de l’application, afin de pouvoir la déployer de manière fiable et identique dans tous les environnements (dev, test, production) au sein d’un pipeline CI/CD.