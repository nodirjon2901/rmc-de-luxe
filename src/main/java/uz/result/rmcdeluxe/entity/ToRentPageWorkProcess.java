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
@Entity(name = "to_rent_page_work_process")
public class ToRentPageWorkProcess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToMany(mappedBy = "workProcess", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ToRentWorkProcessOption> options;

    @PostPersist
    @PostUpdate
    void setOptionId(){
        if (this.options!=null){
            this.options.forEach(option -> option.setWorkProcess(this));
        }
    }

}
