docker rm recipe-service --force
docker build -f dockerfile-run -t lemparty/recipe-service:runtime .
docker run -d --name recipe-service -p 8080:8080 lemparty/recipe-service:runtime