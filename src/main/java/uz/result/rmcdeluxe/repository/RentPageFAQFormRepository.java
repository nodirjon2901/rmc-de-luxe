package uz.result.rmcdeluxe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.result.rmcdeluxe.entity.RentPageFAQForm;

@Repository
public interface RentPageFAQFormRepository extends JpaRepository<RentPageFAQForm, Long> {
}
