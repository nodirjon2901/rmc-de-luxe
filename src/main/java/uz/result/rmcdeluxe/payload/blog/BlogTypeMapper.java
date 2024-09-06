package uz.result.rmcdeluxe.payload.blog;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.BlogType;
import uz.result.rmcdeluxe.exception.LanguageNotSupported;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogTypeMapper {

    Long id;

    String name;

    public BlogTypeMapper(BlogType type, String lang) {
        this.id = type.getId();
        switch (lang.toLowerCase()) {
            case "uz": {
                this.name = type.getNameUz();
                break;
            }
            case "ru": {
                this.name = type.getNameRu();
                break;
            }
            case "en": {
                this.name = type.getNameEn();
                break;
            }
            default:
                throw new LanguageNotSupported("Language not supported: " + lang);
        }
    }

}
