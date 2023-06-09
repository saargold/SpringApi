//package com.devops.webapp;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Arrays;
//
//@Configuration
//public class SeedDB {
//    private static final Logger logger = LoggerFactory.getLogger(SeedDB.class);
////    class declares one or more @Bean method
////    Spring IoC generates bean definitions and handles request beans (at runtime)
//    @Bean
////    CommandLineRunner beans are created once the application context is loaded
//    CommandLineRunner initDatabase(PostRepo postRepo, ProfileRepo profileRepo){
//        return args -> {
//            Profile profile = profileRepo.save(new Profile("Shoshi"));
//            Post post1 = postRepo.save(new Post("title1","This is our 1st post",profile));
//            Post post2 = postRepo.save(new Post("title2","This is our 2nd post",profile));
//            profile.setPosts(Arrays.asList(post1,post2));
//            logger.info("" + profile+"Saar");
//        };
//    }
//}
