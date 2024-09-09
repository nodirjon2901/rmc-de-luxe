package uz.result.rmcdeluxe.payload.banner;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.BannerSlider;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.payload.Translation;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BannerSliderResponseDTO {

    Long id;

    Translation title;

    Translation shortDescription;

    Photo photo;

    String link;

    Integer orderNum;

    boolean active;

    public BannerSliderResponseDTO(BannerSlider slider) {
        this.id = slider.getId();
        this.link = slider.getLink();
        this.orderNum = slider.getOrderNum();
        this.active = slider.isActive();
        this.photo = slider.getPhoto();
        this.title = new Translation(
                slider.getTitleUz(),
                slider.getTitleRu(),
                slider.getTitleEn()
        );
        this.shortDescription = new Translation(
                slider.getShortDescriptionUz(),
                slider.getShortDescriptionRu(),
                slider.getShortDescriptionEn()
        );
    }

}
