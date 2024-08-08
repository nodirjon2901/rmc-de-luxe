package uz.result.rmcdeluxe.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.InvestmentsIntroduction;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.exception.LanguageNotSupported;

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
                this.subTitle = introduction.getSubTitleUz();
                break;
            }

            case "ru": {
                this.title = introduction.getTitleRu();
                this.subTitle = introduction.getSubTitleRu();
                break;
            }

            case "en": {
                this.title = introduction.getTitleEng();
                this.subTitle = introduction.getSubTitleEn();
                break;
            }

            default:
                throw new LanguageNotSupported("Language not supported: " + lang);
        }
    }

}
