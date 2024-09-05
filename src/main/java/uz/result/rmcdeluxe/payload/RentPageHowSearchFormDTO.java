package uz.result.rmcdeluxe.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.RentPageHowSearchForm;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RentPageHowSearchFormDTO {

    Long id;

    List<RentHowSearchOptionDTO> options;

    public RentPageHowSearchFormDTO(RentPageHowSearchForm searchForm, String lang) {
        this.id = searchForm.getId();
        this.options = searchForm.getOptions().stream()
                .map(option -> new RentHowSearchOptionDTO(option, lang))
                .collect(Collectors.toList());
    }

}
