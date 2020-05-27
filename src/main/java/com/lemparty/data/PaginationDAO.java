package com.lemparty.data;

import com.lemparty.entity.Recipe;
import com.lemparty.exception.InvalidRecipeException;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface PaginationDAO  {

    public List<Recipe> findByUserID(String... userID) throws InvalidRecipeException;

}
