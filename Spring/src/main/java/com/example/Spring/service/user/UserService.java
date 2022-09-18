package com.example.Spring.service.user;

import com.example.Spring.model.User;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

public interface UserService {
    Optional<User> getChatId(Long id);
    long createUser(Message message);
    boolean deleteUser(Long id);

    void checkLogin(String login);
}
