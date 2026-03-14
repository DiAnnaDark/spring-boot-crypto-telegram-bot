package com.skillbox.cryptobot.service;

import com.skillbox.cryptobot.entity.Subscriber;
import com.skillbox.cryptobot.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriberService {

    private final SubscriberRepository repository;

    public void createSubscriber(Long telegramId) {
        Optional<Subscriber> subscriber = repository.findByTelegramId(telegramId);

        if (subscriber.isEmpty()) {
            Subscriber newSubscriber = new Subscriber();
            newSubscriber.setTelegramId(telegramId);
            repository.save(newSubscriber);
        }
    }

    public void subscribe(Long telegramId, Double price) {
        Subscriber subscriber = repository.findByTelegramId(telegramId)
                .orElseThrow();

        subscriber.setPrice(price);
        repository.save(subscriber);
    }

    public Double getSubscription(Long telegramId) {
        return repository.findByTelegramId(telegramId)
                .map(Subscriber::getPrice)
                .orElse(null);
    }

    public void unsubscribe(Long telegramId) {
        Subscriber subscriber = repository.findByTelegramId(telegramId)
                .orElseThrow();

        subscriber.setPrice(null);
        repository.save(subscriber);
    }
}