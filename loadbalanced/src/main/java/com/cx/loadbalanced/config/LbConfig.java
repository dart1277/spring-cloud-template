package com.cx.loadbalanced.config;

import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.core.RandomLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.core.env.Environment;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


// or @LoadBalancerClient(name = "custom-alb-name", configuration = LbConfig.class)
// can be used along with a bean annotated with @LoadBalanced annotation
@Configuration
public class LbConfig {

    @Bean
    ReactorLoadBalancer<ServiceInstance> randLB(Environment env, LoadBalancerClientFactory factory) {
        final String name = env.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new RandomLoadBalancer(factory.getLazyProvider(name, ServiceInstanceListSupplier.class), name);
    }

    @Bean
    public ServiceInstanceListSupplier staticServiceSupplier(LbConfigProps props, Environment env) {
        return new StaticServiceInstanceListSupplier(props, env);
    }
}

class StaticServiceInstanceListSupplier implements ServiceInstanceListSupplier {

    private final String serviceId;
    private final List<ServiceInstance> instances;

    StaticServiceInstanceListSupplier(LbConfigProps props, Environment env) {
        serviceId = env.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);

        instances = props.getInstances().stream().flatMap(StaticServiceInstanceListSupplier::toSvcInstance).toList();
    }

    private static Stream<ServiceInstance> toSvcInstance(LbConfigProps.ServiceConfig cfg) {
        return Arrays.stream(cfg.getServers().split(","))
                .map(String::trim)
                .map((urlStr) -> {
                            var instance = new DefaultServiceInstance();
                            instance.setUri(URI.create(urlStr));
                            return instance;
                        }
                );
    }

    @Override
    public String getServiceId() {
        return serviceId;
    }

    @Override
    public Flux<List<ServiceInstance>> get() {
        return Flux.just(instances);
    }
}
