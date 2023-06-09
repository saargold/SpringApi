package com.devops.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

// @SpringBootApplication - meta annotation that includes component scanning, autoconfiguration
// and property support
@EnableAsync
@SpringBootApplication
public class WebappApplication {


	public static void main(String[] args) {
		SpringApplication.run(WebappApplication.class, args);
	}

	@Bean
	public Executor taskExecutor(){
//		ThreadPoolExecutor javaExecutor =
//		new ThreadPoolExecutor(3,5,3000,
//				TimeUnit.MILLISECONDS,
//				new LinkedBlockingQueue<>(100));
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(3);
		taskExecutor.setMaxPoolSize(5);
		taskExecutor.setKeepAliveSeconds(3);
		taskExecutor.setQueueCapacity(100);
		taskExecutor.initialize();
		// define a Prefix for ThreadPoolTaskExecutor threads
		taskExecutor.setThreadNamePrefix("Spring ThreadPoolTaskExecutor");
		return taskExecutor;
	}

}
