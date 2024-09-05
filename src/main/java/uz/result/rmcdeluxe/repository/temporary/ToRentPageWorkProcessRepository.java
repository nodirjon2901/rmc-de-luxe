package uz.result.rmcdeluxe.repository.temporary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.temporary.ToRentPageWorkProcess;

@Repository
public interface ToRentPageWorkProcessRepository extends JpaRepository<ToRentPageWorkProcess, Long> {
}
