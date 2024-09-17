package uz.result.rmcdeluxe.payload.banner;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.Banner;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BannerResponseDTO {

    int id;

    List<BannerSliderResponseDTO> sliders;

    public BannerResponseDTO(Banner banner) {
        this.id = banner.getId();
        this.sliders = banner.getSliders().stream()
                .map(slider -> new BannerSliderResponseDTO(slider))
                .toList();
    }

}
