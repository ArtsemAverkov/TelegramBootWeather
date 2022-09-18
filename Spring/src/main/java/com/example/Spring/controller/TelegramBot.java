package com.example.Spring.controller;

import com.example.Spring.config.BotConfig;
import com.example.Spring.model.Model;
import com.example.Spring.model.User;
import com.example.Spring.service.user.UserService;
import com.vdurmont.emoji.EmojiParser;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    private UserService userService;
    @Autowired
    private Model model;
    @Autowired
    private Weather weather;



    final BotConfig config;
    boolean waitStart = false;
    static final String HELP_TEXT = "This bot is created to demonstrate Spring capabilities. \n\n" +
            "You can execute commands from the main menu\n\n" +
            "Type /start to see a welcome message\n\n" +
            "Type /mydata to see a data stored about yourself\n\n" +
            "Type /help to see a this message again\n\n";

    public TelegramBot(BotConfig config) {
        this.config = config;
        List<BotCommand> listCommands = new ArrayList<>();
        listCommands.add(new BotCommand("/start", "get welcome message"));
        listCommands.add(new BotCommand("/mydata", "get yuo data stored"));
        listCommands.add(new BotCommand("/deletedata", "delete my data"));
        listCommands.add(new BotCommand("/help", "info"));
        listCommands.add(new BotCommand("/salary", "set your salary"));
        listCommands.add(new BotCommand("/expenses", "set your salary"));
        try {
            this.execute(new SetMyCommands(listCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot command list:" + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Message message = update.getMessage();
            long chatId = update.getMessage().getChatId();
            log.info(String.valueOf(chatId));

            switch (messageText) {
                case "/start":
                    if (Objects.nonNull(userService.getChatId(chatId))) {
                        userService.createUser(update.getMessage());
                    }
                    startCommandReceived(message, update.getMessage().getChat().getFirstName());
                    break;


                case "/help":
                    sendMassage(message, HELP_TEXT);
                    break;

                case "/mydata":
                    Optional<User> serviceChatId = userService.getChatId(chatId);
                    if (serviceChatId.isEmpty()) {
                        sendMassage(message, "Извините ваших данных нет в базе");
                    } else {
                        myDataCommand(update.getMessage());
                    }
                    break;

                case "/deletedata":
                    boolean deleteUser = userService.deleteUser(chatId);
                    if (deleteUser) {
                        deleteCommandReceived(update.getMessage());
                    } else {
                        sendMassage(message, "Извините такого пользователя нет");
                    }
                    break;
                default:
                    try {
                        sendMassage(message, weather.getWeather(messageText, model));
                    } catch (IOException e) {
                        sendMassage(message, "Город не найден");
                    }
            }
        }
    }




    private void startCommandReceived(Message message, String name) {
        String answer = EmojiParser.parseToUnicode("Hi," + name + ", nice to mit you!" + " :blush:");
        log.info("Replied to user" + name);
        sendMassage(message, answer);
    }

    private void deleteCommandReceived(Message message) {
        var chatId = message.getChatId();
        String s = EmojiParser.parseToUnicode("Данные успешно удалены");
        sendMassage(message, s);
    }

    private void myDataCommand(Message message) {
        var chatId = message.getChatId();
        var chat = message.getChat();
        String toUnicode = EmojiParser.parseToUnicode(chat.getFirstName() + "\n" + chat.getFirstName() + "\n" +
                chat.getUserName() + "\n" + chat.getId());
        sendMassage(message, toUnicode);
    }

    public void sendMassage(Message message, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText(textToSend);
        sendMessage.setReplyToMessageId(message.getMessageId());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Error occurred:" + e.getMessage());
        }
    }
}




