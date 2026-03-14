package com.skillbox.cryptobot.bot.command;

import com.skillbox.cryptobot.service.CryptoCurrencyService;
import com.skillbox.cryptobot.service.SubscriberService;
import com.skillbox.cryptobot.utils.TextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubscribeCommand implements IBotCommand {

    private final SubscriberService subscriberService;
    private final CryptoCurrencyService cryptoService;

    @Override
    public String getCommandIdentifier() {
        return "subscribe";
    }

    @Override
    public String getDescription() {
        return "Подписывает пользователя на стоимость биткоина";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {

        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());

        try {
            if (arguments.length == 0) {
                answer.setText("Введите цену: /subscribe 35000");
                absSender.execute(answer);
                return;
            }

            String input = arguments[0];

            if (!input.matches("\\d+(\\.\\d+)?")) {
                answer.setText("Некорректная цена. Пример: /subscribe 35000");
                absSender.execute(answer);
                return;
            }

            double price = Double.parseDouble(input);

            subscriberService.subscribe(message.getFrom().getId(), price);

            double currentPrice = cryptoService.getBitcoinPrice();

            answer.setText(
                    "Текущая цена биткоина " + TextUtil.toString(currentPrice) +
                            " USD\nНовая подписка создана на стоимость " + price
            );

            absSender.execute(answer);

        } catch (Exception e) {
            log.error("Ошибка в subscribe", e);
        }
    }
}