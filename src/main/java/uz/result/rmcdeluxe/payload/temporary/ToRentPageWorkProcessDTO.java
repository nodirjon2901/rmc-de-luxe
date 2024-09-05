package uz.result.rmcdeluxe.payload.temporary;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.temporary.ToRentPageWorkProcess;
import uz.result.rmcdeluxe.entity.temporary.ToRentWorkProcessOption;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ToRentPageWorkProcessDTO {

    Long id;

    List<ToRentWorkProcessOptionDTO> options;

    public ToRentPageWorkProcessDTO(ToRentPageWorkProcess workProcess, String lang) {
        this.id = workProcess.getId();
        this.options = workProcess.getOptions().stream()
                .map(option -> new ToRentWorkProcessOptionDTO(option, lang))
                .collect(Collectors.toList());
    }

}
