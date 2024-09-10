package uz.result.rmcdeluxe.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AboutUsBanner {

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

}
