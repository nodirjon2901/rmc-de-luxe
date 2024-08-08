package uz.result.rmcdeluxe.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.MainPageAboutCompany;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.exception.LanguageNotSupported;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MainPageAboutCompanyDTO {

    Long id;

    String title;

    String subtitle;

    Photo photo;

    List<MainPageAboutCompanyOptionDTO> options;

    public MainPageAboutCompanyDTO(MainPageAboutCompany company, String lang) {
        this.id = company.getId();
        this.photo = company.getPhoto();

        switch (lang.toLowerCase()) {

            case "uz": {
                this.title = company.getTitleUz();
                this.subtitle = company.getSubtitleUz();
                break;
            }

            case "ru": {
                this.title = company.getTitleRu();
                this.subtitle = company.getSubtitleRu();
                break;
            }

            case "en": {
                this.title = company.getTitleEn();
                this.subtitle = company.getSubtitleEn();
                break;
            }

            default:
                throw new LanguageNotSupported("Language not supported: " + lang);

        }
        this.options = company.getOptions().stream()
                .map(option -> new MainPageAboutCompanyOptionDTO(option, lang))
                .collect(Collectors.toList());
    }

}
