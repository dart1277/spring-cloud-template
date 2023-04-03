package com.cx.stream.processor;

import com.cx.stream.dto.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@Configuration
public class ConsumerConfig {

    @Bean
    public Consumer<Item> read() {
        return (Item item) ->
                System.out.println("consumer-> :" + item.toString());
    }

    @Bean
    public Function<Item, Item> processItem() {
        return (Item item) -> {
            System.out.println("processor-> :" + item.toString());
            item.setInfo(item.getInfo()+"!");
            return item;
        };
    }

}
