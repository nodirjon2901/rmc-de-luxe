package uz.result.rmcdeluxe.payload.infrastructureArea;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.Photo;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InfrastructureAreaUpdateDTO {

    Long id;

    Long buildingId;

    List<InfrastructSectionResponseDTO> sections;

    Photo photo;

}