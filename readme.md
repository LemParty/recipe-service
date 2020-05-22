docker build -f dockerfile -t lemparty/recipe-builder:latest .
docker run -d --name recipe-builder lemparty/recipe-builder:latest



docker rm recipe-service --force
docker build -f dockerfile-run -t lemparty/recipe-service:runtime .
docker run -d --name recipe-service -p 8080:8080 lemparty/recipe-service:runtime


sudo java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb -dbPath /data


aws dynamodb list-tables --endpoint-url http://localhost:8000
aws dynamodb delete-table --table-name recipes --endpoint-url http://localhost:8000
aws dynamodb delete-table --table-name ingredients --endpoint-url http://localhost:8000


--NO LIVE RELOAD when in httpd
ng serve --live-reload false


IngredientID to encapsulate recipeID as main hash key, name as sort key; extends serializable
this becomes ID for entity Ingredient
In service, custom getting in CRUD repository-- findAllByRecipeIDIn