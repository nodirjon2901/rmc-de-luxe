package uz.result.rmcdeluxe.payload.temporary.investment;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.result.rmcdeluxe.entity.temporary.investment.PurchaseProcess;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchaseProcessDTO {

    Long id;

    String title;

    List<PurchaseProcessStepDTO> stepDTOS;

    Integer orderNum;
    public PurchaseProcessDTO(PurchaseProcess process, String lang){
        this.id=process.getId();
        this.orderNum= process.getOrderNum();
    }

}
