package com.cx.dataflow;

import com.cx.dataflow.dto.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

// run:
// mvn clean install
// or
// ./gradlew clean build
// then register custom processor:
// app register --name itemFilter --type processor --uri maven://com.cx:dataflow:0.0.1-SNAPSHOT
// app register --name itemFilter --type processor --uri file:///home/cnb/scdf/target/dataflow-0.0.2-SNAPSHOT.jar
// app unregister --name itemFilter --type processor

@EnableConfigurationProperties(ConfigProps.class)
@SpringBootApplication

public class DataflowApplication {

    @Autowired
    private ConfigProps props;

    public static void main(String[] args) {
        SpringApplication.run(DataflowApplication.class, args);
    }

    @Bean
    public Function<Item, Item> assign() {
        return (item) -> {
            item.setInfo(item.getInfo() + props.getFilter());
            System.out.println("Item id:" + item.getId());
            return item;
        };
    }

}

