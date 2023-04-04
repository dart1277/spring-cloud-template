package com.cx.fun.function;

import com.cx.fun.dto.Item;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
public class FunctionComponent {

    @Bean
    public Function<String, Item> getItem() {
        return val -> Item.builder().id("id1fun").info("Info fun " + val).build();
    }

    @Bean
    public Consumer<Item> setItem() {
        return val -> System.out.println("Consumer: "+ val.toString());
    }

    @Bean
    public Supplier<Item> getItemSup() {
        return () -> Item.builder().id("id1fun").info("Info fun supplier").build();
    }

}
