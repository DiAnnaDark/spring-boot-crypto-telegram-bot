package com.skillbox.cryptobot.service;

import com.skillbox.cryptobot.entity.Subscriber;
import com.skillbox.cryptobot.repository.SubscriberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import com.skillbox.cryptobot.bot.CryptoBot;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class PriceCheckerService {

    private final CryptoCurrencyService cryptoCurrencyService;
    private final SubscriberRepository subscriberRepository;
    private final CryptoBot cryptoBot;

    @Scheduled(fixedDelayString = "${app.crypto.check-price-delay-ms}")
    public void checkPrice() {

        try {

            double currentPrice = cryptoCurrencyService.getBitcoinPrice();

            List<Subscriber> subscribers = subscriberRepository.findAll();

            subscribers.stream()
                    .filter(s -> s.getPrice() != null)
                    .filter(s -> s.getPrice() > currentPrice)
                    .forEach(subscriber -> sendNotification(subscriber, currentPrice));

        } catch (Exception e) {
            log.error("Ошибка проверки цены", e);
        }
    }

    private void sendNotification(Subscriber subscriber, double currentPrice) {

        try {

            if (subscriber.getLastNotificationTime() != null &&
                    subscriber.getLastNotificationTime().plusMinutes(10).isAfter(LocalDateTime.now())) {
                return;
            }

            SendMessage message = new SendMessage();
            message.setChatId(subscriber.getTelegramId());
            message.setText("Пора покупать, стоимость биткоина " + currentPrice);

            cryptoBot.execute(message);

            subscriber.setLastNotificationTime(LocalDateTime.now());
            subscriberRepository.save(subscriber);

        } catch (Exception e) {
            log.error("Ошибка отправки уведомления", e);
        }
    }
}