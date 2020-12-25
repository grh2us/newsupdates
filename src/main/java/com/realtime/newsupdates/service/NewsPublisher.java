package com.realtime.newsupdates.service;

import com.realtime.newsupdates.model.Joke;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Sinks;

@Service
public class NewsPublisher {
    @Autowired
    private WebClient webClient;

    @Autowired
    private Sinks.Many<Joke> sink;

    @Scheduled(fixedRate = 3000)
    public void publish(){
        System.out.println("Helloooooooooo!");
        this.webClient
                .get()
                .retrieve()
                .bodyToMono(Joke.class)
                .subscribe(this.sink::tryEmitNext);
    }
}
