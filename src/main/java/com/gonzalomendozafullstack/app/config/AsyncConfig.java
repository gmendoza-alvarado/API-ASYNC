package com.gonzalomendozafullstack.app.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {
	
	@Bean(name = "asyncExecutor")
	public Executor asyncExecutor() {
		ThreadPoolTaskExecutor executor =  new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2); //minimo de hilos en esta App
		executor.setMaxPoolSize(4);
		executor.setQueueCapacity(100); //Maximo de hilos que pueden ser creados, pero estan a la espera de ser atendidos
		executor.setThreadNamePrefix("AsyncThread-"); //nombre al hilo que estas creando
		executor.initialize();
		return executor;
		
	}

}
