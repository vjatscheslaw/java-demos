package com.example.demo.config;

import com.example.demo.potroshitel.TerminatorQuoter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PotroshitelConfig {

    @Bean(name = "tq")
    public TerminatorQuoter quoter() {
        TerminatorQuoter tq = new TerminatorQuoter();
        tq.setQuote("I'll be back");
        return tq;
    }

}
