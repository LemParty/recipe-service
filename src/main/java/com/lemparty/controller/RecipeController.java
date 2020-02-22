package com.lemparty.controller;

import com.lemparty.entity.Recipe;
import com.lemparty.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "recipes")
public class RecipeController {

    @Autowired
    RecipeService recipeService;


    @PutMapping("/recipe")
    public ResponseEntity create(@RequestBody Recipe recipe) {

        Recipe created = null;
        try {
            created = recipeService.create(recipe);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
        }


        return new ResponseEntity(created, HttpStatus.OK);
    }

    @PostMapping("/recipe")
    public ResponseEntity update(@RequestBody Recipe recipe) {

        Recipe created = null;
        try {
            created = recipeService.update(recipe);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
        }


        return new ResponseEntity(created, HttpStatus.OK);
    }

    @GetMapping("/recipe")
    public ResponseEntity get( @RequestParam(required = false, name = "name") String name,
                               @RequestParam(required = false, name = "userID") String userID,
                               @RequestParam(required = false, name = "recipeID") String recipeID) {
        Object recipe = null;
        try {
            // The most specific look up
            if(recipeID != null && !recipeID.equals("")) {
                recipe = recipeService.getRecipeByRecipeID(recipeID);


            } else if(name != null && !name.equals("")
                        && userID != null && !userID.equals("")){
                recipe = recipeService.get(name, userID);
            } else if(userID != null && !userID.equals("")){
                recipe = recipeService.getRecipeByUserID(userID);
            } else {
                return new ResponseEntity("Invalid Request", HttpStatus.BAD_REQUEST);
            }
        } catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(recipe, HttpStatus.OK);

    }
}