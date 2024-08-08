package uz.result.rmcdeluxe.payload;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.Photo;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhotoDTO {

    Long id;

    String url;

    public PhotoDTO(Photo photo) {
        this.id = photo.getId();
        this.url = photo.getHttpUrl();
    }

}
