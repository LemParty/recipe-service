package com.lemparty.config.profile;

import com.lemparty.SSLContextHelper;
import com.lemparty.data.MongoRecipeDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//turns out we don't need to specify the profile, or the properties; spring boot so smart
//@Profile("default")
//@PropertySource("application-dev.properties")
@Configuration
public class DataSourcesConfig {

    @Value( "${mongo.url}" )
    private String mongoURL;

    @Value( "${mongo.privatekey.name}" )
    private String mongoPrivateKeyName;

    @Bean
    public MongoRecipeDAO getMongoRecipeDAO() {
        if(mongoPrivateKeyName != null && !mongoPrivateKeyName.equals("")) {
            System.out.println("Using mongo Private Key: "+mongoPrivateKeyName);
            SSLContextHelper.setSslProperties(mongoPrivateKeyName);
        }
        return new MongoRecipeDAO(mongoURL);
    }




}
