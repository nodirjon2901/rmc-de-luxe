package uz.result.rmcdeluxe.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.PropertyManagement;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PropertyManagementDTO {

    Long id;

    List<PropertyManagementOptionDTO> options;

    public PropertyManagementDTO(PropertyManagement management, String lang) {
        this.id = management.getId();
        this.options = management.getOptions().stream()
                .map(option -> new PropertyManagementOptionDTO(option, lang))
                .collect(Collectors.toList());
    }

}
