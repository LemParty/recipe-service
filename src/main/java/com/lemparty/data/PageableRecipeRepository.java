package com.lemparty.data;

import com.lemparty.entity.Recipe;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableScan
public interface PageableRecipeRepository extends PagingAndSortingRepository<Recipe,String> {

    Page<Recipe> findByRecipeID(String recipeID, Pageable page);
    Page<Recipe> findByUserIDIn(List<String> userID, @PageableDefault(size = Integer.MAX_VALUE, sort = "datestring") Pageable page);
    Page<Recipe> findByUserID(String userID, @PageableDefault(size = Integer.MAX_VALUE, sort = "datestring") Pageable page);
    Page<Recipe> findByRecipeIDInOrderByDatestring(List<String> recipeID, @PageableDefault(size = Integer.MAX_VALUE, sort = "datestring") Pageable page);

}
