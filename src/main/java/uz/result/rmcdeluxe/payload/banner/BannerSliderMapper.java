package uz.result.rmcdeluxe.payload.banner;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.BannerSlider;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.exception.LanguageNotSupported;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BannerSliderMapper {

    Long id;

    String title;

    String shortDescription;

    Photo photo;

    String link;

    Integer orderNum;

    boolean active;

    public BannerSliderMapper(BannerSlider slider, String lang) {
        this.id = slider.getId();
        this.link = slider.getLink();
        this.orderNum = slider.getOrderNum();
        this.active = slider.isActive();
        this.photo=slider.getPhoto();
        switch (lang.toLowerCase()) {

            case "uz": {
                this.title = slider.getTitleUz();
                this.shortDescription = slider.getShortDescriptionUz();
                break;
            }

            case "ru": {
                this.title = slider.getTitleRu();
                this.shortDescription = slider.getShortDescriptionRu();
                break;
            }

            case "en": {
                this.title = slider.getTitleEn();
                this.shortDescription = slider.getShortDescriptionEn();
                break;
            }
            default:
                throw new LanguageNotSupported("Language not supported: " + lang);
        }
    }

}
