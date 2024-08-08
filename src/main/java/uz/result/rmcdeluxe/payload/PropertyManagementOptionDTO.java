package uz.result.rmcdeluxe.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.PropertyManagementOption;
import uz.result.rmcdeluxe.exception.LanguageNotSupported;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PropertyManagementOptionDTO {

    Long id;

    String description;

    public PropertyManagementOptionDTO(PropertyManagementOption option, String lang) {
        this.id = option.getId();

        switch (lang.toLowerCase()) {

            case "uz": {
                this.description = option.getDescriptionUz();
                break;
            }

            case "ru": {
                this.description = option.getDescriptionRu();
                break;
            }

            case "en": {
                this.description = option.getDescriptionEn();
                break;
            }

            default:
                throw new LanguageNotSupported("Language not supported: " + lang);

        }
    }

}
