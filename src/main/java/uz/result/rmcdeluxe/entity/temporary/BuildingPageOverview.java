package uz.result.rmcdeluxe.entity.temporary;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.Photo;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "building_page_overview")
public class BuildingPageOverview {

    @Id
    @GeneratedValue
    Long id;

    String titleUz;

    String titleRu;

    String titleEn;

    @Column(length = 1000)
    String descriptionUz;

    @Column(length = 1000)
    String descriptionRu;

    @Column(length = 1000)
    String descriptionEn;

    @OneToOne
    Photo photo;

}
