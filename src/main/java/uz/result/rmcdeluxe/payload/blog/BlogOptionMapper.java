package uz.result.rmcdeluxe.payload.blog;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.BlogOption;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.exception.LanguageNotSupported;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogOptionMapper {

    Long id;

    @JsonProperty("description")
    String title;

    @JsonProperty("text")
    String description;

    Integer orderNum;

    Photo photo;

    public BlogOptionMapper(BlogOption option, String lang) {
        this.id = option.getId();
        this.orderNum = option.getOrderNum();
        this.photo = option.getPhoto();
        switch (lang.toLowerCase()) {
            case "uz": {
                this.title = option.getTitleUz();
                this.description = option.getDescriptionUz();
                break;
            }
            case "ru": {
                this.title = option.getTitleRu();
                this.description = option.getDescriptionRu();
                break;
            }
            case "en": {
                this.title = option.getTitleEn();
                this.description = option.getDescriptionEn();
                break;
            }
            default:
                throw new LanguageNotSupported("Language not supported: " + lang);
        }
    }

}
