package com.lemparty;

import com.lemparty.util.SSLContextHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecipeServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(RecipeServiceApplication.class, args);
	}

}
