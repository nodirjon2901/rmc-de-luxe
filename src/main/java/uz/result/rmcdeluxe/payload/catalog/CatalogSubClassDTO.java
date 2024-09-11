package uz.result.rmcdeluxe.payload.catalog;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.Catalog;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CatalogSubClassDTO {

    Long id;

    String slug;

    public CatalogSubClassDTO(Catalog catalog) {
        this.id = catalog.getId();
        this.slug = catalog.getSlug();
    }

}
