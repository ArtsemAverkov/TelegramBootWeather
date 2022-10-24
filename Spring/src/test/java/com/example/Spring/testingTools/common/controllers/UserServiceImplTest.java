package com.example.Spring.testingTools.common.controllers;

import com.example.Spring.model.User;
import com.example.Spring.service.user.UserService;
import com.example.Spring.testingTools.common.extensions.user.ValidParameterResolverUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;


@Slf4j
@DisplayName("Testing User")
public class UserServiceImplTest {
    @Nested
    @ExtendWith(MockitoExtension.class)
    @ExtendWith(ValidParameterResolverUser.class)

    public class ValidData {
        @Mock
        private UserService userService;


        @RepeatedTest(1)
        void shouldGetUserWhenUserIsValid(User user) {
            Mockito.when(userService.getChatId(user.getChatId())).thenReturn(Optional.of(user));
            Assertions.assertEquals(Optional.of(user),
                    (userService.getChatId(user.getChatId())));
        }

        @RepeatedTest(1)
        void shouldDeleteWhenUserIsValid(User user) {
            Mockito.when(userService.deleteUser(user.getChatId())).thenReturn(true);
            Assertions.assertEquals(true, userService.deleteUser(user.getChatId()));
        }
    }
}
