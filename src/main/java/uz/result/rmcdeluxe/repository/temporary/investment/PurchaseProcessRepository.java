package uz.result.rmcdeluxe.repository.temporary.investment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.result.rmcdeluxe.entity.temporary.investment.PurchaseProcess;

import java.util.Optional;

public interface PurchaseProcessRepository extends JpaRepository<PurchaseProcess ,Long> {

    @Query(value = "SELECT MAX(order_num) FROM purchase_process", nativeQuery = true)
    Optional<Integer> getMaxOrderNum();
}
