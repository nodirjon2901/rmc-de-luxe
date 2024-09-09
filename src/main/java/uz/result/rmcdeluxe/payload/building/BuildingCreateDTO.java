package uz.result.rmcdeluxe.payload.building;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.entity.VideoFile;
import uz.result.rmcdeluxe.payload.Translation;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BuildingCreateDTO {

    Translation title;

    Translation description;

    List<Photo> gallery;

    List<VideoFile> videoList;

    boolean active;

}
