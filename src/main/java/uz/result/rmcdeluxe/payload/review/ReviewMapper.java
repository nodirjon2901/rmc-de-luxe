package uz.result.rmcdeluxe.payload.review;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.Review;
import uz.result.rmcdeluxe.exception.LanguageNotSupported;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewMapper {

    Long id;

    String title;

    String description;

    Date createdDate;

    boolean active;

    public ReviewMapper(Review review, String lang) {
        this.id = review.getId();
        this.createdDate = review.getCreatedDate();
        this.active = review.isActive();
        switch (lang.toLowerCase()) {

            case "uz": {
                this.title = review.getTitleUz();
                this.description = review.getDescriptionUz();
                break;
            }

            case "ru": {
                this.title = review.getTitleRu();
                this.description = review.getDescriptionRu();
                break;
            }

            case "en": {
                this.title = review.getTitleEn();
                this.description = review.getDescriptionEn();
                break;
            }
            default:
                throw new LanguageNotSupported("Language not supported: " + lang);
        }
    }

}
