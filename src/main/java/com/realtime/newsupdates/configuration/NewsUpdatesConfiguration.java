package com.realtime.newsupdates.configuration;

import com.realtime.newsupdates.model.Joke;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class NewsUpdatesConfiguration {
    private static final String JOKE_API_ENDPOINT = "https://official-joke-api.appspot.com/jokes/random";

    @Bean
    public WebClient webClient(){
        return WebClient.builder()
                .baseUrl(JOKE_API_ENDPOINT)
                .build();
    }

    @Bean
    public Sinks.Many<Joke> sink(){
        return Sinks.many().replay().latest();
    }

    @Bean
    public Flux<Joke> flux(Sinks.Many<Joke> sink){
        return sink.asFlux();
    }
}
