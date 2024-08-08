package uz.result.rmcdeluxe.payload;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.Banner;
import uz.result.rmcdeluxe.entity.BannerSlider;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.exception.LanguageNotSupported;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BannerSliderDTO {

    Long id;

    String title;

    String description;

    Photo photo;

    String link;

    Boolean active;

    public BannerSliderDTO(BannerSlider slider, String lang) {
        this.id = slider.getId();
        this.photo = slider.getPhoto();
        this.link = slider.getLink();
        this.active = slider.getActive();

        switch (lang.toLowerCase()) {

            case "uz": {
                this.title = slider.getTitleUz();
                this.description = slider.getDescriptionUz();
                break;
            }

            case "ru": {
                this.title = slider.getTitleRu();
                this.description = slider.getDescriptionRu();
                break;
            }

            case "en": {
                this.title = slider.getTitleEn();
                this.description = slider.getDescriptionEn();
                break;
            }

            default:
                throw new LanguageNotSupported("Language not supported: " + lang);
        }
    }

}
