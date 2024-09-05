package uz.result.rmcdeluxe.payload.temporary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.temporary.BuildingPageHeader;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.exception.LanguageNotSupported;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BuildingPageHeaderDTO {

    Long id;

    String title;

    String subtitle;

    String shortDescription;

    Photo photo;

    List<BuildingHeaderFeatureDTO> headerFeatures;

    public BuildingPageHeaderDTO(BuildingPageHeader header, String lang) {
        this.id = header.getId();
        this.photo=header.getPhoto();
        switch (lang.toLowerCase()) {

            case "uz": {
                this.title = header.getTitleUz();
                this.subtitle = header.getSubtitleUz();
                this.shortDescription = header.getShortDescriptionUz();
                break;
            }

            case "ru": {
                this.title = header.getTitleRu();
                this.subtitle = header.getSubtitleRu();
                this.shortDescription = header.getShortDescriptionRu();
                break;
            }

            case "en": {
                this.title = header.getTitleEn();
                this.subtitle = header.getSubtitleEn();
                this.shortDescription = header.getShortDescriptionEn();
                break;
            }
            default:
                throw new LanguageNotSupported("Language not supported: " + lang);
        }
        this.headerFeatures = header.getHeaderFeatures().stream()
                .map(feature -> new BuildingHeaderFeatureDTO(feature, lang))
                .collect(Collectors.toList());
    }

}
