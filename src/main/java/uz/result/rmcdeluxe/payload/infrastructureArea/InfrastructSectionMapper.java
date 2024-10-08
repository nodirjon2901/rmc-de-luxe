package uz.result.rmcdeluxe.payload.infrastructureArea;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.InfrastructSection;
import uz.result.rmcdeluxe.entity.InfrastructSectionItem;
import uz.result.rmcdeluxe.exception.LanguageNotSupported;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InfrastructSectionMapper {

    Long id;

    String title;

    String description;

    List<InfrastructSectionItemMapper> sectionItems;

    public InfrastructSectionMapper(InfrastructSection section, String lang) {
        this.id = section.getId();
        switch (lang.toLowerCase()) {
            case "uz": {
                this.title = section.getTitleUz();
                this.description = section.getDescriptionUz();
                break;
            }
            case "ru": {
                this.title = section.getTitleRu();
                this.description = section.getDescriptionRu();
                break;
            }
            case "en": {
                this.title = section.getTitleEn();
                this.description = section.getDescriptionEn();
                break;
            }
            default:
                throw new LanguageNotSupported("Language not supported: " + lang);
        }
        this.sectionItems = section.getSectionItems().stream()
                .map(item -> new InfrastructSectionItemMapper(item, lang))
                .collect(Collectors.toList());
    }

}
