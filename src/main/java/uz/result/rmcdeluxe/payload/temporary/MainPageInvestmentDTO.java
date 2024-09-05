package uz.result.rmcdeluxe.payload.temporary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.temporary.MainPageInvestment;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.exception.LanguageNotSupported;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MainPageInvestmentDTO {

    Long id;

    String title;

    String description;

    String link;

    Photo mainPhoto;

    Photo photo;

    public MainPageInvestmentDTO(MainPageInvestment investment, String lang) {
        this.id = investment.getId();
        this.link = investment.getLink();
        this.mainPhoto = investment.getMainPhoto();
        this.photo = investment.getPhoto();

        switch (lang.toLowerCase()) {

            case "uz": {
                this.title = investment.getTitleUz();
                this.description = investment.getDescriptionUz();
                break;
            }

            case "ru": {
                this.title = investment.getTitleRu();
                this.description = investment.getDescriptionRu();
                break;
            }

            case "en": {
                this.title = investment.getTitleEn();
                this.description = investment.getDescriptionEn();
                break;
            }
            default:
                throw new LanguageNotSupported("Language not supported: " + lang);
        }
    }

}
