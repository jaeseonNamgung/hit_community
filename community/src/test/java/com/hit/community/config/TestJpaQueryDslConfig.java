package com.hit.community.config;

import com.hit.community.repository.SearchQueryDslRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestJpaQueryDslConfig {

    @Autowired
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryDslFactory(){
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public SearchQueryDslRepository searchQueryDslRepository(){
        return new SearchQueryDslRepository(jpaQueryDslFactory());
    }
}
