package uz.result.rmcdeluxe.payload.banner;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.Banner;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BannerMapper {

    int id;

    List<BannerSliderMapper> sliders;

    public BannerMapper(Banner banner, String lang) {
        this.id = banner.getId();
        this.sliders = banner.getSliders().stream()
                .map(slider -> new BannerSliderMapper(slider, lang))
                .collect(Collectors.toList());
    }

}
