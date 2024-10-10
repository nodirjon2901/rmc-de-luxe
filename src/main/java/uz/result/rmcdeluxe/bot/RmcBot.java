package uz.result.rmcdeluxe.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.result.rmcdeluxe.entity.Application;
import uz.result.rmcdeluxe.entity.ApplicationOfInvestment;
import uz.result.rmcdeluxe.entity.Button;
import uz.result.rmcdeluxe.entity.Counter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RmcBot extends TelegramLongPollingBot {

    @Value("${bot.token}")
    private String token;

    @Value("${bot.username}")
    private String username;

    @Value("${group.chatId}")
    private String groupChatId;

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public void onUpdateReceived(Update update) {

    }

    public void handleSendApplication(Application application) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(groupChatId);
        sendMessage.setParseMode("Markdown");
        sendMessage.setText(
                "*Новое заявка*\n\n" +
                        "\uD83D\uDC64 *ФИО*: " + application.getFullName() + "\n" +
                        "\uD83D\uDCDE *Номер телефона*: " + application.getPhoneNum() + "\n" +
                        "\uD83D\uDCE7 *Электронная почта*: " + application.getEmail() + "\n" +
                        "\uD83D\uDCAC *Вопрос*: " + application.getQuestion() + "\n"
        );
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void handleSendInvestmentApplication(ApplicationOfInvestment application) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(groupChatId);
        sendMessage.setParseMode("Markdown");
        sendMessage.setText(
                "*Новое заявка на инвестиции*\n\n" +
                        "\uD83D\uDC64 *ФИО*: " + application.getFullName() + "\n" +
                        "\uD83C\uDFE2 *Название компании*: " + application.getCompanyName() + "\n" +
                        "\uD83D\uDCDE *Номер телефона*: " + application.getPhoneNum() + "\n" +
                        "\uD83D\uDCE7 *Выберите услугу*: " + application.getService() + "\n" +
                        "\uD83D\uDCAC *Сообщение*: " + application.getMessage() + "\n"
        );
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendCounter(List<Counter> counters, Long totalApplications) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(groupChatId);
        StringBuilder textBuilder = new StringBuilder();

        textBuilder.append("<b>Еженедельный отчет \uD83D\uDCCB</b>\n \n");

        if (counters == null || counters.isEmpty() && totalApplications == 0) {
            textBuilder.append("<b>Поступившие заявки:</b> 0\n \n");
        } else {
            Map<Button, Long> buttonCountMap = new HashMap<>();
            long totalCalls = 0;
            long totalAppointments = 0;

            for (Counter counter : counters) {
                Button button = counter.getSection();
                long countCall = counter.getCountCall() != null ? counter.getCountCall() : 0;

                if (button == Button.SEND_APPLICATION) {
                    totalAppointments += countCall;
                } else {
                    buttonCountMap.put(button, buttonCountMap.getOrDefault(button, 0L) + countCall);
                }
                totalCalls += countCall;
            }

            textBuilder.append(String.format("<b>Запись на прием: </b> %d\n", totalAppointments));

            for (Map.Entry<Button, Long> entry : buttonCountMap.entrySet()) {
                textBuilder.append(String.format("<b>%s: </b> %d\n", getButtonDisplayName(entry.getKey()), entry.getValue()));
            }
            textBuilder.append(String.format("\n<b>Общее количество заявок:</b> %d\n", totalApplications));
        }
        String text = textBuilder.toString();
        sendMessage.setText(text);
        sendMessage.setParseMode("HTML");

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String getButtonDisplayName(Button button) {
        return switch (button) {
            case CALL -> "Phone (Header)";
            case SEND_APPLICATION -> "Отправить";
            case WHATSAPP -> "WhatsApp (Footer)";
            case INSTAGRAM_FOOTER -> "Instagram (Footer)";
            case TELEGRAM_FOOTER -> "Telegram (Footer)";
            case FACEBOOK_FOOTER -> "FaceBook (Footer)";
            default -> button.name();
        };
    }

}
