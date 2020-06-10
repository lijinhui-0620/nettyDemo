package com.ljk.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auther: kim
 * @create: 2020-05-16 12:25
 * @description:
 */
@Configuration
public class MyRule2 {

    @Bean
    public IRule roundRobinRule() {
        return new RoundRobinRule();
    }
}
