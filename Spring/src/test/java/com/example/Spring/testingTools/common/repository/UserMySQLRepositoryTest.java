package com.example.Spring.testingTools.common.repository;

import com.example.Spring.model.User;
import com.example.Spring.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;


@Slf4j
@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)


public class UserMySQLRepositoryTest{
    @Autowired
    UserRepository userRepository;
    @Autowired
    TestEntityManager testEntityManager;

    private static final DockerImageName MySQL_IMAGE = DockerImageName
            .parse("mysql:latest")
            .asCompatibleSubstituteFor("mysql");
    @Container
    private  static MySQLContainer<?> mySQLContainer = new MySQLContainer<>(MySQL_IMAGE)
            .withDatabaseName("test")
            .withUsername("root")
            .withPassword("root")
            .withExposedPorts(3306);

    @DynamicPropertySource
    private static void setProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
    }

    @Test
    @Sql("/INITIAL_USER_DB_SCRIPT.sql")
    void shouldFindActiveUserLastUserName(){
        int activeUserName = userRepository.existActiveUserName("Qwerty");
        log.info(String.valueOf(activeUserName));
        testEntityManager.flush();
        testEntityManager.getEntityManager().getTransaction().commit();
        Assertions.assertEquals(0, activeUserName);

    }

}
