package uz.result.rmcdeluxe.payload.temporary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.temporary.RentPageWorkProcess;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RentPageWorkProcessDTO {

    Long id;

    List<RentPageWorkProcessOptionDTO> options;

    public RentPageWorkProcessDTO(RentPageWorkProcess workProcess, String lang) {
        this.id = workProcess.getId();
        this.options = workProcess.getOptions().stream()
                .map(option -> new RentPageWorkProcessOptionDTO(option, lang))
                .collect(Collectors.toList());
    }

}
