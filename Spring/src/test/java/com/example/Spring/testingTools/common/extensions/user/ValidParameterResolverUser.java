package com.example.Spring.testingTools.common.extensions.user;

import com.example.Spring.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Slf4j
public class ValidParameterResolverUser implements ParameterResolver {
    public static List<User> validUser = Arrays.asList(
           new User(12231L,
                   "Artsem",
                   "Averkov",
                   "Temaaak",
                   Timestamp.valueOf("2019-04-21 14:17:02.0")),
           new User(124554L,
                   "Anton",
                   "Kurako",
                   "Tonni",
                   Timestamp.valueOf("2019-04-21 14:17:02.0")));


    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return parameterContext.getParameter().getType()==User.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return validUser.get(new Random().nextInt(validUser.size()));
    }
}
