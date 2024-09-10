package uz.result.rmcdeluxe.payload.review;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.Review;
import uz.result.rmcdeluxe.payload.Translation;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewResponseDTO {

    Long id;

    Translation title;

    Translation description;

    Date createdDate;

    boolean active;

    public ReviewResponseDTO(Review review){
        this.id= review.getId();
        this.createdDate=review.getCreatedDate();
        this.active= review.isActive();
        this.title=new Translation(
                review.getTitleUz(),
                review.getTitleRu(),
                review.getTitleEn()
        );
        this.description=new Translation(
                review.getDescriptionUz(),
                review.getDescriptionRu(),
                review.getDescriptionEn()
        );
    }

}
