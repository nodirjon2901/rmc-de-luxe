package uz.result.rmcdeluxe.payload.banner;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.payload.Translation;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BannerSliderCreateDTO {

    Translation title;

    Translation shortDescription;

    String link;

}
