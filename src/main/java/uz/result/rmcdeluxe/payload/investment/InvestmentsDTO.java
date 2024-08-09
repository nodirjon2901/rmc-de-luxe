package uz.result.rmcdeluxe.payload.investment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.investment.Investments;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.exception.LanguageNotSupported;
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvestmentsDTO {
     Long id;

     Photo photo;

     String heading;

    String description;

public InvestmentsDTO(Investments investments,String lang){

    this.id=investments.getId();
    this.photo=investments.getPhoto();

    switch (lang.toLowerCase()) {

        case "uz": {
            this.heading = investments.getHeadingUz();
            this.description = investments.getDescriptionUz();
            break;
        }

        case "ru": {
            this.heading=investments.getHeadingRu();
            this.description = investments.getDescriptionRu();
            break;
        }

        case "en": {
            this.heading=investments.getHeadingEn();
            this.description = investments.getDescriptionEn();
            break;
        }

        default:
            throw new LanguageNotSupported("Language not supported: " + lang);
    }
}
}
