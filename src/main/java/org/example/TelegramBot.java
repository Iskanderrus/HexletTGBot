package org.example;
import io.github.cdimascio.dotenv.Dotenv;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TelegramBot extends TelegramLongPollingBot {
    public static void main(String[] args) throws TelegramApiException {
        System.out.println("Starting Telegram Bot...");
        TelegramBot telegramBot = new TelegramBot();
        final TelegramBotsApi telegramBotApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotApi.registerBot(telegramBot);
    }

    @Override
    public String getBotUsername() {
        return "MyPasswordGeneratorBot";
    }

    @Override
    public String getBotToken() {
        Dotenv dotenv = Dotenv.load();
        return dotenv.get("TELEGRAM_BOT_TOKEN");
    }

    @Override
    public void onUpdateReceived(final Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }
        final Message message = update.getMessage();
        String messageText = message.getText();

        String userName = message.getFrom().getUserName();
        String text = message.getText();
        System.out.println("----------" + "\n" + "From: " + userName + "\n" + text + "\n" + "----------");

        if (messageText.startsWith("/start")) {
            String greetingText = "Hello and Welcome! To generate new password send command '/generate'";
            sendMessage(message.getChatId(), greetingText, false);
        }

        if (messageText.startsWith("/generate")) {
            String[] messageParts = messageText.split(" ");
            int passwordLength = 10;
            if (messageParts.length > 1) {
                passwordLength = Integer.parseInt(messageParts[1]);
            }
            String password = PasswordGenerator.generateNewPassword(passwordLength);
            sendMessage(message.getChatId(), password, true);
        }
    }

    void sendMessage(long chatId, String messageText, boolean isMarkdown) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableHtml(isMarkdown);
        if (isMarkdown) {
            sendMessage.setText("<span class=\"tg-spoiler\">" + messageText + "</span>");
            sendMessage.setChatId(chatId);

        } else {
            sendMessage.setText(messageText);
            sendMessage.setChatId(chatId);

        }
        try {
            execute(sendMessage);
        } catch (
                TelegramApiException e) {
            System.out.println("Failed to send a message");
        }
    }
}
