package uz.result.rmcdeluxe.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.BuildingHeaderFeature;
import uz.result.rmcdeluxe.exception.LanguageNotSupported;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BuildingHeaderFeatureDTO {

    Long id;

    String title;

    String description;

    public BuildingHeaderFeatureDTO(BuildingHeaderFeature feature, String lang) {
        this.id = feature.getId();

        switch (lang.toLowerCase()) {

            case "uz": {
                this.title = feature.getTitleUz();
                this.description = feature.getDescriptionUz();
                break;
            }

            case "ru": {
                this.title = feature.getTitleRu();
                this.description = feature.getDescriptionRu();
                break;
            }

            case "en": {
                this.title = feature.getTitleEn();
                this.description = feature.getDescriptionEn();
                break;
            }
            default:
                throw new LanguageNotSupported("Language not supported: " + lang);

        }
    }

}
