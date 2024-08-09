package uz.result.rmcdeluxe.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.BuildingPageOverview;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.exception.LanguageNotSupported;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BuildingPageOverviewDTO {

    Long id;

    String title;

    String description;

    Photo photo;

    public BuildingPageOverviewDTO(BuildingPageOverview overview, String lang) {
        this.id = overview.getId();
        this.photo = overview.getPhoto();
        switch (lang.toLowerCase()) {

            case "uz": {
                this.title = overview.getTitleUz();
                this.description = overview.getDescriptionUz();
                break;
            }

            case "ru": {
                this.title = overview.getTitleRu();
                this.description = overview.getDescriptionRu();
                break;
            }

            case "en": {
                this.title = overview.getTitleEn();
                this.description = overview.getDescriptionEn();
                break;
            }

            default:
                throw new LanguageNotSupported("Language not supported: " + lang);

        }
    }

}
