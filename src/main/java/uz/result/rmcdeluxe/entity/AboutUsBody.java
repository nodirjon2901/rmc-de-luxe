package uz.result.rmcdeluxe.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.payload.aboutUs.AboutUsBodyCreateDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "about_us_body")
public class AboutUsBody {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String titleUz;

    String titleRu;

    String titleEn;

    String subtitleUz;

    String subtitleRu;

    String subtitleEn;

    String descriptionUz;

    String descriptionRu;

    String descriptionEn;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    Photo photo;

    public AboutUsBody(AboutUsBodyCreateDTO createDTO) {
        if (createDTO == null) {
            return;
        }
        if (createDTO.getTitle() != null) {
            this.titleUz = createDTO.getTitle().getUz();
            this.titleRu = createDTO.getTitle().getRu();
            this.titleEn = createDTO.getTitle().getEn();
        }
        if (createDTO.getSubtitle() != null) {
            this.subtitleUz = createDTO.getSubtitle().getUz();
            this.subtitleRu = createDTO.getSubtitle().getRu();
            this.subtitleEn = createDTO.getSubtitle().getEn();
        }
        if (createDTO.getDescription() != null) {
            this.descriptionUz = createDTO.getDescription().getUz();
            this.descriptionRu = createDTO.getDescription().getRu();
            this.descriptionEn = createDTO.getDescription().getEn();
        }
    }

}
