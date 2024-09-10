package uz.result.rmcdeluxe.payload.aboutUs;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.AboutUsBanner;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.exception.LanguageNotSupported;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AboutUsBannerMapper {

    Integer id;

    String title;

    String shortDescription;

    Photo photo;

    public AboutUsBannerMapper(AboutUsBanner usBanner, String lang) {
        this.id = usBanner.getId();
        this.photo = usBanner.getPhoto();
        switch (lang.toLowerCase()) {

            case "uz": {
                this.title = usBanner.getTitleUz();
                this.shortDescription = usBanner.getShortDescriptionRu();
                break;
            }

            case "ru": {
                this.title = usBanner.getTitleRu();
                this.shortDescription = usBanner.getShortDescriptionRu();
                break;
            }

            case "en": {
                this.title = usBanner.getTitleEn();
                this.shortDescription = usBanner.getShortDescriptionEn();
                break;
            }
            default:
                throw new LanguageNotSupported("Language not supported: " + lang);
        }
    }

}
