package com.cx.worker.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaWorker {

    public static final String TOPIC_NAME = "quote-feedback";

    @EventListener(ApplicationReadyEvent.class)
    public  void startApp() {

        final Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:29092");
        props.setProperty("group.id", "consumer-service-group-name");
        //props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("key.deserializer", IntegerDeserializer.class.getName());
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // read from beginning
        props.setProperty(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, "1000");
        props.setProperty(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "900"); // timeout for heartbeat
        props.setProperty(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "5000"); // timeout for poll (secondary hearbeat)

        try( KafkaConsumer<Integer, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(List.of(TOPIC_NAME));

            while (true) {
                var records = consumer.poll(Duration.of(1, ChronoUnit.SECONDS)); //Duration.ofSeconds()
                for(var record: records) {
                    log.warn(record.toString());
                }
            }
        }

    }

}
