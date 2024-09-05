package uz.result.rmcdeluxe.payload.temporary.investment;

import uz.result.rmcdeluxe.entity.temporary.investment.FrequentlyAskedQuestion;
import uz.result.rmcdeluxe.exception.LanguageNotSupported;

import java.util.ArrayList;
import java.util.List;

public class FrequentlyAskedQuestionDTO {

    Long id;

    String title;

    List<QuestionDTO> questions;

    public FrequentlyAskedQuestionDTO(FrequentlyAskedQuestion askedQuestion, String lang) {
        this.id = askedQuestion.getId();
        this.questions = new ArrayList<>();
        askedQuestion.getQuestions().forEach(i -> this.questions.add(new QuestionDTO(i,lang)));

        switch (lang.toLowerCase()){
            case "uz":
            {
                this.title= askedQuestion.getTitleUz();
                break;
            }
            case "ru":
            {
                this.title= askedQuestion.getTitleRu();
                break;
            }
            case "en":
            {
                this.title= askedQuestion.getTitleEn();
                break;
            }
            default:
                throw new LanguageNotSupported("Language not supported: " + lang);

        }
    }

}
