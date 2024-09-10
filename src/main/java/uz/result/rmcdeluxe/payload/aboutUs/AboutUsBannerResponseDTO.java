package uz.result.rmcdeluxe.payload.aboutUs;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.AboutUsBanner;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.payload.Translation;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AboutUsBannerResponseDTO {

    Integer id;

    Translation title;

    Translation shortDescription;

    Photo photo;

    public AboutUsBannerResponseDTO(AboutUsBanner usBanner) {
        this.id = usBanner.getId();
        this.photo = usBanner.getPhoto();
        this.title = new Translation(
                usBanner.getTitleUz(),
                usBanner.getTitleRu(),
                usBanner.getTitleEn()
        );
        this.shortDescription = new Translation(
                usBanner.getShortDescriptionUz(),
                usBanner.getShortDescriptionRu(),
                usBanner.getShortDescriptionEn()
        );
    }

}
