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



Lessoned Learned:
GSI-- create the table programatticaly in the config file, and add same name annotation to the JPA. However, the JPA will not all for "IN" queries only EQ at the time.
Create custom code to run a Scan-- allows for sort and paging
Scan reads index in ASC order only
Queries will allow for scan forward/backwards, but requires a HashKey partition key for reading from same hash

Solve: create a new rangekey that is far away arbitrary value and subtract real date. This creates "Desc" date values
