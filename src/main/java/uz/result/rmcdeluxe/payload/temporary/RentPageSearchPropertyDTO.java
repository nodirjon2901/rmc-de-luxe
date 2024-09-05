package uz.result.rmcdeluxe.payload.temporary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.temporary.RentPageSearchProperty;
import uz.result.rmcdeluxe.exception.LanguageNotSupported;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RentPageSearchPropertyDTO {

    Long id;

    String title;

    String description;

    List<RentSearchPropertyOptionDTO> options;

    public RentPageSearchPropertyDTO(RentPageSearchProperty property, String lang) {
        this.id = property.getId();
        switch (lang.toLowerCase()) {

            case "uz": {
                this.title = property.getTitleUz();
                this.description = property.getDescriptionUz();
                break;
            }

            case "ru": {
                this.title = property.getTitleRu();
                this.description = property.getDescriptionRu();
                break;
            }

            case "en": {
                this.title = property.getTitleEn();
                this.description = property.getDescriptionEn();
                break;
            }

            default:
                throw new LanguageNotSupported("Language not supported: " + lang);

        }
        this.options = property.getOptions().stream()
                .map(p -> new RentSearchPropertyOptionDTO(p, lang))
                .collect(Collectors.toList());
    }

}
