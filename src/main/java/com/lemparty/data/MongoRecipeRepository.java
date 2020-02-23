package com.lemparty.data;

import com.lemparty.entity.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MongoRecipeRepository extends MongoRepository<Recipe, String> {

    @Query(value="{ 'name' : ?0, 'userID' : ?1 }")
    Optional<Recipe> findByNameAndUserID(String name, String userID);

    @Query(value="{ 'userID' : ?0 }")
    List<Recipe> findByUserID(String userID);
}
