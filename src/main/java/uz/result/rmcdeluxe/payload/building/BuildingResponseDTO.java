package uz.result.rmcdeluxe.payload.building;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.Building;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.entity.VideoFile;
import uz.result.rmcdeluxe.payload.Translation;
import uz.result.rmcdeluxe.payload.catalog.CatalogSubClassDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BuildingResponseDTO {

    Long id;

    String slug;

    Translation title;

    Translation description;

    List<Photo> gallery;

    List<VideoFile> videoList;

    boolean active;

    CatalogSubClassDTO catalog;

    public BuildingResponseDTO(Building build) {
        this.id = build.getId();
        this.slug= build.getSlug();
        this.gallery = build.getGallery();
        this.videoList = build.getVideoList();
        this.active = build.isActive();
        this.catalog = new CatalogSubClassDTO(build.getCatalog());
        this.title = new Translation(
                build.getTitleUz(),
                build.getTitleRu(),
                build.getTitleEn()
        );
        this.description = new Translation(
                build.getDescriptionUz(),
                build.getDescriptionRu(),
                build.getDescriptionEn()
        );
    }

}
