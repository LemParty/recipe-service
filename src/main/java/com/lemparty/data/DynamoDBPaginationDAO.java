package com.lemparty.data;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.lemparty.entity.Ingredient;
import com.lemparty.entity.Recipe;
import com.lemparty.exception.InvalidRecipeException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DynamoDBPaginationDAO implements PaginationDAO {

    @Autowired
    AmazonDynamoDB amazonDynamoDB;

    public List<Recipe> findByUserID(String... userID) throws InvalidRecipeException{

        String userVar = ":user";
        String flatUserVar = "";

        DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

        Map<String, AttributeValue> expressionAttributeValues =
                new HashMap<String, AttributeValue>();

        for(int i =0; i < userID.length; i++){
            expressionAttributeValues.put(":user"+i, new AttributeValue().withS(userID[i]));
        }

        for(String s : expressionAttributeValues.keySet()){
            flatUserVar+=","+s;
        }
        flatUserVar = flatUserVar.replaceFirst(",", "");


        DynamoDBScanExpression scanRequest = new DynamoDBScanExpression()
                .withIndexName("dateUserIndex")
                .withFilterExpression("userID IN ("+flatUserVar+")")
                .withExpressionAttributeValues(expressionAttributeValues);

        List<Recipe> recipeGotten = dynamoDBMapper.scan(Recipe.class, scanRequest);

        return recipeGotten;
    }

}
