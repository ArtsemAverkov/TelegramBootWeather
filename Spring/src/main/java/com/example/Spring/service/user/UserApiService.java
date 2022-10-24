package com.example.Spring.service.user;

import com.example.Spring.model.User;
import com.example.Spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.sql.Timestamp;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserApiService implements UserService{
    private final UserRepository userRepository;


    @Override
    public Optional<User> getChatId(Long chatId) {
        Optional<User> byId = userRepository.findById(chatId);
        log.info(String.valueOf(byId));
        return byId;
    }

    @Override
    @Transactional
    public long createUser(Message message) {

        final User builderUser = builderUser(message);
        log.info(String.valueOf(builderUser));
        return userRepository.save(builderUser).getChatId();

    }

    @Override
    public boolean deleteUser(Long id) {
        Optional<User> chatId = getChatId(id);
        if (chatId.isEmpty()) {
            return false;
        } else {
            userRepository.deleteById(id);
        }
        return true;
    }

    public User builderUser (Message message){
        var chat = message.getChat();
        return User.builder()
                .chatId(message.getChatId())
                .lastName(chat.getLastName())
                .firstName(chat.getFirstName())
                .userName(chat.getUserName())
                .registeredAt(new Timestamp(System.currentTimeMillis()))
                .build();

    }

}
