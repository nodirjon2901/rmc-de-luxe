package uz.result.rmcdeluxe.entity.temporary;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.Photo;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "building_page_header")
public class BuildingPageHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String titleUz;

    String titleRu;

    String titleEn;

    String subtitleUz;

    String subtitleRu;

    String subtitleEn;

    String shortDescriptionUz;

    String shortDescriptionRu;

    String shortDescriptionEn;

    @OneToOne
    Photo photo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "header", orphanRemoval = true)
    List<BuildingHeaderFeature> headerFeatures;

    @PostPersist
    @PostUpdate
    void setOptionId() {
        if (this.headerFeatures != null) {
            this.headerFeatures.forEach(feature -> feature.setHeader(this));
        }
    }

}
