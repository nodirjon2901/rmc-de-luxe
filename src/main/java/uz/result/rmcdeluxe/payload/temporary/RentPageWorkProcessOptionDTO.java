package uz.result.rmcdeluxe.payload.temporary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.temporary.RentPageWorkProcessOption;
import uz.result.rmcdeluxe.exception.LanguageNotSupported;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RentPageWorkProcessOptionDTO {

    Long id;

    String name;

    String description;

    Integer orderNum;

    public RentPageWorkProcessOptionDTO(RentPageWorkProcessOption option, String lang) {
        this.id = option.getId();
        this.orderNum= option.getOrderNum();
        switch (lang.toLowerCase()) {

            case "uz": {
                this.name = option.getNameUz();
                this.description = option.getDescriptionUz();
                break;
            }

            case "ru": {
                this.name = option.getNameRu();
                this.description = option.getDescriptionRu();
                break;
            }

            case "en": {
                this.name = option.getNameEn();
                this.description = option.getDescriptionEn();
                break;
            }

            default:
                throw new LanguageNotSupported("Language not supported: " + lang);

        }
    }

}
