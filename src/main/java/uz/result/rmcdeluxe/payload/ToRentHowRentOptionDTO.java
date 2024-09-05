package uz.result.rmcdeluxe.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.ToRentHowRentOption;
import uz.result.rmcdeluxe.exception.LanguageNotSupported;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ToRentHowRentOptionDTO {

    Long id;

    String title;

    String description;

    public ToRentHowRentOptionDTO(ToRentHowRentOption option, String lang) {
        this.id = option.getId();
        switch (lang.toLowerCase()) {

            case "uz": {
                this.title = option.getTitleUz();
                this.description = option.getDescriptionUz();
                break;
            }

            case "ru": {
                this.title = option.getTitleRu();
                this.description = option.getDescriptionRu();
                break;
            }

            case "en": {
                this.title = option.getTitleEn();
                this.description = option.getDescriptionEn();
                break;
            }

            default:
                throw new LanguageNotSupported("Language not supported: " + lang);

        }
    }

}
