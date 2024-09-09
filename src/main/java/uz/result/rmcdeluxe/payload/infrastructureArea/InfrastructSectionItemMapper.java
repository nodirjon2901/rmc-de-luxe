package uz.result.rmcdeluxe.payload.infrastructureArea;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.InfrastructSectionItem;
import uz.result.rmcdeluxe.exception.LanguageNotSupported;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InfrastructSectionItemMapper {

    Long id;

    String name;

    String timeOrDistance;

    public InfrastructSectionItemMapper(InfrastructSectionItem item, String lang) {
        this.id = item.getId();
        switch (lang.toLowerCase()) {
            case "uz": {
                this.name = item.getNameUz();
                this.timeOrDistance = item.getTimeOrDistanceUz();
                break;
            }
            case "ru": {
                this.name = item.getNameRu();
                this.timeOrDistance = item.getTimeOrDistanceRu();
                break;
            }
            case "en": {
                this.name = item.getNameEn();
                this.timeOrDistance = item.getTimeOrDistanceEn();
                break;
            }
            default:
                throw new LanguageNotSupported("Language not supported: " + lang);
        }
    }

}
