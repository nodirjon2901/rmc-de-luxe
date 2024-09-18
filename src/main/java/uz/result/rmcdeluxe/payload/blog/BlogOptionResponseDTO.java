package uz.result.rmcdeluxe.payload.blog;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.BlogOption;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.payload.Translation;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogOptionResponseDTO {

    Long id;

    Translation title;

    Translation description;

    Integer orderNum;

    Photo photo;

    public BlogOptionResponseDTO(BlogOption option) {
        this.id = option.getId();
        this.orderNum= option.getOrderNum();
        this.photo = option.getPhoto();
        this.title = new Translation(
                option.getTitleUz(),
                option.getTitleRu(),
                option.getTitleEn()
        );
        this.description = new Translation(
                option.getDescriptionUz(),
                option.getDescriptionRu(),
                option.getDescriptionEn()
        );
    }

}
