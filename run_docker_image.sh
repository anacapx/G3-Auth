docker run -d -e DB_USERNAME -e DB_PASSWORD -e DB_URL_AUTH -e JWT_SECRET -p 8081:8081 --name g3-auth debrum/ilab-g3-auth
docker start ilab-g3-auth