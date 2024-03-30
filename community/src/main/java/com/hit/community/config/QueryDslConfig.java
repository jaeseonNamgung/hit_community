package com.hit.community.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class QueryDslConfig {

    private final EntityManager entityManager;

    @Bean
    public JPAQueryFactory JPAQueryDslFactory(){
        return new JPAQueryFactory(entityManager);
    }
}
