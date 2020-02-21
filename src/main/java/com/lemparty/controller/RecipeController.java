package com.lemparty.controller;

import com.lemparty.entity.Recipe;
import com.lemparty.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(value = "recipe")
public class RecipeController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    RecipeService recipeService;

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody Recipe recipe) {


        try {
            recipeService.saveProfile(recipe);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
        }


        return new ResponseEntity(recipeService, HttpStatus.OK);
    }

//    @RequestMapping("/retrieve")
//    public ResponseEntity getRecipe(@RequestParam(name = "email") String email) {
//        Recipe recipe = null;
//        try {
//            recipe = recipeService.getRe(email);
//
//        } catch(Exception e){
//            e.printStackTrace();
//            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        return new ResponseEntity(recipe, HttpStatus.OK);
//
//    }
}