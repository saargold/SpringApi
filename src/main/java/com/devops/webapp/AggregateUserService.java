package com.devops.webapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/*
 We use @Service to do 2 things:
 1. theoretical decoupling: between Views (HTML document, deserialized DAO/DTO),
 Controllers that serve users at different URLs with specific parameters
 and business logic of our application (Model)
 2. In a practical sense: this annotation makes Spring scan this component and use it as
 a candidate for autowiring
 */
@Service

/**
 * This class uses a remote REST endpoint to get data from Github's REST API.
 */
public class AggregateUserService {
    // RestTemplate is used to invoke an external REST endpoint by another service
    private RestTemplate restTemplate;
    private static final Logger serviceLogger = LoggerFactory.getLogger(AggregateUserService.class);


    public AggregateUserService(RestTemplateBuilder templateBuilder){
        this.restTemplate = templateBuilder.build();
    }

                /*
    Asynchronous tasks types in Java:
    1. Runnable - interface
    2. Callable<V> - interface
    3. RunnableFuture<V> - interface that defines a Runnable that can return as Future<V>
    4. A concrete type that implements RunnableFuture is FutureTask<V>. it is a generic type
    that can wrap both Runnable and Callable<V> tasks
    ** Disadvantages of Callable<V> in conjunction with Future<V>?
    1. no manual completion of a task in case of failure
    2. only 3 exceptions can be thrown (what about UnAuthorizedException if API requires authentication)
    3. no concurrent tasks result can be joined in a separate thread
    4. no pipelining of concurrent tasks that depend on each other
    Can be resolved using CompletableFuture<V>
     */

    @Async
    public CompletableFuture<GithubUser> getUserDetails(String userName){
        String template = String.format("https://api.github.com/users/%s",userName);
        GithubUser aUser = this.restTemplate.getForObject(template,GithubUser.class);
        serviceLogger.info("Running in thread = " + Thread.currentThread().getName());
        return CompletableFuture.completedFuture(aUser);
    }
}
