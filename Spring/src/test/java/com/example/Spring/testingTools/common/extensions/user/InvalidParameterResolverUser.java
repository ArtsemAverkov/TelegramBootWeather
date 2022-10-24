package com.example.Spring.testingTools.common.extensions.user;

import com.example.Spring.model.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class InvalidParameterResolverUser implements ParameterResolver {
    public static List<User> validUser = Arrays.asList(
            new User( 1,
                    "Artsem",
                    "Averkov",
                    null,
                    Timestamp.valueOf("2019-04-21 14:17:02.0")),
            new User(2,
                    "Anton",
                    "Kurako",
                    null,
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
