package uz.result.rmcdeluxe.payload.temporary.investment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.temporary.investment.IntroductionDescription;
import uz.result.rmcdeluxe.exception.LanguageNotSupported;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IntroductionDescriptionDTO {

    Long id;

    String description;

public IntroductionDescriptionDTO(IntroductionDescription description,String lang){

    this.id=description.getId();

    switch (lang.toLowerCase()){
        case "uz": {
            this.description = description.getDescriptionUz();
            break;
        }

        case "ru": {
            this.description = description.getDescriptionRu();
            break;
        }

        case "en": {
            this.description = description.getDescriptionEn();
            break;
        }

        default:
            throw new LanguageNotSupported("Language not supported: " + lang);
    }
    }


}
