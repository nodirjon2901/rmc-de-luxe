package uz.result.rmcdeluxe.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.VideoFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VideoDTO {

    Long id;

    String url;

    public VideoDTO(VideoFile entity) {
        if (entity == null)
            return;
        this.id = entity.getId();
        this.url = entity.getVideoUrl();
    }

}
