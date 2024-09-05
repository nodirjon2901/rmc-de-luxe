package uz.result.rmcdeluxe.payload.temporary;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.temporary.ToRentHowRentOption;
import uz.result.rmcdeluxe.entity.temporary.ToRentPageHowRentForm;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ToRentPageHowRentFormDTO {

    Long id;

    List<ToRentHowRentOptionDTO> options;

    public ToRentPageHowRentFormDTO(ToRentPageHowRentForm rentForm, String lang) {
        this.id = rentForm.getId();
        this.options = rentForm.getOptions().stream()
                .map(option -> new ToRentHowRentOptionDTO(option, lang))
                .collect(Collectors.toList());
    }

}
