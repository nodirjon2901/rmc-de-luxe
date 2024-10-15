package uz.result.rmcdeluxe.payload.catalog;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.Photo;
import uz.result.rmcdeluxe.payload.Translation;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CatalogUpdateDTO {

    Long id;

    String name;

    String districtName;

    Double price;

    String typeName;

    Translation numberOfRooms;

    Translation completionDate;

    boolean active;

}
