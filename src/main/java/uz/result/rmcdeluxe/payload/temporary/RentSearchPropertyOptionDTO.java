package uz.result.rmcdeluxe.payload.temporary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.entity.temporary.RentSearchPropertyOption;
import uz.result.rmcdeluxe.exception.LanguageNotSupported;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RentSearchPropertyOptionDTO {

    Long id;

    String name;

    Photo photo;

    public RentSearchPropertyOptionDTO(RentSearchPropertyOption option, String lang) {
        this.id = option.getId();
        this.photo = option.getPhoto();

        switch (lang.toLowerCase()) {

            case "uz": {
                this.name = option.getNameUz();
                break;
            }

            case "ru": {
                this.name = option.getNameRu();
                break;
            }

            case "en": {
                this.name = option.getNameEn();
                break;
            }

            default:
                throw new LanguageNotSupported("Language not supported: " + lang);

        }
    }

}
