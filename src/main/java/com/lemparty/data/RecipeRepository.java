package com.lemparty.data;

import com.lemparty.entity.Recipe;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableScan
public interface RecipeRepository extends CrudRepository<Recipe, String> {

//    @Query(value="{ 'name' : ?0, 'userID' : ?1 }")
    Optional<Recipe> findByNameAndUserID(String name, String userID);

//    @Query(value="{ 'userID' : ?0 }")
    List<Recipe> findByUserID(String userID);
}
