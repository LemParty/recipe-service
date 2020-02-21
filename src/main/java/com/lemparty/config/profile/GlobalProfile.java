package com.lemparty.config.profile;

import com.lemparty.service.RecipeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalProfile {

    @Bean
    public String getSalt(){
        return "vIVZosBTBDhv8adNonr5mjxHq78WXQHNw8DKKqHa1RUkafwUVpcVz3azStz95CMRSW/hExuMOQAxEyL5h0KoBOebAeplJYuaFKKBMLSAcAVaiqvZ+dsRFw8lFK20+Dxck6vNEPnD06KRHt5VAjPSXnjF8M18E99njDTEO9kqwZihBgMPAMW23Flp4IpsyFz8a3R7r3ypG+kwDXP3Xyb1cfgT1Gc0v/2038vgWadLx0z75J98uXrXWygze61FeqqQRyHbUfznAZPLuRsnmeUJGOi0EJmV3vEOQ9HgY2LUBMhlaJqDEOZlilKsM+BXzO7A608KLtYns73rPXPLkFbMxqbUlq3rpXbFciXBAePqIasZsIv8S5STlraY+ty4W1fnReCP4OmwAQxCu4sS8JQMGiF1vsWWqQAKzrsy9o83csUoKkdzBue/olhbAei3tsJX+6P0t5oPii0E2QuKdgouqbGuF1KvjR9+6lle3zxe3NUoaIEAZJGpGRx4RYDOEz6dOxmlfbKZ3hSyq+dH4hejcUL5lWHScMuCeLm0CvJClB8q5WDxvSZF2E/Lj3kdmRddeYGXPLSFehnCZoGYlSfAv2VRRZqezW90zfobhvltquOWtF3tU3h8o/4qPRzK2mMt7uJwCFIzijJlak6wdJfDfwn8woN1POr0DXWMA55Uv8k=";
    }

    @Bean
    public RecipeService getRecipeService() {
        return new RecipeService();
    }
}
