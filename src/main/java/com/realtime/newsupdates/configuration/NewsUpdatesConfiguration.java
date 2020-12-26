package com.realtime.newsupdates.configuration;

import com.realtime.newsupdates.model.Joke;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class NewsUpdatesConfiguration {
    private static final String JOKE_API_ENDPOINT = "https://official-joke-api.appspot.com/jokes/random";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String CLIENT_ID_CONFIG = "newsupdate_producer_1";

    @Bean
    public WebClient webClient(){
        return WebClient.builder()
                .baseUrl(JOKE_API_ENDPOINT)
                .build();
    }

//    @Bean
//    public Sinks.Many<Joke> sink(){
//        return Sinks.many().replay().latest();
//    }
//
//    @Bean
//    public Flux<Joke> flux(Sinks.Many<Joke> sink){
//        return sink.asFlux();
//    }

    @Bean
    public KafkaSender<String, String> kafkaSender() {
        Map<String, Object> props = new HashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, CLIENT_ID_CONFIG);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        SenderOptions<String, String> senderOptions = SenderOptions.create(props);

        return KafkaSender.create(senderOptions);
    }
}
