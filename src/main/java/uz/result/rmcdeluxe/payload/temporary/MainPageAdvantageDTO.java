package uz.result.rmcdeluxe.payload.temporary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.temporary.MainPageAdvantage;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.exception.LanguageNotSupported;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MainPageAdvantageDTO {

    Long id;

    String title;

    String description;

    Photo photo;

    public MainPageAdvantageDTO(MainPageAdvantage advantage, String lang) {
        this.id = advantage.getId();
        this.photo = advantage.getPhoto();

        switch (lang.toLowerCase()) {

            case "uz": {
                this.title = advantage.getTitleUz();
                this.description = advantage.getDescriptionUz();
                break;
            }

            case "ru": {
                this.title = advantage.getTitleRu();
                this.description = advantage.getDescriptionRu();
                break;
            }

            case "en": {
                this.title = advantage.getTitleEn();
                this.description = advantage.getDescriptionEn();
                break;
            }
            default:
                throw new LanguageNotSupported("Language not supported: " + lang);

        }
    }

}
