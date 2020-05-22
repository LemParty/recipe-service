package com.lemparty.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@DynamoDBTable(tableName = "ingredients")
public class Ingredient implements Serializable {

        @Id
        private IngredientID ingredientID;
//        @DynamoDBHashKey(attributeName = "recipeID")
//        private String recipeID;

        private String userID;

//        @DynamoDBRangeKey(attributeName = "name")
//        private String name;
        private String defaultAmount;
        private String defaultUnitOfMeasure;
        private boolean required;
        private String note;

        public boolean isRequired() {
            return required;
        }

        public void setRequired(boolean required) {
            this.required = required;
        }

        public String getUserID() {
                return userID;
        }

        public void setUserID(String userID) {
                this.userID = userID;
        }

        @DynamoDBHashKey
        public String getRecipeID() {
                return this.ingredientID.getRecipeID();
        }

        public void setRecipeID(String recipeID) {
                if (this.ingredientID == null) {
                        this.ingredientID = new IngredientID();
                }
                this.ingredientID.setRecipeID(recipeID);
        }

        @DynamoDBRangeKey
        public String getName() {
                return this.ingredientID.getName();
        }

        public void setName(String name) {
                if (this.ingredientID == null) {
                        this.ingredientID = new IngredientID();
                }
                this.ingredientID.setName(name);        }

//        public String getRecipeID() {
//                return recipeID;
//        }
//
//        public void setRecipeID(String recipeID) {
//                this.recipeID = recipeID;
//        }
//
//        public String getName() {
//                return name;
//        }
//
//        public void setName(String name) {
//                this.name = name;
//        }

        public String getDefaultAmount() {
                return defaultAmount;
        }

        public void setDefaultAmount(String defaultAmount) {
                this.defaultAmount = defaultAmount;
        }

        public String getDefaultUnitOfMeasure() {
                return defaultUnitOfMeasure;
        }

        public void setDefaultUnitOfMeasure(String defaultUnitOfMeasure) {
                this.defaultUnitOfMeasure = defaultUnitOfMeasure;
        }

        public String getNote() {
                return note;
        }

        public void setNote(String note) {
                this.note = note;
        }
}
