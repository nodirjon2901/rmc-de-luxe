package uz.result.rmcdeluxe.payload.district;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.District;
import uz.result.rmcdeluxe.exception.LanguageNotSupported;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DistrictMapper {

    Long id;

    String name;

    public DistrictMapper(District district, String lang) {
        this.id = district.getId();
        switch (lang.toLowerCase()) {
            case "uz": {
                this.name = district.getNameUz();
                break;
            }
            case "ru": {
                this.name = district.getNameRu();
                break;
            }
            case "en": {
                this.name = district.getNameEn();
                break;
            }
            default:
                throw new LanguageNotSupported("Language not supported: " + lang);
        }
    }

}
