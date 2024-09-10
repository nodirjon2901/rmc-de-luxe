package uz.result.rmcdeluxe.payload.aboutUs;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.AboutUsBody;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.payload.Translation;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AboutUsBodyResponseDTO {

    Integer id;

    Translation title;

    Translation subtitle;

    Translation description;

    Photo photo;

    public AboutUsBodyResponseDTO(AboutUsBody body) {
        this.id = body.getId();
        this.photo = body.getPhoto();
        this.title = new Translation(
                body.getTitleUz(),
                body.getTitleRu(),
                body.getTitleEn()
        );
        this.subtitle = new Translation(
                body.getSubtitleUz(),
                body.getSubtitleRu(),
                body.getSubtitleEn()
        );
        this.description = new Translation(
                body.getDescriptionUz(),
                body.getDescriptionRu(),
                body.getDescriptionEn()
        );
    }

}
