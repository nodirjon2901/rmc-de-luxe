package uz.result.rmcdeluxe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.payload.banner.BannerSliderCreateDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "banner_slider")
public class BannerSlider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String titleUz;

    String titleRu;

    String titleEn;

    String shortDescriptionUz;

    String shortDescriptionRu;

    String shortDescriptionEn;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    Photo photo;

    @ManyToOne
    @JsonIgnore
    Banner banner;

    String link;

    Integer orderNum;

    boolean active;

    public BannerSlider(BannerSliderCreateDTO createDTO) {
        if (createDTO == null) {
            return;
        }
        this.link = createDTO.getLink();
        if (createDTO.getTitle() != null) {
            this.titleUz = createDTO.getTitle().getUz();
            this.titleRu = createDTO.getTitle().getRu();
            this.titleEn = createDTO.getTitle().getEn();
        }
        if (createDTO.getShortDescription() != null) {
            this.shortDescriptionUz = createDTO.getShortDescription().getUz();
            this.shortDescriptionRu = createDTO.getShortDescription().getRu();
            this.shortDescriptionEn = createDTO.getShortDescription().getEn();
        }
    }


}
