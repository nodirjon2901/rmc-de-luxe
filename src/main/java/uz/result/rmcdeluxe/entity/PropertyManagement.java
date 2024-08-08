package uz.result.rmcdeluxe.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "property_management")
public class PropertyManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "propertyManagement", orphanRemoval = true)
    List<PropertyManagementOption> options;

    @PostPersist
    @PostUpdate
    void setOptionId() {
        if (options != null) {
            this.options.forEach(option -> option.setPropertyManagement(this));
        }
    }

}
