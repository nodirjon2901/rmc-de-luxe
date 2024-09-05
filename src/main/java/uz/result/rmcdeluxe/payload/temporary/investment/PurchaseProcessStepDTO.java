package uz.result.rmcdeluxe.payload.temporary.investment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.temporary.investment.PurchaseProcessStep;
import uz.result.rmcdeluxe.exception.LanguageNotSupported;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchaseProcessStepDTO {

    Long id;

    String title;

    String description;

    public PurchaseProcessStepDTO(PurchaseProcessStep step,String lang){
        this.id=step.getId();

        switch (lang.toLowerCase()){
            case "uz":
            {
                this.title=step.getTitleUz();
                this.description=step.getDescriptionUz();
                break;
            }
            case "ru":
            {
                this.title=step.getTitleRu();
                this.description=step.getDescriptionRu();
                break;
            }
            case "en":
            {
                this.title=step.getTitleEn();
                this.description=step.getDescriptionEn();
                break;
            }
            default:
                throw new LanguageNotSupported("Language not supported: " + lang);

        }
    }
}
