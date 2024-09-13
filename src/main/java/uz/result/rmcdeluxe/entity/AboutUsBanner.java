package uz.result.rmcdeluxe.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.payload.aboutUs.AboutUsBannerCreateDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "about_us_banner")
public class AboutUsBanner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String titleUz;

    String titleRu;

    String titleEn;

    String shortDescriptionUz;

    String shortDescriptionRu;

    String shortDescriptionEn;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    Photo photo;

    public AboutUsBanner(AboutUsBannerCreateDTO createDTO) {
        if (createDTO == null) {
            return;
        }
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
