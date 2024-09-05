package uz.result.rmcdeluxe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.ToRentPageWorkProcess;

@Repository
public interface ToRentPageWorkProcessRepository extends JpaRepository<ToRentPageWorkProcess, Long> {
}
