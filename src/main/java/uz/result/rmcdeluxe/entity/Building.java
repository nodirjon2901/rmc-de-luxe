package uz.result.rmcdeluxe.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Building {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String titleUz;

    String titleRu;

    String titleEn;

    String descriptionUz;

    String descriptionRu;

    String descriptionEn;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "building", orphanRemoval = true)
    List<Photo> gallery;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "building", orphanRemoval = true)
    List<VideoFile> videoList;

    boolean active;

}
