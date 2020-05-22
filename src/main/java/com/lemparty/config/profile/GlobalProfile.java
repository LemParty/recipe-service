package com.lemparty.config.profile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.lemparty.entity.Ingredient;
import com.lemparty.entity.Recipe;
import com.lemparty.service.RecipeService;
import com.lemparty.util.SSLContextHelper;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

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

    @Bean
    public String getSalt(){
        return "vIVZosBTBDhv8adNonr5mjxHq78WXQHNw8DKKqHa1RUkafwUVpcVz3azStz95CMRSW/hExuMOQAxEyL5h0KoBOebAeplJYuaFKKBMLSAcAVaiqvZ+dsRFw8lFK20+Dxck6vNEPnD06KRHt5VAjPSXnjF8M18E99njDTEO9kqwZihBgMPAMW23Flp4IpsyFz8a3R7r3ypG+kwDXP3Xyb1cfgT1Gc0v/2038vgWadLx0z75J98uXrXWygze61FeqqQRyHbUfznAZPLuRsnmeUJGOi0EJmV3vEOQ9HgY2LUBMhlaJqDEOZlilKsM+BXzO7A608KLtYns73rPXPLkFbMxqbUlq3rpXbFciXBAePqIasZsIv8S5STlraY+ty4W1fnReCP4OmwAQxCu4sS8JQMGiF1vsWWqQAKzrsy9o83csUoKkdzBue/olhbAei3tsJX+6P0t5oPii0E2QuKdgouqbGuF1KvjR9+6lle3zxe3NUoaIEAZJGpGRx4RYDOEz6dOxmlfbKZ3hSyq+dH4hejcUL5lWHScMuCeLm0CvJClB8q5WDxvSZF2E/Lj3kdmRddeYGXPLSFehnCZoGYlSfAv2VRRZqezW90zfobhvltquOWtF3tU3h8o/4qPRzK2mMt7uJwCFIzijJlak6wdJfDfwn8woN1POr0DXWMA55Uv8k=";
    }

    @Bean
    public AWSCredentials amazonAWSCredentials() {
        return new BasicAWSCredentials(awsAccessKey, awsSecretKey);
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        AmazonDynamoDB amazonDynamoDB =
                AmazonDynamoDBClientBuilder
                        .standard()
                        .withEndpointConfiguration(
                                new AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint, amazonDynamoDBRegion))
                        .build();

        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);


//        ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
//        attributeDefinitions.add(new AttributeDefinition().withAttributeName("recipeID").withAttributeType("S"));
//
//        ArrayList<KeySchemaElement> tableKeySchema = new ArrayList<KeySchemaElement>();
//        tableKeySchema.add(new KeySchemaElement().withAttributeName("ingredientID").withKeyType(KeyType.HASH)); // Partition
//
//        // Initial provisioned throughput settings for the indexes
//        ProvisionedThroughput ptIndex = new ProvisionedThroughput().withReadCapacityUnits(1L)
//                .withWriteCapacityUnits(1L);
//
//        GlobalSecondaryIndex createRecipeIDIndex = new GlobalSecondaryIndex().withIndexName("RecipeIDIndex")
//                .withProvisionedThroughput(ptIndex)
//                .withIndexName("PrecipIndex")
////                .withKeySchema(new KeySchemaElement().withAttributeName("recipeID").withKeyType(KeyType.HASH))
//                .withProjection(new Projection().withProjectionType("ALL"));



        CreateTableRequest tableRequestRecipe = dynamoDBMapper
                .generateCreateTableRequest(Recipe.class);
        CreateTableRequest tableRequestIngredients = dynamoDBMapper
                .generateCreateTableRequest(Ingredient.class);
//                .withAttributeDefinitions(attributeDefinitions).withKeySchema(tableKeySchema)
//                .withGlobalSecondaryIndexes(createRecipeIDIndex);


        tableRequestRecipe.setProvisionedThroughput(
                new ProvisionedThroughput(1L, 1L));
        tableRequestIngredients.setProvisionedThroughput(
                new ProvisionedThroughput(1L, 1L));

        TableUtils.createTableIfNotExists(amazonDynamoDB, tableRequestRecipe);
        TableUtils.createTableIfNotExists(amazonDynamoDB, tableRequestIngredients);

        return amazonDynamoDB;
    }
}
