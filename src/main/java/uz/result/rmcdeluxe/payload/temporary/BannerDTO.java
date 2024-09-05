package uz.result.rmcdeluxe.payload.temporary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.temporary.Banner;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BannerDTO {

    Long id;

    List<BannerSliderDTO> sliders;

    public BannerDTO(Banner banner, String lang) {
        this.id = banner.getId();
        this.sliders = banner.getSliders().stream()
                .map(slider -> new BannerSliderDTO(slider, lang))
                .collect(Collectors.toList());
    }

}
