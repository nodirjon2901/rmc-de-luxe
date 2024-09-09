package uz.result.rmcdeluxe.payload.infrastructureArea;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.InfrastructureArea;
import uz.result.rmcdeluxe.entity.Photo;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InfrastructureAreaMapper {

    Long id;

    List<InfrastructSectionMapper> sections;

    Photo photo;

    public InfrastructureAreaMapper(InfrastructureArea area, String lang) {
        this.id = area.getId();
        this.photo = area.getPhoto();
        this.sections = area.getSections().stream()
                .map(section -> new InfrastructSectionMapper(section, lang))
                .collect(Collectors.toList());
    }

}
