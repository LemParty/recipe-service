package com.lemparty.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class IngredientID implements Serializable {

        private static final long serialVersionUID = 1L;

        private String recipeID;
        private String name;

        @DynamoDBHashKey
        public String getRecipeID() {
                return recipeID;
        }

        public void setRecipeID(String recipeID) {
                this.recipeID = recipeID;
        }

        @DynamoDBRangeKey
        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }
}
