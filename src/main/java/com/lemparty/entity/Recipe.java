package com.lemparty.entity;

public class Recipe {

    private String email;
    private String workzipcode;
    private String homezipcode;
    private String gender;
    private String price;
    private String cuisine;
    private String dietrestrictions;
    private String alcohol;

    public Recipe(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWorkzipcode() {
        return workzipcode;
    }

    public void setWorkzipcode(String workzipcode) {
        this.workzipcode = workzipcode;
    }

    public String getHomezipcode() {
        return homezipcode;
    }

    public void setHomezipcode(String homezipcode) {
        this.homezipcode = homezipcode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getDietrestrictions() {
        return dietrestrictions;
    }

    public void setDietrestrictions(String dietrestrictions) {
        this.dietrestrictions = dietrestrictions;
    }

    public String getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(String alcohol) {
        this.alcohol = alcohol;
    }
}
