package uz.result.rmcdeluxe.payload.temporary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.temporary.RentPageFAQOption;
import uz.result.rmcdeluxe.exception.LanguageNotSupported;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RentPageFAQOptionDTO {

    Long id;

    String question;

    String answer;

    public RentPageFAQOptionDTO(RentPageFAQOption option, String lang) {
        this.id = option.getId();
        switch (lang.toLowerCase()) {

            case "uz": {
                this.question = option.getQuestionUz();
                this.answer = option.getAnswerUz();
                break;
            }

            case "ru": {
                this.question = option.getQuestionRu();
                this.answer = option.getAnswerRu();
                break;
            }

            case "en": {
                this.question = option.getQuestionEn();
                this.answer = option.getAnswerEn();
                break;
            }
            default:
                throw new LanguageNotSupported("Language not supported: " + lang);
        }
    }

}