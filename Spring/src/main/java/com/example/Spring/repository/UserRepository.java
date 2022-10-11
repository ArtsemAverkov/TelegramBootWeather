package com.example.Spring.repository;

import com.example.Spring.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
    @Query(value = "select count(user_name) from user where user_name =:userName", nativeQuery = true)
    int existActiveUserName(String userName);

}
