package com.cx.web.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
@Service
public class KafkaProd {

    public static final String TOPIC_NAME = "quote-feedback";

    public void publish(String body) {
        try (Admin admin = Admin.create(Map.of(
                "bootstrap.servers", "localhost:29092"
        ))) {
            final Map<String, TopicListing> topicListings = admin.listTopics().namesToListings().get();
            log.warn(topicListings.toString());
            if (topicListings.isEmpty()) {
                final NewTopic newTopic = new NewTopic(TOPIC_NAME, 2, (short) 1)
                        .configs(Map.of("min.insync.replicas", "1",
                                "segment.bytes", "573741824",
                                "segment.ms", "86400000",
                                "retention.bytes", "1073741824",
                                "retention.ms", "604800000"
                                ));
                //final NewTopic newTopic = new NewTopic(TOPIC_NAME, Optional.empty(), Optional.empty());
                admin.createTopics(List.of(newTopic)).all();
            } else {
                System.out.println(topicListings);
            }

            System.out.println(admin.describeTopics(List.of(TOPIC_NAME)).allTopicNames().get());

            // kafka stores topics in /var/lib/kafka/data/
            final Properties props = new Properties();
            props.setProperty("bootstrap.servers", "localhost:29092");
            //props.setProperty("group.id", "consumer-service-group-name");
            // props.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            //props.setProperty("key.serializer", IntegerSerializer.class.getName());
            props.setProperty("key.serializer", IntegerSerializer.class.getName());
            props.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            // high throughput producer (at the expense of a bit of latency and CPU usage)
            //props.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy"); // "gzip"
            //props.setProperty(ProducerConfig.LINGER_MS_CONFIG, "20"); // 0 by default
           // props.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32*1024)); // 32 KB batch size
           props.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(1*1024)); // 1 KB batch size
           props.setProperty(ProducerConfig.RETRIES_CONFIG, "3");
           props.setProperty(ProducerConfig.ACKS_CONFIG, "all");
           //props.setProperty(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, "1000"); // = linger + request timeout

            try (KafkaProducer<Integer, String> producer = new KafkaProducer<>(props)) {
                // by default kafka uses hash based partitioning
                for (int i=0; i< 128; ++i) {
                    ProducerRecord<Integer, String> record = new ProducerRecord<>(TOPIC_NAME, i%2, i, body);
                    Future<RecordMetadata> future = producer.send(record);
                    System.out.println(future.get().partition());
                    producer.flush();
                }

            }


        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}
