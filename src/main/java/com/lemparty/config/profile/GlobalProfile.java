package com.lemparty.config.profile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Page;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.lemparty.data.DynamoDBPaginationDAO;
import com.lemparty.data.PaginationDAO;
import com.lemparty.entity.Ingredient;
import com.lemparty.entity.Recipe;
import com.lemparty.service.RecipeService;
import com.lemparty.util.SSLContextHelper;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.lemparty.data")
public class GlobalProfile {

    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;

    @Value("${amazon.dynamodb.region}")
    private String amazonDynamoDBRegion;

    @Value("${amazon.aws.accesskey}")
    private String awsAccessKey;

    @Value("${amazon.aws.secretkey}")
    private String awsSecretKey;

    @Value("${recipe.date.cap}")
    private String dateCap;

    @Bean
    public Date getDateCap(){
        try {
            return new SimpleDateFormat("MM/dd/yyyy").parse(dateCap);
        } catch(ParseException e){
            System.out.println(e);
        }

        return null;
    }

    @Bean
    public String getSalt(){
        return "vIVZosBTBDhv8adNonr5mjxHq78WXQHNw8DKKqHa1RUkafwUVpcVz3azStz95CMRSW/hExuMOQAxEyL5h0KoBOebAeplJYuaFKKBMLSAcAVaiqvZ+dsRFw8lFK20+Dxck6vNEPnD06KRHt5VAjPSXnjF8M18E99njDTEO9kqwZihBgMPAMW23Flp4IpsyFz8a3R7r3ypG+kwDXP3Xyb1cfgT1Gc0v/2038vgWadLx0z75J98uXrXWygze61FeqqQRyHbUfznAZPLuRsnmeUJGOi0EJmV3vEOQ9HgY2LUBMhlaJqDEOZlilKsM+BXzO7A608KLtYns73rPXPLkFbMxqbUlq3rpXbFciXBAePqIasZsIv8S5STlraY+ty4W1fnReCP4OmwAQxCu4sS8JQMGiF1vsWWqQAKzrsy9o83csUoKkdzBue/olhbAei3tsJX+6P0t5oPii0E2QuKdgouqbGuF1KvjR9+6lle3zxe3NUoaIEAZJGpGRx4RYDOEz6dOxmlfbKZ3hSyq+dH4hejcUL5lWHScMuCeLm0CvJClB8q5WDxvSZF2E/Lj3kdmRddeYGXPLSFehnCZoGYlSfAv2VRRZqezW90zfobhvltquOWtF3tU3h8o/4qPRzK2mMt7uJwCFIzijJlak6wdJfDfwn8woN1POr0DXWMA55Uv8k=";
    }

    @Bean
    public AWSCredentials amazonAWSCredentials() {
        return new BasicAWSCredentials(awsAccessKey, awsSecretKey);
    }

    @Bean
    public PaginationDAO getPaginationDAO(){
        return new DynamoDBPaginationDAO();
    }



    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        AmazonDynamoDBClientBuilder builder=   AmazonDynamoDBClientBuilder
                .standard();

        if(!amazonDynamoDBEndpoint.equals("") && !amazonDynamoDBRegion.equals("")) {
            builder.withEndpointConfiguration(
                    new AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint, amazonDynamoDBRegion));
        }

        AmazonDynamoDB amazonDynamoDB = builder.build();
        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);


        ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
        attributeDefinitions.add(new AttributeDefinition().withAttributeName("recipeID").withAttributeType("S"));
        attributeDefinitions.add(new AttributeDefinition().withAttributeName("userID").withAttributeType("S"));
        attributeDefinitions.add(new AttributeDefinition().withAttributeName("datestring").withAttributeType("N"));
//
        ArrayList<KeySchemaElement> tableKeySchema = new ArrayList<KeySchemaElement>();
        tableKeySchema.add(new KeySchemaElement().withAttributeName("recipeID").withKeyType(KeyType.HASH)); // Partition
//        tableKeySchema.add(new KeySchemaElement().withAttributeName("datestring").withKeyType(KeyType.RANGE)); // Partition

        ArrayList<KeySchemaElement> indexKeySchema = new ArrayList<KeySchemaElement>();
        indexKeySchema.add(new KeySchemaElement()
                .withAttributeName("userID")
                .withKeyType(KeyType.HASH));  //Partition key
        indexKeySchema.add(new KeySchemaElement()
                .withAttributeName("datestring")
                .withKeyType(KeyType.RANGE));  //Sort key

//
        GlobalSecondaryIndex userDateIndex = new GlobalSecondaryIndex().withIndexName("dateUserIndex")
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L))
                .withKeySchema(indexKeySchema)
                .withProjection(new Projection().withProjectionType("ALL"));



//        CreateTableRequest tableRequestRecipe = dynamoDBMapper.
//                .generateCreateTableRequest(Recipe.class).withTableName("recipes");
        CreateTableRequest tableRequestRecipe =new CreateTableRequest()
                .withTableName("recipes");
        tableRequestRecipe.setAttributeDefinitions(attributeDefinitions);
        tableRequestRecipe.setKeySchema(tableKeySchema);
        List<GlobalSecondaryIndex> gsiList = new ArrayList<GlobalSecondaryIndex>();
        gsiList.add(userDateIndex);
        tableRequestRecipe.setGlobalSecondaryIndexes(gsiList);

//        CreateTableRequest tableRequestRecipe =new CreateTableRequest()
//                .withTableName("Recipe")
//                .withAttributeDefinitions(attributeDefinitions)
//                .withKeySchema(tableKeySchema)
//                .withGlobalSecondaryIndexes(userDateIndex);

        CreateTableRequest tableRequestIngredients = dynamoDBMapper
                .generateCreateTableRequest(Ingredient.class);

        tableRequestRecipe.setProvisionedThroughput(
                new ProvisionedThroughput(1L, 1L));
        tableRequestIngredients.setProvisionedThroughput(
                new ProvisionedThroughput(1L, 1L));

        TableUtils.createTableIfNotExists(amazonDynamoDB, tableRequestRecipe);
        TableUtils.createTableIfNotExists(amazonDynamoDB, tableRequestIngredients);

        try {
            TableUtils.waitUntilActive(amazonDynamoDB, "recipes");
            DescribeTableResult dtr = amazonDynamoDB.describeTable("recipes");
            dtr.getTable().getGlobalSecondaryIndexes();
        } catch(InterruptedException e){
            System.out.println(e);
        }
        return amazonDynamoDB;
    }
}
