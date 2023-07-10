package com.devops.webapp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SeedDB {
    private static final Logger logger = LoggerFactory.getLogger(SeedDB.class);
//    class declares one or more @Bean method
//    Spring IoC generates bean definitions and handles request beans (at runtime)
    @Bean
//    CommandLineRunner beans are created once the application context is loaded
    CommandLineRunner initDatabase(ProductRepo productRepo,UserRepo userRepo,OrderRepo orderRepo){
        return args -> {
//            Profile profile = profileRepo.save(new Profile("Shoshi"));
//            Post post1 = postRepo.save(new Post("title1","This is our 1st post",profile));
//            Post post2 = postRepo.save(new Post("title2","This is our 2nd post",profile));
            Product p1 = productRepo.save(new Product("test", 1231.0,"cat","", 4.0));
            Product p2 = productRepo.save(new Product("test1",1231,"Sport","", 2.1));
            Product p3 = productRepo.save(new Product("test2",1231,"haifa","", 3.5));
            Product p4 = productRepo.save(new Product("test3",1231,"1321","", 4.1));
            Product p5 = productRepo.save(new Product("test5",1231,"1321","", 2.9));

            User u1 = userRepo.save(new User("saarg"));
            List<Product> products = new ArrayList<>();
            products.add(p1);
            products.add(p2);
            products.add(p3);
            products.add(p4);
            Order order = orderRepo.save(new Order(products,u1));



           // profile.setPosts(Arrays.asList(post1,post2));
            logger.info(u1.getUserName() + u1.getId());
            logger.info(order.toString());

        };
    }
}
