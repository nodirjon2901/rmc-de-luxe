package uz.result.rmcdeluxe.payload.temporary.investment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.temporary.investment.Question;
import uz.result.rmcdeluxe.exception.LanguageNotSupported;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionDTO {

    Long id;

    String title;

    String description;

    public QuestionDTO(Question question,String lang)
    {
        this.id= question.getId();
        switch (lang.toLowerCase()){
            case "uz":
            {
                this.title= question.getTitleUz();
                this.description= question.getDescriptionUz();
                break;
            }
            case "ru":
            {
                this.title= question.getTitleRu();
                this.description= question.getDescriptionRu();
                break;
            }
            case "en":
            {
                this.title= question.getTitleEn();
                this.description= question.getDescriptionEn();
                break;
            }
            default:
                throw new LanguageNotSupported("Language not supported: " + lang);

        }
    }

}
