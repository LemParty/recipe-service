package com.lemparty.data;

import com.lemparty.entity.Ingredient;
import com.lemparty.entity.IngredientID;
import com.lemparty.entity.Recipe;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableScan
public interface IngredientRepository extends CrudRepository<Ingredient, IngredientID> {

    List<Ingredient> findByRecipeID(String recipeID);
    List<Ingredient> findByName(String name);

//    @Override
//    Iterable<Ingredient> findAllById(Iterable<String> iterable);

    List<Ingredient> findAllByRecipeIDIn(List<String> recipeID);

}
