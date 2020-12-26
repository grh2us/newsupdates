package com.realtime.newsupdates.service;

import com.realtime.newsupdates.model.Joke;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Service
@Slf4j
public class NewsPublisher {
    @Autowired
    private WebClient webClient;

    @Autowired
    private KafkaSender<String, String> kafkaSender;

    private static final String TOPIC = "news-topic";

    @Scheduled(fixedRate = 3000)
    public void publish() {
        log.info("Publishing more data");
        dispatchMessage(this.webClient
                .get()
                .retrieve()
                .bodyToFlux(Joke.class)
        );
    }

    public void dispatchMessage(Flux<Joke> jokeFlux) {
        kafkaSender.send(jokeFlux
                .map(i -> SenderRecord.create(new ProducerRecord<>(TOPIC, i.toString()), i)))
                .doOnError(e -> log.error("Send failed", e))
                .subscribe(r -> log.info("Sent to topic {}", r.recordMetadata().topic()));
    }
}
