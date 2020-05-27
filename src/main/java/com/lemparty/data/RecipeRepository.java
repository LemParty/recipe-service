package com.lemparty.data;

import com.lemparty.entity.Recipe;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
@EnableScan
public interface RecipeRepository extends CrudRepository<Recipe, String> {

    Optional<Recipe> findByNameAndUserID(String name, String userID);
    List<Recipe> findByUserID(String userID);
    List<Recipe> findByUserIDIn(String[] userID);

}
