package uz.result.rmcdeluxe.payload.investment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.investment.InvestmentsIntroduction;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.exception.LanguageNotSupported;
import uz.result.rmcdeluxe.payload.investment.IntroductionDescriptionDTO;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvestmentsIntroductionDTO {

    Long id;

    Photo photo;

    String title;

    String subTitle;

    List<IntroductionDescriptionDTO> descriptions;

    public InvestmentsIntroductionDTO(InvestmentsIntroduction introduction, String lang) {
        this.id = introduction.getId();
        this.photo = introduction.getPhoto();
        this.descriptions = new ArrayList<>();
        introduction.getDescriptions().forEach(i -> this.descriptions.add(new IntroductionDescriptionDTO(i, lang)));

        switch (lang.toLowerCase()) {
            case "uz": {
                this.title = introduction.getTitleUz();
                this.subTitle = introduction.getDescriptionUz();
                break;
            }

            case "ru": {
                this.title = introduction.getTitleRu();
                this.subTitle = introduction.getDescriptionUz();
                break;
            }

            case "en": {
                this.title = introduction.getTitleEng();
                this.subTitle = introduction.getDescriptionUz();
                break;
            }

            default:
                throw new LanguageNotSupported("Language not supported: " + lang);
        }
    }

}
