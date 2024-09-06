package uz.result.rmcdeluxe.payload.blog;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.BlogType;
import uz.result.rmcdeluxe.payload.Translation;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogTypeResponse {

    Long id;

    Translation name;

    public BlogTypeResponse(BlogType type) {
        this.id = type.getId();
        this.name = new Translation(
                type.getNameUz(),
                type.getNameRu(),
                type.getNameEn()
        );
    }

}
