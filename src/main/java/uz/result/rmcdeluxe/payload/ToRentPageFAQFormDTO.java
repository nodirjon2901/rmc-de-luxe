package uz.result.rmcdeluxe.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.ToRentPageFAQForm;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ToRentPageFAQFormDTO {

    Long id;

    List<ToRentPageFAQOptionDTO> options;

    public ToRentPageFAQFormDTO(ToRentPageFAQForm faqForm, String lang) {
        this.id = faqForm.getId();
        this.options = faqForm.getOptions().stream()
                .map(option -> new ToRentPageFAQOptionDTO(option, lang))
                .collect(Collectors.toList());
    }

}
