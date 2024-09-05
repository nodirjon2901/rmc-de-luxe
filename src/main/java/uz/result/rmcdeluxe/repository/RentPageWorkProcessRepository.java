package uz.result.rmcdeluxe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.RentPageWorkProcess;

@Repository
public interface RentPageWorkProcessRepository extends JpaRepository<RentPageWorkProcess, Long> {
}
