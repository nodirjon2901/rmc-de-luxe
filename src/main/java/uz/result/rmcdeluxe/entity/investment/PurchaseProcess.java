package uz.result.rmcdeluxe.entity.investment;

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
@Entity(name = "purchase_process")
public class PurchaseProcess {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String titleUz;

    String titleRu;

    String titleEn;

    @OneToMany
    List<PurchaseProcessStep> steps;

    Integer orderNum;
}
