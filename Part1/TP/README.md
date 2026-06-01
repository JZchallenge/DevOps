# 1-1 
-e permet de sécuriser les clés API, mot de passe et utilisateur

# 1-2 
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

  # 1-4 
  Un multistage build permet de séparer l’environnement de construction (build) et l’environnement d’exécution (runtime).
  Dans une première étape, on utilise une image contenant un JDK et des outils comme Maven afin de compiler l’application Spring Boot et générer un fichier .jar. Dans une deuxième étape, on utilise une image le JRE, suffisant pour exécuter l’application.

  # 1-5 Why do we need a reverse proxy?
  Un reverse proxy sert d’intermédiaire entre le client et les services backend. Dans notre cas, Apache reçoit les requêtes sur le port 80 et les redirige vers le backend Spring Boot sur le port 8080. Cela permet de ne pas exposer directement le backend à l’extérieur, améliorant ainsi la sécurité.

  # 1-6
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
