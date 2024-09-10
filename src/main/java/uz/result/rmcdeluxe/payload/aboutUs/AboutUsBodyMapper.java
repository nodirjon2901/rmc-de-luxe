package uz.result.rmcdeluxe.payload.aboutUs;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.AboutUsBody;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.exception.LanguageNotSupported;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AboutUsBodyMapper {

    Integer id;

    String title;

    String subtitle;

    String description;

    Photo photo;

    public AboutUsBodyMapper(AboutUsBody body, String lang) {
        this.id = body.getId();
        this.photo = body.getPhoto();
        switch (lang.toLowerCase()) {

            case "uz": {
                this.title = body.getTitleUz();
                this.subtitle = body.getSubtitleUz();
                this.description=body.getDescriptionUz();
                break;
            }

            case "ru": {
                this.title = body.getTitleRu();
                this.subtitle = body.getSubtitleRu();
                this.description=body.getDescriptionRu();
                break;
            }

            case "en": {
                this.title = body.getTitleEn();
                this.subtitle = body.getSubtitleEn();
                this.description=body.getDescriptionEn();
                break;
            }
            default:
                throw new LanguageNotSupported("Language not supported: " + lang);
        }
    }

}
